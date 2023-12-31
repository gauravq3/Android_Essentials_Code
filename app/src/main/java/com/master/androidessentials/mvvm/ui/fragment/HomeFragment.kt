package com.master.androidessentials.mvvm.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.master.androidessentials.R
import com.master.androidessentials.adapters.GenericAdapter
import com.master.androidessentials.databinding.FragmentHomeBinding
import com.master.androidessentials.databinding.ItemViewBinding
import com.master.androidessentials.mvvm.models.userslist.User
import com.master.androidessentials.mvvm.ui.base.BaseFragment
import com.master.androidessentials.mvvm.viewmodels.SharedViewModel
import com.master.androidessentials.networking.ApiResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewmodel: SharedViewModel by activityViewModels()
    lateinit var mAdapter: GenericAdapter<User, ItemViewBinding>
    lateinit var postItems: List<User>

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postItems = emptyList<User>()

        mAdapter = GenericAdapter(postItems,
            { inflater -> ItemViewBinding.inflate(inflater) },
            { binding, item ->
                with(binding) {
                    user = item
                    executePendingBindings()
                }
            },
            areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
            areContentsTheSame = { oldItem, newItem -> oldItem == newItem }
        )
        binding.rviewPosts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            adapter = mAdapter

        }
        CoroutineScope(Dispatchers.Main).launch {
            viewmodel.usersList.collect { result ->
                when (result) {
                    is ApiResponse.Success -> {
                        binding.progressBar.visibility = View.GONE
                        mAdapter.updateList1(result.data)

                        Handler().postDelayed({
                            var d=  (result.data as ArrayList<User>).removeAt(1)
                            mAdapter.updateList1(result.data)
                        }, 8000)


                    }
                    is ApiResponse.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ApiResponse.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
        mAdapter.setItemClickListener { item ->

            viewmodel.setUserDetails(item)
            findNavController().navigate(R.id.action_homeFragment_to_DetailsFragment)

        }

        //api call to fetch data
        viewmodel.fetchAllPosts()


    }

}