package com.kotsu.malvina.sandbox.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.kotsu.malvina.databinding.FragSandboxMainBinding
import com.kotsu.malvina.ui.BaseFragment
import com.kotsu.malvina.util.extensions.navigateSafe


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

        binding.learningCompose.setOnClickListener {
            navigateToLearningComposeFragment()
        }
    }

    private fun navigate(directions: NavDirections) {
        findNavController()
            .navigateSafe(directions)
    }

    private fun navigateToPickImageFragment() {
        navigate(
            SandboxMainFragmentDirections.actionToPickImage()
        )
    }

    private fun navigateToLearningComposeFragment() {
        navigate(
            SandboxMainFragmentDirections.actionToLearningCompose()
        )
    }
}