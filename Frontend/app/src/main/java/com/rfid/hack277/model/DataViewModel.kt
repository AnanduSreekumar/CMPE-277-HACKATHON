package com.rfid.hack277.model

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.rfid.hack277.dao.AgricultureService
import com.rfid.hack277.dao.MacroeconomicService
import com.rfid.hack277.dao.TradeService
import com.rfid.hack277.ui.Option
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataViewModel: ViewModel() {
    val isPersonaElevated = mutableStateOf(false)
    val country = mutableStateOf("India")

    val showProgress = mutableStateOf(false)
    val mustRefresh = mutableIntStateOf(0)

    var chartArguments = mutableStateListOf<Option>()
}
