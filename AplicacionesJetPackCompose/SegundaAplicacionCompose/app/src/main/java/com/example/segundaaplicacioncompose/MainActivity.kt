package com.example.segundaaplicacioncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.segundaaplicacioncompose.ui.theme.SegundaAplicacionComposeTheme
import java.util.Calendar

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkMode by remember { mutableStateOf(false) }
            SegundaAplicacionComposeTheme(darkTheme = isDarkMode) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        "BIENVENIDO A RENTCAR",
                                        style = TextStyle(
                                            fontFamily = FontFamily.SansSerif,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = MaterialTheme.typography.titleLarge.fontSize
                                        )
                                    )
                                },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    ) { paddingValues ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AlquilerCoches(
                                isDarkMode = isDarkMode,
                                onModeChange = { isDarkMode = it }
                            )
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AlquilerCoches(
        isDarkMode: Boolean,
        onModeChange: (Boolean) -> Unit
    ) {
        var nombre by remember { mutableStateOf(TextFieldValue()) }
        var correo by remember { mutableStateOf(TextFieldValue()) }
        var coche by remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }
        val marcas = listOf("Toyota", "Seat", "BMW", "Audi", "Mercedes", "Honda")
        var fechaAlquiler by remember { mutableStateOf("") }
        var dialogVisible by remember { mutableStateOf(false) }
        var isConfirmed by remember { mutableStateOf(false) }
        val context = LocalContext.current

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

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = coche,
                    onValueChange = { },
                    label = { Text("Modelo de coche") },
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    shape = RoundedCornerShape(16.dp)
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    marcas.forEach { marca ->
                        DropdownMenuItem(
                            text = { Text(marca) },
                            onClick = {
                                coche = marca
                                expanded = false
                            }
                        )
                    }
                }
            }

            TextField(
                value = fechaAlquiler,
                onValueChange = { },
                label = { Text("Fecha de alquiler") },
                shape = RoundedCornerShape(16.dp),
                readOnly = true
            )

            Button(
                onClick = {
                    val calendar = Calendar.getInstance()
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)

                    android.app.DatePickerDialog(
                        context,
                        { _, selectedYear, selectedMonth, selectedDay ->
                            fechaAlquiler = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                        },
                        year,
                        month,
                        day
                    ).show()
                }
            ) {
                Text("Seleccionar Fecha")
            }

            Button(
                onClick = { dialogVisible = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isConfirmed) "Enviado" else "Enviar")
            }

            Image(
                painter = painterResource(id = R.drawable.alquiler),
                contentDescription = "Imagen de alquiler",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )

            if (dialogVisible) {
                AlertDialog(
                    onDismissRequest = { dialogVisible = false },
                    title = { Text("Confirmación de Envío") },
                    text = { Text("¿Estás seguro de que deseas enviar los datos?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                dialogVisible = false
                                isConfirmed = true
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

            if (isConfirmed) {
                Text(
                    text = "Datos enviados exitosamente",
                    color = Color.Green,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Modo oscuro", modifier = Modifier.padding(end = 8.dp))
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = onModeChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Green,
                        uncheckedThumbColor = Color.Gray,
                        checkedTrackColor = Color.Blue,
                        uncheckedTrackColor = Color.LightGray
                    )
                )
            }
        }
    }
}
