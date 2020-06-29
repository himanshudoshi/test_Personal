package ca.bell.nmf.ui.localization

import android.content.Context
import android.content.res.Configuration
import android.os.Build

import java.util.Locale

/**
 * This class is used to manage the changes for Locale Lang of the app
 */
open class LanguageManager(context: Context) {

    /**
     * @property mContext the context object which requested the Locale Lang change.
     */
    private val mContext = context

    /**
     * @property localeLanguage used to determine the current Locale Lang of the app.
     * if empty from SharedPreferences, it will return the Language from the device Locale configuration.
     */
    val localeLanguage: String
        get() {
            val currentLanguage = InternalData(mContext).getCurrentLanguage()
            return if (currentLanguage.isNotEmpty()) {
                currentLanguage
            } else {
                val configuration = mContext.resources.configuration
                val appLanguageLocale = if (Build.VERSION.SDK_INT >= 24) configuration.locales.get(0) else configuration.locale
                appLanguageLocale.toString()
            }
        }

    companion object {
        // TODO tracking changes
        /**
         * @property ENGLISH_LANGUAGE_ACRONYM const value that defines the english language for the Locale
         */
        const val ENGLISH_LANGUAGE_ACRONYM = "en"

        /**
         * @property FRENCH_LANGUAGE_ACRONYM const value that defines the french language for the Locale
         */
        const val FRENCH_LANGUAGE_ACRONYM = "fr"
    }

    /**
     * this method determines if we should trigger an action if Locale Lang of the app or Device Locale
     * is not En or FR
     * @return TRUE if the language from Locale differ from "en" OR "fr"
     */
    fun shouldPromptLanguageChange(): Boolean {
        return !(localeLanguage.startsWith(ENGLISH_LANGUAGE_ACRONYM, true) ||
                localeLanguage.startsWith(FRENCH_LANGUAGE_ACRONYM, true))
    }

    /**
     * This method is a short version for the "updateThenSetPrefLanguage" when the Dev
     * provides only the Lang to which he wants to change.
     * @param language takes as input the String "en" or "fr" for changing the Locale Lang of the app.
     * @return Context
     */
    fun updateThenSetPrefLanguage(language: String): Context {
        return this.updateThenSetPrefLanguage(mContext, language)
    }

    /**
     * This method takes the String and Context, and makes changes to configuration of the app
     * and stores in SharedPreferences the name of Locale Lang selected by the user.
     * @param language takes as input the String "en" or "fr" for changing the Locale Lang of the app.
     * @param context the context of the application that is passed to change Locale resources.
     * @return Context
     */
    fun updateThenSetPrefLanguage(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        InternalData(context).storePreferenceLanguageLocale(language)
        return context.createConfigurationContext(configuration)
    }
}
