package com.example.viveknaik.instagram.ui.splash

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.viveknaik.instagram.data.repository.UserRepository
import com.example.viveknaik.instagram.utils.common.Event
import com.example.viveknaik.instagram.utils.network.NetworkHelper
import com.example.viveknaik.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class SplashViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val compositeDisposable: CompositeDisposable,
    private val networkHelper: NetworkHelper,
    private val userRepository: UserRepository
) : ViewModel() {

    // Event is used by the view model to tell the activity to launch another Activity
    // view model also provided the Bundle in the event that is needed for the Activity
    val launchDummy: MutableLiveData<Event<Bundle>> = MutableLiveData()
    val launchLogin: MutableLiveData<Event<Bundle>> = MutableLiveData()



    fun onViewCreated() {
        // Empty Bundle passed to Activity in Event that is needed by the other Activity
        if (userRepository.getCurrentUser() != null)
            launchDummy.postValue(Event(Bundle()))
        else
            launchLogin.postValue(Event(Bundle()))
    }
}