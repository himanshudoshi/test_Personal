package ca.virginmobile.myaccount.virginmobile.ui.settings.view

import android.content.Context
import android.content.res.Resources
import android.support.test.rule.ActivityTestRule
import ca.bell.nmf.ui.localization.LanguageManager
import ca.bell.nmf.utils.common.internaldata.InternalDataManager
import ca.virginmobile.myaccount.virginmobile.R
import ca.virginmobile.myaccount.virginmobile.ui.settings.model.SettingsContract
import ca.virginmobile.myaccount.virginmobile.ui.settings.presenter.SettingsPresenter
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Gaganpreet Singh( GG00539076 )on 7/13/2018.
 */
class SettingsActivityTest{

    @Rule
    @JvmField
    val mActivityRule: ActivityTestRule<SettingsActivity> = ActivityTestRule<SettingsActivity>(SettingsActivity::class.java)
    private lateinit var mSettingsActivity: SettingsActivity

    private lateinit var resources: Resources
    private lateinit var english:String
    private lateinit var french :String
    private lateinit var language: Array<String>
    private lateinit var settingsPresenter:SettingsPresenter
    private lateinit var context: Context
    private lateinit var internalDataManager: InternalDataManager
    private lateinit var languageManager: LanguageManager

    @Before
    fun setUp() {
        mSettingsActivity = mActivityRule.activity
        context = mSettingsActivity
        resources = mSettingsActivity.baseContext.resources
        internalDataManager = InternalDataManager(context)
        languageManager = LanguageManager(mSettingsActivity)

        english = resources.getString(R.string.english)
        french = resources.getString(R.string.french)
        language = resources.getStringArray(R.array.languages)

        settingsPresenter = SettingsPresenter(
                languageManager , resources, internalDataManager)
        settingsPresenter.attachView(mSettingsActivity)

    }


    @Test
    fun tests() {
        /**
         * All tests are nested into other
         */
        englishToFrench()
    }


    /**
     * Set default language as english, then tries to change it to french
     */
    private fun englishToFrench(){
        internalDataManager.setValue(resources.getString(R.string.selectedLanguage), english)
        val acronym = LanguageManager.ENGLISH_LANGUAGE_ACRONYM
        languageManager.updateThenSetPrefLanguage(acronym)
        val languageIndex: Int = (settingsPresenter).getSelectedLanguageIndex(language)
        val languageChangeListener = object : SettingsContract.ISettingsPresenter.LanguageChangeListener
        {
            override fun onLanguageChanged(closeDialog: Boolean) {
                val isLocaleFrench = LanguageManager(context)
                        .localeLanguage
                        .startsWith(LanguageManager.FRENCH_LANGUAGE_ACRONYM)
                Assert.assertEquals(isLocaleFrench, true)

                englishToFrench_negative()
            }
        }
        settingsPresenter.onLanguageSelected(languageIndex, 1, language[1], languageChangeListener)
    }



    /**
     * Set default language as english, then checks if after changing if its english
     */
    private fun englishToFrench_negative(){
        internalDataManager.setValue(resources.getString(R.string.selectedLanguage), english)
        val acronym = LanguageManager.ENGLISH_LANGUAGE_ACRONYM
        languageManager.updateThenSetPrefLanguage(acronym)
        val languageIndex: Int = (settingsPresenter).getSelectedLanguageIndex(language)
        val languageChangeListener = object : SettingsContract.ISettingsPresenter.LanguageChangeListener
        {
            override fun onLanguageChanged(closeDialog: Boolean) {
                val isLocaleEnglish = LanguageManager(context).localeLanguage
                        .startsWith(LanguageManager.ENGLISH_LANGUAGE_ACRONYM)
                Assert.assertEquals(isLocaleEnglish, false)
                frenchToEnglish()
            }
        }
        settingsPresenter.onLanguageSelected(languageIndex, 1, language[1], languageChangeListener)
    }




    /**
     * Set default language as french, then tries to change it to english
     */
    private fun frenchToEnglish(){
        internalDataManager.setValue(resources.getString(R.string.selectedLanguage), french)
        val acronym = LanguageManager.FRENCH_LANGUAGE_ACRONYM
        languageManager.updateThenSetPrefLanguage(acronym)
        val languageIndex: Int = (settingsPresenter).getSelectedLanguageIndex(language)
        val languageChangeListener = object : SettingsContract.ISettingsPresenter.LanguageChangeListener
        {
            override fun onLanguageChanged(closeDialog: Boolean) {
                val isLocaleEnglish = LanguageManager(context).localeLanguage
                        .startsWith(LanguageManager.ENGLISH_LANGUAGE_ACRONYM)
                Assert.assertEquals(isLocaleEnglish, true)
                frenchToEnglishNegative()
            }
        }
        settingsPresenter.onLanguageSelected(languageIndex, 0, language[0], languageChangeListener)
    }



    /**
     * Set default language as french, then after changing it checks its french
     */
    private fun frenchToEnglishNegative() {
        internalDataManager.setValue(resources.getString(R.string.selectedLanguage), french)
        val acronym = LanguageManager.FRENCH_LANGUAGE_ACRONYM
        languageManager.updateThenSetPrefLanguage(acronym)
        val languageIndex: Int = (settingsPresenter).getSelectedLanguageIndex(language)
        val languageChangeListener = object : SettingsContract.ISettingsPresenter.LanguageChangeListener
        {
            override fun onLanguageChanged(closeDialog: Boolean) {
                val isLocaleFrench = LanguageManager(context).localeLanguage
                        .startsWith(LanguageManager.FRENCH_LANGUAGE_ACRONYM)
                Assert.assertEquals(isLocaleFrench, false)

            }
        }
        settingsPresenter.onLanguageSelected(languageIndex, 0, language[0], languageChangeListener)
    }


}