package com.example.currencyconverter

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import com.example.currencyconverter.databinding.ActivityMainBinding
import com.example.currencyconverter.viewmodel.CurrencyViewModel

/**
 * Actividad principal de la aplicación que maneja la interfaz de usuario para la conversión de divisas.
 * Utiliza View Binding para acceder a las vistas y un ViewModel para la lógica de negocio.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: CurrencyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupListeners()
    }

    /**
     * Configura los observadores para los LiveData del ViewModel.
     * Actualiza la UI cuando cambian los datos.
     */
    private fun setupObservers() {
        viewModel.availableCurrencies.observe(this) { currencies ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencies)
            binding.fromCurrencySpinner.adapter = adapter
            binding.toCurrencySpinner.adapter = adapter
        }

        viewModel.conversionResult.observe(this) { result ->
            binding.resultTextView.text = result
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.convertButton.isEnabled = !isLoading
        }
    }

    /**
     * Configura los listeners para los elementos interactivos de la UI.
     * Maneja la validación de entrada y las acciones del usuario.
     */
    private fun setupListeners() {
        binding.convertButton.setOnClickListener {
            val amountStr = binding.amountEditText.text.toString()
            if (amountStr.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese una cantidad", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountStr.toDoubleOrNull()
            if (amount == null) {
                Toast.makeText(this, "Por favor ingrese una cantidad válida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fromCurrency = binding.fromCurrencySpinner.selectedItem.toString()
            val toCurrency = binding.toCurrencySpinner.selectedItem.toString()
            viewModel.convertCurrency(amount, fromCurrency, toCurrency)
        }

        binding.swapButton.setOnClickListener {
            val fromPosition = binding.fromCurrencySpinner.selectedItemPosition
            val toPosition = binding.toCurrencySpinner.selectedItemPosition
            binding.fromCurrencySpinner.setSelection(toPosition)
            binding.toCurrencySpinner.setSelection(fromPosition)
        }
    }
}