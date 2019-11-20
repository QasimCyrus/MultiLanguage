package com.cyrus.languagedemo

import android.app.Application
import android.content.Context
import com.cyrus.language.LanguageHelper

/**
 * 应用
 *
 * @author Cyrus
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        LanguageHelper.init(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageHelper.onAttach(base))
    }
}