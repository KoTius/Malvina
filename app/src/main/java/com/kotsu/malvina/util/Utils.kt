package com.kotsu.malvina.util

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.kotsu.malvina.BuildConfig
import com.kotsu.malvina.R
import java.io.IOException
import java.text.NumberFormat
import java.util.concurrent.TimeUnit
import kotlin.math.roundToLong


object Utils {

    fun hideKeyboard(view: View) {
        val context = view.context
        val windowToken = view.windowToken

        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun formatPrice(context: Context, price: Float): String {
        val formatted = NumberFormat.getNumberInstance().format(price.roundToLong())

        return context.getString(R.string.currency, formatted)
    }

    fun formatElapsedTime(context: Context, elapsedSeconds: Long): String {

        val days = TimeUnit.SECONDS.toDays(elapsedSeconds)

        val totalHours = TimeUnit.SECONDS.toHours(elapsedSeconds)

        val hours =  totalHours -
                TimeUnit.DAYS.toHours(days)

        return if (totalHours > 0) {
            val daysString = context.getString(R.string.days)
            val hoursString = context.getString(R.string.hours)
            String.format("%d $daysString %2d $hoursString",
                days,
                hours
            );

        } else {
            context.getString(R.string.less_then_hour)
        }
    }

    fun log(text: String) {
        if (BuildConfig.DEBUG) {
            Log.d("debugLogs", text)
        }
    }

    @Throws(IOException::class)
    fun readBytes(context: Context, uri: Uri): ByteArray? =
        context.contentResolver.openInputStream(uri)
            ?.buffered()
            ?.use {
                it.readBytes()
            }
}