package ca.virginmobile.myaccount.virginmobile.ui.settings.view

import android.os.Bundle
import android.widget.ImageButton

import ca.bell.nmf.ui.view.TopBar
import ca.virginmobile.myaccount.virginmobile.R
import ca.virginmobile.myaccount.virginmobile.base.AppBaseActivity

class PrivacyAgreement : AppBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_agreement)

        topBar = TopBar(this)
        topBar?.setTitle(R.string.title_privacy_policy)
        topBar?.showHomeButton()
        topBar?.hideMenuButton()
        topBar?.setOnHomeButtonClicked(object : TopBar.TopBarButtonClickListener {
            override fun onClicked(button: ImageButton) {
                onBackPressed()
            }

        })


    }
}