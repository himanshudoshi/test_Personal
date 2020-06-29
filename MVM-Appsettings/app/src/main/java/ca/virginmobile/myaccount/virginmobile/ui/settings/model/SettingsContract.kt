package ca.virginmobile.myaccount.virginmobile.ui.settings.model


import ca.virginmobile.myaccount.virginmobile.base.BasePresenter
import ca.virginmobile.myaccount.virginmobile.base.BaseView



class SettingsContract {

    /**
     * interface that Settings activity will implement
     */
    interface SettingsView : BaseView<ISettingsPresenter> {
        fun navigateToPrivacyPolicy()
        fun navigateToAboutTheApp()
        fun openLanguageSelectionDialog()
        fun loadLanguageOverUI(language: String)
        fun hideLanguageTile()

    }


    /**
     * interface that Settings presenter will implement
     */
    interface ISettingsPresenter : BasePresenter {
        fun handlePrivacyPolicyClick()
        fun handleAboutTheAppClick()
        fun handleLanguageClick()
        fun onLanguageSelected( olderPosition: Int, position: Int, language: String, languageChangeListener: LanguageChangeListener)
        fun getSelectedLanguageIndex(languagesArray: Array<String>): Int


        interface LanguageChangeListener {
            fun onLanguageChanged(closeDialog: Boolean)
        }

        fun processDeviceLanguage()

    }

}