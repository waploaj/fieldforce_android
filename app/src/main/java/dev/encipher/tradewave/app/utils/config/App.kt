package dev.encipher.tradewave.app.utils.config

import androidx.multidex.MultiDexApplication
import dev.encipher.tradewave.app.R
import dev.encipher.tradewave.app.utils.prefs.SharedPrefs
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