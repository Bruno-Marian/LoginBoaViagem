package com.senac.boasviagens.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Menu(onBack: () ->Unit){

    Column (
        modifier = Modifier
            .fillMaxSize()

    ){

        Row {
            Button(
                onClick = { onBack() },
                modifier = Modifier
                    .padding(start = 312.dp, top = 5.dp)

            ) {
                Text(text = "Sair")
            }
        }

        Row {
            Text(
                text = "Info do sistema",
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMenu() {
    Menu({})
}