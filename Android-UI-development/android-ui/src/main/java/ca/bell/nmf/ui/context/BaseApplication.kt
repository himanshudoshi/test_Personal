package ca.bell.nmf.ui.context

import android.app.Application
import ca.bell.nmf.ui.R
import ca.bell.nmf.ui.FontsManager

class BaseApplication: Application() {

    enum class TYPE {
        PC_MOBILE, LUCKY_MOBILE, VIRGIN_MOBILE, BELL_MOBILE, NONE
    }

    private var appType: TYPE? = null

    override fun onCreate() {
        super.onCreate()

        //This initializes project or app type. To change please go to nmf.xml file and change
        appType = getAppType()

        //Initialize fonts manager. Please use
        FontsManager.getInstance(this, appType!!)

    }

    override fun onTerminate() {
        super.onTerminate()
    }

    private fun getAppType(): TYPE {
        val appType = resources.getString(R.string.app_type)
        when(appType) {
            resources.getString(R.string.pc_mobile_app) -> return TYPE.PC_MOBILE
            resources.getString(R.string.lucky_mobile_app) -> return TYPE.LUCKY_MOBILE
            resources.getString(R.string.virgin_mobile_app) -> return TYPE.VIRGIN_MOBILE
            resources.getString(R.string.bell_mobile_app) -> return TYPE.BELL_MOBILE
            else -> {
                throw RuntimeException("Undefined app type, \"none\" can't be an app type. " +
                                       "Please check your app_type property under nmf.xml file.")
            }
        }
    }

}