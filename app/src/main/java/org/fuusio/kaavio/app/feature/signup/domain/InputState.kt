package org.fuusio.kaavio.app.feature.signup.domain

sealed class InputState

data class UserNameInputState(val error: String? = null) : InputState()

data class EmailInputState(val error: String? = null) : InputState()

data class EmailConfirmInputState(val error: String? = null) : InputState()

data class PasswordInputState(val error: String? = null) : InputState()

data class PasswordConfirmInputState(val error: String? = null) : InputState()
