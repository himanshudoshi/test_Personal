package ca.virginmobile.myaccount.virginmobile.ui.myProfile.view

import android.os.Bundle
import android.widget.ImageButton
import ca.bell.nmf.ui.view.TopBar
import ca.virginmobile.myaccount.virginmobile.R
import ca.virginmobile.myaccount.virginmobile.base.AppBaseActivity



class MyProfileActivity : AppBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile_layout)


        topBar = TopBar(this)
        topBar?.setTitle(R.string.my_profile)
        topBar?.showHomeButton()
        topBar?.hideMenuButton()

        topBar?.setOnHomeButtonClicked(object :TopBar.TopBarButtonClickListener{
            override fun onClicked(button: ImageButton) {
                onBackPressed()
            }
        })

    }




}
