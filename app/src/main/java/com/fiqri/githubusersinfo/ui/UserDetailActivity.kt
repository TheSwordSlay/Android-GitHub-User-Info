package com.fiqri.githubusersinfo.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.fiqri.githubusersinfo.R
import com.fiqri.githubusersinfo.database.Fav
import com.fiqri.githubusersinfo.databinding.ActivityUserDetailBinding
import com.fiqri.githubusersinfo.helper.FavViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var favAddDelete: FavAddDeleteViewModel
    private lateinit var favViewModel: FavViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("User's info")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val userDetailModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserDetailModel::class.java)

        binding.fabDelete.setVisibility(View.GONE)
        favAddDelete = obtainViewModel(this@UserDetailActivity)

        favViewModel = obtainViewModel2(this@UserDetailActivity)
        favViewModel.getAllNotes().observe(this) { favList ->
            if (favList != null) {
                for (prop in favList) {
                    if(prop.username!! == intent.getStringExtra("username")!!) {
                        binding.fabAdd.setVisibility(View.GONE)
                        binding.fabDelete.setVisibility(View.VISIBLE)
                    }
                }
            }
        }


        var uname = ""
        var avatarUrl = ""
        userDetailModel.username.observe(this) { username ->
            binding.userName.text = username
            uname = username
        }

        userDetailModel.nama.observe(this) { nama ->
            binding.name.text = nama
        }

        userDetailModel.avatar_url.observe(this) { avatar_url ->
            Glide.with(this)
                .load(avatar_url)
                .into(binding.profPic)
            avatarUrl = avatar_url
        }

        userDetailModel.followers.observe(this) { followers ->
            binding.followers.text = "${followers.toString()} followers"
        }

        userDetailModel.followings.observe(this) { followings ->
            binding.following.text = "${followings.toString()} following"
        }

        userDetailModel.isLoading.observe(this) {
            showLoading(it)
        }

        userDetailModel.getDetailUser(intent.getStringExtra("username")!!)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, intent.getStringExtra("username")!!)
        val viewPager: ViewPager2 = findViewById(R.id.viewpager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        var fav = Fav()

        binding?.fabAdd?.setOnClickListener {
            fav.let { fav ->
                fav?.username = uname
                fav?.avatar_url = avatarUrl
            }
            favAddDelete.insert(fav)
            var username = intent.getStringExtra("username")!!
            showToast("User $username has been added to your favourite users list")
            binding.fabDelete.setVisibility(View.VISIBLE)
        }

        favViewModel.getFavUsername(intent.getStringExtra("username")!!).observe(this) {username ->
            binding?.fabDelete?.setOnClickListener {
                favAddDelete.delete(username)
                var unames = intent.getStringExtra("username")!!
                showToast("User $unames has been removed from your favourite users list")
                binding.fabDelete.setVisibility(View.GONE)
                binding.fabAdd.setVisibility(View.VISIBLE)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_for_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            R.id.theme -> {
                val changeTheme = Intent(this@UserDetailActivity, ThemeChangeActivity::class.java)
                startActivity(changeTheme)
            }
        }
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.view2.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.name.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.userName.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.followers.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.following.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.viewpager.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavAddDeleteViewModel {
        val factory = FavViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavAddDeleteViewModel::class.java)
    }

    private fun obtainViewModel2(activity: AppCompatActivity): FavViewModel {
        val factory = FavViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavViewModel::class.java)
    }
}