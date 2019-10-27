package com.swivelgroup.newsticker.utils

import androidx.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.swivelgroup.newsticker.R

object ImageViewBindingUtil {

    @JvmStatic
    @BindingAdapter("app:setImage")
    fun setImage(view: ImageView, url: String?) {
        Glide.with(view).load(url).placeholder(R.drawable.sample_image).into(view)
    }
}