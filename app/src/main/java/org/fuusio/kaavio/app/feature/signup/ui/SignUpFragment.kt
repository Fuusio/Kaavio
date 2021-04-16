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
import org.fuusio.kaavio.app.databinding.FragmentSignUpBinding
import org.fuusio.kaavio.extensions.connect

class SignUpFragment : Fragment() {

    private lateinit var viewModel: SignUpViewModel

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
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        val binding = FragmentSignUpBinding.inflate(inflater)

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

            signUpState.observe(viewLifecycleOwner) { state -> onSignUpStateChanged(state) }
        }
    }

    private fun onSignUpStateChanged(state: SignUpState) {
        when (state) {
            is SignUpInfoInput -> { signUpButton.isEnabled = false }
            is SignUpInfoReady -> { signUpButton.isEnabled = true }
            is SignUpRequested -> {
                showProgressIndicator()
            }
            is SignUpSucceeded -> {
                hideProgressIndicator()
            }
            is SignUpFailed -> {
                hideProgressIndicator()
            }
        }
    }

    private fun showProgressIndicator() {
        progressIndicator.show()
    }

    private fun hideProgressIndicator() {
        progressIndicator.hide()
    }
}