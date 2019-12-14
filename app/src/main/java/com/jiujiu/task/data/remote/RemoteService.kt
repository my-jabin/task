package com.jiujiu.task.data.remote

import com.jiujiu.task.data.model.Trip

interface RemoteService {

    suspend fun saveTrip(trip: Trip): Result<Trip>

}
