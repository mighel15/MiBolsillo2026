package unap.epis.dp2.mibolsillo.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import unap.epis.dp2.mibolsillo.presentation.model.Categoria
import unap.epis.dp2.mibolsillo.presentation.model.Movimiento
import unap.epis.dp2.mibolsillo.presentation.model.TipoMovimiento
import unap.epis.dp2.mibolsillo.util.soles
import java.time.LocalDate


@Composable
fun MovimientoRow(mov: Movimiento) {
    val esIngreso = mov.tipo == TipoMovimiento.INGRESO
    val etiqueta = if (esIngreso) mov.fuente ?: "Ingreso" else mov.categoria?.nombre ?: "Gasto"
    val color = if (esIngreso) Color(0xFF3B6D11) else Color(0xFFA32D2D)
    val signo = if (esIngreso) "+ " else "- "

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(etiqueta, style = MaterialTheme.typography.bodyMedium)
        Text(signo + soles.format(mov.monto), color = color, fontWeight = FontWeight.Medium)
    }
}

@Preview
@Composable
fun MovimientoRowPreview() {
    MovimientoRow(
        Movimiento(
            1, TipoMovimiento.INGRESO, 100.00, Categoria(1, "Lonche", Color.Red), "De por hay",
            LocalDate.now(), ""
        )
    )
}