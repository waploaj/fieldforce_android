package com.bytcode.tradetool.app.controllers.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.bytcode.tradetool.app.R

class SplashActivity : AppCompatActivity() {

    private var progressStatus = 0
    // lateinit var pb: ProgressBar

    private val splashDelay = 5000L
    private var splashDelayHandler: Handler? = null

    private val splashRunnable: Runnable = Runnable {

        progressStatus = 0
        // pb = findViewById(R.id.splashProgressBar)
        Thread(Runnable {
            do {
                progressStatus += 1
                try {
                    Thread.sleep(20)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                splashDelayHandler?.post {
                    // pb.progress = progressStatus
                }
                if (progressStatus == 100) {
                    val activityIntent = Intent(this, LoginActivity::class.java)
                    startActivity(activityIntent)
                    finish()
                    break
                }
            } while (progressStatus < 100)
        }).start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)
        splashDelayHandler = Handler()
        splashDelayHandler!!.postDelayed(splashRunnable, splashDelay)

    }

    public override fun onDestroy() {

        if (splashDelayHandler != null) {
            splashDelayHandler!!.removeCallbacks(splashRunnable)
        }
        super.onDestroy()
    }
}
