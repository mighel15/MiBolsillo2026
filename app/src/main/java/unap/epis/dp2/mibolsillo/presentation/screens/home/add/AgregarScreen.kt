package unap.epis.dp2.mibolsillo.presentation.screens.home.add

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import unap.epis.dp2.mibolsillo.presentation.model.Categoria
import unap.epis.dp2.mibolsillo.presentation.model.DatosDemo
import unap.epis.dp2.mibolsillo.presentation.model.Movimiento
import unap.epis.dp2.mibolsillo.presentation.model.TipoMovimiento
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarScreenModal(visible: Boolean,
                       onDismiss: () -> Unit,
                       categorias: List<Categoria> = DatosDemo.categorias,
                       onGuardar: (Movimiento) -> Unit = {}
) {
    if (!visible) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    fun cerrar(accion: () -> Unit = {}) {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            accion()
            onDismiss()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        AgregarScreen(
            categorias = categorias,
            onGuardar = { movimiento ->
                cerrar { onGuardar(movimiento) }
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .navigationBarsPadding()
                .imePadding()
        )
    }
}

@Composable
fun AgregarScreen(
    categorias: List<Categoria> = DatosDemo.categorias,
    onGuardar: (Movimiento) -> Unit = {},
    modifier: Modifier = Modifier.fillMaxSize()
) {
    var tipo by remember { mutableStateOf(TipoMovimiento.GASTO) }
    var monto by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf(categorias.first()) }
    var fuente by remember { mutableStateOf("Sueldo") }
    var nota by remember { mutableStateOf("") }

    val fuentes = listOf("Sueldo", "Freelance", "Otro")

    Column(
        modifier = modifier
            .padding(20.dp)
    ) {
        Text(
            if (tipo == TipoMovimiento.GASTO) "Nuevo gasto" else "Nuevo ingreso",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(16.dp))

        SegmentedTipoSelector(tipo) { tipo = it }
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = monto,
            onValueChange = { monto = it },
            label = { Text("Monto") },
            placeholder = { Text("0.00") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        if (tipo == TipoMovimiento.GASTO) {
            DropdownSelector(
                label = "Categoria",
                opciones = categorias.map { it.nombre },
                seleccionado = categoriaSeleccionada.nombre,
                onSeleccion = { nombre -> categoriaSeleccionada = categorias.first { it.nombre == nombre } }
            )
        } else {
            DropdownSelector(
                label = "Fuente",
                opciones = fuentes,
                seleccionado = fuente,
                onSeleccion = { fuente = it }
            )
        }
        Spacer(Modifier.height(12.dp))

        // En una app real esto seria un DatePicker; se deja como campo simple
        OutlinedTextField(
            value = LocalDate.now().toString(),
            onValueChange = {},
            label = { Text("Fecha") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = nota,
            onValueChange = { nota = it },
            label = { Text("Nota") },
            placeholder = { Text("Ej: Almuerzo con equipo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                val montoDouble = monto.toDoubleOrNull() ?: return@Button
                val movimiento = Movimiento(
                    id = 0,
                    tipo = tipo,
                    monto = montoDouble,
                    categoria = if (tipo == TipoMovimiento.GASTO) categoriaSeleccionada else null,
                    fuente = if (tipo == TipoMovimiento.INGRESO) fuente else null,
                    fecha = LocalDate.now(),
                    nota = nota
                )
                onGuardar(movimiento)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(if (tipo == TipoMovimiento.GASTO) "Guardar gasto" else "Guardar ingreso")
        }
    }
}

@Composable
private fun SegmentedTipoSelector(
    tipo: TipoMovimiento,
    onCambio: (TipoMovimiento) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(3.dp)
    ) {
        SegmentButton("Gasto", tipo == TipoMovimiento.GASTO, Modifier.weight(1f)) {
            onCambio(TipoMovimiento.GASTO)
        }
        SegmentButton("Ingreso", tipo == TipoMovimiento.INGRESO, Modifier.weight(1f)) {
            onCambio(TipoMovimiento.INGRESO)
        }
    }
}

@Composable
private fun SegmentButton(
    texto: String,
    activo: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val bg = if (activo) MaterialTheme.colorScheme.primary else androidx.compose.ui.graphics.Color.Transparent
    val fg = if (activo) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bg)
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(texto, color = fg, style = MaterialTheme.typography.labelLarge)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownSelector(
    label: String,
    opciones: List<String>,
    seleccionado: String,
    onSeleccion: (String) -> Unit
) {
    var expandido by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expandido, onExpandedChange = { expandido = it }) {
        OutlinedTextField(
            value = seleccionado,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onSeleccion(opcion)
                        expandido = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AgregarScreenPreview() {
    MaterialTheme { AgregarScreen() }
}