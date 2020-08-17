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
    private lateinit var viewDataBinding: RegistrationFragBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewDataBinding = RegistrationFragBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@RegistrationFragment
            }

        return viewDataBinding.root
    }
}