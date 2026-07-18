package unap.epis.dp2.mibolsillo.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<LoginUiState> (LoginUiState.Initial)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _nombre = MutableStateFlow<String> ("")
    val nombre: StateFlow<String> = _nombre.asStateFlow()

    private val _email = MutableStateFlow<String> ("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _passwordIsVisible = MutableStateFlow<Boolean> (false)
    val passwordIsVisible: StateFlow<Boolean> = _passwordIsVisible.asStateFlow()

    private val _password = MutableStateFlow<String> ("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun doLogin() {
        viewModelScope.launch {
            try {
                _uiState.value = LoginUiState.Loading
                // do the login
                if(_email.value != "maceituno@unap.edu.pe" || _password.value != "123456")
                    _uiState.value = LoginUiState.Error("Correo o contrasena incorrectos")
                else
                    _uiState.value = LoginUiState.Success("Welcome")

                delay(5000.milliseconds)
                _uiState.value = LoginUiState.Success("Welcome")

            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun registrar() {
        viewModelScope.launch {
            try {
                _uiState.value = LoginUiState.Loading
                // do the register
                delay(5000.milliseconds)
                _uiState.value = LoginUiState.Success("Welcome")

            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onNameChange(newName: String) {
        _nombre.value = newName
    }

}