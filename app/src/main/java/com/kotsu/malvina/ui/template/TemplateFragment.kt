package com.kotsu.malvina.ui.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kotsu.malvina.databinding.TemplateFragBinding
import com.kotsu.malvina.injection.InjectionUtils


class TemplateFragment : Fragment() {

    private lateinit var viewModel: TemplateViewModel
    private var viewDataBinding: TemplateFragBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val factory = InjectionUtils.provideTemplateViewModelFactory(requireContext().applicationContext)
        viewModel = ViewModelProviders.of(this, factory)
            .get(TemplateViewModel::class.java)

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