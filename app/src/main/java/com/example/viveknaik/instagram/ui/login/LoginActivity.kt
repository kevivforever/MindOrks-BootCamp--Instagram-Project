package com.example.viveknaik.instagram.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.viveknaik.instagram.BootcampApplication
import com.example.viveknaik.instagram.R
import com.example.viveknaik.instagram.databinding.ActivityLoginBinding
import com.example.viveknaik.instagram.di.component.ActivityModule
import com.example.viveknaik.instagram.di.component.DaggerActivityComponent
import com.example.viveknaik.instagram.utils.display.Toaster
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LoginActivity"
    }

    @Inject
    lateinit var viewModel: LoginViewModel

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        setupObservers()
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
        viewModel.messageString.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.messageStringId.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

//        viewModel.dummyNavigation.observe(this, Observer<Event<Bundle>> {
//            it.getIfNotHandled()?.run {
//                startActivity(Intent(applicationContext, DummyActivity::class.java))
//                finish()
//            }
//        })
    }

    private fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    private fun showMessage(message: String) =  Toaster.show(application, message)
}