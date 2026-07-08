package unap.epis.dp2.mibolsillo.presentation.screens.budget


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import unap.epis.dp2.mibolsillo.presentation.model.Categoria
import unap.epis.dp2.mibolsillo.presentation.model.DatosDemo
import unap.epis.dp2.mibolsillo.presentation.model.PresupuestoCategoria
import unap.epis.dp2.mibolsillo.util.soles


@Composable
fun PresupuestoScreen(
    presupuestos: List<PresupuestoCategoria> = DatosDemo.presupuestos,
    mes: String = "julio",
    onConfigurarClick: () -> Unit = {}
) {

    var isConfiguration by remember { mutableStateOf(false) }

    if(isConfiguration)
    {
        ConfigurationPresupuesto(
            onVolver = {isConfiguration = false}
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Presupuestos de $mes", style = MaterialTheme.typography.titleLarge)
            IconButton(onClick = {isConfiguration = !isConfiguration}) {
                Icon(Icons.Filled.Settings, contentDescription = "Configurar presupuesto")
            }
        }

        Spacer(Modifier.height(16.dp))

        if(isConfiguration)
        {
            Text("Configuración")
        }
        else
        {
            presupuestos.forEach { p ->
                PresupuestoRow(p)
                Spacer(Modifier.height(14.dp))
            }

            val excedidas = presupuestos.filter { it.excedido }
            if (excedidas.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFCEBEB)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.Warning, contentDescription = null, tint = Color(0xFFA32D2D))
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Superaste el presupuesto de " + excedidas.joinToString { it.categoria.nombre },
                            color = Color(0xFFA32D2D),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ConfigurationPresupuesto(
    categorias: List<Categoria> = DatosDemo.categorias,
    limitesPrevios: Map<Int, Double> = DatosDemo.presupuestos.associate { it.categoria.id to it.montoLimite },
    mes: String = "julio",
    onVolver: () -> Unit = {},
    onCopiarMesAnterior: () -> Unit = {},
    onGuardar: (Map<Int, Double>) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val limites = remember {
        mutableStateMapOf<Int, String>().apply {
            categorias.forEach { cat -> this[cat.id] = limitesPrevios[cat.id]?.toString() ?: "" }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onVolver) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
            }
            Text("Configurar $mes", style = MaterialTheme.typography.titleLarge)
        }
        Spacer(Modifier.height(4.dp))
        Text(
            "Define el limite maximo por categoria para este mes.",
            style = MaterialTheme.typography.bodySmall,
            color = androidx.compose.ui.graphics.Color.Gray
        )
        Spacer(Modifier.height(16.dp))

        categorias.forEach { cat ->
            OutlinedTextField(
                value = limites[cat.id] ?: "",
                onValueChange = { limites[cat.id] = it },
                label = { Text(cat.nombre) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
        }

        OutlinedButton(
            onClick = onCopiarMesAnterior,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text("Copiar presupuesto de junio")
        }

        Button(
            onClick = {
                val resultado = limites.mapNotNull { (id, valor) ->
                    valor.toDoubleOrNull()?.let { id to it }
                }.toMap()
                onGuardar(resultado)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Guardar configuracion")
        }
    }
}

@Composable
private fun PresupuestoRow(p: PresupuestoCategoria) {
    val colorBarra = if (p.excedido) Color(0xFFA32D2D)
    else if (p.progreso > 0.85f) Color(0xFF854F0B)
    else Color(0xFF3B6D11)

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(p.categoria.nombre, fontWeight = FontWeight.Medium)
            Text(
                "${soles.format(p.montoGastado)} / ${soles.format(p.montoLimite)}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
        Spacer(Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { p.progreso.coerceAtMost(1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = colorBarra,
            trackColor = Color(0xFFF1EFE8)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PresupuestoScreenPreview() {
    MaterialTheme { PresupuestoScreen() }
}