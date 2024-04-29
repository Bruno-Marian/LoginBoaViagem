package com.senac.boasviagens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.senac.boasviagens.screens.Menu
import com.senac.boasviagens.screens.Login
import com.senac.boasviagens.screens.CadastrarUsuario
import com.senac.boasviagens.ui.theme.BoaViagenTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoaViagenTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun MyApp(){

    val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "telaLogin"
            ){
                composable("telaLogin"){

                    Login (onCadastrarUsuario = {
                        navController.navigate("CadastrarUsuario")
                    },
                        onLogin = {
                            navController.navigate("menu")
                        })
                }

                composable("cadastrarUsuario"){
                    CadastrarUsuario(onBack = {navController.navigateUp()})
                }

                composable("menu"){
                    Menu(onBack = {
                        navController.navigateUp()
                    })
                }

            }
        }