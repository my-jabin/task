package com.jiujiu.task.di.scope

import androidx.work.ListenableWorker
import dagger.MapKey
import kotlin.reflect.KClass

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class WorkerKey(val value: KClass<out ListenableWorker>)
