package com.kotsu.malvina.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.kotsu.malvina.R

/**
 * Contains login and registration fragments
 */
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_act)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}