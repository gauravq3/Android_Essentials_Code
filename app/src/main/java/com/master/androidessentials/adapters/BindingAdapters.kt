package com.master.androidessentials.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.RequestManager

object BindingAdapters {


    @JvmStatic
    @BindingAdapter("android:txt")
    fun setTxt(view: TextView, txt: String?) {
        view.text = txt ?: ""

    }

    @JvmStatic
    @BindingAdapter("imageUrl", "requestManager")
    fun setImageUrl(view: ImageView, imageUrl: String?,requestManager: RequestManager) {
        if (!imageUrl.isNullOrEmpty()) {
            requestManager.load(imageUrl).into(view)
        }

    }

}