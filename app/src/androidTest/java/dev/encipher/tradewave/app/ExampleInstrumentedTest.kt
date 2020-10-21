package dev.encipher.tradewave.app

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the dev.encipher.tradewave.app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("dev.encipher.tradewave.app", appContext.packageName)
    }
}
