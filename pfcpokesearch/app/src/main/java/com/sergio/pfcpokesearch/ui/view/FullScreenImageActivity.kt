package com.sergio.pfcpokesearch.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sergio.pfcpokesearch.databinding.FullscreenImageActivityBinding
import com.squareup.picasso.Picasso

class FullScreenImageActivity: AppCompatActivity() {
    private lateinit var binding: FullscreenImageActivityBinding
    companion object {
        const val EXTRA_IMAGE = "extra_image"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FullscreenImageActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val image: String = intent.getStringExtra(FullScreenImageActivity.EXTRA_IMAGE).orEmpty()

        Picasso.get().load(image).into(binding.pokemonImage)
    }
}