package com.jiujiu.task.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import com.jiujiu.task.R
import com.jiujiu.task.databinding.FragmentMainBinding
import com.jiujiu.task.ui.base.BaseFragment
import com.jiujiu.task.ui.widget.AutoCompletePlaceAdapter
import com.jiujiu.task.util.afterTextChanged
import com.jiujiu.task.util.onItemSelected
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.info
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject


class MainFragment : BaseFragment<FragmentMainBinding, MainFragViewModel>() {

    override val bindingVariableId: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_main

    override val viewModelType: Class<MainFragViewModel>
        get() = MainFragViewModel::class.java

    @Inject
    lateinit var currentLocale: Locale

    private lateinit var dateFormatter: DateTimeFormatter
    private lateinit var timeFormatter: DateTimeFormatter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", currentLocale)
        timeFormatter = DateTimeFormatter.ofPattern("HH:mm", currentLocale)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding.dateFormat = dateFormatter
        binding.timeFormat = timeFormatter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
        setupLiveData()
    }

    private fun setupLiveData() {

        this.viewModel.departureLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                val adapter = AutoCompletePlaceAdapter(et_from.context, it)
                et_from.setAdapter(adapter)
                et_from.showDropDown()
            }
        })

        this.viewModel.arrivalLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                val adapter = AutoCompletePlaceAdapter(et_to.context, it)
                et_to.setAdapter(adapter)
                et_to.showDropDown()
            }
        })

        this.viewModel.departureInValidEvent.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled().also {
                til_from.isErrorEnabled = it != null
                til_from.error = it
            }
        })

        this.viewModel.arrivalInValidEvent.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled().also {
                til_to.isErrorEnabled = it != null
                til_to.error = it
            }
        })

        this.viewModel.allTripsLiveData.observe(viewLifecycleOwner, Observer { trips ->
            info { "all trips. size = ${trips.size}" }
            trips.forEach { t -> info { t } }
            tv_size.text = "size=${trips.size}"
        })
    }

    private fun setupLayout() {

        et_from.afterTextChanged { text ->
            this.viewModel.departurePredictions(text)
            this.viewModel.updateDepartureInValid(text)
        }

        et_to.afterTextChanged { text ->
            this.viewModel.arrivalPredictions(text)
            this.viewModel.updateArrivalInValid(text)
        }

        et_from.onItemSelected { et_from.dismissDropDown() }
        et_to.onItemSelected { et_to.dismissDropDown() }


        val datePicker = DatePickerDialog.newInstance { view, year, monthOfYear, dayOfMonth ->
            val localDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
            if (view.tag == "from_date") {
                viewModel.updateTripDepartureDate(localDate)
            }
            if (view.tag == "to_date") {
                viewModel.updateTripArrivalDate(localDate)
            }
        }

        et_from_date.onClick { datePicker.show(childFragmentManager, "from_date") }

        et_to_date.onClick { datePicker.show(childFragmentManager, "to_date") }

        val timePicker = TimePickerDialog.newInstance({ view, hourOfDay, minute, second ->
            if (view.tag == "from_time") {
                viewModel.updateTripDepartureTime(LocalTime.of(hourOfDay, minute))
            }
            if (view.tag == "to_time") {
                viewModel.updateTripArrivalTime(LocalTime.of(hourOfDay, minute))
            }
        }, true)

        et_from_time.onClick { timePicker.show(childFragmentManager, "from_time") }

        et_to_time.onClick { timePicker.show(childFragmentManager, "to_time") }

    }

}
