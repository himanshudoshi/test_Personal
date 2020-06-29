package ca.bell.nmf.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.support.design.widget.BottomNavigationView
import android.util.AttributeSet
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView

/**
 * In this class we override the existing component to make the bottom bar keep
 * titles of buttons all the time visible.
 */
class AndroidBottomNavigationView : BottomNavigationView {

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    init {
        disableShiftMode(this)
    }

    @SuppressLint("RestrictedApi")
    fun disableShiftMode(view: BottomNavigationView) {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                item.setShiftingMode(false)
                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {
        } catch (e: IllegalAccessException) {
        }
    }

}
