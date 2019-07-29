package com.bytcode.tradetool.app.utils.prefs

import android.content.Context


class SharedPrefs(context: Context){

    val prefName = "prefs"
    val prefs = context.getSharedPreferences(prefName, 0)

    private val IS_LOGGED_IN = "isLoggedIn"
    private val AUTH_TOKEN = "authToken"
    private val USER = "user"
    private val CALL_CARD = "callCard"

    var isLoggedIn: Boolean
        get() = prefs.getBoolean(IS_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean("isLoggedIn", value).apply()

    var user: String?
        get() = prefs.getString(USER, "")
        set(value) = prefs.edit().putString("user", value).apply()

    var callCard: String?
        get() = prefs.getString(CALL_CARD, "")
        set(value) = prefs.edit().putString(CALL_CARD, value).apply()

    var authToken: String
        get() = prefs.getString(AUTH_TOKEN, "")!!
        set(value) = prefs.edit().putString("authToken", value).apply()
}