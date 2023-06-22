package com.master.androidessentials.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.master.androidessentials.R
import com.master.androidessentials.databinding.FragmentDetailsBinding
import com.master.androidessentials.networking.ApiResponse
import com.master.androidessentials.mvvm.ui.base.BaseFragment
import com.master.androidessentials.mvvm.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {
    private val viewmodel: SharedViewModel by activityViewModels()

    @Inject
    lateinit var glide: RequestManager
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDetailsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewmodel.user.observe(viewLifecycleOwner) { result ->

            with(binding)
            {
                //set the data to all the views
                tvName.text = "Name: " + result.firstName + " " + result.lastName
                tvEmail.text = "Email: " + result.email
                tvCompany.text = "Company: " + result.company.name
                tvPhone.text = "Contact: " + result.phone
                loadImage(result.image, userImage)

            }
        }

    }

    private fun loadImage(imageUrl: String, imageView: ImageView) {
        glide.load(imageUrl)
            .apply(RequestOptions().placeholder(R.drawable.ic_launcher_background)) // Optional: Set a placeholder image
            .into(imageView)
    }

}