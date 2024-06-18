package com.senac.boasviagens.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.senac.boasviagens.components.MyTopBar
import com.senac.boasviagens.database.AppDatabase
import com.senac.boasviagens.entities.TipoViagem
import com.senac.boasviagens.entities.Viagem
import com.senac.boasviagens.viewmodels.ViagemViewModel
import com.senac.boasviagens.viewmodels.ViagemViewModelFatory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastrarViagens(onBack: ()->Unit) {
    Scaffold(
        topBar = {
            MyTopBar()
        }
    ) { it ->
        val context = LocalContext.current
        val db = AppDatabase.getDatabase(context)
        val viagemViewModel: ViagemViewModel = viewModel(
            factory = ViagemViewModelFatory(db)
        )
        val state = viagemViewModel.uiState.collectAsState()

        var showDatePickerDialogInicio = remember {
            mutableStateOf(false)
        }
        var selectedDateInicio = remember {
            mutableStateOf("")
        }

        val datePickerStateInicio = rememberDatePickerState()

        var showDatePickerDialogFinal = remember {
            mutableStateOf(false)
        }
        var selectedDateFinal = remember {
            mutableStateOf("")
        }

        val datePickerStateFinal = rememberDatePickerState()

        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Destino",
                    fontSize = 22.sp,
                    modifier = Modifier
                        .weight(1f)
                )
            }

            Row {
                OutlinedTextField(
                    value = state.value.destino,
                    onValueChange = { viagemViewModel.updateDestino(it) },
                    modifier = Modifier
                        .weight(4f)
                        .padding(top = 10.dp)
                )
            }

            Row {
                Text(
                    text = "Tipo",
                    fontSize = 22.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 10.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = state.value.tipo == TipoViagem.Lazer,
                    onClick = { viagemViewModel.updateTipo(TipoViagem.Lazer) },
                    modifier = Modifier
                        .weight(0.5f)
                )

                Text(
                    text = "Lazer",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .weight(1.5f)
                )

                RadioButton(
                    selected = state.value.tipo != TipoViagem.Negocio,
                    onClick = { viagemViewModel.updateTipo(TipoViagem.Negocio)},
                    modifier = Modifier
                        .weight(0.5f)
                )

                Text(
                    text = "Negócios",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .weight(1.5f)

                )
            }

            Row {

                Text(
                    text = "Data Inicio",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(top = 16.dp)
                )
            }

            Row {

                if (showDatePickerDialogInicio.value) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePickerDialogInicio.value = false },
                        confirmButton = {
                            Button(
                                onClick = {
                                    datePickerStateInicio
                                        .selectedDateMillis?.let { millis ->
                                            selectedDateInicio.value = millis.toBrazilianDateFormat()
                                        }
                                    showDatePickerDialogInicio.value = false
                                }) {
                                Text(text = "Escolher data")
                            }
                        },
                        modifier = Modifier
                            .weight(4f)
                    ) {
                        DatePicker(state = datePickerStateInicio)
                    }
                }

                OutlinedTextField(
                    value = state.value.inicio.toString(),
                    onValueChange = { viagemViewModel.updateInicio(it.toLong()) },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .onFocusChanged {
                            if (it.isFocused) {
                                showDatePickerDialogInicio.value = true
                            }
                        },
                    readOnly = true
                )

            }

            Row {

                Text(
                    text = "Data Final",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(top = 16.dp)
                )
            }

            Row {

                if (showDatePickerDialogFinal.value) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePickerDialogFinal.value = false },
                        confirmButton = {
                            Button(
                                onClick = {
                                    datePickerStateFinal
                                        .selectedDateMillis?.let { millis ->
                                            selectedDateFinal.value = millis.toBrazilianDateFormat()
                                        }
                                    showDatePickerDialogFinal.value = false
                                }) {
                                Text(text = "Escolher data")
                            }
                        },
                        modifier = Modifier
                            .weight(4f)
                    ) {
                        DatePicker(state = datePickerStateFinal)
                    }
                }

                OutlinedTextField(
                    value = state.value.fim.toString(),
                    onValueChange = { viagemViewModel.updateFim(it.toLong()) },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .onFocusChanged {
                            if (it.isFocused) {
                                showDatePickerDialogFinal.value = true
                            }
                        },
                    readOnly = true
                )

            }

            Row {

                Text(
                    text = "Orçamento",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(top = 16.dp)
                )
            }

            Row {
                OutlinedTextField(
                    value = state.value.orcamento.toString(),
                    onValueChange = { viagemViewModel.updateOrcamento(it.toFloat()) },
                    modifier = Modifier
                        .weight(4f)
                        .padding(top = 10.dp)
                )
            }

            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Button(
                    onClick = {
                        viagemViewModel.save()
                        Toast.makeText(context, "Viagem salva!", Toast.LENGTH_SHORT).show()
                        onBack() },
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .weight(2f)
                ){
                    Text(text = "Salvar")
                }
            }

        }

    }
}

fun Long.toBrazilianDateFormat(
    pattern: String = "dd/MM/yyyy"
): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(
        pattern, Locale("pt-br")
    ).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }
    return formatter.format(date)
}