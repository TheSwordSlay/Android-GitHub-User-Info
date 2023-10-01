package com.fiqri.githubusersinfo.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.fiqri.githubusersinfo.R
import com.fiqri.githubusersinfo.ThemePreferences
import com.fiqri.githubusersinfo.ThemeViewModel
import com.fiqri.githubusersinfo.ViewModelFactory
import com.fiqri.githubusersinfo.dataStore
import com.google.android.material.switchmaterial.SwitchMaterial

class ThemeChangeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_change)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle("Change theme")

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = ThemePreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            ThemeViewModel::class.java
        )
        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_for_theme, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            R.id.fav -> {
                val fav = Intent(this@ThemeChangeActivity, FavListActivity::class.java)
                startActivity(fav)
            }
        }
        return true
    }
}