package com.bytcode.tradetool.app.utils.config

import android.support.multidex.MultiDexApplication
import com.bytcode.tradetool.app.R
import com.bytcode.tradetool.app.utils.prefs.SharedPrefs
import io.sentry.Sentry
import io.sentry.android.AndroidSentryClientFactory

class App: MultiDexApplication(){

    companion object {
        lateinit var sharedPrefs: SharedPrefs
    }

    override fun onCreate() {
        sharedPrefs = SharedPrefs(applicationContext)

        val sentryDsn = getString(R.string.sentry_dsn)
        Sentry.init(
            sentryDsn,
            AndroidSentryClientFactory(applicationContext)
        )

        super.onCreate()
    }
}