package org.fuusio.kaavio.app.feature.signup.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.progressindicator.CircularProgressIndicator
import org.fuusio.kaavio.app.databinding.SignUpFragmentBinding
import org.fuusio.kaavio.app.feature.signup.domain.*
import org.fuusio.kaavio.extensions.connect

class SignUpFragment : Fragment() {

    private lateinit var viewModel: SignUpViewModelNew

    private lateinit var userNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var confirmEmailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var progressIndicator: CircularProgressIndicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel = ViewModelProvider(this).get(SignUpViewModelNew::class.java)
        val binding = SignUpFragmentBinding.inflate(inflater)

        userNameEditText = binding.editTextUserName
        emailEditText = binding.editTextEmail
        confirmEmailEditText = binding.editTextConfirmEmail
        passwordEditText = binding.editTextPassword
        confirmPasswordEditText = binding.editTextConfirmPassword
        signUpButton = binding.buttonSignUp
        progressIndicator = binding.progressIndicator

        connectViewModel()
        return binding.root
    }

    private fun connectViewModel() {
        viewModel.apply {
            userNameEditText  connect userName.input
            emailEditText  connect email1.input
            confirmEmailEditText  connect email2.input
            passwordEditText  connect password1.input
            confirmPasswordEditText  connect password2.input
            signUpButton connect doSignUp.input

            infoInputState.observe(viewLifecycleOwner) { errors -> onInputStateChanged(errors)}
            signUpState.observe(viewLifecycleOwner) { state -> onSignUpStateChanged(state) }
        }
    }

    private fun onInputStateChanged(inputState: InputState) {
        when (inputState) {
            is UserNameInputState -> { userNameEditText.error = inputState.error }
            is EmailInputState -> { emailEditText.error = inputState.error }
            is EmailConfirmInputState -> { confirmEmailEditText.error = inputState.error}
            is PasswordInputState -> { passwordEditText.error = inputState.error }
            is PasswordConfirmInputState -> { confirmPasswordEditText.error = inputState.error }
        }
    }

    private fun onSignUpStateChanged(state: SignUpState) {
        when (state) {
            is SignUpInfoNotReady -> { signUpButton.isEnabled = false }
            is SignUpInfoReady -> { signUpButton.isEnabled = true }
            is SignUpRequested -> {
                setEditTextsEnabled(false)
                signUpButton.isEnabled = false
                showProgressIndicator()
            }
            is SignUpSucceeded -> {
                hideProgressIndicator()
            }
            is SignUpFailed -> {
                hideProgressIndicator()
                setEditTextsEnabled(true)
                signUpButton.isEnabled = true
            }
        }
    }

    private fun setEditTextsEnabled(isEnabled: Boolean) {
        userNameEditText.isEnabled = isEnabled
        emailEditText.isEnabled = isEnabled
        confirmEmailEditText.isEnabled = isEnabled
        passwordEditText.isEnabled = isEnabled
        confirmPasswordEditText.isEnabled = isEnabled
    }

    override fun onResume() {
        super.onResume()
        viewModel.activate()
    }

    override fun onStop() {
        super.onStop()
        viewModel.dispose()
    }

    private fun showProgressIndicator() {
        progressIndicator.show()
    }

    private fun hideProgressIndicator() {
        progressIndicator.hide()
    }
}