package com.example.viveknaik.instagram.di.component

import com.example.viveknaik.instagram.di.FragmentScope
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {

//    fun inject(fragment: DummiesFragment)
}