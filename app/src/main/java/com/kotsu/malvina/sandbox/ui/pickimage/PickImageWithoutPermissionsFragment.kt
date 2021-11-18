package com.kotsu.malvina.sandbox.ui.pickimage

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotsu.malvina.databinding.FragSandboxPickImageBinding
import com.kotsu.malvina.ui.BaseFragment
import com.kotsu.malvina.util.Utils

private const val RC_PICK_IMAGE = 1

/**
 * Simple fragment to test selecting an image from gallery without Gallery permissions
 */
class PickImageWithoutPermissionsFragment : BaseFragment() {

    private lateinit var binding: FragSandboxPickImageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragSandboxPickImageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.pickImageBtn.setOnClickListener {
            showGalleryWithoutPermissions()
        }
    }

    private fun showGalleryWithoutPermissions() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }

        // Check if anything can resolve this intent should be added
        startActivityForResult(intent, RC_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Utils.log("PickImageWithoutPermissionsFragment -> onActivityResult() requestCode:$requestCode, resultCode:$resultCode, data:$data")

        data ?: return

        if (resultCode == RESULT_OK) {

            val selectedImageUri = data.data

            selectedImageUri ?: return

            // Bytes if you need to send them somewhere
            // Should it to be wrapped with try/catch?
            val bytes = Utils.readBytes(requireContext(), selectedImageUri)

            Utils.log("Bytes.size:${bytes?.size}")

            // bitmap from bytes
            val bitmap = BitmapFactory.decodeByteArray(bytes!!, 0, bytes.size)

//            _binding!!.selectedImageView.setImageBitmap(bitmap)
            binding.selectedImageView.setImageURI(selectedImageUri)
        }
    }
}