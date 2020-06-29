package ca.bell.nmf

import android.content.pm.ActivityInfo
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import ca.bell.nmf.activities.TestActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import android.content.Intent
import org.junit.Before


@RunWith(AndroidJUnit4::class)
class OrientationTesting {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<TestActivity>(TestActivity::class.java)

    private var testActivity: TestActivity? = null

    @Before
    fun setup() {
        val intent = Intent(Intent.ACTION_PICK)
        testActivity = activityRule.launchActivity(intent)
    }

    @Test
    fun testLandscapeOrientation() {
        testActivity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        val landscape: Boolean = testActivity?.requestedOrientation === ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        assertTrue(landscape)

    }

    @Test
    fun testPortraitOrientation() {
        testActivity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val portrait = testActivity?.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        assertTrue(portrait)
    }

}