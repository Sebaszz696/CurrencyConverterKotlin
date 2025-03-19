package com.example.currencyconverter.data

/**
 * Modelo de datos que representa la respuesta de la API de tasas de cambio.
 * 
 * @property result Estado de la respuesta de la API ("success" o "error")
 * @property base_code Código de la moneda base utilizada para las tasas de cambio
 * @property conversion_rates Mapa de tasas de cambio donde la clave es el código de la moneda
 *                          y el valor es la tasa de cambio respecto a la moneda base
 */
data class ExchangeRateResponse(
    val result: String,
    val base_code: String,
    val conversion_rates: Map<String, Double>
) 