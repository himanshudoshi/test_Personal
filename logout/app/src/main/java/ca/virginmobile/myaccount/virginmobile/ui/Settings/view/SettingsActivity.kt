package ca.virginmobile.myaccount.virginmobile.ui.Settings.view

import android.os.Bundle
import ca.virginmobile.myaccount.virginmobile.base.BaseActivity
import ca.virginmobile.myaccount.virginmobile.R

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initToolbar(R.id.toolbar)
        setToolbarTitle(getString(R.string.settings_and_privacy))
        showBackButton()
        showToolbar()

    }

}
