# Conversor de Divisas

Una aplicación Android moderna para convertir divisas utilizando tasas de cambio en tiempo real.

## Características

- Conversión en tiempo real de divisas
- Interfaz de usuario intuitiva y moderna
- Soporte para múltiples monedas
- Actualización automática de tasas de cambio
- Manejo de errores y estados de carga
- Función de intercambio rápido entre monedas

## Tecnologías Utilizadas

- Kotlin
- Android Architecture Components (ViewModel, LiveData)
- Retrofit para llamadas API
- OkHttp para logging
- Corrutinas de Kotlin para operaciones asíncronas
- Material Design Components
- View Binding para interacción segura con la UI

## Estructura del Proyecto

```
app/
├── src/
│   └── main/
│       ├── java/com/example/currencyconverter/
│       │   ├── api/
│       │   │   └── ExchangeRateApi.kt
│       │   ├── data/
│       │   │   └── ExchangeRateResponse.kt
│       │   ├── viewmodel/
│       │   │   └── CurrencyViewModel.kt
│       │   └── MainActivity.kt
│       └── res/
│           └── layout/
│               └── activity_main.xml
```

## Configuración

1. Requisitos:
   - Android Studio Arctic Fox o superior
   - SDK mínimo: 24 (Android 7.0)
   - SDK objetivo: 35 (Android 14)

2. API Key:
   - Se utiliza la API de ExchangeRate-API
   - La API key está configurada en `ExchangeRateApi.kt`

## Componentes Principales

### MainActivity
- Actividad principal que maneja la UI
- Implementa la lógica de interacción del usuario
- Utiliza View Binding para acceder a las vistas
- Observa los cambios en el ViewModel

### CurrencyViewModel
- Maneja la lógica de negocio
- Gestiona las llamadas a la API
- Mantiene el estado de la UI
- Procesa los datos de conversión

### ExchangeRateApi
- Interface para las llamadas a la API
- Define los endpoints disponibles
- Utiliza Retrofit para las peticiones HTTP

### Layout Principal
- Diseño moderno con Material Design
- Campos para cantidad y selección de monedas
- Botón de intercambio rápido
- Indicador de carga
- Visualización de resultados

## Manejo de Errores

La aplicación incluye manejo robusto de errores para:
- Errores de conexión
- Tasas de cambio no encontradas
- Errores de API
- Validación de entrada de usuario

## Permisos Requeridos

- `INTERNET`: Para acceder a la API de tasas de cambio
- `ACCESS_NETWORK_STATE`: Para verificar el estado de la conexión 