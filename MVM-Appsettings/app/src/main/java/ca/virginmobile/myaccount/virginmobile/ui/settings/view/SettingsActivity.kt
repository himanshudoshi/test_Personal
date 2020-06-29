package ca.virginmobile.myaccount.virginmobile.ui.settings.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton

import ca.bell.nmf.ui.view.TopBar
import ca.bell.nmf.ui.localization.LanguageManager
import ca.bell.nmf.utils.common.internaldata.InternalDataManager
import ca.virginmobile.myaccount.virginmobile.R
import ca.virginmobile.myaccount.virginmobile.base.AppBaseActivity
import ca.virginmobile.myaccount.virginmobile.ui.settings.model.SettingsContract
import ca.virginmobile.myaccount.virginmobile.ui.settings.presenter.SettingsPresenter
import kotlinx.android.synthetic.main.activity_settings_layout.*


class SettingsActivity : AppBaseActivity(), SettingsContract.SettingsView, View.OnClickListener {


    /**
     * This method is used to hide the language tile when device language is French or English
     */
    override fun hideLanguageTile() {
        languageRelativeLayout.visibility =View.GONE
        space.visibility =View.GONE
    }

    private lateinit var settingsPresenter: SettingsContract.ISettingsPresenter


    companion object {
        const val RESULT_CODE_LANGAUGE_CHANGED = 2001
    }

    /**
     *  This method does create toolbar, initialisation of view & OnClickListener
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_layout)
        configureToolbar()
        attachPresenter()
        setOnClickListeners()
        settingsPresenter.processDeviceLanguage()
    }


    /**
     * This method is responsible for creating toolbar with Title &  BackButton
     */
    private fun configureToolbar() {
        topBar = TopBar(this)
        topBar?.setTitle(R.string.settings_and_privacy)
        topBar?.hideSubTitle()
        topBar?.showHomeButton()
        topBar?.hideMenuButton()


    }

    /**
     * This method is responsible for apply OnClickListener
     */
    private fun setOnClickListeners() {
        aboutTheAppTextView.setOnClickListener(this)
        privacyPolicyTextView.setOnClickListener(this)
        languageRelativeLayout.setOnClickListener(this)

        topBar?.setOnHomeButtonClicked(object :TopBar.TopBarButtonClickListener{
            override fun onClicked(button: ImageButton) {
                onBackPressed()
            }
        })
    }

    /**
     * @method:  interface method to attach presenter with view
     */
    override fun attachPresenter() {
        settingsPresenter = SettingsPresenter(LanguageManager(this), resources, InternalDataManager.getInstance(this))
        settingsPresenter.attachView(this)
    }


    /**
     * @method: method to handle click events
     */
    override fun onClick(view: View) {
        when (view.id) {
            R.id.aboutTheAppTextView -> settingsPresenter.handleAboutTheAppClick()
            R.id.privacyPolicyTextView -> settingsPresenter.handlePrivacyPolicyClick()
            R.id.languageRelativeLayout -> settingsPresenter.handleLanguageClick()
        }
    }


    /**
     * This method is responsible for redirecting to Policy screen
     */
    override fun navigateToPrivacyPolicy() {
        val privacyPolicyPage = Intent(this, PrivacyAgreement::class.java)
        startActivity(privacyPolicyPage)
    }


    /**
     * This method is responsible for redirecting to AboutApp screen
     */
    override fun navigateToAboutTheApp() {
        val aboutUsPage = Intent(this, AboutApp::class.java)
        startActivity(aboutUsPage)
    }
    /**
     * This method is responsible for language selection dialog
     */
    override fun openLanguageSelectionDialog() {
        val language: Array<String> = resources.getStringArray(R.array.languages)
        val languageIndex: Int = (settingsPresenter).getSelectedLanguageIndex(language)


        val onSingleChoiceItemClick = object : DialogInterface.OnClickListener {
            override fun onClick(dialogInterface: DialogInterface, position: Int) {

                val languageChangeListener = object : SettingsContract.ISettingsPresenter.LanguageChangeListener {
                    override fun onLanguageChanged(closeDialog: Boolean) {
                        exitToLandingScreen(closeDialog, dialogInterface)
                    }
                }

                settingsPresenter.onLanguageSelected(languageIndex, position, language[position], languageChangeListener)
            }
        }

        val onCancelClicked = object : DialogInterface.OnClickListener {
            override fun onClick(dialogInterface: DialogInterface, p1: Int) {
                dialogInterface.dismiss()
            }
        }
        AlertDialog.Builder(this)
                .setTitle(R.string.language)
                .setSingleChoiceItems(language, languageIndex, onSingleChoiceItemClick)
                .setNegativeButton(getString(R.string.cancel), onCancelClicked)
                .show()
    }


    /**
     * This method is responsible for dismissing the dialog and redirecting the user to LandingActivity.kt
     */
    private fun exitToLandingScreen(languageChanged: Boolean, dialog: DialogInterface) {
        dialog.dismiss()
        if (languageChanged) {
            setResult(SettingsActivity.RESULT_CODE_LANGAUGE_CHANGED)
            SettingsActivity@ this.finish()
        }
    }


    /**
     * This method is used to load selected language over UI
     */
    override fun loadLanguageOverUI(language: String) {
        changeLanguageResources()
    }


}



