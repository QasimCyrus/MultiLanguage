# MultiLanguage
[![Download](https://api.bintray.com/packages/qasimcyrus/maven/language/images/download.svg?version=1.0) ](https://bintray.com/qasimcyrus/maven/language/1.0/link)

Android 多语言国际化框架

#### 集成
```gradle
dependencies {
    implementation 'com.cyrus:language:1.0'
}
```

#### 初始化框架
在 Applicaiton 中初始化：
```kotlin
override fun onCreate() {
    super.onCreate()
    LanguageHelper.init(this)
}
```

#### 应用配置
覆写 Applicaiton 中的方法：
```kotlin
override fun attachBaseContext(base: Context) {
    super.attachBaseContext(LanguageHelper.onAttach(base))
}
```

覆写 ContextWrapper 子类（如 Activity、Service）中的方法（androidx 版本在 Activity 中需要多调用一次 onAttach(Context)）：
```kotlin
override fun attachBaseContext(newBase: Context) {
    super.attachBaseContext(LanguageHelper.onAttach(newBase))
    /*
     * androidx 方法内部调用的代理方法覆盖了我们设置的属性，所以这里再设置一次。
     * 注意：这里传的上下文需要是被覆盖过之后的，所以是 this 不是 newBase。
     */
    LanguageHelper.onAttach(this)
}
```

#### 修改配置
在需要修改配置的地方调用：
```kotlin
LanguageHelper.updateLanguage(context, locale)
```
或者跟随系统语言：
```kotlin
LanguageHelper.autoLanguage(context)
```
并根据项目自身需要重启 Activity。

#### 其他 API
```kotlin
/** 设置语言存储实现 */
public fun setLocaleStore(context: Context, store: LanguageStore): Unit

/** 获取系统语言 */
public fun getSystemLanguage(): Locale

/** 获取设置的语言，[defaultLocale]为获取不到时的默认返回 */
public fun getLanguage(context: Context, defaultLocale: Locale? = null): Locale

/** 获取设置语言的资源 */
public fun getResources(context: Context): Resources

/** 获取对应语言的资源 */
public fun getResourcesByLocale(context: Context, locale: Locale): Resources

/** 通过[id]获取设置语言的对应字符串，[formatArgs]用于置换字符串中的占位符 */
public fun getString(context: Context, id: Int, vararg formatArgs: Any): String

/** 通过[id]获取对应语言的对应字符串，[formatArgs]用于置换字符串中的占位符 */
public fun getStringByLocale(context: Context, locale: Locale, id: Int, vararg formatArgs: Any): String
```
