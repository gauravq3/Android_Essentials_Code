package com.master.androidessentials.mvvm.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.master.androidessentials.R
import com.master.androidessentials.databinding.ActivityHomeBinding
import com.master.androidessentials.mvvm.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    lateinit var navController: NavController

    override fun inflateBinding(): ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.more)
        binding.navigationView.setNavigationItemSelectedListener { item: MenuItem ->

            binding.drawerLayout.closeDrawer(GravityCompat.START);
            true
        }
      val configutation=  AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.seriesNetworkCall
            ), binding.drawerLayout
        )
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment?
        navController = navHostFragment?.navController!!
        setupActionBarWithNavController(navController, configutation)
        setupWithNavController(binding.navigationView   , navController)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout.openDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.seriesNetworkCall -> {
                navController.navigate(R.id.seriesNetworkCall)
                return true
            }

            R.id.parallelNetworkCall -> {

                return true
            }

            R.id.twoLongRunningTasks -> {

                return true
            }

            R.id.ignoreError -> {
                return true
            }
            R.id.lamdasAndHigherOrderFn -> {

                return true
            }


        }
        return super.onOptionsItemSelected(item)

    }


}