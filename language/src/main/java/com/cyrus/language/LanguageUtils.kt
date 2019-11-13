package com.cyrus.language

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import androidx.annotation.RequiresApi
import java.util.*

/**
 * 语言工具类
 *
 * @author Cyrus
 */
internal object LanguageUtils {

    /** 使用[locale]更新配置 */
    internal fun updateConfiguration(context: Context, locale: Locale): Context {
        return when {
            Build.VERSION.SDK_INT == Build.VERSION_CODES.N -> {
                updateConfigurationAfterApi24(context, locale)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 -> {
                updateConfigurationAfterApi17(context, locale)
            }
            else -> updateConfigurationLegacy(context, locale)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateConfigurationAfterApi24(context: Context, locale: Locale): Context {
        val configuration = context.resources.configuration
        val localeList = LocaleList(locale)
        LocaleList.setDefault(localeList)
        configuration.setLocales(localeList)
        return context.createConfigurationContext(configuration)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun updateConfigurationAfterApi17(context: Context, locale: Locale): Context {
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    @Suppress("DEPRECATION")
    private fun updateConfigurationLegacy(context: Context, locale: Locale): Context {
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }

    /** 获取对应语言的资源 */
    internal fun getResources(context: Context, locale: Locale): Resources {
        return when {
            Build.VERSION.SDK_INT == Build.VERSION_CODES.N -> {
                getResourcesAfterApi24(context, locale)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 -> {
                getResourcesAfterApi17(context, locale)
            }
            else -> getResourcesLegacy(context, locale)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getResourcesAfterApi24(context: Context, locale: Locale): Resources {
        return Configuration().let {
            it.setLocales(LocaleList(locale))
            context.createConfigurationContext(it).resources
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun getResourcesAfterApi17(context: Context, locale: Locale): Resources {
        return Configuration().let {
            it.setLocale(locale)
            context.createConfigurationContext(it).resources
        }
    }

    @Suppress("DEPRECATION")
    private fun getResourcesLegacy(context: Context, locale: Locale): Resources {
        return Configuration().let {
            it.locale = locale
            Resources(context.assets, context.resources.displayMetrics, it)
        }
    }
}