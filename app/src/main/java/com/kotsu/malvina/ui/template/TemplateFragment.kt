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
    private lateinit var viewDataBinding: TemplateFragBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val factory = InjectionUtils.provideTemplateViewModelFactory(requireContext().applicationContext)
        viewModel = ViewModelProviders.of(this, factory)
            .get(TemplateViewModel::class.java)

        viewDataBinding = TemplateFragBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@TemplateFragment
            }

        return viewDataBinding.root
    }
}