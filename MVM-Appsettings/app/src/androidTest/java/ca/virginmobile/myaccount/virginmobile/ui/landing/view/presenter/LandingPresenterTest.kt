package ca.virginmobile.myaccount.virginmobile.ui.landing.view.presenter

import android.app.Application
import android.app.Instrumentation
import android.content.Context
import android.support.test.InstrumentationRegistry
import ca.bell.nmf.utils.common.internaldata.InternalDataManager
import ca.virginmobile.myaccount.virginmobile.ui.landing.model.LandingContract
import ca.virginmobile.myaccount.virginmobile.ui.landing.presenter.LandingPresenter
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.lang.Exception

/**
 *
 */
class LandingPresenterTest {


    private lateinit var landingPresenter: LandingPresenter
    private lateinit var mContext: Context
    private lateinit var sm: InternalDataManager

    @Before
    fun setUp() {
        landingPresenter = LandingPresenter()
        mContext = Instrumentation.newApplication(Application::class.java, InstrumentationRegistry.getTargetContext())
        sm = InternalDataManager.getInstance(mContext)
    }

    @Test(expected = Exception::class)
    fun testAttachViewWithInvalidObject() {
        val any = ArrayList<String>()
        landingPresenter.attachView(any)

    }

    @Test
    fun testAttachViewWithValidObject() {
        val mockObject = Mockito.mock(LandingContract.ILandingView::class.java)
        landingPresenter.attachView(mockObject)
    }

    @Test
    fun testIsBupUser() {
        Assert.assertEquals(true, landingPresenter.isBupUser())
    }


    @Test
    fun testIsNotBupUser() {
        Assert.assertEquals(false, !landingPresenter.isBupUser())
    }



}



