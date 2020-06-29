package ca.virginmobile.myaccount.virginmobile.base


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import ca.bell.nmf.ui.context.BaseActivity
import ca.bell.nmf.ui.localization.ILocalization
import ca.bell.nmf.ui.localization.LanguageManager
import ca.bell.nmf.ui.view.TopBar


/**
 * Base activity of all class
 */
open class AppBaseActivity : BaseActivity() , ILocalization {

    /**
     * @property topBar this class(widget) manages the Toolbar and its assets.
     * We can set Title and Subtitle for the current layout
     */
    var topBar: TopBar? = null


    fun launchFragment(fragment: Fragment, view: Int, replace: Boolean, addToBackStack: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val tag = fragment::class.java.name
        if (replace) {
            fragmentTransaction.replace(view, fragment, tag)
        } else {
            fragmentTransaction.add(view, fragment, tag)
        }
        if (addToBackStack)
            fragmentTransaction.addToBackStack(tag)

        fragmentTransaction.commit()
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1)
            supportFragmentManager.popBackStackImmediate()
        else
            finish()
    }


    private var localeLanguage: String? = null





    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return (when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        })
    }




    protected fun restart() {
        finish()
        val intent = Intent(this, javaClass)
        startActivity(intent)
    }


    open protected fun updateUI() {}



    /**
     * this method is used to load string resources corresponding to LocaleLang
     */
    override fun attachBaseContext(newBase: Context?) {
        val languageManager = LanguageManager(newBase!!)
        val lang = languageManager.localeLanguage
        super.attachBaseContext(languageManager.updateThenSetPrefLanguage(lang))
        changeLanguageResources()
    }


    /**
     * on creating an activity we grab and set the initial value of the @property localeLanguage
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (localeLanguage == null) {
            localeLanguage = LanguageManager(this).localeLanguage
        }
        handleOrientation()

    }



    override fun onResume() {
        super.onResume()
        if (!LanguageManager(this).localeLanguage.startsWith(localeLanguage!!)) {
            updateUI()
            localeLanguage = LanguageManager(this).localeLanguage
        }
    }

    /**
     * ILocalization method
     */
    override fun requestLanguageChange() {
        changeLanguageResources()
    }

}
