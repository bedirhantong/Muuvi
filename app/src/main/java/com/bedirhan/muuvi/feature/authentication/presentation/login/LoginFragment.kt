package com.bedirhan.muuvi.feature.authentication.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bedirhan.muuvi.R
import com.bedirhan.muuvi.databinding.FragmentLoginBinding
import com.bedirhan.muuvi.feature.authentication.presentation.AuthScreenFragmentDirections
import com.bedirhan.muuvi.feature.authentication.presentation.AuthScreenViewModel
import com.bedirhan.muuvi.utils.extensions.addTextChangedListener
import com.bedirhan.muuvi.utils.extensions.clearError
import com.bedirhan.muuvi.utils.extensions.setErrorC
import com.bedirhan.muuvi.utils.extensions.setSuccessIcon
import com.bedirhan.muuvi.utils.extensions.updateState

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            edtLoginEmail.setText(viewModel.email)
            edtLoginPassword.setText(viewModel.password)
        }

        binding.edtLoginEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.tilLoginEmail.clearError()
        }

        binding.edtLoginPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (binding.tilLoginPassword.isErrorEnabled) {
                    binding.tilLoginPassword.clearError()
                }
            } else {
                if (isPasswordValid()) {
                    binding.tilLoginPassword.setSuccessIcon()
                }
            }
        }

        binding.loginButton.setOnClickListener {
            val action = AuthScreenFragmentDirections.actionAuthScreenToHomeScreenFragment()
            findNavController().navigate(action)
        }

        binding.edtLoginEmail.addTextChangedListener { text ->
            viewModel.email = text
            updateButtonState()
        }

        binding.edtLoginPassword.addTextChangedListener { text ->
            viewModel.password = text
            updateButtonState()
        }

        updateButtonState()
    }

    private fun updateButtonState() {
        binding.loginButton.updateState(
            viewModel.isLoginButtonEnabled(),
            requireContext()
        )
    }

    private fun isPasswordValid(): Boolean {
        var errorMessage: String? = null
        val passwordText: String = binding.edtLoginPassword.text.toString()
        if (passwordText.isEmpty()) {
            errorMessage = getString(R.string.password_required)
        } else if (passwordText.length < 8 || !passwordText.any { it.isUpperCase() } || !passwordText.any { !it.isLetterOrDigit() }) {
            errorMessage = getString(R.string.password_detail)
        }
        if (errorMessage != null) {
            binding.tilLoginPassword.setErrorC(errorMessage)
        }
        return errorMessage == null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}