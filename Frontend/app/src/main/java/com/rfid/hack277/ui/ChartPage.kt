package com.rfid.hack277.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.chart.scale.AutoScaleUp
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.rfid.hack277.charting.rememberMarker
import com.rfid.hack277.dao.AgricultureService
import com.rfid.hack277.dao.MacroeconomicService
import com.rfid.hack277.dao.TradeService
import com.rfid.hack277.model.DataViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar

@ExperimentalMaterial3Api
@Composable
fun ChartPage(
    viewModel: DataViewModel,
    category: Category
) {
    var startYear by remember { mutableStateOf("1900") }
    var endYear by remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR).toString()) }
    var mustAnnotate by remember { mutableStateOf(false) }
    var chartEntryModel by remember { mutableStateOf(entryModelOf(0)) }
    val composableScope = rememberCoroutineScope()

    val executeApiCalls: () -> Unit = {
        val series: MutableList<List<FloatEntry>> = mutableListOf()

        viewModel.chartArguments.forEach { selectedOption ->
            Log.e("SELECTED", selectedOption.name)

            viewModel.showProgress.value = true
            val response = Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.0.27:8080/api/")
                .build()
                .create(getServiceFromCategory(category))
                .get(
                    viewModel.country.value,
                    startYear,
                    endYear,
                    selectedOption.name
                ).execute().body()
            viewModel.showProgress.value = false

            series.add(entriesOf(
                *response!!
                    .filter { res ->
                        res.value != null
                    }
                    .map { res ->
                        Pair(res.year.toInt(), res.value.toFloat())
                    }
                    .toTypedArray()
            ))
        }

        chartEntryModel = entryModelOf(*series.toTypedArray())
    }

    LaunchedEffect(viewModel.mustRefresh.value) {
        composableScope.launch(Dispatchers.IO) {
            executeApiCalls()
        }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        Row(
            modifier = Modifier.weight(1f, true),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f, true),
                label = { Text("Start Year") },
                value = startYear,
                onValueChange = { startYear = it }
            )

            OutlinedTextField(
                modifier = Modifier.weight(1f, true),
                label = { Text("End Year") },
                value = endYear,
                onValueChange = { endYear = it }
            )
        }

        Column(
            modifier = Modifier.weight(3f, true),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            Chart(
                modifier = Modifier.fillMaxSize(),
                chart = lineChart(),
                model = chartEntryModel,
                startAxis =
                if (mustAnnotate && viewModel.isPersonaElevated.value)
                    rememberStartAxis()
                else null,
                bottomAxis =
                if (mustAnnotate && viewModel.isPersonaElevated.value)
                    rememberBottomAxis()
                else null,
                marker =
                if (mustAnnotate && viewModel.isPersonaElevated.value)
                    rememberMarker()
                else null
            )
        }

        Column(
            modifier = Modifier.weight(1f, true),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            Button(
                onClick = {
                    mustAnnotate = true
                },
                enabled = viewModel.isPersonaElevated.value
            ) {
                Text(text = "Annotate")
            }
        }
    }
}
