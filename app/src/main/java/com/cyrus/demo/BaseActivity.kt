package com.cyrus.demo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.cyrus.language.LanguageHelper

/**
 * 活动基类
 *
 * @author Cyrus
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageHelper.onAttach(newBase))
        /*
         * androidx 方法内部调用的代理方法覆盖了我们设置的属性，所以这里再设置一次。
         * 注意：这里传的上下文需要是被覆盖过之后的，所以是 this 不是 newBase。
         */
        LanguageHelper.onAttach(this)
    }
}