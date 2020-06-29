package ca.bell.nmf.ui.view

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import ca.bell.nmf.activities.TestActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.content.Intent

import org.junit.Before



@RunWith(AndroidJUnit4::class)
class TopBarTesting {

    @Rule @JvmField
    val activityRule = ActivityTestRule<TestActivity>(TestActivity::class.java)

    var testActivity: TestActivity = activityRule.activity

    @Before
    fun setup() {
        val intent = Intent(Intent.ACTION_PICK)
        testActivity = activityRule.launchActivity(intent)
    }

    @Test(expected = RuntimeException::class)
    fun testValueChecker() {
        val topbar = TopBar(testActivity)
        topbar.checkResource("123")
        topbar.checkResource(1)

    }

    @Test
    fun testValueCheckerWithException() {
        val topbar = TopBar(testActivity)
        topbar.checkResource("")
        topbar.checkResource(0)

    }

}