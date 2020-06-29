package ca.virginmobile.myaccount.virginmobile.ui.landing.presenter

import android.content.Context
import ca.bell.nmf.utils.common.internaldata.InternalDataManager
import ca.virginmobile.myaccount.virginmobile.R
import ca.virginmobile.myaccount.virginmobile.ui.landing.model.LandingContract

class LandingPresenter : LandingContract.ILandingPresenter {
    private var landingView: LandingContract.ILandingView? = null

    /**
     * @method:  interface method to attach view with presenter
     */
    override fun attachView(view: Any) {
        if (view is LandingContract.ILandingView)
            landingView = view
        else
            throw Exception("view must be of LandingContract.ILandingView type")
    }

    /**
     * @method: interface method of presenter will proceed to
     * interface method of view navigateToMyProfile implemented in login activity
     */
    override fun onMyProfileClick() {
        landingView!!.navigateToMyProfile()
    }


    /**
     * @method: interface method of presenter will proceed to
     * interface method of view navigateToStoreLocator implemented in login activity
     */
    override fun onStoreLocatorClick() {
        landingView!!.navigateToStoreLocator()
    }


    /**
     * @method: interface method of presenter will proceed to
     * interface method of view navigateToSettingsAndPrivacy implemented in login activity
     */
    override fun onSettingsAndPrivacyClick() {
        landingView!!.navigateToSettingsAndPrivacy()
    }


    /**
     * @method: interface method of presenter will proceed to
     * interface method of view handleLogoutAction implemented in login activity
     */
    override fun onLogoutClick() {
        if (isBupUser()) {
            removeSessionDataFromSharedPrefs()
        }

        landingView!!.navigateToLogin()
    }

    /**
     * This method will delete the SMSession and RememberMe from the SharedPreferences
     */
    private fun removeSessionDataFromSharedPrefs() {
        val context = landingView as Context
        val im = InternalDataManager(context)
        im.removeKey(context.getString(R.string.sm_session))
        im.removeKey(context.getString(R.string.remember_me))
    }


    override fun detachView() {
        this.landingView = null
    }


    /**
     *This method will return true is user is a bup user and false when
     *@return true is user is a bup user
     */
    fun isBupUser(): Boolean {
        return true
    }

}