package com.master.androidessentials.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.master.androidessentials.R
import com.master.androidessentials.databinding.FragmentSeriesNetworkCallBinding
import com.master.androidessentials.mvvm.ui.base.BaseFragment


class SeriesNetworkCall : BaseFragment<FragmentSeriesNetworkCallBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSeriesNetworkCallBinding.inflate(inflater, container, false)


}