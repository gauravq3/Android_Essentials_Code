package com.master.androidessentials.mvvm.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.master.androidessentials.R
import com.master.androidessentials.databinding.ActivityHomeBinding
import com.master.androidessentials.mvvm.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override fun inflateBinding(): ActivityHomeBinding {
       return ActivityHomeBinding.inflate(layoutInflater)
    }
}