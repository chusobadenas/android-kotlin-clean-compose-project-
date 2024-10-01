package com.jesusbadenas.kotlin_clean_compose_project.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jesusbadenas.kotlin_clean_compose_project.R
import com.jesusbadenas.kotlin_clean_compose_project.di.GlideApp

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(imageUrl: String?) {
    GlideApp.with(context)
        .load(imageUrl)
        .centerCrop()
        .placeholder(R.color.bg_light_grey)
        .error(R.color.bg_light_grey)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
