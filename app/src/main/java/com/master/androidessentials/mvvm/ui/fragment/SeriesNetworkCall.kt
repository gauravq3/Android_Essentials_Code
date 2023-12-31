package com.master.androidessentials.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.master.androidessentials.databinding.FragmentSeriesNetworkCallBinding
import com.master.androidessentials.mvvm.ui.base.BaseFragment
import com.master.androidessentials.mvvm.viewmodels.SeriesNetworkCallsViewModel
import com.master.androidessentials.networking.ApiResponse


class SeriesNetworkCall : BaseFragment<FragmentSeriesNetworkCallBinding>() {
    private val viewModel: SeriesNetworkCallsViewModel by activityViewModels()
    var data=StringBuilder()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSeriesNetworkCallBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUsersData().observe(this) {
            when (it) {
                is ApiResponse.Success -> {

                    data.append(it.data.joinToString("\n\n") { line-> line.toString() })
                    binding.tview.text = data
                    binding.progressBar.visibility = View.INVISIBLE
                }
                is ApiResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ApiResponse.Failure -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.tview.text = it.error
                }
            }
        }
    }


}