package com.master.androidessentials.mvvm.ui.activity

import com.master.androidessentials.databinding.ActivityHomeBinding
import com.master.androidessentials.mvvm.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override fun inflateBinding(): ActivityHomeBinding =ActivityHomeBinding.inflate(layoutInflater)

}