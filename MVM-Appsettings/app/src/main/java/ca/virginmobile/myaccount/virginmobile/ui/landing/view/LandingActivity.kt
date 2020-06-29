package ca.virginmobile.myaccount.virginmobile.ui.landing.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import ca.bell.nmf.ui.view.ActionOnClickListener
import ca.bell.nmf.ui.view.AndroidBottomNavigationView
import ca.bell.nmf.ui.view.TopBar
import ca.virginmobile.myaccount.virginmobile.R
import ca.virginmobile.myaccount.virginmobile.base.AppBaseActivity
import ca.virginmobile.myaccount.virginmobile.ui.bills.view.BillsFragment
import ca.virginmobile.myaccount.virginmobile.ui.landing.model.LandingContract
import ca.virginmobile.myaccount.virginmobile.ui.landing.presenter.LandingPresenter
import ca.virginmobile.myaccount.virginmobile.ui.login.view.LoginActivity
import ca.virginmobile.myaccount.virginmobile.ui.myProfile.view.MyProfileActivity
import ca.virginmobile.myaccount.virginmobile.ui.services.view.ServiceFragment
import ca.virginmobile.myaccount.virginmobile.ui.settings.view.SettingsActivity
import ca.virginmobile.myaccount.virginmobile.ui.storeLocator.view.StoreLocatorActivity
import ca.virginmobile.myaccount.virginmobile.ui.support.view.SupportFragment
import ca.virginmobile.myaccount.virginmobile.ui.usage.view.UsageFragment
import ca.virginmobile.myaccount.virginmobile.util.OnFragmentInteractionListener
import kotlinx.android.synthetic.main.activity_landing_layout.*


class LandingActivity : AppBaseActivity(), LandingContract.ILandingView, OnFragmentInteractionListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var landingPresenter: LandingPresenter
    private lateinit var popupMenu: PopupMenu

    companion object {
        const val REQUEST_CODE_LANGUAGE_CHANGED = 1001
    }


    override val context: Context
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_layout)
        configureToolbar()
        attachPresenter()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            @Suppress("DEPRECATION")
            (bottomNavigationView.findViewById(R.id.androidBottomNavigationView) as View)
                    .setBackgroundColor(resources.getColor(R.color.bottom_nav_background_color))
        } else {
            (bottomNavigationView.findViewById(R.id.androidBottomNavigationView) as View)
                    .setBackgroundColor(getColor(R.color.bottom_nav_background_color))
        }
        topBarClickEvents(topBar)
        setClickListeners()
        launchFragment(ServiceFragment(), R.id.fragmentView, true, false)
    }


    /**
     * Implementation of click events is done in this method
     */
    private fun setClickListeners() {
        bottomNavigationView.setOnItemSelectedListener(this)
        bottomNavigationView.addAction1(R.string.add_data_usage, R.drawable.ic_icon_pop_over_add_data, object : ActionOnClickListener {
            override fun onActionClicked(view: View) {
                Toast.makeText(baseContext, "Add data usage", Toast.LENGTH_SHORT).show()
            }
        })

        bottomNavigationView.addAction2(R.string.add_travel_usage, R.drawable.ic_icon_pop_over_travel_add_on, object : ActionOnClickListener {
            override fun onActionClicked(view: View) {
                Toast.makeText(baseContext, "Add travel usage", Toast.LENGTH_SHORT).show()
            }
        })

        bottomNavigationView.addAction3(R.string.change_tv_channels, R.drawable.ic_icon_pop_over_upgrade_device, object : ActionOnClickListener {
            override fun onActionClicked(view: View) {
                Toast.makeText(baseContext, "Change TV channels", Toast.LENGTH_SHORT).show()
            }
        })

        bottomNavigationView.addAction4(R.string.make_a_payment, R.drawable.ic_icon_pop_over_make_payment, object : ActionOnClickListener {
            override fun onActionClicked(view: View) {
                Toast.makeText(baseContext, "Make a payment", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * Render customised toolbar on top
     */
    private fun configureToolbar() {
        topBar = TopBar(this)
        topBar?.setTitle(R.string.landing_title)
        topBar?.hideSubTitle()
        topBar?.showHomeButton()
        topBar?.showMenuButton()
    }


    /**
     * @method:  interface method to attach presenter with view
     */
    override fun attachPresenter() {
        landingPresenter = LandingPresenter()
        landingPresenter.attachView(this)
    }


    /**
     * @method:  method to handle topBar related click events
     * @param: topBar object required to access the required methods exposed by ui layer
     */
    private fun topBarClickEvents(topBar: TopBar?) {

        /* topBar?.addOptionButton(R.drawable.icon_username_white, object : TopBar.TopBarButtonClickListener {
             override fun onClicked(button: ImageButton) {
                 //inflatePopUpMenu(button)
             }
         })
 */

        topBar?.setOnHomeButtonClicked(object : TopBar.TopBarButtonClickListener {
            override fun onClicked(button: ImageButton) {
                finish()
            }
        })



        if (landingPresenter.isBupUser()) {
            topBar?.setMenuItemTitle(R.id.action_logout, getString(R.string.logout))
        } else {
            topBar?.setMenuItemTitle(R.id.action_logout, getString(R.string.login))
        }



        topBar?.setItemClickedListener(PopupMenu.OnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_my_profile -> {
                    landingPresenter.onMyProfileClick()
                    return@OnMenuItemClickListener true
                }
                R.id.action_store_locator -> {
                    landingPresenter.onStoreLocatorClick()
                    return@OnMenuItemClickListener true
                }
                R.id.action_settings -> {
                    landingPresenter.onSettingsAndPrivacyClick()
                    return@OnMenuItemClickListener true
                }
                R.id.action_logout -> {
                    landingPresenter.onLogoutClick()
                    return@OnMenuItemClickListener true
                }
                else -> {
                    return@OnMenuItemClickListener true

                }
            }
        })


    }


    //Need to remove later after sammi provide the methods
/*
    */
    /**
     * @method: method to display more option menu and also handle more option menu items click events
     * @param: ImageButton is to  provide reference of view  to PopupMenu on clicking which more menu options
     *//*
    private fun inflatePopUpMenu(button: ImageButton) {

        popupMenu = PopupMenu(context, button)
        popupMenu.menuInflater.inflate(R.menu.top_bar_popup_menu, popupMenu.menu)



        val menuItem = popupMenu.menu.findItem(R.id.action_logout)
        if (landingPresenter.isBupUser()) {
            menuItem.setTitle(getString(R.string.logout))
        } else {
            menuItem.setTitle(getString(R.string.login))
        }



        val fields = popupMenu.javaClass.declaredFields
        for (field in fields) {
            if ("mPopup" == field.name) {
                field.isAccessible = true
                val menuPopupHelper = field.get(popupMenu)
                val classPopupHelper = Class.forName(menuPopupHelper.javaClass.name)
                val setForceIcons = classPopupHelper.getMethod(
                        "setForceShowIcon", Boolean::class.javaPrimitiveType)
                setForceIcons.invoke(menuPopupHelper, true)
                break
            }
        }


        onPrepareOptionsMenu(popupMenu.menu)
        popupMenu.show()



        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item?.itemId) {
                R.id.action_my_profile -> {
                    landingPresenter.onMyProfileClick()
                    return@OnMenuItemClickListener true
                }
                R.id.action_store_locator -> {
                    landingPresenter.onStoreLocatorClick()
                    return@OnMenuItemClickListener true
                }
                R.id.action_settings -> {
                    landingPresenter.onSettingsAndPrivacyClick()
                    return@OnMenuItemClickListener true
                }
                R.id.action_logout -> {
                    landingPresenter.onLogoutClick()
                    return@OnMenuItemClickListener true
                }
                else -> {
                    return@OnMenuItemClickListener true

                }
            }
        })

    }*/

    /**
     * @method: Inflate xml for option menu items
     * @return: Boolean value regarding xml inflated or not
     * @param:  Interface required for inflating the option menu items
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_bar_popup_menu, menu)
        return true
    }

    /**
     * @method: interface method to navigate user to My profile screen
     */
    override fun navigateToMyProfile() {
        val intent = Intent(this, MyProfileActivity::class.java)
        startActivity(intent)
    }


    /**
     * @method: interface method to navigate user to My profile screen
     */
    override fun navigateToStoreLocator() {
        val intent = Intent(this, StoreLocatorActivity::class.java)
        startActivity(intent)
    }


    /**
     * @method: interface method to navigate user to Settings and Privacy screen
     */
    override fun navigateToSettingsAndPrivacy() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivityForResult(intent,REQUEST_CODE_LANGUAGE_CHANGED)
    }


    /*Method to navigate user to login screen once user taps on logout option*/
    override fun navigateToLogin() {
        //Remove the existing fragment from the back stack
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }


    override fun onFragmentInteraction(uri: Uri) {
        var title = ""
        var id = 1
        val navigationView = bottomNavigationView.findViewById<AndroidBottomNavigationView>(R.id.androidBottomNavigationView)

        when (uri.toString()) {
            ServiceFragment::class.java.simpleName -> {
                title = getString(R.string.services)
                id = R.id.bottomNavigationAction1
            }
            UsageFragment::class.java.simpleName -> {
                title = getString(R.string.usage)
                id = R.id.bottomNavigationAction2
            }
            BillsFragment::class.java.simpleName -> {
                title = getString(R.string.bills)
                id = R.id.bottomNavigationAction3
            }
            SupportFragment::class.java.simpleName -> {
                title = getString(R.string.support)
                id = R.id.bottomNavigationAction4
            }
        }

        if (navigationView.selectedItemId != id) {
            navigationView.selectedItemId = id
        }
        topBar?.setTitle(title)

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE_LANGUAGE_CHANGED ->{
                if (resultCode == SettingsActivity.RESULT_CODE_LANGAUGE_CHANGED){
                    restart()
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment = Fragment()
        when (item.title) {
            getString(R.string.support) -> fragment = SupportFragment()
            getString(R.string.usage) -> fragment = UsageFragment()
            getString(R.string.services) -> fragment = ServiceFragment()
            getString(R.string.bills) -> fragment = BillsFragment()
        }

        launchFragment(fragment, R.id.fragmentView, true, true)
        return true
    }


}
