package ca.bell.nmf.ui.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import ca.bell.nmf.ui.R
import ca.bell.nmf.ui.payment.model.CardsScrollerObject
import ca.bell.nmf.ui.payment.model.CreditCard
import ca.bell.nmf.ui.payment.model.SupportedCards
import ca.bell.nmf.ui.payment.model.TopUpAmount
import ca.bell.nmf.ui.utility.ResourceParser
import kotlinx.android.synthetic.main.item_cc_layout.view.*
import kotlinx.android.synthetic.main.item_topup_amount_layout.view.*


class CardsScrollerView(val mContext: Context, private val attrs: AttributeSet): LinearLayout(mContext, attrs) {

    private val rv = RecyclerView(mContext)
    internal var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
    private var supportedCreditCards = emptyArray<CreditCard>()
    var itemSelectedListener: OnItemSelectedListener? = null
    var clickableCards: Boolean = true

    private var viewType: Int = 1

    interface OnItemSelectedListener {
        fun onItemSelected(itemData: CardsScrollerObject, itemIndex: Int)
    }

    init {
        supportedCreditCards = getSupportedCards()
        initView()
        rv.adapter = adapter
        rv.addOnScrollListener(ScrollListener())
        rv.layoutManager = ZoomLayoutManager(mContext, true)

        orientation = HORIZONTAL

        PagerSnapHelper().attachToRecyclerView(rv)
        addView(rv)

        scrollToMiddle()

    }

    private fun scrollToMiddle() {
        rv.smoothScrollToPosition(adapter?.itemCount?.div(2) ?: 0)
    }

    private fun initView() {
        attrs.let {
            val values = mContext.obtainStyledAttributes(attrs,
                    R.styleable.cards_scroller, 0, 0)
            viewType = values.getInteger(R.styleable.cards_scroller_viewType, 1)
            
            when(viewType) {
                1 -> {
                    adapter = CreditCardsAdapter(supportedCreditCards) as RecyclerView.Adapter<RecyclerView.ViewHolder>

                }
                2 -> {
                    adapter
                }
                3 -> {
                    adapter
                }
                else -> {
                    adapter = CreditCardsAdapter(supportedCreditCards) as RecyclerView.Adapter<RecyclerView.ViewHolder>
                }
            }
        }
    }

    fun addItemSelectedListener(itemSelectedListener: OnItemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener
    }

    private fun getSupportedCards(): Array<CreditCard> {
        val resourceParser = ResourceParser(mContext)
        return (resourceParser.fromJsonFile(R.raw.credit_cards, SupportedCards::class.java) as SupportedCards).cards
    }

    fun asTopUpAmountView(topUpAmountList: Array<TopUpAmount>) {
        adapter = TopUpAmountAdapter(topUpAmountList) as RecyclerView.Adapter<RecyclerView.ViewHolder>
        rv.adapter = adapter
        rv.adapter.notifyDataSetChanged()

        scrollToMiddle()

        viewType = 2
    }

    fun asCreditCardScrollerView() {
        supportedCreditCards = getSupportedCards()
        adapter = CreditCardsAdapter(supportedCreditCards) as RecyclerView.Adapter<RecyclerView.ViewHolder>
        rv.adapter = adapter
        rv.adapter.notifyDataSetChanged()

        scrollToMiddle()

        viewType = 1
    }

    fun asCustomScrollerView(customAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        adapter = customAdapter
        rv.adapter = adapter
        rv.adapter.notifyDataSetChanged()

        scrollToMiddle()

        viewType = 3
    }

    /**
     * Adapters
     * */
    internal inner class CreditCardsAdapter(items: Array<CreditCard>):
                         RecyclerView.Adapter<CreditCardsAdapter.CreditCardViewHolder>() {

        private val items = items
        private val checkedArray: BooleanArray = BooleanArray(items.size)
        private val inflater = LayoutInflater.from(mContext)

        init {
            initCheckedArray()
        }

        private fun initCheckedArray() {
            for (i in 0 until itemCount) {
                checkedArray[i] = false
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditCardViewHolder {
            val rootView = inflater.inflate(R.layout.item_cc_layout, parent, false)
            return CreditCardViewHolder(rootView)
        }

        override fun onBindViewHolder(holder: CreditCardViewHolder, position: Int) {
            holder.let {

                val creditCard = supportedCreditCards[position]
                it.view.id = creditCard.viewId
                it.position = position

                /**
                 *
                 * json files will contain the name of background, however the full identifier
                 * will be taken from package manager.
                 * These two lines just provide full resource identifier name in string
                 *
                 * */
                val backgroundResourceId = mContext.resources.getIdentifier(creditCard.backgroundResource, "drawable", mContext.packageName)
                it.view.ccBackgroundImageView.setImageResource(backgroundResourceId)
            }
        }

        override fun getItemCount(): Int {
            return items.size
        }

        inner class CreditCardViewHolder(internal val view: View): RecyclerView.ViewHolder(view),
                    OnClickListener {

            internal var position: Int = 0

            init {
                view.setOnClickListener(this)
            }

            override fun onClick(v: View?) {

                if (!clickableCards) {
                    return
                }

                initCheckedArray()
                checkedArray[position] = true

                notifyDataSetChanged()

                itemSelectedListener.let {
                    itemSelectedListener?.onItemSelected(items[position], position)
                }
            }

        }


    }


    inner class TopUpAmountAdapter(items: Array<TopUpAmount>):
                RecyclerView.Adapter<TopUpAmountAdapter.TopUpAmountViewHolder>() {

        private val items = items
        private val checkedArray: BooleanArray = BooleanArray(items.size)
        private val inflater = LayoutInflater.from(mContext)

        private val viewWidth: Int

        init {
            val displayMetrics = DisplayMetrics()
            val windowManager: WindowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(displayMetrics)

            val edge = (displayMetrics.widthPixels * 20) / 100
            viewWidth = displayMetrics.widthPixels - edge

            initCheckedArray()
        }

        private fun initCheckedArray() {
            for (i in 0 until itemCount) {
                checkedArray[i] = false
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopUpAmountViewHolder {
            val rootView = inflater.inflate(R.layout.item_topup_amount_layout, parent, false)
            rootView.layoutParams = LayoutParams(viewWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
            return TopUpAmountViewHolder(rootView)
        }

        override fun onBindViewHolder(holder: TopUpAmountViewHolder, position: Int) {
            holder.let {

                val topUpObject = items[position]
                it.position = position

                /**
                 *
                 * json files will contain the name of background, however the full identifier
                 * will be taken from package manager.
                 * These two lines just provide full resource identifier name in string
                 *
                 * */

                it.view.topUpAmountTextView.text = mContext.resources.getString(R.string.price_dollar,
                        topUpObject.amount)
                it.view.topUpAmountExpiryTextView.text = mContext.resources.getString(R.string.expires_in,
                        topUpObject.expiryDays)
                it.view.appLogoImageView.setImageResource(getLogoDarwable())

                it.view.topUpAmountCheckbox.isChecked = checkedArray[position]
            }
        }

        private fun getLogoDarwable(): Int {
            // TODO add logic from BaseApplication to get app type then chose the right logo
            return R.drawable.icon_logo_voucher_pc_mobile
        }

        override fun getItemCount(): Int {
            return items.size
        }

        inner class TopUpAmountViewHolder(val view: View): RecyclerView.ViewHolder(view), OnClickListener {

            internal var position: Int = 0

            init {
                view.setOnClickListener(this)
            }

            override fun onClick(v: View?) {

                if (!clickableCards) {
                    return
                }

                initCheckedArray()
                checkedArray[position] = true

                notifyDataSetChanged()

                itemSelectedListener.let {
                    itemSelectedListener?.onItemSelected(items[position], position)
                }
            }
        }

    }


    /**
     *
     *
     * */
    private inner class ScrollListener: RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            /**
             *
             * I just leave this code commented fo future use upon UX discussion
             * */

            /*(val layoutManager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

            val indexOfCentered = (layoutManager.findLastCompletelyVisibleItemPosition() -
                    layoutManager.findFirstCompletelyVisibleItemPosition())  / 2 +
                    layoutManager.findFirstVisibleItemPosition() + 1

            /temSelectedListener.let {
                if (supportedCreditCards.size <= indexOfCentered) {
                    return
                }
                val selectedCard = supportedCreditCards[indexOfCentered]
                it?.onItemSelected(selectedCard, indexOfCentered)
            }




            This is the logic for enabling automatic selection after scroll is done

            val cardView = layoutManager.findViewByPosition(indexOfCentered) as ViewGroup
            val checkBox: CheckBox = (cardView.getChildAt(1) as CheckBox)

            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                checkBox.isChecked = false
            }

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                checkBox.isChecked = true
            }*/
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        }
    }


    /**
     *
     *
     *
     * */
    internal inner class ZoomLayoutManager(mContext: Context, reverse: Boolean):
                         LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, reverse) {

        private val mShrinkAmount = 0.15f
        private val mShrinkDistance = 0.9f

        override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
            return scroll(dy, recycler, state)
        }

        override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
            return scroll(dx, recycler, state)
        }

        private fun scroll(dxy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
            val orientation = orientation
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                val scrolled = super.scrollHorizontallyBy(dxy, recycler, state)
                val midpoint = width / 2f
                val d0 = 0f
                val d1 = mShrinkDistance * midpoint
                val s0 = 1f
                val s1 = 1f - mShrinkAmount
                for (i in 0 until childCount) {
                    val child = getChildAt(i)
                    val childMidpoint = (getDecoratedRight(child) + getDecoratedLeft(child)) / 2f
                    val d = Math.min(d1, Math.abs(midpoint - childMidpoint))
                    val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                    child.scaleX = scale
                    child.scaleY = scale
                }
                return scrolled
            } else {
                return 0
            }
        }
    }

}