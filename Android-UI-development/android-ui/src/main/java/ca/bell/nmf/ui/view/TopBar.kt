package ca.bell.nmf.ui.view

import android.support.v7.widget.Toolbar
import android.view.View
import ca.bell.nmf.ui.context.BaseActivity
import ca.bell.nmf.ui.R
import android.util.TypedValue
import android.widget.*


/**
 * This class represents a custom Android Topbar/Toolbar. Every activity should have its own topbar
 * that has title only and/or subtitle.
 * @constructor creates topbar object and injects its activity. It supports only one constructor
 * @param mActivity activity to be injected.
 */

class TopBar(mActivity: BaseActivity): View.OnClickListener {

    companion object {
        /**
         * @property MIN_TITLE_LENGTH used to check minimum legnth of topbar's title
         */
        private const val MIN_TITLE_LENGTH = 2
    }

    /**
     * @property mActivity global reference to topbar's activity
     */
    private val mActivity = mActivity
    /**
     * @property toolBar Toolbar from top_bar_layout.xml file
     */
    private val toolBar: Toolbar = mActivity.findViewById(R.id.topbar)!!

    /**
     * @property titleTextView A TextView to show activity's title.
     */
    private val titleTextView: TextView?
    /**
     * @property subTitleTextView TextView to show activity's subtitle if needed.
     */
    private val subTitleTextView: TextView?
    /**
     * @property subTitleBackgroundView View to show darker color of topbar for @property subTitleTextView.
     */
    private val subTitleBackgroundView: View?
    /**
     * @property homeButton home button that is used also as software back button.
     */
    private val homeButton: ImageButton?
    /**
     * @property homeButton menu button to show option menu.
     */
    private val menuButton: ImageButton?

    /**
     * @property popupMenu Options Menu to be shown after menu button clicked (more menu).
     */
    private var popupMenu: PopupMenu? = null

    /**
     * @property onHomeClickedListener click listener for home button.
     */
    private var onHomeClickedListener: TopBarButtonClickListener? = null

    /**
     * @property onMenuItemClickedListener popup menu item clicked listener.
     */
    private var onMenuItemClickedListener: PopupMenu.OnMenuItemClickListener? = null

    /**
     * @property listenersMap map that holds references to all registered custom option menu buttons.
     */
    private val listenersMap: HashMap<Int, TopBarButtonClickListener?> by lazy {
        HashMap<Int, TopBarButtonClickListener?>()
    }

    private val optionButtonsLinearLayout: LinearLayout by lazy {
        toolBar.findViewById<LinearLayout>(R.id.menuOptionButtonsLinearLayout)
    }

    /**
     * @interface HomeButtonClickListener used to be the implementation of home/back button click listener
     */
    interface TopBarButtonClickListener {
        fun onClicked(button: ImageButton)
    }

    init {
        titleTextView = toolBar.findViewById(R.id.titleTextView)
        subTitleTextView = toolBar.findViewById(R.id.subTitleTextView)
        subTitleBackgroundView = toolBar.findViewById(R.id.subtitleBackgroundView)

        homeButton = toolBar.findViewById(R.id.homeButton)
        homeButton.setOnClickListener(this)
        menuButton = toolBar.findViewById(R.id.menuButton)
        menuButton.setOnClickListener(this)

        preparePopUpMenu(menuButton)
    }

    /**
     * @param titleRes resource string to be shown as title
     * @return TopBar object, light builder pattern
     *
     */
    fun setTitle(titleRes: Int): TopBar {
        checkResource(titleRes)
        val title = mActivity.let {
            it.resources!!.getString(titleRes)
        }
        return setTitle(title)
    }

    /**
     * @param title string to be shown as title
     * @return TopBar object, light builder pattern
     *
     */
    fun setTitle(title: String): TopBar {
        title.let {
            checkResource(title)
            titleTextView?.text = title
        }
        return this
    }

    /**
     * @param subTitleRes resource string to be shown as subtitle
     * @return TopBar object, light builder pattern
     *
     */
    fun setSubTitle(subTitleRes: Int): TopBar {
        checkResource(subTitleRes)
        val title = mActivity.resources!!.getString(subTitleRes)
        return setSubTitle(title)
    }

    /**
     * @param subTitle string to be shown as subtitle
     * @return TopBar object, light builder pattern
     *
     */
    fun setSubTitle(subTitle: String): TopBar {
        subTitle.let {
            checkResource(subTitle)
            subTitleTextView?.text = subTitle
            showSubTitle()
        }
        return this
    }

    /**
     * @param value any value to to check its validity, int should be positive, string should be
     * neither null nor less than 3 chars long.
     */
    internal fun checkResource(value: Any) {
        when (value) {
            is Int -> {
                if(value <= 0) {
                    throw RuntimeException("Invalide resource, please check your resource id")
                }
            }
            is String -> {
                if (value.length < MIN_TITLE_LENGTH) {
                    throw RuntimeException("Text can not be less than $MIN_TITLE_LENGTH chars")
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.homeButton -> {
                onHomeClickedListener.let {
                    onHomeClickedListener?.onClicked(v as ImageButton)
                    return
                }
                mActivity.onBackPressed()
            }
            R.id.menuButton -> {
                showPopup()
                return
            }
        }

        listenersMap[v?.id].let {
            it?.onClicked(v as ImageButton)
        }
    }

    /**
     * Hiding subtitle TextView and Background
     *
     */
    fun hideSubTitle() {
        subTitleTextView.let{
            it?.visibility = View.GONE
            subTitleBackgroundView.let {
                it!!.visibility = View.GONE
            }
        }
    }

    /**
     * Showing subtitle TextView and Background
     *
     */
    fun showSubTitle() {
        subTitleTextView.let{
            it?.visibility = View.VISIBLE
            subTitleBackgroundView.let {
                it?.visibility = View.VISIBLE
            }
        }
    }

    /**
     * Hiding Home/Back button
     *
     */
    fun hideHomeButton() {
        homeButton.let {
            it?.visibility = View.GONE
        }
    }

    /**
     * Show Home/Back button
     *
     */
    fun showHomeButton() {
        homeButton.let{
            it?.visibility = View.VISIBLE
        }
    }

    /**
     * Showing Menu button
     *
     */
    fun showMenuButton() {
        menuButton.let {
            it?.visibility = View.VISIBLE
        }
    }

    /**
     * Hiding Menu button
     *
     */
    fun hideMenuButton() {
        menuButton.let {
            it?.visibility = View.GONE
        }
    }

    private fun preparePopUpMenu(v: View) {
        popupMenu = PopupMenu(mActivity, menuButton)
        popupMenu.let {
        val inflater = popupMenu?.menuInflater
        inflater?.inflate(R.menu.top_bar_popup_menu, popupMenu?.menu)
        }
    }

    /**
    * @param v The view where options menu will be anchored. In our case, it is Menu Button.
    * Show options menu I(more menu) and anchor it to Menu Button
    * */
    private fun showPopup() {
        popupMenu?.setOnMenuItemClickListener(onMenuItemClickedListener)
        popupMenu?.show()
    }


    /**
     * @param listener Popup menu item clicked listener as setter.
     * Manages all menu items click events.
     * */
    fun setItemClickedListener(listener: PopupMenu.OnMenuItemClickListener) {
        onMenuItemClickedListener = listener
        popupMenu?.setOnMenuItemClickListener(onMenuItemClickedListener)
    }

    /**
     * @param listener Popup menu item clicked listener as setter.
     * Manages all menu items click events.
     * */
    fun setOnHomeButtonClicked(listener: TopBarButtonClickListener) {
        onHomeClickedListener = listener
    }

    /**
     * @param iconResource resource drawable to change home button icon.
     * */
    fun addHomeButtonIcon(iconResource: Int) {
        checkResource(iconResource)
        homeButton!!.setImageResource(iconResource)
    }

    /**
     * @param menuItemId popup menu item id to change its title.
     * @param textResource string resource to add it as title.
     * */
    fun setMenuItemTitle(menuItemId: Int, textResource: Int) {
        checkResource(menuItemId)
        checkResource(textResource)
        popupMenu?.let{
            it.menu.findItem(menuItemId).title = mActivity.resources.getString(textResource)
        }
    }

    /**
     * @param menuItemId popup menu item id to change its title.
     * @param titleText string text to add it as title.
     * */
    fun setMenuItemTitle(menuItemId: Int, titleText: String) {
        checkResource(menuItemId)
        checkResource(titleText)
        popupMenu?.let{
            it.menu.findItem(menuItemId).title = titleText
        }
    }

    /**
     * @param id option button id. In case this button is not provided (equals to 0 or less),
     * this id will be auto generated.
     * @param icon option button icon.
     * @param onClickListener click listener for the added option menu button.
     * This method is ment to add visually custom option buttons on the top right side.
     * Also, it assigns a click listener to respond to click  events of the custom option button.
     *
     * */
    fun addOptionButton(id: Int, icon: Int, onClickListener: TopBarButtonClickListener?): ImageView {

        var buttonId = id

        if (id <= 0) {
            buttonId = listenersMap.size+1
        }

        checkResource(icon)

        //Adding visually button
        val newOptionButton = getOptionButtonView(icon)
        newOptionButton.id = buttonId
        optionButtonsLinearLayout.addView(newOptionButton, 0)

        listenersMap[buttonId] = onClickListener
        newOptionButton.setOnClickListener(this)

        return newOptionButton
    }

    /**
     * @param icon option button icon.
     * @param onClickListener click listener for the added option menu button.
     * This method is ment to add visually custom option buttons on the top right side.
     * Also, it assigns a click listener to respond to click  events of the custom option button.
     *
     * */
    fun addOptionButton(icon: Int, onClickListener: TopBarButtonClickListener?): ImageView {
        return addOptionButton(0, icon, onClickListener)
    }

    /**
     * @param icon option button icon.
     * Preparing visually ImageButton view.
     * */
    private fun getOptionButtonView(icon: Int): ImageButton {
        val optionButton:ImageButton = ImageButton(mActivity)

        val size:Int = mActivity.resources.getDimension(R.dimen.menu_button_size).toInt()
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(size, size)

        optionButton.setImageResource(icon)
        optionButton.setBackgroundColor(mActivity.getColor(android.R.color.transparent))

        val outValue = TypedValue()
        mActivity.theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true)
        optionButton.foreground = mActivity.getDrawable(outValue.resourceId)

        optionButton.layoutParams = layoutParams
        return optionButton
    }
}