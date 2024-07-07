package com.senac.boasviagens.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.senac.boasviagens.R
import com.senac.boasviagens.database.AppDatabase
import com.senac.boasviagens.viewmodels.UsuarioViewModel
import com.senac.boasviagens.viewmodels.UsuarioViewModelFatory
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Composable
fun Login(onCadastrarUsuario: ()->Unit, onLogin: (id: String) ->Unit){

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val coroutineScope = rememberCoroutineScope()

    val focus = LocalFocusManager.current


    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { it ->
        Column(
            modifier = Modifier
                .padding(10.dp)
                .padding(it)

        ) {
            val context = LocalContext.current
            val db = AppDatabase.getDatabase(context)
            val usuarioViewModel: UsuarioViewModel = viewModel(
                factory = UsuarioViewModelFatory(db)
            )

            val loginState = usuarioViewModel.uiState.collectAsState()
            val passState = usuarioViewModel.uiState.collectAsState()

            Image(
                painter = painterResource(id = R.drawable.viagem),
                contentDescription = "Cabana",
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(350.dp)
                    .fillMaxWidth()
                    .padding(top = 0.dp, start = 15.dp)
            )

            Text(
                text = "Usuário",
                fontSize = 22.sp

            )

            OutlinedTextField(
                value = loginState.value.usuario,
                onValueChange = { usuarioViewModel.updateLogin(it) },
                label = {
                    Text(text = "login")
                },
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
            )

            Text(
                text = "Senha",
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(top = 15.dp)

            )

            val visible = remember { mutableStateOf(true) }

            OutlinedTextField(
                value = passState.value.senha,
                onValueChange = { usuarioViewModel.updateSenha(it) },
                label = {
                    Text(text = "Password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation =
                if (visible.value)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),

                trailingIcon = {
                    IconButton(onClick = {
                        visible.value = (!visible.value)
                    }) {
                        if (visible.value)
                            Icon(
                                painterResource(id = R.drawable.visiblee), ""
                            )
                        else
                            Icon(
                                painterResource(id = R.drawable.nonvisible), ""
                            )
                    }
                },
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
            )

            Button(
                onClick = {
                    MainScope().launch {
                        val pass = usuarioViewModel.findByLogin(
                            usuarioViewModel.uiState.value.usuario,
                            usuarioViewModel.uiState.value.senha
                        )

                        if (pass != null) {
                            onLogin(pass.toString())
                        } else {
                            coroutineScope.launch {
                                focus.clearFocus()
                                snackbarHostState.showSnackbar(
                                    message = "Usuário ou Senha incorretos!",
                                    withDismissAction = true
                                )
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = "Login",
                    fontSize = 22.sp
                )
            }

            Button(
                onClick = { onCadastrarUsuario() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                Text(text = "Registrar")
            }
        }
    }
}

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()