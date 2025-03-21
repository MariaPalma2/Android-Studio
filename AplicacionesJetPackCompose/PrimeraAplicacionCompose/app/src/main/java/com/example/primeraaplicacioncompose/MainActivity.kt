package com.example.primeraaplicacioncompose


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.primeraaplicacioncompose.ui.theme.PrimeraAplicacionComposeTheme
import java.util.Calendar

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrimeraAplicacionComposeTheme {

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("BIENVENIDO A RENTCAR") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                ) { padding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Column(

                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AlquilerCoches()

                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AlquilerCoches() {
        var nombre by remember { mutableStateOf(TextFieldValue()) }
        var correo by remember { mutableStateOf(TextFieldValue()) }
        var coche by remember { mutableStateOf("") }
        val marcas by remember { mutableStateOf(TextFieldValue()) }
        var expanded by remember { mutableStateOf(false) }
        var fechaAlquiler by remember { mutableStateOf("") }
        var dialogVisible by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                shape = RoundedCornerShape(16.dp)
            )
            TextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") },
                shape = RoundedCornerShape(16.dp)
            )
            TextField(
                value = marcas ,
                onValueChange = { correo = it },
                label = { Text("Marcas de coche") },
                shape = RoundedCornerShape(16.dp)
            )


            }

            TextField(
                value = fechaAlquiler,
                onValueChange = { },  // Sin cambio directo ya que el usuario selecciona desde el DatePicker
                label = { Text("Fecha de alquiler") },
                shape = RoundedCornerShape(16.dp),
                readOnly = true  // Solo lectura
            )

            // Botón para abrir el DatePicker
            val context = LocalContext.current
            Button(onClick = {
                // Obtiene la fecha actual
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                // Muestra el DatePicker
                android.app.DatePickerDialog(
                    context,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        // Actualiza la fecha seleccionada en el formato deseado
                        fechaAlquiler = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    },
                    year,
                    month,
                    day
                ).show()
            }) {
                Text("Seleccionar Fecha")
            }


            Button(
                onClick = { dialogVisible = true },  // Muestra el cuadro de diálogo
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Enviar")
            }

            if (dialogVisible) {
                AlertDialog(
                    onDismissRequest = { dialogVisible = false },
                    title = { Text("Confirmación de Envío") },
                    text = { Text("¿Estás seguro de que deseas enviar los datos?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                dialogVisible = false
                                // Aquí puedes agregar la acción de enviar datos
                            }
                        ) {
                            Text("Confirmar")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { dialogVisible = false }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }







