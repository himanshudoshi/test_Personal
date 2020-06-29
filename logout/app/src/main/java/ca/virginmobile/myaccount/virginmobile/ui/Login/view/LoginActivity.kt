package ca.virginmobile.myaccount.virginmobile.ui.Login.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ca.virginmobile.myaccount.virginmobile.R
import ca.virginmobile.myaccount.virginmobile.ui.Landing.view.LandingActivity


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_view)

        val intent = Intent(this, LandingActivity::class.java)
        startActivity(intent)

    }
}
