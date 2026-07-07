package unap.epis.dp2.mibolsillo.util

import java.text.NumberFormat
import java.util.Locale

private val locale = Locale.Builder().setLanguage("es").setRegion("PE").build()

val soles = NumberFormat.getCurrencyInstance(locale)
