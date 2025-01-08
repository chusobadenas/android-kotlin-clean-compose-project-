package com.jesusbadenas.kotlin_clean_compose_project.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.jesusbadenas.kotlin_clean_compose_project.R

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(imageUrl: String?) {
        this.load(imageUrl) {
            crossfade(true)
            error(R.color.bg_light_grey)
            placeholder(R.color.bg_light_grey)
        }
}
