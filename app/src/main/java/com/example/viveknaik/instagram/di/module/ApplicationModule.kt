package com.example.viveknaik.instagram.di.component

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.viveknaik.instagram.BootcampApplication
import com.example.viveknaik.instagram.BuildConfig
import com.example.viveknaik.instagram.data.remote.NetworkService
import com.example.viveknaik.instagram.data.remote.Networking
import com.example.viveknaik.instagram.di.ApplicationContext
import com.example.viveknaik.instagram.utils.network.NetworkHelper
import com.example.viveknaik.instagram.utils.rx.RxSchedulerProvider
import com.example.viveknaik.instagram.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: BootcampApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences =
        application.getSharedPreferences("bootcamp-instagram-project-prefs", Context.MODE_PRIVATE)

//    @Provides
//    @Singleton
//    fun provideDatabaseService(): DatabaseService =
//        Room.databaseBuilder(
//            application, DatabaseService::class.java,
//            "bootcamp-instagram-project-db"
//        ).build()

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService =
        Networking.create(
            BuildConfig.API_KEY,
            BuildConfig.BASE_URL,
            application.cacheDir,
            10 * 1024 * 1024 // 10MB
        )

    @Singleton
    @Provides
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)
}