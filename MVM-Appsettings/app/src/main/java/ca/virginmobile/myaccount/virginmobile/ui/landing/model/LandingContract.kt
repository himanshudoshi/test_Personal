package ca.virginmobile.myaccount.virginmobile.ui.landing.model

import android.content.Context
import ca.virginmobile.myaccount.virginmobile.base.BasePresenter
import ca.virginmobile.myaccount.virginmobile.base.BaseView

class LandingContract {

    /**
     * interface that Landing activity will implement
     */
    interface ILandingView : BaseView<ILandingPresenter> {

        val context: Context

        fun navigateToMyProfile()

        fun navigateToStoreLocator()

        fun navigateToSettingsAndPrivacy()

        fun navigateToLogin()
    }

    /**
     * interface that Landing presenter will implement
     */
    interface ILandingPresenter : BasePresenter {
        fun onMyProfileClick()

        fun onStoreLocatorClick()

        fun onSettingsAndPrivacyClick()

        fun onLogoutClick()

    }

}