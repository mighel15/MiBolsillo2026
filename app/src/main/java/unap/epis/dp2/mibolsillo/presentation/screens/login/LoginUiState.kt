package unap.epis.dp2.mibolsillo.presentation.screens.login

sealed class LoginUiState {
    object Initial : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val token: String) : LoginUiState()
    data class Error(val mensaje: String) : LoginUiState()
}
