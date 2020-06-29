package ca.virginmobile.myaccount.virginmobile.ui.StoreLocator.view

import android.os.Bundle
import ca.virginmobile.myaccount.virginmobile.base.BaseActivity
import ca.virginmobile.myaccount.virginmobile.R

class StoreLocatorActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_locator)
        initToolbar(R.id.toolbar)
        setToolbarTitle(getString(R.string.store_locator))
        showBackButton()
        showToolbar()


    }

}
