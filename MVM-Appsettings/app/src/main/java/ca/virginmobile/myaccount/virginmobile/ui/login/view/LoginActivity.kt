package ca.virginmobile.myaccount.virginmobile.ui.login.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ca.bell.nmf.utils.common.internaldata.InternalDataManager
import ca.virginmobile.myaccount.virginmobile.R
import ca.virginmobile.myaccount.virginmobile.ui.landing.view.LandingActivity
import kotlinx.android.synthetic.main.activity_login_layout.*
import java.util.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_layout)


        button.setOnClickListener {
            val intent = Intent(this@LoginActivity ,LandingActivity::class.java )
            startActivity(intent)
        }

        saveLanguageInSharedPreference()
        moveToLanding()
    }

    private fun saveLanguageInSharedPreference() {
        val language =  Locale.getDefault().displayLanguage
        InternalDataManager.getInstance(baseContext).setValue(getString(R.string.deviceLanguage),language)
    }

    private fun moveToLanding() {
        val intent = Intent(this, LandingActivity::class.java)
        startActivity(intent)
        finish()
    }
}
