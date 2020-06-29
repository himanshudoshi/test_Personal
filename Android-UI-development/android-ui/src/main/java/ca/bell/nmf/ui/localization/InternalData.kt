package ca.bell.nmf.ui.localization

import android.content.Context

/**
 * This class is used to store key-values data of the application.
 * This class manages the SharedPreferences of the app.
 */
class InternalData(context: Context) {

    /**
     * @property sharedPreferences gets the interface of SharedPreferences for the app, and will use it
     * to operate data concerning the app.
     */
    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)

    /**
     * @property editor gets the Interface of editor for the SharedPreferences and is used
     * to operate data for the app.
     */
    private val editor = sharedPreferences.edit()

    companion object {
        /**
         * @property CURRENT_LANGUAGE_KEY const key to keep the value for current language
         */
        const val CURRENT_LANGUAGE_KEY = "CURRENT_LANGUAGE"

        /**
         * @property SHARED_PREFERENCE_NAME const key to define the file name of SharedPreferences
         */
        private const val SHARED_PREFERENCE_NAME = "NMF_INTERNAL_DATA"
    }

    /**
     * This method reads the shared preferences file, and returns the value of Locale Lang in case if it exists, or empty otherwise.
     * @return String
     */
    fun getCurrentLanguage(): String {
        return sharedPreferences.getString(CURRENT_LANGUAGE_KEY, "") ?: ""
    }

    /**
     * This method stores the Locale Lang selected by the User in the SharedPreferences of the app.
     * @param language takes the String "en" or "fr" as a value.
     */
    fun storePreferenceLanguageLocale(language: String) {
        editor.putString(CURRENT_LANGUAGE_KEY, language)
              .commit()
    }
}