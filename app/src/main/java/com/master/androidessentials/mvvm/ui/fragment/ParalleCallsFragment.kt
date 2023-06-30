package com.master.androidessentials.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.master.androidessentials.databinding.FragmentParalleCallsBinding
import com.master.androidessentials.mvvm.ui.base.BaseFragment
import com.master.androidessentials.mvvm.viewmodels.ParallelCallsViewmodel
import com.master.androidessentials.networking.ApiResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParallelCallsFragment : BaseFragment<FragmentParalleCallsBinding>() {
    val viewmodels :ParallelCallsViewmodel by activityViewModels()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentParalleCallsBinding {
        return  FragmentParalleCallsBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewmodels.getData().observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Loading -> {
                    //show loading
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ApiResponse.Success -> {
                    //hide loading
                    //show data
                    binding.progressBar.visibility = View.GONE
                    binding.tview.text = it.data.toString()

                }
                is ApiResponse.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    //hide loading
                    //show error
                }
            }
        }
    }


}