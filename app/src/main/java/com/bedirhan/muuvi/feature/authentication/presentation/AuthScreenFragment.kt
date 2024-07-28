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
class AuthScreenFragment : Fragment(), View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener {

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

        binding.edtEmail.setText(viewModel.email)
        binding.edtPassword.setText(viewModel.password)

        binding.edtEmail.onFocusChangeListener = this
        binding.edtPassword.onFocusChangeListener = this

        val loginButton = binding.loginButton
        loginButton.setOnClickListener {
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
                ColorStateList.valueOf(Color.parseColor("#1E2C3F"))
        } else {
            binding.loginButton.isEnabled = false
            binding.loginButton.backgroundTintList = ColorStateList.valueOf(Color.LTGRAY)
        }
    }

    private fun validatePassword(): Boolean {
        var errorMessage: String? = null
        val passwordText: String = binding.edtPassword.text.toString()
        if (passwordText.isEmpty()) {
            errorMessage = "Password is required"
        } else if (passwordText.length < 8 || !passwordText.any { it.isUpperCase() } || !passwordText.any { !it.isLetterOrDigit() }) {
            errorMessage = "Password must be at least 8 characters, contain a special character, and a capital letter"
        }
        if (errorMessage != null) {
            binding.tilPassword.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    override fun onClick(view: View?) {}

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.edtEmail -> {
                    if (hasFocus) {
                        if (binding.tilEmail.isErrorEnabled) {
                            binding.tilEmail.isErrorEnabled = false
                        }
                    }
                }
                R.id.edtPassword -> {
                    if (hasFocus) {
                        if (binding.tilPassword.isErrorEnabled) {
                            binding.tilPassword.isErrorEnabled = false
                        }
                    } else {
                        if (validatePassword()) {
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
            }
        }
    }

    override fun onKey(view: View?, event: Int, keyEvent: KeyEvent?): Boolean {
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}