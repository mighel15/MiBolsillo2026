package unap.epis.dp2.mibolsillo.presentation.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.launch
import unap.epis.dp2.mibolsillo.presentation.screens.budget.PresupuestoScreen
import unap.epis.dp2.mibolsillo.presentation.screens.home.HomeScreen
import unap.epis.dp2.mibolsillo.presentation.screens.report.ReporteScreen


enum class Destination(
    val label: String,
    val icon: ImageVector,
    val contentDescription: String,
    val screen: @Composable () -> Unit
) {
    HOME("Inicio", Icons.Default.Home, "Home", { HomeScreen() }),
    BUDGET("Presupuesto", Icons.Default.AccountBalanceWallet, "Icon budget",{ PresupuestoScreen() }),
    REPORTS("Reportes", Icons.Default.BarChart, "Icon reports", { ReporteScreen() })
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val startDestination = Destination.HOME
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    val pagerState = rememberPagerState { Destination.entries.size }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.padding(),
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                Destination.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedDestination == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = destination.contentDescription
                            )
                        },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { contentPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(contentPadding).fillMaxSize()
        ) { page ->
            selectedDestination = pagerState.currentPage
            Destination.entries[page].screen()
        }
    }
}
