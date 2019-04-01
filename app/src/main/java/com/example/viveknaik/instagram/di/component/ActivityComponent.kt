package com.example.viveknaik.instagram.di.component

import com.example.viveknaik.instagram.di.ActivityScope
import com.example.viveknaik.instagram.ui.dummy.DummyActivity
import com.example.viveknaik.instagram.ui.login.LoginActivity
import com.example.viveknaik.instagram.ui.signup.SignUpActivity
import com.example.viveknaik.instagram.ui.splash.SplashActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {

    fun inject(activity: SplashActivity)

    fun inject(activity: DummyActivity)

    fun inject(activity: LoginActivity)

    fun inject(activity: SignUpActivity)

}