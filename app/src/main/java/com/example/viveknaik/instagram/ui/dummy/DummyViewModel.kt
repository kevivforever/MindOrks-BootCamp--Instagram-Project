package com.example.viveknaik.instagram.ui.dummy

import androidx.lifecycle.ViewModel
import com.example.viveknaik.instagram.data.repository.DummyRepository
import com.example.viveknaik.instagram.utils.network.NetworkHelper
import com.example.viveknaik.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable


class DummyViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val compositeDisposable: CompositeDisposable,
    private val networkHelper: NetworkHelper,
    private val dummyRepository: DummyRepository
) : ViewModel() {

    fun onViewCreated() {}
}