package org.fuusio.kaavio.app.feature.signup.ui


import org.fuusio.kaavio.Kaavio.inputs
import org.fuusio.kaavio.Kaavio.outputs
import org.fuusio.kaavio.app.feature.signup.domain.*
import org.fuusio.kaavio.graph.GraphViewModel
import org.fuusio.kaavio.node.comparison.Equals
import org.fuusio.kaavio.node.function.Fun3
import org.fuusio.kaavio.node.logic.And
import org.fuusio.kaavio.node.state.StringVar
import org.fuusio.kaavio.node.validation.EmailValidator
import org.fuusio.kaavio.node.validation.ValidatorFun
import org.fuusio.kaavio.node.state.LiveDataNode
import org.fuusio.kaavio.node.stream.Map
import org.fuusio.kaavio.node.function.Fun2

data class SignUpViewModel(

    // Graph input nodes
    val userName: StringVar = StringVar(),
    val email1: StringVar = StringVar(),
    val email2: StringVar = StringVar(),
    val password1: StringVar = StringVar(),
    val password2: StringVar = StringVar(),
    val doSignUp: Map<Unit, SignUpState> = Map { SignUpRequested },

    // LiveData nodes
    val infoInputState: LiveDataNode<InputState> = LiveDataNode(),
    val signUpState: LiveDataNode<SignUpState> = LiveDataNode(),

    // Internal nodes
    internal val isUserNameValid: ValidatorFun<String> = ValidatorFun { string -> string.length > 2 },
    internal val isEmailValid: EmailValidator = EmailValidator(),
    internal val areEmailsEqual: Equals<String> = Equals(),
    internal val isPasswordValid: ValidatorFun<String> = ValidatorFun { string -> string.length >= 8 },
    internal val arePasswordsEqual: Equals<String> = Equals(),
    internal val isSignUpInfoValid: And = And(),
    internal val mapSignUpInfo: Fun3<String, String, String, SignUpInfo> =
        Fun3 { user, email, password -> SignUpInfo(email = email, password = password, user = user) },
    internal val onSignUpInfoReady: Fun2<Boolean, SignUpInfo, SignUpState> =
        Fun2 { isInfoValid, info ->
            if (isInfoValid) SignUpInfoReady(info) else SignUpInfoNotReady},
    internal val onUserNameValidationChanged: Map<Boolean, InputState> = Map { isValid ->
        UserNameInputState(if (isValid) null else "Invalid User Name")
    },
    internal val onEmailValidationChanged: Map<Boolean, InputState> = Map { isValid ->
        EmailInputState(if (isValid) null else "Invalid email")
    },
    internal val onEmailsEqualChanged: Map<Boolean, InputState> = Map { isValid ->
        EmailConfirmInputState(if (isValid) null else "Emails do not match")
    },
    internal val onPasswordValidationChanged: Map<Boolean, InputState> = Map { isValid ->
        PasswordInputState(if (isValid) null else "Invalid password")
    },
    internal val onPasswordsEqualChanged: Map<Boolean, InputState> = Map { isValid ->
        PasswordConfirmInputState(if (isValid) null else "Passwords do not match")
    },
    ) : GraphViewModel() {

    override fun onConnectNodes() {
        userName.output connect inputs(isUserNameValid.input, mapSignUpInfo.arg1)
        email1.output connect inputs(isEmailValid.input, mapSignUpInfo.arg2)
        password1.output connect inputs(isPasswordValid.input, mapSignUpInfo.arg3)
        isSignUpInfoValid.output connect onSignUpInfoReady.arg1
        mapSignUpInfo.output connect onSignUpInfoReady.arg2

        areEmailsEqual.input connect outputs(email1.output, email2.output)
        arePasswordsEqual.input connect outputs(password1.output, password2.output)

        isSignUpInfoValid.input connect outputs(
            isUserNameValid.output,
            isEmailValid.output,
            areEmailsEqual.output,
            arePasswordsEqual.output,
            isPasswordValid.output)

        isUserNameValid.output connect onUserNameValidationChanged.input
        isEmailValid.output connect onEmailValidationChanged.input
        areEmailsEqual.output connect onEmailsEqualChanged.input
        arePasswordsEqual.output connect onPasswordsEqualChanged.input
        isPasswordValid.output connect onPasswordValidationChanged.input

        onUserNameValidationChanged.output connect infoInputState.input
        onEmailValidationChanged.output connect infoInputState.input
        onEmailsEqualChanged.output connect infoInputState.input
        onPasswordValidationChanged.output connect infoInputState.input
        onPasswordsEqualChanged.output connect infoInputState.input

        doSignUp.output connect signUpState.input
        onSignUpInfoReady.output connect signUpState.input
    }

    override fun onDispose() {}
}