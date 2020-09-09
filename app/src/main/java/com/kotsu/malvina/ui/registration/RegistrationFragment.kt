package com.kotsu.malvina.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kotsu.malvina.databinding.RegistrationFragBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private val viewModel: RegistrationViewModel by viewModels()
    private var viewDataBinding: RegistrationFragBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = RegistrationFragBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@RegistrationFragment
            }

        viewDataBinding = binding

        testNavigation()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding = null
    }

    private fun testNavigation() {
        viewDataBinding!!.toMainActBtn.setOnClickListener {
            navigateToMain()
        }
    }

    private fun navigateToMain() {
//        findNavController().navigate(R.id.action_registration_to_main_act)
//        activity?.finish()
    }
}