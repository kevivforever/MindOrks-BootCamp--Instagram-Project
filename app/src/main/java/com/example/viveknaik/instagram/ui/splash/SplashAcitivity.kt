package com.example.viveknaik.instagram.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.viveknaik.instagram.BootcampApplication
import com.example.viveknaik.instagram.R
import com.example.viveknaik.instagram.databinding.ActivitySplashBinding
import com.example.viveknaik.instagram.di.component.ActivityModule
import com.example.viveknaik.instagram.di.component.DaggerActivityComponent
import com.example.viveknaik.instagram.ui.dummy.DummyActivity
import com.example.viveknaik.instagram.ui.login.LoginActivity
import com.example.viveknaik.instagram.utils.common.Event
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    companion object {
        const val TAG = "SplashActivity"
    }

    @Inject
    lateinit var viewModel: SplashViewModel

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        setupObservers()
        viewModel.onViewCreated()
    }

    private fun injectDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as BootcampApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
    }

    private fun setupObservers() {
        // Event is used by the view model to tell the activity to launch another activity
        // view model also provided the Bundle in the event that is needed for the Activity
        viewModel.launchDummy.observe(this, Observer<Event<Bundle>> {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, DummyActivity::class.java))
            }
        })

        viewModel.launchLogin.observe(this, Observer<Event<Bundle>> {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            }
        })
    }
}