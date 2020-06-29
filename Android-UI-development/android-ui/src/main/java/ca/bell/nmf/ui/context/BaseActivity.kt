package ca.bell.nmf.ui.context

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import ca.bell.nmf.ui.R
import ca.bell.nmf.ui.localization.ILocalization
import ca.bell.nmf.ui.localization.LanguageManager
import java.util.*

open class BaseActivity : AppCompatActivity(), ILocalization {

    private var localeLanguage: String? = null

    override fun attachBaseContext(newBase: Context?) {
        val languageManager = LanguageManager(newBase!!)
        super.attachBaseContext(languageManager.updateThenSetPrefLanguage(languageManager.localeLanguage))
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        handleOrientation()
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        handleOrientation()
        if (localeLanguage == null) {
            localeLanguage = LanguageManager(this).localeLanguage
        }
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        localeLanguage?.let {
            if (localeLanguage?.startsWith(LanguageManager(this).localeLanguage)!!) {
                changeLanguageResources()
            }
        }
        changeLanguageResources()
        super.onResume()
    }

    override fun onLanguageChanged(languageName: String) {
        LanguageManager(this).updateThenSetPrefLanguage(this, languageName)
        changeLanguageResources()
    }

    override fun requestLanguageChange() {
    }

    fun handleOrientation() {
        if (!resources.getBoolean(R.bool.tablet)) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            return
        }
    }

    fun changeLanguageResources() {
        val currentResources = this.resources
        val displayMetrics = currentResources.displayMetrics
        val configuration = currentResources.configuration
        configuration.setLocale(Locale(LanguageManager(this).localeLanguage))
        currentResources.updateConfiguration(configuration, displayMetrics)
    }
}