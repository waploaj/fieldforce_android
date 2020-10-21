package dev.encipher.tradewave.app.controllers.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.FrameLayout
import dev.encipher.tradewave.app.R
import dev.encipher.tradewave.app.utils.config.App
import dev.encipher.tradewave.app.utils.helpers.DotProgressBar

class SplashActivity : AppCompatActivity() {

    private var progressStatus = 0
    private lateinit var dotProgressBar: DotProgressBar
    private val splashDelay = 5000L
    private var splashDelayHandler: Handler? = null

    private val splashRunnable: Runnable = Runnable {

        progressStatus = 0
        Thread(Runnable {
            do {
                progressStatus += 1
                try {
                    Thread.sleep(20)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                if (progressStatus == 100) {
                    if(!App.sharedPrefs.isLoggedIn) {
                        val activityIntent = Intent(this, LoginActivity::class.java)
                        startActivity(activityIntent)
                        finish()
                    }else{
                        val activityIntent = Intent(this, HomeActivity::class.java)
                        startActivity(activityIntent)
                        finish()
                    }
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
        val frameLayout = findViewById<FrameLayout>(R.id.frame_layout)


        dotProgressBar = DotProgressBar.Builder()
            .setMargin(1)
            .setAnimationDuration(2000)
            .setDotBackground(R.drawable.ic_dot)
            .setMaxScale(1f)
            .setMinScale(0.3f)
            .setNumberOfDots(5)
            .setdotRadius(16)
            .build(this@SplashActivity)
        frameLayout.addView(dotProgressBar)
        dotProgressBar.startAnimation()

        splashDelayHandler = Handler()
        splashDelayHandler!!.postDelayed(splashRunnable, splashDelay)

    }

    public override fun onDestroy() {
        dotProgressBar.stopAnimation()
        if (splashDelayHandler != null) {
            splashDelayHandler!!.removeCallbacks(splashRunnable)
        }
        super.onDestroy()
    }
}
