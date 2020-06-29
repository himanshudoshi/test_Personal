package ca.virginmobile.myaccount.virginmobile.base


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View


/**
 * Base activity of all class
 */
open class BaseActivity : AppCompatActivity() {
    private lateinit var baseToolbar: Toolbar
    private  var isToolbarInitialized: Boolean=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    fun initToolbar(toolbarId: Int) {
        baseToolbar = findViewById(toolbarId)
        setSupportActionBar(baseToolbar)
        isToolbarInitialized=true
    }


    fun showToolbar() {
        if(isToolbarInitialized) baseToolbar.visibility = View.VISIBLE

    }

    fun hideToolbar() {
        if(isToolbarInitialized) baseToolbar.visibility = View.GONE
    }


    fun setToolbarTitle(title: String) {
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = title
    }


    fun hideBackButton() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowHomeEnabled(false)
    }

    fun showBackButton() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return (when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        })
    }



    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }



}
