package com.sergio.pfcpokesearch

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Anotación necesaria junto con esta clase para indicar a la librería de DaggerHilt que esta app utiliza Hilt y poder usar sus funcionalidades
@HiltAndroidApp
class PFCPokeSearch: Application() {
}