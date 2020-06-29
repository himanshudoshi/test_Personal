package ca.bell.nmf.ui

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.widget.TextView
import ca.bell.nmf.ui.context.BaseApplication.TYPE
import ca.bell.nmf.ui.R

open class FontsManager private constructor() {

    var mContext: Context? = null
    private val fontResourceMap: HashMap<String, String> = HashMap()

    val size: Int get() {
        return fontResourceMap.size
    }

    companion object FontsManagerHelper {

        //TODO move to resource xml file
        val FONT_CATEGORIES = arrayOf("regular", "medium", "bold")

        private val INSTANCE : FontsManager by lazy {
            FontsManager()
        }

        internal fun getInstance(mContext: Context?, appType: TYPE) : FontsManager {
            INSTANCE.mContext = mContext
            INSTANCE.loadFonts(appType)
            return INSTANCE
        }

        fun getInstance() : FontsManager {
            return INSTANCE
        }


    }


    private fun loadFonts(appType: TYPE) {

        var fontsArray: TypedArray? = null

        when (appType) {
            TYPE.PC_MOBILE -> {
                fontsArray = mContext?.resources?.obtainTypedArray(R.array.pc_fonts)
            }
            TYPE.LUCKY_MOBILE -> {
                fontsArray = mContext?.resources?.obtainTypedArray(R.array.lucky_fonts)
            }
            TYPE.VIRGIN_MOBILE -> {
                fontsArray = mContext?.resources?.obtainTypedArray(R.array.virgin_fonts)
            }
            TYPE.BELL_MOBILE -> {
                fontsArray = mContext?.resources?.obtainTypedArray(R.array.mbm_fonts)
            }
            TYPE.NONE -> {
                throw InvalidFontProfileException("None is not a recognized font profile, Please change app type property from res/values/nmf.xml")
            }
        }

        if (FONT_CATEGORIES.size < fontsArray!!.length()) {
            throw RuntimeException("NMF doesn't support all fonts you provided in nmf.xml for ${appType.name}")
        }

        for (i in 0 until fontsArray.length()) {
            fontResourceMap.put(FONT_CATEGORIES[i], fontsArray.getString(i))
        }

    }

    private fun <T: TextView> applyFont(view: T?, fontFileName: String?) {
        val typeFace: Typeface = Typeface.createFromAsset(mContext?.assets, fontFileName)
        view?.typeface = typeFace
    }

    private fun <T: TextView> applyFontByIndex(view: T?, index: Int) {
        if (index < 0 || index > fontResourceMap.size) {
            return
        }
        applyFont(view, fontResourceMap.get(FONT_CATEGORIES[index]))
    }

    fun <T: TextView> setRegularFont(view: T?) {
        applyFontByIndex(view, 0)
    }

    fun <T: TextView> setMediumFont(view: T?) {
        applyFontByIndex(view, 1)
    }

    fun <T: TextView> setBoldFont(view: T?) {
        applyFontByIndex(view, 2)
    }

    internal fun destroy() {
        //TODO to be done by changing immutability of INSTANCE
    }


}