package unap.epis.dp2.mibolsillo.presentation.screens.report


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import unap.epis.dp2.mibolsillo.presentation.model.ComparativoMes
import unap.epis.dp2.mibolsillo.presentation.model.DatosDemo
import unap.epis.dp2.mibolsillo.presentation.model.GastoPorCategoria
import unap.epis.dp2.mibolsillo.util.soles


@Composable
fun ReporteScreen(
    gastosPorCategoria: List<GastoPorCategoria> = DatosDemo.presupuestos.map {
        GastoPorCategoria(it.categoria.nombre, it.montoGastado, it.categoria.color)
    },
    comparativo: List<ComparativoMes> = listOf(
        ComparativoMes("Marzo", 1050.0),
        ComparativoMes("Abril", 1150.0),
        ComparativoMes("Mayo", 1250.0),
        ComparativoMes("Junio", 1050.0),
        ComparativoMes("Julio", 1240.0)
    ),
    mes: String = "julio"
) {
    val maximo = gastosPorCategoria.maxOf { it.monto + 50 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text("Reportes", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        Text(
            "Gasto por categoria \u2014 $mes",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            gastosPorCategoria.forEach { g ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val alturaRelativa = (g.monto / maximo).toFloat().coerceIn(0.05f, 1f)
                    Box(
                        modifier = Modifier
                            .width(36.dp)
                            .fillMaxHeight(alturaRelativa)
                            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                            .background(g.color)
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(g.nombre, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        Text("Comparativo mensual", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        Spacer(Modifier.height(8.dp))

        comparativo.forEachIndexed { index, c ->
            val esUltimo = index == comparativo.lastIndex
            val variacion = if (index > 0) {
                ((c.total - comparativo[index - 1].total) / comparativo[index - 1].total) * 100
            } else null

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(c.mes)
                Text(
                    soles.format(c.total) + (variacion?.let { " (%+.0f%%)".format(it) } ?: ""),
                    color = if (esUltimo && variacion != null && variacion > 0) Color(0xFFA32D2D) else Color.Unspecified,
                    fontWeight = if (esUltimo) FontWeight.Medium else FontWeight.Normal
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReporteScreenPreview() {
    MaterialTheme { ReporteScreen() }
}