package com.master.androidessentials.mvvm.ui.activity

import android.R
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import com.master.androidessentials.databinding.ActivityHomeBinding
import com.master.androidessentials.mvvm.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override fun inflateBinding(): ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_input_add)
        binding.navigationView.setNavigationItemSelectedListener { item: MenuItem ->

            binding.drawerLayout.closeDrawer(GravityCompat.START);
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item)

    }


}