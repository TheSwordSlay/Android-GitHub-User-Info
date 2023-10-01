package com.fiqri.githubusersinfo.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fiqri.githubusersinfo.R
import com.fiqri.githubusersinfo.data.response.ItemsItem
import com.fiqri.githubusersinfo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)

        mainViewModel.listDataUsers.observe(this) { listDataUsers ->
            setUserData(listDataUsers)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.listUsers.setLayoutManager(layoutManager)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listUsers.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    mainViewModel.getUserList(searchView.text.toString())
                    false
                }
        }
    }

    private fun setUserData(usersData: List<ItemsItem>) {
        val adapter = UserListAdapter(object : UserListAdapter.OnAdapterListener{
            override fun onClick(data: ItemsItem) {
                val moveToDetail = Intent(this@MainActivity, UserDetailActivity::class.java)
                moveToDetail.putExtra("username", data.login)
                startActivity(moveToDetail)
            }
        })
        adapter.submitList(usersData)
        binding.listUsers.adapter = adapter

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            R.id.theme -> {
                val changeTheme = Intent(this@MainActivity, ThemeChangeActivity::class.java)
                startActivity(changeTheme)
            }
            R.id.fav -> {
                val fav = Intent(this@MainActivity, FavListActivity::class.java)
                startActivity(fav)
            }
        }
        return true
    }

}