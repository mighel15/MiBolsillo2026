package unap.epis.dp2.mibolsillo.presentation.model


import androidx.compose.ui.graphics.Color
import java.time.LocalDate

enum class TipoMovimiento { GASTO, INGRESO }

data class Categoria(
    val id: Int,
    val nombre: String,
    val color: Color
)

data class Movimiento(
    val id: Int,
    val tipo: TipoMovimiento,
    val monto: Double,
    val categoria: Categoria?,   // null cuando tipo == INGRESO
    val fuente: String?,        // null cuando tipo == GASTO
    val fecha: LocalDate,
    val nota: String = ""
)

data class PresupuestoCategoria(
    val categoria: Categoria,
    val montoLimite: Double,
    val montoGastado: Double
) {
    val progreso: Float
        get() = if (montoLimite <= 0) 0f else (montoGastado / montoLimite).toFloat().coerceIn(0f, 1.2f)

    val excedido: Boolean
        get() = montoGastado > montoLimite
}

data class GastoPorCategoria(val nombre: String, val monto: Double, val color: Color)
data class ComparativoMes(val mes: String, val total: Double)


// Categorias de ejemplo usadas en los previews de las 5 pantallas
object DatosDemo {
    val categorias = listOf(
        Categoria(1, "Comida", Color(0xFF3B6D11)),
        Categoria(2, "Transporte", Color(0xFF854F0B)),
        Categoria(3, "Ocio", Color(0xFFA32D2D)),
        Categoria(4, "Salud", Color(0xFF185FA5))
    )

    val movimientos = listOf(
        Movimiento(1, TipoMovimiento.INGRESO, 2500.0, null, "Sueldo", LocalDate.of(2026, 7, 1)),
        Movimiento(2, TipoMovimiento.GASTO, 18.0, categorias[0], null, LocalDate.of(2026, 7, 5), "Almuerzo"),
        Movimiento(3, TipoMovimiento.GASTO, 6.5, categorias[1], null, LocalDate.of(2026, 7, 5), "Bus")
    )

    val presupuestos = listOf(
        PresupuestoCategoria(categorias[0], 600.0, 480.0),
        PresupuestoCategoria(categorias[1], 200.0, 190.0),
        PresupuestoCategoria(categorias[2], 150.0, 210.0),
        PresupuestoCategoria(categorias[3], 150.0, 27.0)
    )
}