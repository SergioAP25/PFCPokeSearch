package com.sergio.pfcpokesearch.ui.view

import androidx.appcompat.app.AppCompatActivity
import com.sergio.pfcpokesearch.databinding.FullscreenImageActivityBinding

class FullScreenImageActivity: AppCompatActivity() {
    private lateinit var binding: FullscreenImageActivityBinding
    companion object {
        const val EXTRA_IMAGE = "extra_image"
    }
}