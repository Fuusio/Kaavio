package org.fuusio.kaavio.app.feature.signup.ui

import org.fuusio.kaavio.app.feature.signup.domain.SignUpInfo

sealed class SignUpState

object SignUpInfoInput : SignUpState()

data class SignUpInfoReady(val signUpInfo: SignUpInfo) : SignUpState()

object SignUpRequested : SignUpState()

object SignUpSucceeded : SignUpState()

object SignUpFailed : SignUpState()