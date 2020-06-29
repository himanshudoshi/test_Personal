package ca.virginmobile.myaccount.virginmobile.ui.Landing.view
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ca.virginmobile.myaccount.virginmobile.base.BaseActivity
import ca.virginmobile.myaccount.virginmobile.R
import ca.virginmobile.myaccount.virginmobile.ui.Landing.modal.LandingContract
import ca.virginmobile.myaccount.virginmobile.ui.Landing.presenter.LandingPresenter
import ca.virginmobile.myaccount.virginmobile.ui.MyProfile.view.MyProfileActivity
import ca.virginmobile.myaccount.virginmobile.ui.Settings.view.SettingsActivity
import ca.virginmobile.myaccount.virginmobile.ui.StoreLocator.view.StoreLocatorActivity




class LandingActivity : BaseActivity(),LandingContract.LandingView {

    private lateinit var landingPresenter: LandingPresenter

    override val context: Context
        get() = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        initToolbar(R.id.toolbar)
        setToolbarTitle(getString(R.string.landing_title))

        showToolbar()
        hideBackButton()
        attachPresenter()
    }



    override fun attachPresenter() {
        landingPresenter = LandingPresenter()
        landingPresenter.attachView(this)
    }



    /*method to inflate option menu items*/
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.more_menu_options, menu)
        return true
    }



    /*method to implement click events on option menu items*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return (when(item.itemId) {

            R.id.action_my_profile -> {
                landingPresenter.onMyProfileClick()
                return true
            }
            R.id.action_store_locator ->{
                landingPresenter.onStoreLocatorClick()
                return true
            }
            R.id.action_settings -> {
                landingPresenter.onSettingsAndPrivacyClick()
                return true
            }
            R.id.action_logout ->{
                landingPresenter.onLogoutClick()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        })

    }


    /*Method to navigate user to my profile screen*/
    override fun navigateToMyProfile() {
        val intent = Intent(this, MyProfileActivity::class.java)
        startActivity(intent)
    }


    /*Method to navigate user to store locator*/
    override fun navigateToStoreLocator() {
        val intent = Intent(this, StoreLocatorActivity::class.java)
        startActivity(intent)
    }


    /*Method to navigate user to settings and privacy screen*/
    override fun navigateToSettingsAndPrivacy() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }


    /*Method to navigate user to login screen once user taps on logout option*/
    override fun navigateToLogout() {

    }




}
