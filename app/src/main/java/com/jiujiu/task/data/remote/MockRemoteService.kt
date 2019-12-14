package com.jiujiu.task.data.remote

import com.jiujiu.task.data.model.Trip
import kotlinx.coroutines.delay
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import kotlin.random.Random

class MockRemoteService : RemoteService, AnkoLogger {

    override suspend fun saveTrip(trip: Trip): Result<Trip> {
        delay(2000)
        // todo: randomly produce failure or success
        return if (Random.nextBoolean()) {
            info { "successfully save trip object" }
            Result.success(trip)
        } else {
            Result.failure(IllegalAccessException("Unable to reach remote system"))
        }
    }


}