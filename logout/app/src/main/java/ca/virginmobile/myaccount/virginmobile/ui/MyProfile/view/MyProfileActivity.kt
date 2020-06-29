package ca.virginmobile.myaccount.virginmobile.ui.MyProfile.view

import android.os.Bundle
import ca.virginmobile.myaccount.virginmobile.R
import ca.virginmobile.myaccount.virginmobile.base.BaseActivity



class MyProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        initToolbar(R.id.toolbar)
        setToolbarTitle(getString(R.string.my_profile))
        showBackButton()
        showToolbar()
    }




}
