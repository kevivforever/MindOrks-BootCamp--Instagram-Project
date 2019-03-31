package com.example.viveknaik.instagram.di.component

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viveknaik.instagram.utils.rx.RxSchedulerProvider
import com.example.viveknaik.instagram.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class FragmentModule(private val fragment: Fragment) {

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideFragment(): Fragment = fragment

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(fragment.context)

//    @Provides
//    fun provideDummiesViewModel(
//        schedulerProvider: SchedulerProvider,
//        compositeDisposable: CompositeDisposable,
//        networkHelper: NetworkHelper,
//        dummyRepository: DummyRepository
//    ): DummiesViewModel =
//        ViewModelProviders.of(fragment,
//            ViewModelProviderFactory(DummiesViewModel::class) {
//                DummiesViewModel(schedulerProvider, compositeDisposable, networkHelper, dummyRepository)
//            }
//        ).get(DummiesViewModel::class.java)
//
//    @Provides
//    fun provideDummiesAdapter() = DummiesAdapter(ArrayList())
}