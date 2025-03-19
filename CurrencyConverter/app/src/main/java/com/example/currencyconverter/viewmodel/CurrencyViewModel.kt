package com.example.currencyconverter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.api.ExchangeRateApi
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * ViewModel que maneja la lógica de negocio para la conversión de divisas.
 * Gestiona las llamadas a la API y mantiene el estado de la UI.
 */
class CurrencyViewModel : ViewModel() {
    // LiveData para el resultado de la conversión
    private val _conversionResult = MutableLiveData<String>()
    val conversionResult: LiveData<String> = _conversionResult

    // LiveData para el estado de carga
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // LiveData para la lista de monedas disponibles
    private val _availableCurrencies = MutableLiveData<List<String>>()
    val availableCurrencies: LiveData<List<String>> = _availableCurrencies

    // Inicialización de la API de Retrofit
    private val api: ExchangeRateApi = Retrofit.Builder()
        .baseUrl("https://v6.exchangerate-api.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ExchangeRateApi::class.java)

    init {
        loadAvailableCurrencies()
    }

    /**
     * Carga la lista de monedas disponibles desde la API.
     * En caso de error, proporciona una lista predeterminada de monedas comunes.
     */
    private fun loadAvailableCurrencies() {
        viewModelScope.launch {
            try {
                val response = api.getExchangeRates("USD")
                _availableCurrencies.value = response.conversion_rates.keys.toList().sorted()
            } catch (e: IOException) {
                _availableCurrencies.value = listOf("USD", "EUR", "GBP", "JPY", "MXN", "CAD", "AUD")
                _conversionResult.value = "Error de conexión: Por favor verifica tu conexión a internet"
            } catch (e: Exception) {
                _availableCurrencies.value = listOf("USD", "EUR", "GBP", "JPY", "MXN", "CAD", "AUD")
                _conversionResult.value = "Error: ${e.message ?: "Error desconocido"}"
            }
        }
    }

    /**
     * Realiza la conversión de divisas utilizando la API.
     * 
     * @param amount Cantidad a convertir
     * @param fromCurrency Código de la moneda de origen
     * @param toCurrency Código de la moneda de destino
     */
    fun convertCurrency(amount: Double, fromCurrency: String, toCurrency: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = api.getExchangeRates(fromCurrency)
                val rate = response.conversion_rates[toCurrency] ?: throw Exception("Tasa de cambio no encontrada")
                val result = amount * rate
                _conversionResult.value = String.format("%.2f %s = %.2f %s", amount, fromCurrency, result, toCurrency)
            } catch (e: IOException) {
                _conversionResult.value = "Error de conexión: Por favor verifica tu conexión a internet"
            } catch (e: Exception) {
                _conversionResult.value = "Error: ${e.message ?: "Error desconocido"}"
            } finally {
                _isLoading.value = false
            }
        }
    }
} 