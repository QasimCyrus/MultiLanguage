package com.cyrus.demo

import android.content.Intent
import android.os.Bundle
import com.cyrus.language.LanguageHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val locale = LanguageHelper.getLanguage(this)
        val systemLocale = LanguageHelper.getSystemLanguage()
        val systemResources = LanguageHelper.getResourcesByLocale(this, systemLocale)
        val zhResources = LanguageHelper.getResourcesByLocale(this, Locale.SIMPLIFIED_CHINESE)
        val zhTwResources = LanguageHelper.getResourcesByLocale(this, Locale.TRADITIONAL_CHINESE)
        val enResources = LanguageHelper.getResourcesByLocale(this, Locale.ENGLISH)

        btnAuto.text = systemResources.getString(R.string.system_auto)
        btnZh.text = zhResources.getString(R.string.simplified_chinese)
        btnZhTw.text = zhTwResources.getString(R.string.traditional_chinese)
        btnEn.text = enResources.getString(R.string.english)
        tvCurrent.text = getString(R.string.current_language_xx, getDisplayName(locale))
        tvSystem.text = systemResources.getString(
            R.string.system_language_xx,
            getDisplayName(systemLocale)
        )

        btnAuto.setOnClickListener { autoLanguage() }
        btnZh.setOnClickListener { updateLanguage(Locale.SIMPLIFIED_CHINESE) }
        btnZhTw.setOnClickListener { updateLanguage(Locale.TRADITIONAL_CHINESE) }
        btnEn.setOnClickListener { updateLanguage(Locale.ENGLISH) }
    }

    private fun getDisplayName(locale: Locale?): String? {
        return locale?.let {
            val displayLanguage = it.getDisplayLanguage(it)
            val displayCountry = it.getDisplayCountry(it)
            if (displayCountry.isNotEmpty()) {
                "$displayLanguage($displayCountry)"
            } else {
                displayLanguage
            }
        }
    }

    private fun autoLanguage() {
        LanguageHelper.autoLanguage(this)
        recreateActivity()
    }

    private fun updateLanguage(locale: Locale) {
        LanguageHelper.updateLanguage(this, locale)
        recreateActivity()
    }

    private fun recreateActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

