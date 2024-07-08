package com.senac.boasviagens.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.senac.boasviagens.R
import com.senac.boasviagens.components.MyTopBar
import com.senac.boasviagens.database.AppDatabase
import com.senac.boasviagens.viewmodels.UsuarioViewModel
import com.senac.boasviagens.viewmodels.UsuarioViewModelFatory
import kotlinx.coroutines.launch

@Composable
fun CadastrarUsuario(
        onBack: () -> Unit
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val coroutineScope = rememberCoroutineScope()
    val focus = LocalFocusManager.current

    Scaffold(
        topBar = {
            MyTopBar("Cadastrar usuário") { onBack() }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ){ it ->
        Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(it)
    ) {

        val context = LocalContext.current
        val db = AppDatabase.getDatabase(context)
        val usuarioViewModel: UsuarioViewModel = viewModel(
            factory = UsuarioViewModelFatory(db)
        )

        val usuarioState = usuarioViewModel.uiState.collectAsState()
        Row {
            Text(
                text = "Cadastro de Usuário",
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
            )
        }
        Row {
            Text(
                text = "Login",
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 25.dp)
                    .fillMaxWidth()
            )
        }
        Row(
        ) {

            OutlinedTextField(
                value = usuarioState.value.usuario,
                onValueChange = {usuarioViewModel.updateLogin(it)},
                modifier = Modifier
                    .padding(start = 55.dp, top = 10.dp)
            )
        }

        Row {
            Text(
                text = "Senha",
                fontSize = 22.sp,
                textAlign = TextAlign.Center,

                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
            )
        }

        Row(

        ) {
            val visible = remember { mutableStateOf(false) }
            OutlinedTextField(
                value = usuarioState.value.senha,
                onValueChange = {usuarioViewModel.updateSenha(it)},
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
                    .padding(start = 55.dp, top = 10.dp)
            )
        }

        Row {
            Text(
                text = "Email",
                fontSize = 22.sp,
                textAlign = TextAlign.Center,

                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
            )
        }

        Row(
        ) {
            OutlinedTextField(
                value = usuarioState.value.email,
                onValueChange = {usuarioViewModel.updateEmail(it)},
                modifier = Modifier
                    .padding(start = 55.dp, top = 10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }


        Row {
            Button(
                onClick = {
                    var mensagem = ""

                    if (usuarioState.value.usuario.isEmpty()){
                        mensagem += "Usuário é obrigatório\n"
                    }
                    if (usuarioState.value.email.isEmpty()){
                        mensagem += "E-mail é obrigatório\n"
                    }
                    if (usuarioState.value.senha.isEmpty()){
                        mensagem += "Senha é obrigatória\n"
                    }

                    if (mensagem.isNotEmpty()){
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = mensagem,
                                withDismissAction = true
                            )
                        }
                        focus.clearFocus()
                    }
                    else{
                        usuarioViewModel.save()
                        onBack()
                    }
                     },
                modifier = Modifier
                    .padding(start = 127.dp, top = 25.dp)
            ) {
                Text(
                    text = "Cadastrar",
                    fontSize = 22.sp
                )
            }
        }

    }
}}

@Preview(showBackground = true)
@Composable
fun PreviewAbout() {
    CadastrarUsuario({})
}