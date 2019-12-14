package com.jiujiu.task.di.module

import com.jiujiu.task.di.module.subModules.MainFragmentModule
import com.jiujiu.task.ui.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun bindsMainFragment(): MainFragment


}
