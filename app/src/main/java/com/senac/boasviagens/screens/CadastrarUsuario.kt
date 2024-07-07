package com.senac.boasviagens.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.senac.boasviagens.database.AppDatabase
import com.senac.boasviagens.viewmodels.UsuarioViewModel
import com.senac.boasviagens.viewmodels.UsuarioViewModelFatory

@Composable
fun CadastrarUsuario(
        onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {

        val context = LocalContext.current
        val db = AppDatabase.getDatabase(context)
        val usuarioViewModel: UsuarioViewModel = viewModel(
            factory = UsuarioViewModelFatory(db)
        )

        val loginState = usuarioViewModel.uiState.collectAsState()
        val passState = usuarioViewModel.uiState.collectAsState()
        val emailState = usuarioViewModel.uiState.collectAsState()


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
                value = loginState.value.usuario,
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
            OutlinedTextField(
                value = passState.value.senha,
                onValueChange = {usuarioViewModel.updateSenha(it)},
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
                value = emailState.value.email,
                onValueChange = {usuarioViewModel.updateEmail(it)},
                modifier = Modifier
                    .padding(start = 55.dp, top = 10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }


        Row {
            Button(
                onClick = {
                    usuarioViewModel.save()
                    onBack() },
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
}

@Preview(showBackground = true)
@Composable
fun PreviewAbout() {
    CadastrarUsuario({})
}