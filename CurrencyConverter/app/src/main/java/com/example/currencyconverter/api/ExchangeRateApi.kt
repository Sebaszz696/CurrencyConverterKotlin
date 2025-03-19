package com.example.currencyconverter.api

import com.example.currencyconverter.data.ExchangeRateResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface que define los endpoints para la API de ExchangeRate-API.
 * Utiliza Retrofit para realizar las llamadas HTTP.
 */
interface ExchangeRateApi {
    /**
     * Obtiene las tasas de cambio para una moneda base específica.
     * 
     * @param baseCurrency Código de la moneda base (ejemplo: "USD", "EUR")
     * @return ExchangeRateResponse con las tasas de cambio para todas las monedas disponibles
     */
    @GET("v6/26410a8913d0dc1fc254ba46/latest/{base}")
    suspend fun getExchangeRates(@Path("base") baseCurrency: String): ExchangeRateResponse
} 