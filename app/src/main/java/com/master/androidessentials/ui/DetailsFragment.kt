package com.master.androidessentials.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.master.androidessentials.databinding.FragmentDetailsBinding
import com.master.androidessentials.networking.ApiResponse
import com.master.androidessentials.ui.base.BaseFragment
import com.master.androidessentials.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {
    private val viewmodel: SharedViewModel by activityViewModels()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDetailsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val secondFragment = HomeFragment()


        viewmodel.allPosts.observe(viewLifecycleOwner) { result ->


            when (result) {
                is ApiResponse.Success -> {
                   binding.text1.text=result.data.total.toString()
                }
                is ApiResponse.Failure -> {

                }
                is ApiResponse.Loading -> {

                }
            }
        }
    }

}