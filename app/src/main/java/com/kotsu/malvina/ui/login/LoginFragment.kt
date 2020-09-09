package com.kotsu.malvina.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.kotsu.malvina.R
import com.kotsu.malvina.databinding.LoginFragBinding
import com.kotsu.malvina.injection.InjectionUtils
import com.kotsu.malvina.util.Utils


class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private var viewDataBinding: LoginFragBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val factory = InjectionUtils.provideLoginViewModelFactory(requireContext().applicationContext)
        viewModel = ViewModelProviders.of(this, factory)
            .get(LoginViewModel::class.java)

        val binding = LoginFragBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@LoginFragment
                this.viewModel = this@LoginFragment.viewModel
            }


        binding.signIn.setOnClickListener {
            Utils.hideKeyboard(it)
            attemptSignIn()
        }

        viewDataBinding = binding

        subscribeUI()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding = null
    }

    private fun subscribeUI() {

        viewModel.popFromBackStack.observe(this, Observer {
            activity?.finish()
        })

        viewModel.showMessage.observe(this, Observer {
            showToast(it)
        })
    }

    private fun attemptSignIn() {

        val login = viewDataBinding!!.loginInput.text.toString()
        val password = viewDataBinding!!.passwordInput.text.toString()

        if (!validate(login, password)) {
            return
        }

        viewModel.attemptSignIn(login, password)
    }

    private fun validate(login: String, password: String): Boolean {
        var valid = true

        // TODO: add length validation
        if (login.isEmpty()) {
            valid = false
            showLoginInputError(getString(R.string.error_field_required))
        } else {
            showLoginInputError(null)
        }

        if (password.isEmpty()) {
            valid = false
            showPasswordInputError(getString(R.string.error_field_required))
        } else {
            showPasswordInputError(null)
        }

        return valid
    }

    private fun showLoginInputError(errorText: String?) {
        viewDataBinding!!.loginLayout.error = errorText
    }

    private fun showPasswordInputError(errorText: String?) {
        viewDataBinding!!.passwordLayout.error = errorText
    }

    private fun showToast(@StringRes resId: Int) {
        Toast.makeText(context, getString(resId), Toast.LENGTH_LONG).show()
    }

    private fun navigateToRegistration() {
        findNavController().navigate(R.id.action_to_registration_frag)
    }
}