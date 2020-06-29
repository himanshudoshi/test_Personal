package ca.bell.nmf.ui.localization

/**
 * This Interface is used to notify subscribers about the events on changing Locale Lang.
 */
interface ILocalization {

    /**
     * this method will be called when there will be a need to change the language
     */
    fun requestLanguageChange()

    /**
     * this method will be triggered when the user takes the action that changes the Locale Lang
     * of the application
     * @param languageName provide "en" or "fr" as String to operate changes in the app configuration
     */
    fun onLanguageChanged(languageName: String)
}