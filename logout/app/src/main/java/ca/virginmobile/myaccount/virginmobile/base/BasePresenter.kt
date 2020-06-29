package ca.virginmobile.myaccount.virginmobile.base

import android.support.v4.app.LoaderManager

/*
* This is a base class of presenter contract between view class and it's presenter
*/
interface BasePresenter {

    fun attachView(view: Any)

    fun detachView()

}
