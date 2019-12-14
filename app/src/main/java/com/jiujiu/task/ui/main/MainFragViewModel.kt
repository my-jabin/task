package com.jiujiu.task.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.jiujiu.task.data.DataManager
import com.jiujiu.task.data.model.Trip
import com.jiujiu.task.ui.base.BaseViewModel
import com.jiujiu.task.util.Event
import com.jiujiu.task.util.PlaceValidator
import com.jiujiu.task.util.notifyObservers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class MainFragViewModel @Inject constructor(
        dataManager: DataManager,
        private val placesClient: PlacesClient
) : BaseViewModel(dataManager) {

    private val _departureLiveData = MutableLiveData<List<String>>()
    val departureLiveData: LiveData<List<String>>
        get() = _departureLiveData

    private val _arrivalLiveData = MutableLiveData<List<String>>()
    val arrivalLiveData: LiveData<List<String>>
        get() = _arrivalLiveData

    private lateinit var queryText: String

    val trip: MutableLiveData<Trip> = MutableLiveData(Trip(from = "Walldorf, Germany", to = ""))

    private val _departureInValidEvent = MutableLiveData<Event<String?>>()
    val departureInValidEvent: LiveData<Event<String?>>
        get() = _departureInValidEvent

    private val _arrivalInValidEvent = MutableLiveData<Event<String?>>()
    val arrivalInValidEvent: LiveData<Event<String?>>
        get() = _arrivalInValidEvent

    val allTripsLiveData = dataManager.loadAllTrips()

    fun departurePredictions(text: String) {
        queryText = text
        viewModelScope.launch {
            delay(2000)
            if (queryText == text) {
                info { "current Time ${LocalDateTime.now()}" }
                makePredictions(text, _departureLiveData)
            }
        }
    }

    fun arrivalPredictions(text: String) {
        queryText = text
        viewModelScope.launch {
            delay(2000)
            if (queryText == text) {
                info { "current Time ${LocalDateTime.now()}" }
                makePredictions(text, _arrivalLiveData)
            }
        }
    }

    private fun makePredictions(text: String, liveData: MutableLiveData<List<String>>) {
        val token = AutocompleteSessionToken.newInstance()
        val request = FindAutocompletePredictionsRequest.builder()
                .setCountry("de")
                .setQuery(text)
                .setSessionToken(token)
                .build()

        placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener { response ->
                    val list = mutableListOf<String>()
                    response.autocompletePredictions.forEach {
                        list.add(it.getFullText(null).toString())
                    }
                    liveData.postValue(list)
                }.addOnFailureListener { exception ->
                    error {
                        val message = (exception as ApiException).message
                        "place not found : $message"
                    }
                    liveData.postValue(emptyList())
                }
    }

    fun saveTrip() {
        val tripObj = trip.value!!
        var valid = updateDepartureInValid(tripObj.from)
        valid = updateArrivalInValid(tripObj.to)
        if (valid) {
            info { trip.value }
            viewModelScope.launch {
                dataManager.saveTrip(tripObj)
            }
            // todo: send an event to observer that object has been successfully saved
        }
    }

    fun updateTripDepartureDate(localDate: LocalDate) {
        trip.value?.departureDate = localDate
        trip.notifyObservers()
    }

    fun updateTripArrivalDate(localDate: LocalDate) {
        trip.value?.arrivalDate = localDate
        trip.notifyObservers()
    }

    fun updateTripDepartureTime(time: LocalTime) {
        trip.value?.departureTime = time
        trip.notifyObservers()
    }

    fun updateTripArrivalTime(time: LocalTime) {
        trip.value?.arrivalTime = time
        trip.notifyObservers()
    }

    fun updateArrivalInValid(text: String): Boolean {
        return PlaceValidator.isValid(text).also {
            _arrivalInValidEvent.value = Event(if (it) null else "to is empty")
        }
    }

    fun updateDepartureInValid(text: String): Boolean {
        return PlaceValidator.isValid(text).also {
            _departureInValidEvent.value = Event(if (it) null else "from is empty")
        }
    }
}
