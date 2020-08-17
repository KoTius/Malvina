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
    private lateinit var viewDataBinding: TemplateFragBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewDataBinding = TemplateFragBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@TemplateFragment
            }

        return viewDataBinding.root
    }
}