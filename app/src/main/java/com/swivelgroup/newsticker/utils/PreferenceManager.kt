package com.swivelgroup.newsticker.utils

import android.content.Context
import android.content.SharedPreferences
import com.swivelgroup.newsticker.R

class PreferenceManager(context: Context) {

    var sharedPreferences: SharedPreferences
    var editor: SharedPreferences.Editor

    init {
        sharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name) + "_data", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun saveUserName(userName: String){
        editor.putString(Constants.data_username, userName).commit()
    }

    fun getUserName(): String?{
        return sharedPreferences.getString(Constants.data_username, "")
    }

    fun savePrefs(prefs: String){

        val arrayListString = getPref()
        if (!arrayListString.contains(prefs)) {
            arrayListString.add(prefs)

            val stringBuilder = StringBuilder()
            arrayListString.forEach {
                stringBuilder.append("$it,")
            }

            editor.putString(Constants.data_preferences, stringBuilder.toString().trim()).commit()
        }
    }

    fun getPref():ArrayList<String>{

        val csvString = sharedPreferences.getString(Constants.data_preferences, "")
        val  arrayPrefs = csvString?.split(",")
        val stringArrayList = ArrayList<String>()

        if (arrayPrefs != null && arrayPrefs.isNotEmpty()) {
            arrayPrefs.iterator().forEach {
                if (it.isNotEmpty())
                stringArrayList.add(it)
            }
        }
        return stringArrayList
    }
}