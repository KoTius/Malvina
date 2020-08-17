package com.kotsu.malvina.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kotsu.malvina.databinding.RegistrationFragBinding
import com.kotsu.malvina.injection.InjectionUtils


class RegistrationFragment : Fragment() {

    private lateinit var viewModel: RegistrationViewModel
    private lateinit var viewDataBinding: RegistrationFragBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val factory = InjectionUtils.provideRegistrationViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory)
            .get(RegistrationViewModel::class.java)

        viewDataBinding = RegistrationFragBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@RegistrationFragment
            }

        testNavigation()

        return viewDataBinding.root
    }

    private fun testNavigation() {
        viewDataBinding.toMainActBtn.setOnClickListener {
            navigateToMain()
        }
    }

    private fun navigateToMain() {
//        findNavController().navigate(R.id.action_registration_to_main_act)
//        activity?.finish()
    }
}