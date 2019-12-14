package com.jiujiu.task.di.module

import androidx.work.WorkerFactory

import com.jiujiu.task.di.MyWorkerFactory
import com.jiujiu.task.di.scope.WorkerKey
import com.jiujiu.task.worker.CustomWorkerFactory
import com.jiujiu.task.worker.PrePopulateDataWorker
import com.jiujiu.task.worker.RemoteSyncWorker

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WorkerBindingModule {

    @Binds
    @IntoMap
    @WorkerKey(PrePopulateDataWorker::class)
    abstract fun bindsPrePopulateDataWorker(factory: PrePopulateDataWorker.Factory): CustomWorkerFactory


    @Binds
    @IntoMap
    @WorkerKey(RemoteSyncWorker::class)
    abstract fun bindsRemoteSyncWorker(factory: RemoteSyncWorker.Factory): CustomWorkerFactory

    @Binds
    abstract fun bindsWorkerFactory(factory: MyWorkerFactory): WorkerFactory
}
