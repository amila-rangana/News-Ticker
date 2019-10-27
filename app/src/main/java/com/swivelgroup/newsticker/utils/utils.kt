package com.swivelgroup.newsticker.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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

fun getDateString(dateAndTime: String): String {

    // Create a DateFormatter object for displaying date in specified format.
    val receivedFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val sendFormat = SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.getDefault())

    receivedFormat.timeZone = TimeZone.getTimeZone("UTC")

    // Create a calendar object that will convert the date and time value in milliseconds to date.
    val calendar = Calendar.getInstance()
    try {
        calendar.time = receivedFormat.parse(dateAndTime)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    val sendDate = sendFormat.format(calendar.time)
//    val relativeTime = DateUtils.getRelativeTimeSpanString(calendar.timeInMillis)
//    val sendFormatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    return sendDate.toString()
}