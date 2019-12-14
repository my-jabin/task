package com.jiujiu.task.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jiujiu.task.data.remote.RemoteService
import com.jiujiu.task.data.repository.TripRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class RemoteSyncWorker @AssistedInject constructor(
        @Assisted context: Context,
        @Assisted workerParams: WorkerParameters,
        private val tripRepository: TripRepository,
        private val remoteService: RemoteService
) : CoroutineWorker(context, workerParams), AnkoLogger {

    override val coroutineContext = Dispatchers.IO

    override suspend fun doWork(): Result {
        return coroutineScope {
            info { "starting work: sync data to remote service" }
            val trips = tripRepository.loadUnSyncTrip()
            if (trips.isNotEmpty()) {
                info { "not synchronized data: size = ${trips.size}" }
                trips.forEach { trip ->
                    val result = remoteService.saveTrip(trip)
                    result.getOrNull()?.apply {
                        inRemote = true
                        tripRepository.updateLocally(this)
                    }
                }
            }
            Result.success()
        }
    }

    @AssistedInject.Factory
    interface Factory : CustomWorkerFactory
}
