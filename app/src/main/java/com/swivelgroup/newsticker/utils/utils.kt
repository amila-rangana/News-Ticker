package com.swivelgroup.newsticker.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.util.HashMap

fun getRequestHeaders(): HashMap<String, String> {
    val hashMap = hashMapOf<String, String>()
    hashMap["Content-type"] = "application/json"
    hashMap["Authorization"] = Constants.api_key
//    hashMap["Authorization"] = "dfgsdshdh"
    return hashMap
}

// method to check if the device has an active internet connection
fun isConnected(context: Context): Boolean {
    var connected = false
    return try {
        val cm = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nInfo = cm.activeNetworkInfo
        connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
        connected
    } catch (e: Exception) {
        false
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showAlertDialog(context: Context, title: String, message: String) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton(
        context.getString(android.R.string.ok)
    ) { dialogInterface, _ -> dialogInterface.dismiss() }
    builder.create().show()
}