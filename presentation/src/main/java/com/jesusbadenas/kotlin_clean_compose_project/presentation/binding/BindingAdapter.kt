package com.jesusbadenas.kotlin_clean_compose_project.presentation.binding

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import com.jesusbadenas.kotlin_clean_compose_project.presentation.R

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(imageUrl: String?) {
        this.load(imageUrl) {
            crossfade(true)
            error(R.color.error)
            placeholder(R.color.error)
        }
}

@BindingAdapter("isVisible")
fun View.setVisibility(isVisible: Boolean) {
    this.isVisible = isVisible
}
