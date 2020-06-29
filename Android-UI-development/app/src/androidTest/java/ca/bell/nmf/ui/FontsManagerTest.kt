package ca.bell.nmf.ui

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import ca.bell.nmf.context.BaseApplication
import ca.bell.nmf.ui.FontsManager

import org.junit.Test
import org.junit.runner.RunWith
import ca.bell.nmf.context.BaseApplication.TYPE
import org.junit.Assert.*


@RunWith(AndroidJUnit4::class)
class FontsManagerTest {

    @Test
    fun fontsManagerCreation() {
        val context = InstrumentationRegistry.getContext()

        val fontsManager1 = FontsManager.getInstance()
        assertNull(fontsManager1.mContext)

        val fontsManager2 = FontsManager.getInstance(context, BaseApplication.TYPE.BELL_MOBILE)
        assertNotNull(fontsManager2.mContext)

    }

    @Test
    fun testCreationOnBellApp() {
        val bellFontsManager = createAndCheckFontManager(TYPE.BELL_MOBILE)
        assertEquals(bellFontsManager.size, 1)
    }

    @Test
    fun testCreationOnLuckyApp() {
        val bellFontsManager = createAndCheckFontManager(TYPE.LUCKY_MOBILE)
        assertEquals(bellFontsManager.size, 2)
    }

    @Test
    fun testCreationOnPcMobileApp() {
        val bellFontsManager = createAndCheckFontManager(TYPE.PC_MOBILE)
        assertEquals(bellFontsManager.size, 1)
    }

    @Test
    fun testCreationOnVirginApp() {
        val bellFontsManager = createAndCheckFontManager(TYPE.VIRGIN_MOBILE)
        assertEquals(bellFontsManager.size, 1)
    }

    private fun createAndCheckFontManager(appType: TYPE): FontsManager {
        val context = InstrumentationRegistry.getContext()
        val fontsManager = FontsManager.getInstance(context, appType)
        assertNotNull(fontsManager.mContext)
        return fontsManager
    }

}
