package com.example.viveknaik.instagram.ui.dummy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.viveknaik.instagram.BootcampApplication
import com.example.viveknaik.instagram.R
import com.example.viveknaik.instagram.databinding.ActivityDummyBinding
import com.example.viveknaik.instagram.di.component.ActivityModule
import com.example.viveknaik.instagram.di.component.DaggerActivityComponent
import com.example.viveknaik.instagram.ui.dummies.DummiesFragment
import javax.inject.Inject

class DummyActivity : AppCompatActivity() {

    companion object {
        const val TAG = "DummyActivity"
    }

    @Inject
    lateinit var viewModel: DummyViewModel

    lateinit var binding: ActivityDummyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dummy)
        binding.setLifecycleOwner(this)
        setupObservers()
        setupView()
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

    private fun setupObservers() {}

    private fun setupView() {
        addDummiesFragment()
    }

    private fun addDummiesFragment() {
        supportFragmentManager.findFragmentByTag(DummiesFragment.TAG) ?: supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, DummiesFragment.newInstance(), DummiesFragment.TAG)
            .commitAllowingStateLoss()
    }
}