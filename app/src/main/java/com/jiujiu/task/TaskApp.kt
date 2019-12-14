package com.jiujiu.task

import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.WorkerFactory
import com.google.android.libraries.places.api.Places
import com.jiujiu.task.data.local.AppDatabase
import com.jiujiu.task.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class TaskApp : DaggerApplication() {

    @Inject
    lateinit var myWorkerFactory: WorkerFactory

    @Inject
    lateinit var mDatabase: AppDatabase

    @Inject
    lateinit var workRequest: WorkRequest

    override fun onCreate() {
        super.onCreate()

        // Initialize the Google Places SDK
        Places.initialize(applicationContext, BuildConfig.PlaceAPI)

        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(myWorkerFactory).build())

        WorkManager.getInstance(this).enqueue(workRequest)
    }

    override fun applicationInjector(): AndroidInjector<out TaskApp> {
        return DaggerAppComponent.builder().create(this)
    }
}
