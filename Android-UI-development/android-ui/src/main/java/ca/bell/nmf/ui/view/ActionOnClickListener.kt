package ca.bell.nmf.ui.view

import android.view.View

/**
 * This interface intercepts allows to assign on click listener to buttons
 * in order to use them across the app.
 */
interface ActionOnClickListener {
    /**
     * this method is triggered when clicked on an FAB from BottomNavigationView
     */
    fun onActionClicked(view: View)
}