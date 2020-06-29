package ca.bell.nmf.ui.view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import ca.bell.nmf.ui.R
import kotlinx.android.synthetic.main.component_bottom_navigation_bar_layout.view.*


/**
 * This class represents a custom Android BottomNavigationView with 4 Floating Action Buttons.
 * This class manages two views of the CustomBottomNav,
 * first one is the default Bottom Nav View and the second one is the expanded view.
 * @constructor creates the CustomBottomNavigationBar and sets click actions
 * @param context to be injected
 * @param AttributeSet
 * @author Vitalie Suba
 */

class BottomNavigationView(context: Context, val attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    companion object {
        /**
         * @property ACTION1_TAG tag assigned to FAB1 (from left to right), to access this view by tag name
         */
        const val ACTION1_TAG = 1

        /**
         * @property ACTION2_TAG tag assigned to FAB2 (from left to right), to access this view by tag name
         */
        const val ACTION2_TAG = 2

        /**
         * @property ACTION3_TAG tag assigned to FAB3 (from left to right), to access this view by tag name
         */
        const val ACTION3_TAG = 3

        /**
         * @property ACTION4_TAG tag assigned to FAB4 (from left to right), to access this view by tag name
         */
        const val ACTION4_TAG = 4
    }

    /**
     * @property isRevealMenuShown flag to toggle reveal/hide effect
     */
    private var isRevealMenuShown = false

    /**
     * @property offsetXForCornerFABs default X coordinate for the middle of current device display
     * also is the coordinate where should be moved the top left and right FABs
     */
    private var offsetXForCornerFABs = 500.0f

    /**
     * @property offsetXForMiddleFABs coordinate where should be moved middles left and right FABs
     */
    private var offsetXForMiddleFABs = 200.0f

    /**
     * @property hideRevealDuration the period to animate the reveal and hide of current component.
     * should be set in milliseconds
     */
    private val hideRevealDuration = 200L

    /**
     * @property defaultOffsetAssetPosition default offset X position for the assets in the layout
     */
    private var defaultOffsetAssetPosition = 0.0f

    /**
     *  @property onClickListeners hashMap with action listeners assigned to all 4 FABs
     *  When the component is created, every FAB has a null action listener assigned.
     *  Each listener should be added using separate methods.
     */
    private val onClickListeners = hashMapOf<Int, ActionOnClickListener>()

    /**
     * @property actionFabsMap is used to keep and map FABs from BottomNavigationView with
     * their tag.
     */
    private val actionFabsMap = hashMapOf<Int, View>()

    /**
     * @property alphaFullVisibility value used to show the assets after fade effect
     */
    private val alphaFullVisibility = 1.0f

    /**
     * @property alphaTransparency value used for fade effect to "hide" assets
     */
    private val alphaTransparency = 0.0f

    /**
     * @property androidBottomNavigation bottom navigation view reference.
     */
    private var androidBottomNavigation: AndroidBottomNavigationView? = null

    /**
     * @property itemSelectedListener bottom navigation item selected listener.
     */
    private var itemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener? = null

    init {
        val phoneDisplaySize = getPhoneDisplaySize(context)
        val phoneDisplayWidth = phoneDisplaySize.x
        LayoutInflater.from(context).inflate(R.layout.component_bottom_navigation_bar_layout, this, true)
        this.offsetXForCornerFABs = (phoneDisplayWidth / 2).toFloat() - resources.getDimension(
                R.dimen.action_fab_size)
        this.offsetXForMiddleFABs = (phoneDisplayWidth / 5).toFloat() - resources.getDimension(
                R.dimen.action_fab_size) / 2
        hideSupportMenu()
        expandActionFAB.setOnClickListener {
            if (!isRevealMenuShown) rotateFabLeft() else rotateFabRight()
            isRevealMenuShown = !isRevealMenuShown
        }
        setAssetsVisible()
        initView()
    }

    private fun initView() {
        attrs.let {
            val values = context.obtainStyledAttributes(attrs,
                    R.styleable.bottom_navigation, 0, 0)

            val action1Title = values.getString(R.styleable.bottom_navigation_action1Title)
            val action2Title = values.getString(R.styleable.bottom_navigation_action2Title)
            val action3Title = values.getString(R.styleable.bottom_navigation_action3Title)
            val action4Title = values.getString(R.styleable.bottom_navigation_action4Title)

            val action1TitleTextView = findViewById<TextView>(R.id.action1TextView)
            action1TitleTextView.text = action1Title ?: ""
            val action2TitleTextView = findViewById<TextView>(R.id.action2TextView)
            action2TitleTextView.text = action2Title ?: ""
            val action3TitleTextView = findViewById<TextView>(R.id.action3TextView)
            action3TitleTextView.text = action3Title ?: ""
            val action4TitleTextView = findViewById<TextView>(R.id.action4TextView)
            action4TitleTextView.text = action4Title ?: ""

            val action1Icon = values.getInt(R.styleable.bottom_navigation_action1Icon, 0)
            val action2Icon = values.getInt(R.styleable.bottom_navigation_action2Icon, 0)
            val action3Icon = values.getInt(R.styleable.bottom_navigation_action3Icon, 0)
            val action4Icon = values.getInt(R.styleable.bottom_navigation_action4Icon, 0)

            val defaultIconDrawable = resources.getDrawable(R.drawable.icon_earth)

            val action1FAB = findViewById<FloatingActionButton>(R.id.action1FAB)
            action1FAB.setImageDrawable(if (action1Icon != 0) values.getDrawable(action1Icon) else defaultIconDrawable)

            val action2FAB = findViewById<FloatingActionButton>(R.id.action2FAB)
            action2FAB.setImageDrawable(if (action2Icon != 0) values.getDrawable(action2Icon) else defaultIconDrawable)

            val action3FAB = findViewById<FloatingActionButton>(R.id.action3FAB)
            action3FAB.setImageDrawable(if (action3Icon != 0) values.getDrawable(action3Icon) else defaultIconDrawable)

            val action4FAB = findViewById<FloatingActionButton>(R.id.action4FAB)
            action4FAB.setImageDrawable(if (action4Icon != 0) values.getDrawable(action4Icon) else defaultIconDrawable)

            androidBottomNavigation = findViewById(R.id.androidBottomNavigationView)
        }
    }

    /**
     * @param actionOnClickListener interface to catch the Click on FAB 1 (from left to right) from this component
     * In this case the title and the icon are defaults.
     */
    fun addAction1(actionOnClickListener: ActionOnClickListener): ca.bell.nmf.ui.view.BottomNavigationView {
        return addAction(action1FAB, ACTION1_TAG, actionOnClickListener)
    }

    /**
     * @param text title (resource ID) for the FAB
     * @param icon resource ID for the icon on FAB
     * @param actionOnClickListener interface to catch the Click on FAB 1 (from left to right) from this component
     */
    fun addAction1(text: Int, icon: Int,
                   actionOnClickListener: ActionOnClickListener): ca.bell.nmf.ui.view.BottomNavigationView {
        action1TextView.text = resources.getString(text)
        action1FAB.setImageResource(icon)
        return addAction(action1FAB, ACTION1_TAG, actionOnClickListener)
    }

    /**
     * @param text title STRING for the FAB
     * @param icon resource DRAWABLE for the icon on FAB
     * @param actionOnClickListener interface to catch the Click on FAB 1 (from left to right) from this component
     */
    fun addAction1(text: String, icon: Drawable,
                   actionOnClickListener: ActionOnClickListener): ca.bell.nmf.ui.view.BottomNavigationView {
        action1TextView.text = text
        action1FAB.setImageDrawable(icon)
        return addAction(action1FAB, ACTION1_TAG, actionOnClickListener)
    }

    /**
     * @param actionOnClickListener interface to catch the Click on FAB 2 (from left to right) from this component
     * In this case the title and the icon are defaults.
     */
    fun addAction2(actionOnClickListener: ActionOnClickListener): ca.bell.nmf.ui.view.BottomNavigationView {
        return addAction(action2FAB, ACTION2_TAG, actionOnClickListener)
    }

    /**
     * @param text title (resource ID) for the FAB
     * @param icon resource ID for the icon on FAB
     * @param actionOnClickListener interface to catch the Click on FAB 2 (from left to right) from this component
     */
    fun addAction2(text: Int, icon: Int,
                   actionOnClickListener: ActionOnClickListener): ca.bell.nmf.ui.view.BottomNavigationView {
        action2TextView.text = resources.getString(text)
        action2FAB.setImageResource(icon)
        return addAction(action2FAB, ACTION2_TAG, actionOnClickListener)
    }

    /**
     * @param text title STRING for the FAB
     * @param icon resource DRAWABLE for the icon on FAB
     * @param actionOnClickListener interface to catch the Click on FAB 2 (from left to right) from this component
     */
    fun addAction2(text: String, icon: Drawable,
                   actionOnClickListener: ActionOnClickListener): ca.bell.nmf.ui.view.BottomNavigationView {
        action2TextView.text = text
        action2FAB.setImageDrawable(icon)
        return addAction(action2FAB, ACTION2_TAG, actionOnClickListener)
    }

    /**
     * @param actionOnClickListener interface to catch the Click on FAB 3 (from left to right) from this component
     * In this case the title and the icon are defaults.
     */
    fun addAction3(actionOnClickListener: ActionOnClickListener): ca.bell.nmf.ui.view.BottomNavigationView {
        return addAction(action3FAB, ACTION3_TAG, actionOnClickListener)
    }

    /**
     * @param text title (resource ID) for the FAB
     * @param icon resource ID for the icon on FAB
     * @param actionOnClickListener interface to catch the Click on FAB 3 (from left to right) from this component
     */
    fun addAction3(text: Int, icon: Int,
                   actionOnClickListener: ActionOnClickListener): ca.bell.nmf.ui.view.BottomNavigationView {
        action3TextView.text = resources.getString(text)
        action3FAB.setImageResource(icon)
        return addAction(action3FAB, ACTION3_TAG, actionOnClickListener)
    }

    /**
     * @param text title STRING for the FAB
     * @param icon resource DRAWABLE for the icon on FAB
     * @param actionOnClickListener interface to catch the Click on FAB 3 (from left to right) from this component
     */
    fun addAction3(text: String, icon: Drawable,
                   actionOnClickListener: ActionOnClickListener): ca.bell.nmf.ui.view.BottomNavigationView {
        action3TextView.text = text
        action3FAB.setImageDrawable(icon)
        return addAction(action3FAB, ACTION3_TAG, actionOnClickListener)
    }

    /**
     * @param actionOnClickListener interface to catch the Click on FAB 4 (from left to right) from this component
     * In this case the title and the icon are defaults.
     */
    fun addAction4(actionOnClickListener: ActionOnClickListener): ca.bell.nmf.ui.view.BottomNavigationView {
        return addAction(action4FAB, ACTION4_TAG, actionOnClickListener)
    }

    /**
     * @param text title (resource ID) for the FAB
     * @param icon resource ID for the icon on FAB
     * @param actionOnClickListener interface to catch the Click on FAB 4 (from left to right) from this component
     */
    fun addAction4(text: Int, icon: Int,
                   actionOnClickListener: ActionOnClickListener): ca.bell.nmf.ui.view.BottomNavigationView {
        action4TextView.text = resources.getString(text)
        action4FAB.setImageResource(icon)
        return addAction(action4FAB, ACTION4_TAG, actionOnClickListener)
    }

    /**
     * @param text title STRING for the FAB
     * @param icon resource DRAWABLE for the icon on FAB
     * @param actionOnClickListener interface to catch the Click on FAB 4 (from left to right) from this component
     */
    fun addAction4(text: String, icon: Drawable,
                   actionOnClickListener: ActionOnClickListener): ca.bell.nmf.ui.view.BottomNavigationView {
        action4TextView.text = text
        action4FAB.setImageDrawable(icon)
        return addAction(action4FAB, ACTION4_TAG, actionOnClickListener)
    }

    /**
     * this method is used to map existing fabs and clickListeners
     * @param actionTag tag of each action FAB
     * @param actionOnClickListener clickListener for each fab
     */
    private fun addAction(actionFAB: FloatingActionButton, actionTag: Int,
                          actionOnClickListener: ActionOnClickListener):
            ca.bell.nmf.ui.view.BottomNavigationView {

        onClickListeners[actionTag] = actionOnClickListener
        actionFabsMap[actionTag] = actionFAB
        actionFabsMap[actionTag]?.setOnClickListener {
            actionOnClickListener.onActionClicked(it)
        }

        return this
    }

    private fun addAction(actionTag: Int, actionOnClickListener: ActionOnClickListener) {
        onClickListeners[actionTag] = actionOnClickListener
        actionFabsMap[actionTag]?.setOnClickListener {
            actionOnClickListener.onActionClicked(it)
        }
    }

    /**
     * Assign Navigation Item selected listener to Bottom NAvigation
     * @param itemSelectedListener Navigation item selected listener
     *
     */
    fun setOnItemSelectedListener(itemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener
        androidBottomNavigation.let {
            it?.setOnNavigationItemSelectedListener(this.itemSelectedListener)
        }
    }

    /**
     * @return the click listener for FAB 1 if assigned, else returns null
     */
    fun getAction1Listener() = onClickListeners[ACTION1_TAG]

    /**
     * @return the click listener for FAB 2 if assigned, else returns null
     */
    fun getAction2Listener() = onClickListeners[ACTION2_TAG]

    /**
     * @return the click listener for FAB 3 if assigned, else returns null
     */
    fun getAction3Listener() = onClickListeners[ACTION3_TAG]

    /**
     * @return the click listener for FAB 4 if assigned, else returns null
     */
    fun getAction4Listener() = onClickListeners[ACTION4_TAG]

    /**
     * @return the Point size of the current device display
     */
    private fun getPhoneDisplaySize(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val currentDisplay = windowManager.defaultDisplay
        val size = Point()
        currentDisplay.getSize(size)
        return size
    }

    /**
     * this method sets the FABs and corresponding titles visible.
     * In default initial state, the assets are invisible
     */
    private fun setAssetsVisible() {
        revealShapeImageView.visibility = View.VISIBLE
        action1TextView.visibility = View.VISIBLE
        action2TextView.visibility = View.VISIBLE
        action3TextView.visibility = View.VISIBLE
        action4TextView.visibility = View.VISIBLE
    }

    /**
     * this method rotates the main action FAB to left and reveals this navigation bar with 4 FABs
     * it rotates the FAB to left to 45 degrees
     */
    private fun rotateFabLeft() {
        expandActionFAB?.let {
            val animX = ObjectAnimator.ofFloat(expandActionFAB, View.ROTATION, -45.0f)
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(animX)
            animatorSet.setDuration(hideRevealDuration).start()
            revealSupportMenu()
        }
    }

    /**
     * this method rotates the main action FAB to right and hides this navigation bar.
     * it rotates the FAB to the right to initial position
     */
    private fun rotateFabRight() {
        expandActionFAB?.let {
            val animatorSet = AnimatorSet()
            val animate = ObjectAnimator.ofFloat(expandActionFAB, View.ROTATION, defaultOffsetAssetPosition)
            animatorSet.playTogether(animate)
            animatorSet.setDuration(hideRevealDuration).start()
            hideSupportMenu()
        }
    }

    /**
     * this method is used to reveal this custom navigation bar
     */
    private fun revealSupportMenu() {
        val animatorSet = AnimatorSet()
        val animateScaleX = ObjectAnimator.ofFloat(revealShapeImageView, View.SCALE_X, 1.6f)
        val animateX = ObjectAnimator.ofFloat(revealShapeImageView, View.X, defaultOffsetAssetPosition)
        val animateY = ObjectAnimator.ofFloat(revealShapeImageView, View.Y, defaultOffsetAssetPosition)
        val animateAlpha = ObjectAnimator.ofFloat(revealShapeImageView, View.ALPHA, alphaFullVisibility)
        val animateTranslationY =
                ObjectAnimator.ofFloat(revealShapeImageView, View.TRANSLATION_Y, defaultOffsetAssetPosition)
        animatorSet.playTogether(animateScaleX, animateX, animateAlpha, animateTranslationY, animateY)
        animatorSet.setDuration(hideRevealDuration)
                .start()

        playAnimation(action1FAB, defaultOffsetAssetPosition, defaultOffsetAssetPosition, alphaFullVisibility)
        playAnimation(action1TextView, defaultOffsetAssetPosition, defaultOffsetAssetPosition, alphaFullVisibility)
        playAnimation(action2FAB, defaultOffsetAssetPosition, defaultOffsetAssetPosition, alphaFullVisibility)
        playAnimation(action2TextView, defaultOffsetAssetPosition, defaultOffsetAssetPosition, alphaFullVisibility)
        playAnimation(action3FAB, defaultOffsetAssetPosition, defaultOffsetAssetPosition, alphaFullVisibility)
        playAnimation(action3TextView, defaultOffsetAssetPosition, defaultOffsetAssetPosition, alphaFullVisibility)
        playAnimation(action4FAB, defaultOffsetAssetPosition, defaultOffsetAssetPosition, alphaFullVisibility)
        playAnimation(action4TextView, defaultOffsetAssetPosition, defaultOffsetAssetPosition, alphaFullVisibility)
    }

    /**
     * this property is used to hide this custom navigation bar
     */
    private fun hideSupportMenu() {
        val lowerYOffset = 300.0f
        val middleYOffset = 200.0f
        val shapeYOffset = 500f
        val animatorSet = AnimatorSet()
        val animScaleX = ObjectAnimator.ofFloat(revealShapeImageView, View.SCALE_X, defaultOffsetAssetPosition)
        val animateY = ObjectAnimator.ofFloat(revealShapeImageView, View.Y, shapeYOffset)
        val animateTranslationY = ObjectAnimator.ofFloat(revealShapeImageView, View.TRANSLATION_Y, offsetXForCornerFABs)
        val animateAlpha = ObjectAnimator.ofFloat(revealShapeImageView, View.ALPHA, alphaFullVisibility)
        animatorSet.playTogether(animateTranslationY, animScaleX, animateAlpha, animateY)
        animatorSet.setDuration(hideRevealDuration)
                .start()

        playAnimation(action1FAB, offsetXForCornerFABs, middleYOffset, alphaTransparency)
        playAnimation(action1TextView, offsetXForCornerFABs, middleYOffset, alphaTransparency)
        playAnimation(action2FAB, offsetXForMiddleFABs, lowerYOffset, alphaTransparency)
        playAnimation(action2TextView, offsetXForMiddleFABs, lowerYOffset, alphaTransparency)
        playAnimation(action3FAB, -offsetXForMiddleFABs, lowerYOffset, alphaTransparency)
        playAnimation(action3TextView, -offsetXForMiddleFABs, lowerYOffset, alphaTransparency)
        playAnimation(action4FAB, -offsetXForCornerFABs, middleYOffset, alphaTransparency)
        playAnimation(action4TextView, -offsetXForCornerFABs, middleYOffset, alphaTransparency)
    }

    /**
     * this method is used to play each assets animation
     */
    internal fun playAnimation(animatedView: View, translationX: Float, translationY: Float, alpha: Float) {
        val animatorSet = AnimatorSet()
        val animateTranslationX = ObjectAnimator.ofFloat(animatedView, View.TRANSLATION_X, translationX)
        val animateTranslationY = ObjectAnimator.ofFloat(animatedView, View.TRANSLATION_Y, translationY)
        val animateAlpha = ObjectAnimator.ofFloat(animatedView, View.ALPHA, alpha)
        animatorSet.playTogether(animateTranslationX, animateTranslationY, animateAlpha)
        animateAlpha?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                if (alpha == 1f) {
                    animatedView.visibility = View.VISIBLE
                    animatedView.isClickable = false
                }
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                if (alpha == 0f) {
                    animatedView.visibility = View.GONE
                    if (animatedView is FloatingActionButton) {
                        animatedView.isClickable = false
                    }
                }
                if (alpha == 1f && animatedView is FloatingActionButton) {
                    animatedView.isClickable = true
                }
            }
        })
        animatorSet.setDuration(hideRevealDuration)
                .start()
    }
}
