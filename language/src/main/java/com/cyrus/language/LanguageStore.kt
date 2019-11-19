package com.cyrus.language

import android.content.Context
import java.util.*

/**
 * 语言存储接口
 *
 * @author Cyrus
 */
interface LanguageStore {

    /** 获取语言，[defaultLocale]为获取不到时的默认返回 */
    public fun getLanguage(context: Context, defaultLocale: Locale): Locale

    /** 保存语言 */
    public fun saveLanguage(context: Context, locale: Locale): Unit
}