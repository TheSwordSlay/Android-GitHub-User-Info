package com.fiqri.githubusersinfo.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fiqri.githubusersinfo.R
import com.fiqri.githubusersinfo.databinding.ActivityFavListBinding
import com.fiqri.githubusersinfo.helper.FavViewModelFactory

class FavListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavListBinding
    private lateinit var adapter: FavAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle("Favourite Users")

        adapter = FavAdapter()
        val layoutManager = LinearLayoutManager(this)
        binding?.listFav?.layoutManager = LinearLayoutManager(this)
        binding?.listFav?.setHasFixedSize(true)
        binding?.listFav?.adapter = adapter
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listFav.addItemDecoration(itemDecoration)

        val favViewModel = obtainViewModel(this@FavListActivity)
        favViewModel.getAllNotes().observe(this) { favList ->
            if (favList != null) {
                adapter.setListFav(favList)
            }
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            R.id.theme -> {
                val changeTheme = Intent(this@FavListActivity, ThemeChangeActivity::class.java)
                startActivity(changeTheme)
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_for_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavViewModel {
        val factory = FavViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavViewModel::class.java)
    }
}