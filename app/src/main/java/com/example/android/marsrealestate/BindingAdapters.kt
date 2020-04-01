/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.marsrealestate.network.MarsProperty
import com.example.android.marsrealestate.overview.MarsApiStatus
import com.example.android.marsrealestate.overview.PhotoGridAdapter

@BindingAdapter("marsProperties")
fun RecyclerView.setMarsProperties(properties: List<MarsProperty>?) {
    val adapter = this.adapter as PhotoGridAdapter
    adapter.submitList(properties)
}

@BindingAdapter("imageUrl")
fun ImageView.loadImageUrl(url: String?) {
    url?.let {
        val uri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(this.context)
                .load(uri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(this)
    }
}

@BindingAdapter("dataStatus")
fun ImageView.setDataStatus(status: MarsApiStatus?) {
    this.apply {
        status?.let {
            when (status) {
                MarsApiStatus.LOADING -> {
                    visibility = View.VISIBLE
                    setImageResource(R.drawable.loading_animation)
                }
                MarsApiStatus.ERROR -> {
                    visibility = View.VISIBLE
                    setImageResource(R.drawable.ic_connection_error)
                }
                MarsApiStatus.DONE -> {
                    visibility = View.GONE
                }
            }
        }
    }
}