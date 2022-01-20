package com.perozzi_package.smashmouthsonggenerator

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.FragmentActivity

fun hideKeyboard(activity: Activity, view: View?) {
    if (activity.currentFocus == null) { return }
    val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun copyToClipboard(fragActivity: FragmentActivity, title: String, lyrics: String) {
    val clipboard = fragActivity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(title, lyrics)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(
        fragActivity.applicationContext, fragActivity.resources.getString(R.string.copied_to_clipboard),
        Toast.LENGTH_SHORT
    ).show()
}
