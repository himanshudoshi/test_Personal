package ca.virginmobile.myaccount.virginmobile.ui.settings.presenter

import android.content.res.Resources
import ca.bell.nmf.ui.localization.LanguageManager
import ca.bell.nmf.utils.common.internaldata.InternalDataManager
import ca.virginmobile.myaccount.virginmobile.R

import ca.virginmobile.myaccount.virginmobile.ui.settings.model.SettingsContract

class SettingsPresenter(private val languageManager: LanguageManager, private val resources: Resources, private val internalDataManager: InternalDataManager)
    : SettingsContract.ISettingsPresenter {


    /**
     * THis method checks whether device language is supported then instructs view to hide Language tile.
     */
    override fun processDeviceLanguage() {
        val language: String? = getDefaultLocale()

        val isLanguageMatched = (language.equals(english, true)
                || language.equals(french, true)
                || language.equals("Francais", true)
                || language.equals("French", true)
                )

        if (isLanguageMatched) {
            settingsView.hideLanguageTile()
        }
    }


    private fun getDefaultLocale(): String? {
//        return Locale.getDefault().getDisplayLanguage()
        return internalDataManager.getStringValue(
                resources.getString(R.string.deviceLanguage), null)
    }

    private lateinit var settingsView: SettingsContract.SettingsView
    private val english = resources.getString(R.string.english)
    private val french = resources.getString(R.string.french)

    /**
     *This method is called by the view class which sends parameter SettingsView .
     * The view object is used to give commands to the view
     * @param : SettingsView
     *
     */
    override fun attachView(view: Any) {
        settingsView = view as SettingsContract.SettingsView
    }


    /**
     * when privacyButton is clicked this method is called
     */
    override fun handlePrivacyPolicyClick() {
        settingsView.navigateToPrivacyPolicy()
    }


    /**
     * when About app is clicked this method is called
     */
    override fun handleAboutTheAppClick() {
        settingsView.navigateToAboutTheApp()
    }


    /**
     * This method can be used to release the resources
     */
    override fun detachView() {

        /**
         * The below code needs to be un-commented used if SettingsView is made nullable
         */
//        this.settingsView =null
    }

    /**
     * This method commands view class to open language selection dialog
     */
    override fun handleLanguageClick() {
        settingsView.openLanguageSelectionDialog()
    }


    /**
     * When user selects language, this method is called
     */
    override fun getSelectedLanguageIndex(languagesArray: Array<String>): Int {
        val langLocale = languageManager.localeLanguage
        var language: String = english
        if (langLocale.startsWith(LanguageManager.FRENCH_LANGUAGE_ACRONYM)) {
            language = french
        }
        val size = languagesArray.size
        for (selection in 0 until size) {
            val isLanguageIndexFound = languagesArray[selection] == language
            if (isLanguageIndexFound) {
                return selection
            }
        }
        return 0
    }


    /**
     *  this method saves the selected language
     */
    private fun saveSelectedLanguageIndex(language: String) {
        internalDataManager.setValue(resources.getString(R.string.selectedLanguage), language)
    }


    /**
     * When the language is selected, this method is called.
     */
    override fun onLanguageSelected(olderPosition: Int, position: Int, language: String, languageChangeListener: SettingsContract.ISettingsPresenter.LanguageChangeListener) {
        saveSelectedLanguageIndex(language)

        var acronym = LanguageManager.ENGLISH_LANGUAGE_ACRONYM

        if (language == french)
            acronym = LanguageManager.FRENCH_LANGUAGE_ACRONYM

        languageManager.updateThenSetPrefLanguage(acronym)
        settingsView.loadLanguageOverUI(language)
        languageChangeListener.onLanguageChanged(closeDialog = olderPosition != position)
    }


}