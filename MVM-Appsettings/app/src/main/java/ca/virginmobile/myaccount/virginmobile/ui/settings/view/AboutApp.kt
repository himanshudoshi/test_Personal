package ca.virginmobile.myaccount.virginmobile.ui.settings.view

import android.os.Bundle
import android.widget.ImageButton
import ca.bell.nmf.ui.view.TopBar
import ca.virginmobile.myaccount.virginmobile.R
import ca.virginmobile.myaccount.virginmobile.base.AppBaseActivity


class AboutApp : AppBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)

        topBar = TopBar(this)
        topBar?.setTitle(R.string.title_about_the_app)
        topBar?.showHomeButton()
        topBar?.hideMenuButton()
        topBar?.setOnHomeButtonClicked(object : TopBar.TopBarButtonClickListener {
            override fun onClicked(button: ImageButton) {
                onBackPressed()
            }
        })


    }
}






