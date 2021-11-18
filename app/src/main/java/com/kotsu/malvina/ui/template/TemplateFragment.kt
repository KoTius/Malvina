package com.kotsu.malvina.ui.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kotsu.malvina.databinding.TemplateFragBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TemplateFragment : Fragment() {

    private val viewModel: TemplateViewModel by viewModels()

    private var viewDataBinding: TemplateFragBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = TemplateFragBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@TemplateFragment
            }

        viewDataBinding = binding

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding = null
    }
}