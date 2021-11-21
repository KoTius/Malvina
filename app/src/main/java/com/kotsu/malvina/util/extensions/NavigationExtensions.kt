/**
 * This required to work around with known issue of navigate() crash when double clicked
 * https://issuetracker.google.com/u/1/issues/118975714
 * The navigateSafe() overloaded methods will just swallow the crash and print the stack trace
 */

package com.kotsu.malvina.util.extensions

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigator


fun NavController.navigateSafe(directions: NavDirections){
    try {
        navigate(directions)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}

fun NavController.navigateSafe(actionId: Int){
    try {
        navigate(actionId)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}

fun NavController.navigateSafe(actionId: Int, args: Bundle){
    try {
        navigate(actionId, args)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}

fun NavController.navigateSafe(directions: NavDirections, args: Navigator.Extras){
    try {
        navigate(directions, args)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}