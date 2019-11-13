package com.cyrus.language

import android.content.Context
import android.content.res.Resources
import java.util.*

/**
 * 语言助手
 *
 * @author Cyrus
 */
object LanguageHelper {

    private var languageStore: LanguageStore = LanguageStoreImpl

    /** 在[android.app.Application.onCreate]进行初始化 */
    public fun init(context: Context): Unit {
        SystemLanguage.register(context)
    }

    /** 设置语言存储实现 */
    public fun setLocaleStore(context: Context, store: LanguageStore): Unit {
        // 把旧存储的数据迁移到新的存储实现上
        store.saveLanguage(context, languageStore.getLanguage(context, Locale.getDefault()))
        languageStore = store
    }

    /** 在[android.content.ContextWrapper.attachBaseContext]的覆写方法中调用 */
    public fun onAttach(context: Context): Context {
        return languageStore.getLanguage(context, Locale.getDefault()).let {
            LanguageUtils.updateConfiguration(context, it)
        }
    }

    /** 跟随系统语言 */
    public fun autoLanguage(context: Context): Context {
        return updateLanguage(context, SystemLanguage.locale)
    }

    /** 使用[locale]更新配置 */
    public fun updateLanguage(context: Context, locale: Locale): Context {
        languageStore.saveLanguage(context, locale)
        return LanguageUtils.updateConfiguration(context, locale)
    }

    /** 获取系统语言 */
    public fun getSystemLanguage(): Locale = SystemLanguage.locale

    /** 获取设置的语言，[defaultLocale]为获取不到时的默认返回 */
    public fun getLanguage(context: Context, defaultLocale: Locale? = null): Locale {
        return languageStore.getLanguage(context, defaultLocale ?: Locale.getDefault())
    }

    /** 获取设置语言的资源 */
    public fun getResources(context: Context): Resources {
        return getResourcesByLocale(context, getLanguage(context, Locale.getDefault()))
    }

    /** 获取对应语言的资源 */
    public fun getResourcesByLocale(context: Context, locale: Locale): Resources {
        return LanguageUtils.getResources(context, locale)
    }

    /** 通过[id]获取设置语言的对应字符串，[formatArgs]用于置换字符串中的占位符 */
    public fun getString(context: Context, id: Int, vararg formatArgs: Any): String {
        return getStringByLocale(context, getLanguage(context, Locale.getDefault()), id, formatArgs)
    }

    /** 通过[id]获取对应语言的对应字符串，[formatArgs]用于置换字符串中的占位符 */
    public fun getStringByLocale(
        context: Context,
        locale: Locale,
        id: Int,
        vararg formatArgs: Any
    ): String {
        return getResourcesByLocale(context, locale).getString(id, formatArgs)
    }
}