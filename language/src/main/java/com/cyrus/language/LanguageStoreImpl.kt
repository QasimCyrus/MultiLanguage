package com.cyrus.language

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.preference.PreferenceManager
import java.util.*

/**
 * 语言存储实现类
 *
 * @author Cyrus
 */
internal object LanguageStoreImpl : LanguageStore {

    private const val KEY_LANGUAGE = "language_store.language"
    private const val KEY_COUNTRY = "language_store.country"
    private const val KEY_VARIANT = "language_store.variant"
    private const val KEY_LANGUAGE_TAG = "language_store.language_tag"

    override fun getLanguage(context: Context, defaultLocale: Locale): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getLanguageAfterApi21(context, defaultLocale)
        } else {
            getLanguageLegacy(context, defaultLocale)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getLanguageAfterApi21(context: Context, defaultLocale: Locale): Locale {
        return getPreferences(context).getString(KEY_LANGUAGE_TAG, defaultLocale.toLanguageTag())
            ?.let { Locale.forLanguageTag(it) } ?: Locale.getDefault()
    }

    private fun getLanguageLegacy(context: Context, defaultLocale: Locale?): Locale {
        val preferences = getPreferences(context)
        return preferences.getString(KEY_LANGUAGE, defaultLocale?.language)?.let {
            val country = preferences.getString(KEY_COUNTRY, defaultLocale?.country) ?: ""
            val variant = preferences.getString(KEY_VARIANT, defaultLocale?.variant) ?: ""
            Locale(it, country, variant)
        } ?: Locale.getDefault()
    }

    override fun saveLanguage(context: Context, locale: Locale) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            saveLanguageAfterApi21(context, locale)
        } else {
            saveLanguageLegacy(context, locale)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun saveLanguageAfterApi21(context: Context, locale: Locale) {
        getPreferences(context).edit()
            .putString(KEY_LANGUAGE_TAG, locale.toLanguageTag())
            .apply()
    }

    private fun saveLanguageLegacy(context: Context, locale: Locale) {
        getPreferences(context).edit()
            .putString(KEY_LANGUAGE, locale.language)
            .putString(KEY_COUNTRY, locale.country)
            .putString(KEY_VARIANT, locale.variant)
            .apply()
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}