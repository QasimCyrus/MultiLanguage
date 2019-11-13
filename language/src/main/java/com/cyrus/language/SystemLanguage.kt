package com.cyrus.language

import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.*

/**
 * 系统语言
 *
 * @author Cyrus
 */
internal object SystemLanguage : ComponentCallbacks2 {

    internal var locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Resources.getSystem().configuration.locales.get(0)
    } else {
        @Suppress("DEPRECATION")
        Resources.getSystem().configuration.locale
    }
    private set

    internal fun register(context: Context) {
        context.registerComponentCallbacks(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newConfig.locales[0]
        } else {
            @Suppress("DEPRECATION")
            newConfig.locale
        }
    }

    override fun onLowMemory() {
    }

    override fun onTrimMemory(level: Int) {
    }
}
