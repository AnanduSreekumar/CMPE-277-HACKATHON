package com.rfid.hack277.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.rfid.hack277.dao.AgricultureService
import com.rfid.hack277.dao.MacroeconomicService
import com.rfid.hack277.dao.TradeService
import com.rfid.hack277.model.DataViewModel

enum class Category {
    MACROECONOMIC, AGRICULTURE, TRADE
}

enum class Option {
    GDP, FDI_INFLOWS, FDI_OUTFLOWS, IMPORT_EXPORT_FLOW, CONTRIBUTION_TO_GDP,
    CREDIT, FERTILIZERS, FERTILIZERS_PRODUCTION, RESERVES, GNI, TOTAL_DEBT, GNI_CURRENT
}

data class LandingPageData(
    val category: Category,
    val categoryList: List<String>,
)

val categoryMap = mapOf(
    Category.MACROECONOMIC to mapOf(
        Option.GDP to "GDP (USD)",
        Option.FDI_INFLOWS to "FDI Inflows (USD)",
        Option.FDI_OUTFLOWS to "FDI Outflows (USD)",
        Option.IMPORT_EXPORT_FLOW to "Import/Export Flow",
    ),
    Category.AGRICULTURE to mapOf(
        Option.CONTRIBUTION_TO_GDP to "Contribution to GDP",
        Option.CREDIT to "Credit",
        Option.FERTILIZERS to "Fertilizers",
        Option.FERTILIZERS_PRODUCTION to "Fertilizer Production",
    ),
    Category.TRADE to mapOf(
        Option.RESERVES to "Reserves",
        Option.GNI to "GNI",
        Option.TOTAL_DEBT to "Total Debt",
        Option.GNI_CURRENT to "GNI (current US$)",
    ),
)

fun getServiceFromCategory(category: Category) =
    when (category) {
        Category.MACROECONOMIC -> MacroeconomicService::class.java
        Category.AGRICULTURE -> AgricultureService::class.java
        Category.TRADE -> TradeService::class.java
    }

@Composable
fun LandingPage(
    viewModel: DataViewModel,
    navController: NavController,
    category: Category,
    title: String,
    options: Map<Option, String>
) {
    val checkedStates = remember { mutableMapOf<Option, MutableState<Boolean>>() }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.padding(40.dp, 0.dp).weight(3f, true),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        ) {
            Text(
                text = title,
                fontSize = TextUnit(20f, TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            )
            options.entries.forEach { entry ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = checkedStates.getOrPut(entry.key) { mutableStateOf(false) }.value,
                        onCheckedChange = { checkedStates[entry.key]!!.value = it }
                    )
                    Text(text = entry.value)
                }
            }
        }

        Column(
            modifier = Modifier.padding(40.dp, 0.dp).weight(1f, true),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        ) {
            Button(
                onClick = {
                    navController.apply {
                        viewModel.chartArguments.clear()
                        checkedStates.entries.forEach { entry ->
                            if (entry.value.value)
                                viewModel.chartArguments.add(entry.key)
                        }
                        navigate(navController.currentBackStackEntry!!.destination.route + "-chart/$category")
                    }
                }
            ) {
                Text(text = "Show")
            }
        }
    }
}
