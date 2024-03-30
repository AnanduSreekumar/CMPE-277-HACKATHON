package com.rfid.hack277

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlagCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.rfid.hack277.model.DataViewModel
import com.rfid.hack277.nav.Chart
import com.rfid.hack277.nav.Fragment
import com.rfid.hack277.nav.Page
import com.rfid.hack277.nav.getOptionKeySetForCategory
import com.rfid.hack277.ui.Category
import com.rfid.hack277.ui.ChartPage
import com.rfid.hack277.ui.ChatGpt
import com.rfid.hack277.ui.CorePage
import com.rfid.hack277.ui.LandingPage
import com.rfid.hack277.ui.categoryMap
import com.rfid.hack277.ui.theme.Hack277Theme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    val dataViewModel: DataViewModel by viewModels()

    private val chatUrl = "https://chat.openai.com/"

    private val navFragments = listOf(
        Fragment.Macroeconomic,
        Fragment.Agriculture,
        Fragment.Trade,
        Fragment.ChatGpt,
    )

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Hack277Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val appName = stringResource(id = R.string.app_name)

                    val navController = rememberNavController()
                    val currentBackStackEntry by navController.currentBackStackEntryAsState()
                    var pageTitle by remember { mutableStateOf("") }

                    val webViewState = rememberWebViewState("https://chat.openai.com")

                    var showCountrySelectionAlertDialog by remember { mutableStateOf(false) }

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TopAppBar(
                            title = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.logo_vector),
                                        contentDescription = ""
                                    )

                                    Column {
                                        Text(
                                            text = pageTitle.ifEmpty { appName },
                                            color = Color.White
                                        )

                                        if (dataViewModel.country.value.isNotEmpty())
                                            Text(
                                                text = dataViewModel.country.value,
                                                color = Color.White,
                                                fontSize = TextUnit(12f, TextUnitType.Sp)
                                            )
                                    }
                                }
                            },
                            backgroundColor = MaterialTheme.colorScheme.primary,
                            actions = {
                                IconButton(onClick = { showCountrySelectionAlertDialog = true }) {
                                    Icon(
                                        imageVector = Icons.Filled.FlagCircle,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }

                                if (currentBackStackEntry?.destination?.route?.indexOf("-chart") != -1)
                                    IconButton(onClick = { dataViewModel.mustRefresh.value = (dataViewModel.mustRefresh.value + 1) % 2 }) {
                                        Icon(
                                            imageVector = Icons.Filled.Refresh,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                            }
                        )

                        Scaffold(
                            bottomBar = {
                                BottomNavigation(
                                    backgroundColor = MaterialTheme.colorScheme.primary,
                                ) {
                                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                                    val currentDestination = navBackStackEntry?.destination

                                    navFragments.forEach { fragment ->
                                        BottomNavigationItem(
                                            alwaysShowLabel = false,
                                            icon = {
                                                Icon(
                                                    fragment.icon,
                                                    contentDescription = null,
                                                    tint = Color.White
                                                )
                                            },
                                            label = {
                                                Text(
                                                    text = fragment.title,
                                                    color = Color.White,
                                                    fontSize = TextUnit(14f, TextUnitType.Sp),
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            },
                                            selected = currentDestination?.hierarchy?.any { it.route == fragment.route } == true,
                                            onClick = {
                                                pageTitle = fragment.title

                                                navController.navigate(fragment.route) {
                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                        )
                                    }

                                    if (showCountrySelectionAlertDialog) {
                                        AlertDialog(
                                            onDismissRequest = {
                                                showCountrySelectionAlertDialog = false
                                            }
                                        ) {
                                            Surface(
                                                color = MaterialTheme.colorScheme.background
                                            ) {
                                                Column(
                                                    modifier = Modifier.padding(16.dp),
                                                    verticalArrangement = Arrangement.spacedBy(
                                                        8.dp,
                                                        Alignment.CenterVertically
                                                    )
                                                ) {
                                                    // Country selection list can be added here
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        ) { innerPadding ->
                            NavHost(
                                navController = navController,
                                startDestination = Page.corePage.route,
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                composable(Page.corePage.route) {
                                    CorePage(
                                        viewModel = dataViewModel
                                    )
                                }
                                // Code for other composable destinations
                            }
                        }
                    }

                    if (showCountrySelectionAlertDialog)
                        AlertDialog(
                            onDismissRequest = {
                                showCountrySelectionAlertDialog = false
                            }
                        ) {
                            Surface(
                                color = MaterialTheme.colorScheme.background
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(
                                        8.dp,
                                        Alignment.CenterVertically
                                    )
                                ) {
                                    /"Aruba",
                                        "Africa Eastern and Southern",
                                        "Afghanistan",
                                        "Africa Western and Central",
                                        "Angola",
                                        "Albania",
                                        "Andorra",
                                        "Arab World",
                                        "United Arab Emirates",
                                        "Argentina",
                                        "Armenia",
                                        "American Samoa",
                                        "Antigua and Barbuda",
                                        "Australia",
                                        "Austria",
                                        "Azerbaijan",
                                        "Burundi",
                                        "Belgium",
                                        "Benin",
                                        "Burkina Faso",
                                        "Bangladesh",
                                        "Bulgaria",
                                        "Bahrain",
                                        "Bahamas, The",
                                        "Bosnia and Herzegovina",
                                        "Belarus",
                                        "Belize",
                                        "Bermuda",
                                        "Bolivia",
                                        "Brazil",
                                        "Barbados",
                                        "Brunei Darussalam",
                                        "Bhutan",
                                        "Botswana",
                                        "Central African Republic",
                                        "Canada",
                                        "Central Europe and the Baltics",
                                        "Switzerland",
                                        "Channel Islands",
                                        "Chile",
                                        "China",
                                        "Cote d'Ivoire",
                                        "Cameroon",
                                        "Congo, Dem. Rep.",
                                        "Congo, Rep.",
                                        "Colombia",
                                        "Comoros",
                                        "Cabo Verde",
                                        "Costa Rica",
                                        "Caribbean small states",
                                        "Cuba",
                                        "Curacao",
                                        "Cayman Islands",
                                        "Cyprus",
                                        "Czech Republic",
                                        "Germany",
                                        "Djibouti",
                                        "Dominica",
                                        "Denmark",
                                        "Dominican Republic",
                                        "Algeria",
                                        "East Asia & Pacific (excluding high income)",
                                        "Early-demographic dividend",
                                        "East Asia & Pacific",
                                        "Europe & Central Asia (excluding high income)",
                                        "Europe & Central Asia",
                                        "Ecuador",
                                        "Egypt, Arab Rep.",
                                        "Euro area",
                                        "Eritrea",
                                        "Spain",
                                        "Estonia",
                                        "Ethiopia",
                                        "European Union",
                                        "Fragile and conflict affected situations",
                                        "Finland",
                                        "Fiji",
                                        "France",
                                        "Faroe Islands",
                                        "Micronesia, Fed. Sts.",
                                        "Gabon",
                                        "United Kingdom",
                                        "Georgia",
                                        "Ghana",
                                        "Gibraltar",
                                        "Guinea",
                                        "Gambia, The",
                                        "Guinea-Bissau",
                                        "Equatorial Guinea",
                                        "Greece",
                                        "Grenada",
                                        "Greenland",
                                        "Guatemala",
                                        "Guam",
                                        "Guyana",
                                        "High income",
                                        "Hong Kong SAR, China",
                                        "Honduras",
                                        "Heavily indebted poor countries (HIPC)",
                                        "Croatia",
                                        "Haiti",
                                        "Hungary",
                                        "IBRD only",
                                        "IDA & IBRD total",
                                        "IDA total",
                                        "IDA blend",
                                        "Indonesia",
                                        "IDA only",
                                        "Isle of Man",
                                        "India",
                                        "Not classified",
                                        "Ireland",
                                        "Iran, Islamic Rep.",
                                        "Iraq",
                                        "Iceland",
                                        "Israel",
                                        "Italy",
                                        "Jamaica",
                                        "Jordan",
                                        "Japan",
                                        "Kazakhstan",
                                        "Kenya",
                                        "Kyrgyz Republic",
                                        "Cambodia",
                                        "Kiribati",
                                        "St. Kitts and Nevis",
                                        "Korea, Rep.",
                                        "Kuwait",
                                        "Latin America & Caribbean (excluding high income)",
                                        "Lao PDR",
                                        "Lebanon",
                                        "Liberia",
                                        "Libya",
                                        "St. Lucia",
                                        "Latin America & Caribbean",
                                        "Least developed countries: UN classification",
                                        "Low income",
                                        "Liechtenstein",
                                        "Sri Lanka",
                                        "Lower middle income",
                                        "Low & middle income",
                                        "Lesotho",
                                        "Late-demographic dividend",
                                        "Lithuania",
                                        "Luxembourg",
                                        "Latvia",
                                        "Macao SAR, China",
                                        "St. Martin (French part)",
                                        "Morocco",
                                        "Monaco",
                                        "Moldova",
                                        "Madagascar",
                                        "Maldives",
                                        "Middle East & North Africa",
                                        "Mexico",
                                        "Marshall Islands",
                                        "Middle income",
                                        "North Macedonia",
                                        "Mali",
                                        "Malta",
                                        "Myanmar",
                                        "Middle East & North Africa (excluding high income)",
                                        "Montenegro",
                                        "Mongolia",
                                        "Northern Mariana Islands",
                                        "Mozambique",
                                        "Mauritania",
                                        "Mauritius",
                                        "Malawi",
                                        "Malaysia",
                                        "North America",
                                        "Namibia",
                                        "New Caledonia",
                                        "Niger",
                                        "Nigeria",
                                        "Nicaragua",
                                        "Netherlands",
                                        "Norway",
                                        "Nepal",
                                        "Nauru",
                                        "New Zealand",
                                        "OECD members",
                                        "Oman",
                                        "Other small states",
                                        "Pakistan",
                                        "Panama",
                                        "Peru",
                                        "Philippines",
                                        "Palau",
                                        "Papua New Guinea",
                                        "Poland",
                                        "Pre-demographic dividend",
                                        "Puerto Rico",
                                        "Korea, Dem. People's Rep.",
                                        "Portugal",
                                        "Paraguay",
                                        "West Bank and Gaza",
                                        "Pacific island small states",
                                        "Post-demographic dividend",
                                        "French Polynesia",
                                        "Qatar",
                                        "Romania",
                                        "Russian Federation",
                                        "Rwanda",
                                        "South Asia",
                                        "Saudi Arabia",
                                        "Sudan",
                                        "Senegal",
                                        "Singapore",
                                        "Solomon Islands",
                                        "Sierra Leone",
                                        "El Salvador",
                                        "San Marino",
                                        "Somalia",
                                        "Serbia",
                                        "Sub-Saharan Africa (excluding high income)",
                                        "South Sudan",
                                        "Sub-Saharan Africa",
                                        "Small states",
                                        "Sao Tome and Principe",
                                        "Suriname",
                                        "Slovak Republic",
                                        "Slovenia",
                                        "Sweden",
                                        "Eswatini",
                                        "Sint Maarten (Dutch part)",
                                        "Seychelles",
                                        "Syrian Arab Republic",
                                        "Turks and Caicos Islands",
                                        "Chad",
                                        "East Asia & Pacific (IDA & IBRD countries)",
                                        "Europe & Central Asia (IDA & IBRD countries)",
                                        "Togo",
                                        "Thailand",
                                        "Tajikistan",
                                        "Turkmenistan",
                                        "Latin America & the Caribbean (IDA & IBRD countries)",
                                        "Timor-Leste",
                                        "Middle East & North Africa (IDA & IBRD countries)",
                                        "Tonga",
                                        "South Asia (IDA & IBRD)",
                                        "Sub-Saharan Africa (IDA & IBRD countries)",
                                        "Trinidad and Tobago",
                                        "Tunisia",
                                        "Turkey",
                                        "Tuvalu",
                                        "Tanzania",
                                        "Uganda",
                                        "Ukraine",
                                        "Upper middle income",
                                        "Uruguay",
                                        "United States",
                                        "Uzbekistan",
                                        "St. Vincent and the Grenadines",
                                        "Venezuela, RB",
                                        "British Virgin Islands",
                                        "Virgin Islands (U.S.)",
                                        "Vietnam",
                                        "Vanuatu",
                                        "World",
                                        "Samoa",
                                        "Kosovo",
                                        "Yemen, Rep.",
                                        "South Africa",
                                        "Zambia",
                                        "Zimbabwe"
                                }
                            }
                        }

                    if (dataViewModel.showProgress.value)
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = Color(1f,1f,1f,0.5f)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Hack277Theme {
        Greeting("Android")
    }
}
