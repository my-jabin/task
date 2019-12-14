package com.jiujiu.task.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.jiujiu.task.di.ViewModelFactory
import com.jiujiu.task.di.scope.ViewModelKey
import com.jiujiu.task.ui.main.MainActivityViewModel
import com.jiujiu.task.ui.main.MainFragViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindsMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainFragViewModel::class)
    abstract fun bindsMainFragViewModel(viewModel: MainFragViewModel): ViewModel

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
