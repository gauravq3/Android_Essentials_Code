package com.master.androidessentials.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.master.androidessentials.R
import com.master.androidessentials.databinding.FragmentSeriesNetworkCallBinding
import com.master.androidessentials.mvvm.ui.base.BaseFragment
import com.master.androidessentials.mvvm.viewmodels.SeriesNetworkCallsViewModel
import com.master.androidessentials.mvvm.viewmodels.SharedViewModel
import com.master.androidessentials.networking.ApiResponse


class SeriesNetworkCall : BaseFragment<FragmentSeriesNetworkCallBinding>() {
    private val viewModel: SeriesNetworkCallsViewModel by activityViewModels()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSeriesNetworkCallBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUiState().observe(this) {
            when (it) {
                is ApiResponse.Success -> {

                }
                is ApiResponse.Loading -> {

                }
                is ApiResponse.Failure -> {
                    //Handle Error

                }
            }
        }
    }




}