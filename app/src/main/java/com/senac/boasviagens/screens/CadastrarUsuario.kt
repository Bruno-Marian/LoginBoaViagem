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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CadastrarUsuario(onBack: () -> Unit) {

    val login = remember {
        mutableStateOf("")
    }

    val senha = remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {

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
                value = login.value,
                onValueChange = {login.value = it},
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
                value = senha.value,
                onValueChange = {senha.value = it},
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
                value = email.value,
                onValueChange = {email.value = it},
                modifier = Modifier
                    .padding(start = 55.dp, top = 10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }


        Row {
            Button(
                onClick = { onBack() },
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