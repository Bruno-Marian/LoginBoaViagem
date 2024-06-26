package com.senac.boasviagens.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.senac.boasviagens.R
import com.senac.boasviagens.database.AppDatabase
import com.senac.boasviagens.entities.Viagem
import com.senac.boasviagens.entities.TipoViagem
import com.senac.boasviagens.viewmodels.ViagemViewModel
import com.senac.boasviagens.viewmodels.ViagemViewModelFatory
fun Dest(){

}
@Composable
fun Viagens(){
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val viagemViewModel: ViagemViewModel = viewModel(
        factory = ViagemViewModelFatory(db)
    )
    val listViagem = viagemViewModel.viagemDao.getAll().collectAsState(initial = emptyList())

    val navController = rememberNavController()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("cadastroViagens")
            }) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = null)
            }
        }
    ) { it ->
        Column(modifier = Modifier.padding(it)) {
            NavHost(
                navController = navController,
                startDestination = "dest"
            ) {
                composable("cadastroViagens/{viagemId}",
                    arguments = listOf(navArgument("viagemId") { type = NavType.LongType })) {
                    CadastrarViagens(
                        onBack = {navController.navigateUp()}
                    )
                }
                composable("dest") {
                    Dest()
                }
            }
            LazyColumn {
                items(items = listViagem.value){
                    ViagemCard(it, onDelete = {
                        viagemViewModel.delete(it)
                    })
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViagemCard(v: Viagem, onDelete: () -> Unit){
    val ctx = LocalContext.current
    Card(elevation = CardDefaults.cardElevation(
        defaultElevation = 6.dp
    ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    Toast
                        .makeText(
                            ctx,
                            "Destino: ${v.destino}, ${v.inicio} - ${v.fim}",
                            Toast.LENGTH_LONG
                        )
                        .show()
                },
                onLongClick = {
                    onDelete()
                }
            )
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            if (v.tipo == TipoViagem.Negocio){
                Image(
                    painter = painterResource(id = R.drawable.negocio),
                    contentDescription = "Negócio",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .fillMaxWidth()
                        .padding(top = 0.dp, start = 15.dp)
                )
            }else{
                Image(
                    painter = painterResource(id = R.drawable.lazer),
                    contentDescription = "Lazer",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .fillMaxWidth()
                        .padding(top = 0.dp, start = 15.dp)
                )
            }

            Column {
                Text(text = "Destino: ${v.destino}", fontSize = 25.sp, style = MaterialTheme.typography.titleLarge)
                Row{
                    Text(text = "${v.inicio?.time?.toBrazilianDateFormat()} - ${v.fim?.time?.toBrazilianDateFormat()}")
                }
                Row{
                    Text(text = "Orçamento: R$ ${v.orcamento}")
                }
            }
        }
    }
}