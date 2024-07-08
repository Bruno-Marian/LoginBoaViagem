package com.senac.boasviagens.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.senac.boasviagens.components.MyTopBar
import com.senac.boasviagens.database.AppDatabase
import com.senac.boasviagens.viewmodels.UsuarioViewModel
import com.senac.boasviagens.viewmodels.UsuarioViewModelFatory
import kotlin.system.exitProcess


private fun isSelected(currentDestination: NavDestination?, route:String): Boolean {
    return currentDestination?.hierarchy?.any {it.route == route} == true
}

@Composable
fun Home(id: String) {
    Scaffold(
        topBar = {
            MyTopBar("Boa viagem") { exitProcess(0) }
        }
    ) { it ->

        val db = AppDatabase.getDatabase(LocalContext.current)

        val usuarioViewModel: UsuarioViewModel = viewModel(
            factory = UsuarioViewModelFatory(db)
        )

        LaunchedEffect(id) {
            val user = usuarioViewModel.findById(id.toLong())
            user?.let { usuarioViewModel.setUiState(it) }
        }

        val state = usuarioViewModel.uiState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)

        ) {

            Row {
                Text(
                    text = "Bem-vindo: " + state.value.usuario,
                    fontSize = 34.sp
                )
            }
        }
    }
}

@Composable
fun Menu(id: String){
    val navController = rememberNavController()

    Scaffold (
        bottomBar = {
            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.value?.destination

            BottomNavigation {

                BottomNavigationItem(
                    selected = isSelected(currentDestination, "home"),
                    onClick = { navController.navigate("home") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = ""
                        )
                    }
                )

                BottomNavigationItem(
                    selected = isSelected(currentDestination, "viagem"),
                    onClick = { navController.navigate("viagem") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = ""
                        )
                    }
                )

                BottomNavigationItem(
                    selected = isSelected(currentDestination, "sobre"),
                    onClick = { navController.navigate("sobre") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = ""
                        )
                    }
                )
            }
        }
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)

        ){
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    Home(id)
                }
                composable("viagem"){
                    Viagens(id)
                }

                composable("sobre"){
                    Sobre()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMenu() {
    Menu("")
}