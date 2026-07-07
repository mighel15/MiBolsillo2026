package unap.epis.dp2.mibolsillo.presentation.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import unap.epis.dp2.mibolsillo.util.soles
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ResumenCard(
    titulo: String,
    monto: Double,
    color: Color,
    icono: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.12f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icono, contentDescription = null, tint = color, modifier = Modifier.size(14.dp))
                Spacer(Modifier.width(4.dp))
                Text(titulo, style = MaterialTheme.typography.labelMedium, color = color)
            }
            Spacer(Modifier.height(2.dp))
            Text(
                soles.format(monto),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

@Preview
@Composable
private fun ResumenCardPreview()
{
    Column {

        ResumenCard(
            "Ingresos",
            1500.00,
            color = Color.Green,
            Icons.Default.Preview,
            modifier = Modifier
        )
        ResumenCard(
            "Egresos",
            -500.00,
            color = Color.Red,
            Icons.Default.Preview,
            modifier = Modifier
        )
    }

}