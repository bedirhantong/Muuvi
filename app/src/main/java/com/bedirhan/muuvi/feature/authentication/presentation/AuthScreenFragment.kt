package com.bedirhan.muuvi.feature.authentication.presentation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bedirhan.muuvi.R
import com.bedirhan.muuvi.databinding.FragmentAuthScreenBinding
import com.bedirhan.muuvi.utils.extensions.addTextChangedListener
import com.bedirhan.muuvi.utils.extensions.clearError
import com.bedirhan.muuvi.utils.extensions.setErrorC
import com.bedirhan.muuvi.utils.extensions.setSuccessIcon
import com.bedirhan.muuvi.utils.extensions.updateState

class AuthScreenFragment : Fragment() {

    private var _binding: FragmentAuthScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            edtEmail.setText(viewModel.email)
            edtPassword.setText(viewModel.password)
        }

        binding.edtEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.tilEmail.clearError()
        }

        binding.edtPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (binding.tilPassword.isErrorEnabled) {
                    binding.tilPassword.clearError()
                }
            } else {
                if (isPasswordValid()) {
                    binding.tilPassword.setSuccessIcon()
                }
            }
        }

        binding.loginButton.setOnClickListener {
            val action = AuthScreenFragmentDirections.actionAuthScreenToHomeScreenFragment()
            findNavController().navigate(action)
        }

        binding.edtEmail.addTextChangedListener { text ->
            viewModel.email = text
            updateButtonState()
        }

        binding.edtPassword.addTextChangedListener { text ->
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
        val passwordText: String = binding.edtPassword.text.toString()
        if (passwordText.isEmpty()) {
            errorMessage = getString(R.string.password_required)
        } else if (passwordText.length < 8 || !passwordText.any { it.isUpperCase() } || !passwordText.any { !it.isLetterOrDigit() }) {
            errorMessage = getString(R.string.password_detail)
        }
        if (errorMessage != null) {
            binding.tilPassword.setErrorC(errorMessage)
        }
        return errorMessage == null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
