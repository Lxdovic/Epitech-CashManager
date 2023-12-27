package com.example.moulamanagerclient.utils

import android.content.Context


import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

    fun setValue(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValue(key: String): String? {
        return sharedPreferences.getString(key, null)
    }
    fun removeValue(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }
}
