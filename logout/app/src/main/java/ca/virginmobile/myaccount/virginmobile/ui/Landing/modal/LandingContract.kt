package ca.virginmobile.myaccount.virginmobile.ui.Landing.modal

import android.content.Context
import ca.virginmobile.myaccount.virginmobile.base.BasePresenter
import ca.virginmobile.myaccount.virginmobile.base.BaseView
import ca.virginmobile.myaccount.virginmobile.ui.Login.presenter.LoginPresenter

class LandingContract{

    /**
     * interface that Landing activity will implement
     */
    interface LandingView : BaseView<LoginPresenter> {

        val context: Context

        fun navigateToMyProfile()

        fun navigateToStoreLocator()

        fun navigateToSettingsAndPrivacy()

        fun navigateToLogout()
    }

    /**
     * interface that Landing presenter will implement
     */
    interface LandingPresenter : BasePresenter {
        fun onMyProfileClick()

        fun onStoreLocatorClick()

        fun onSettingsAndPrivacyClick()

        fun onLogoutClick()

    }

}