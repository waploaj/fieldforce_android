package dev.encipher.tradewave.app.utils.prefs

import android.content.Context


class SharedPrefs(context: Context){

    val prefName = "prefs"
    val prefs = context.getSharedPreferences(prefName, 0)

    private val IS_LOGGED_IN = "isLoggedIn"
    private val AUTH_TOKEN = "authToken"
    private val USER = "user"
    private val CALL_CARD = "callCard"
    private val VISIT_TYPE = "visitType"

    var isLoggedIn: Boolean
        get() = prefs.getBoolean(IS_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(IS_LOGGED_IN, value).apply()

    var user: String?
        get() = prefs.getString(USER, "")
        set(value) = prefs.edit().putString(USER, value).apply()

    var callCard: String?
        get() = prefs.getString(CALL_CARD, "")
        set(value) = prefs.edit().putString(CALL_CARD, value).apply()

    var authToken: String
        get() = prefs.getString(AUTH_TOKEN, "")!!
        set(value) = prefs.edit().putString(AUTH_TOKEN, value).apply()

    var visitType: String
        get() = prefs.getString(VISIT_TYPE, "")!!
        set(value) = prefs.edit().putString(VISIT_TYPE, value).apply()
}