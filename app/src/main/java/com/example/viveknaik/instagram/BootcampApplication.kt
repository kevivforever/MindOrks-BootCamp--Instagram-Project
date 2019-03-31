package com.example.viveknaik.instagram

import android.app.Application
import com.example.viveknaik.instagram.di.component.ApplicationComponent
import com.example.viveknaik.instagram.di.component.ApplicationModule
import com.example.viveknaik.instagram.di.component.DaggerApplicationComponent

class BootcampApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}