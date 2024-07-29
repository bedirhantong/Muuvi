package com.bedirhan.muuvi.feature.authentication.presentation

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bedirhan.muuvi.R
import com.bedirhan.muuvi.databinding.FragmentAuthScreenBinding

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
            if (hasFocus) {
                if (binding.tilEmail.isErrorEnabled) {
                    binding.tilEmail.isErrorEnabled = false
                }
            }
        }

        binding.edtPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (binding.tilPassword.isErrorEnabled) {
                    binding.tilPassword.isErrorEnabled = false
                }
            } else {
                if (isPasswordValid()) {
                    if (binding.tilEmail.isErrorEnabled) {
                        binding.tilEmail.isErrorEnabled = false
                    }
                    binding.tilPassword.apply {
                        setStartIconDrawable(R.drawable.check_circle)
                        setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                    }
                }
            }
        }

        binding.loginButton.setOnClickListener {
            val action = AuthScreenFragmentDirections.actionAuthScreenToHomeScreenFragment()
            findNavController().navigate(action)
        }

        binding.edtEmail.addTextChangedListener(loginTextWatcher)
        binding.edtPassword.addTextChangedListener(loginTextWatcher)

        updateButtonState()
    }

    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            viewModel.email = binding.edtEmail.text.toString()
            viewModel.password = binding.edtPassword.text.toString()
            updateButtonState()
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    private fun updateButtonState() {
        binding.loginButton.isEnabled = viewModel.isLoginButtonEnabled()
        binding.loginButton.backgroundTintList =
            ColorStateList.valueOf(if (binding.loginButton.isEnabled) Color.DKGRAY else Color.LTGRAY)

        if (viewModel.isEmailValid() && viewModel.isPasswordValid()) {
            binding.loginButton.isEnabled = true
            binding.loginButton.backgroundTintList =
                    // color taşı
                ColorStateList.valueOf(Color.parseColor("#1E2C3F"))
        } else {
            binding.loginButton.isEnabled = false
            binding.loginButton.backgroundTintList = ColorStateList.valueOf(Color.LTGRAY)
        }
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
            binding.tilPassword.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}