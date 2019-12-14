package com.jiujiu.task.di.builder

import com.jiujiu.task.di.module.MainActivityModule
import com.jiujiu.task.ui.main.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindsMainActivity(): MainActivity

}
