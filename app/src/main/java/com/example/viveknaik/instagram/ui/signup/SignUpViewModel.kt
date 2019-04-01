package com.example.viveknaik.instagram.ui.signup

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.viveknaik.instagram.R
import com.example.viveknaik.instagram.data.repository.UserRepository
import com.example.viveknaik.instagram.utils.common.*
import com.example.viveknaik.instagram.utils.network.NetworkHelper
import com.example.viveknaik.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.net.ssl.HttpsURLConnection


class SignUpViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val compositeDisposable: CompositeDisposable,
    private val networkHelper: NetworkHelper,
    private val userRepository: UserRepository
) : ViewModel() {

    private val validationsList: MutableLiveData<List<Validation>> = MutableLiveData()

    val messageStringId: MutableLiveData<Resource<Int>> = MutableLiveData()
    val messageString: MutableLiveData<Resource<String>> = MutableLiveData()
    val loginNavigation: MutableLiveData<Event<Bundle>> = MutableLiveData()

    val nameField: MutableLiveData<String> = MutableLiveData()
    val emailField: MutableLiveData<String> = MutableLiveData()
    val passwordField: MutableLiveData<String> = MutableLiveData()
    val SigningUp: MutableLiveData<Boolean> = MutableLiveData()

    val nameValidation: LiveData<Resource<Int>> =
        transformValidation(Validation.Field.NAME)

    val emailValidation: LiveData<Resource<Int>> =
        transformValidation(Validation.Field.EMAIL)

    val passwordValidation: LiveData<Resource<Int>> =
        transformValidation(Validation.Field.PASSWORD)

    private fun transformValidation(field: Validation.Field) =
        Transformations.map(validationsList) {
            it.find { validation -> validation.field == field }
                ?.run { return@run this.resource }
                ?: Resource.unknown()
        }

    /**
     * Get email and password field value
     * validate email & password field using validateLoginFiels & save in validations field
     * check if validations is not empty, email & password are not null
     * check if all the validations have success status
     * check if internet connection is available
     */
    fun onSignUp() {
        val name = nameField.value
        val email = emailField.value
        val password = passwordField.value

        val validations = Validator.validateSignUpFields(email, password, name)
        validationsList.postValue(validations)

        if (validations.isNotEmpty() && email != null && password != null && name != null) {
            val successValidation = validations.filter { it.resource.status == Status.SUCCESS }
            if (successValidation.size == validations.size && checkInternetConnectionWithMessage()) {
                SigningUp.postValue(true)
                compositeDisposable.addAll(
                    userRepository.doUserSignUp(email, password, name)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                            {
                                SigningUp.postValue(false)
                                loginNavigation.postValue(Event(Bundle()))
                            },
                            {
                                handleNetworkError(it)
                                SigningUp.postValue(false)
                            }
                        )
                )
            }
        }
    }

    fun onLogingIn () {
        loginNavigation.postValue(Event(Bundle()))
    }

    private fun checkInternetConnectionWithMessage(): Boolean =
        if (networkHelper.isNetworkConnected()) {
            true
        } else {
            messageStringId.postValue(Resource.error(R.string.network_connection_error))
            false
        }

    /**
     * set the error message using NetworkError class
     */
    private fun handleNetworkError(err: Throwable?) =
        err?.let {
            networkHelper.castToNetworkError(it).run {
                when (status) {
                    -1 ->
                        messageStringId.postValue(Resource.error(R.string.network_default_error))
                    0 ->
                        messageStringId.postValue(Resource.error(R.string.server_connection_error))
                    HttpsURLConnection.HTTP_INTERNAL_ERROR ->
                        messageStringId.postValue(Resource.error(R.string.network_internal_error))
                    HttpsURLConnection.HTTP_UNAVAILABLE ->
                        messageStringId.postValue(Resource.error(R.string.network_server_not_available))
                    else ->
                        messageString.postValue(Resource.error(message))
                }
            }
        }
}