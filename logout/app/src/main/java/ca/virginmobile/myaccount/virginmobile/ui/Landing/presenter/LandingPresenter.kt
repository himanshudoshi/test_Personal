package ca.virginmobile.myaccount.virginmobile.ui.Landing.presenter

import ca.virginmobile.myaccount.virginmobile.ui.Landing.modal.LandingContract


class LandingPresenter : LandingContract.LandingPresenter {


    private var landingView: LandingContract.LandingView? = null



    override fun attachView(view: Any) {
        landingView = view as LandingContract.LandingView
    }



    override fun onMyProfileClick() {
        landingView!!.navigateToMyProfile()
    }



    override fun onStoreLocatorClick() {
        landingView!!.navigateToStoreLocator()
    }



    override fun onSettingsAndPrivacyClick() {
        landingView!!.navigateToSettingsAndPrivacy()
    }



    override fun onLogoutClick() {
        landingView!!.navigateToLogout()
    }



    override fun detachView() {
        this.landingView = null
    }


}