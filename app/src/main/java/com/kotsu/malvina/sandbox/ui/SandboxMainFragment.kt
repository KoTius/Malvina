package com.kotsu.malvina.sandbox.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kotsu.malvina.databinding.FragSandboxMainBinding
import com.kotsu.malvina.ui.BaseFragment


/**
 * Hosts buttons for navigating to different sandbox fragments or things
 */
class SandboxMainFragment : BaseFragment() {

    private lateinit var binding: FragSandboxMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragSandboxMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.pickImage.setOnClickListener {
            navigateToPickImageFragment()
        }
    }

    private fun navigateToPickImageFragment() {
        findNavController().navigate(
            SandboxMainFragmentDirections.actionToPickImage()
        )
    }
}