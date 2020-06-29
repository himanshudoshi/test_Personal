package ca.bell.nmf.ui.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import ca.bell.nmf.ui.R
import kotlinx.android.synthetic.main.view_ban_details_layout.view.*


/**
 * This class represents a custom view from BEll Mobile Project. Most of landing pages on our four
 * streams have Ban details View, and this class represents well that shared view.
 * @constructor creates BanDetailsView from all params either coming from xml or code.
 * @param mContext View's context.
 * @param attrs View's attribute set.
 * @author Sami LASSED
 */
class BanDetailsView(val mContext: Context, val attrs: AttributeSet?): ConstraintLayout(mContext, attrs) {

    /**
     * @property primaryButton reference to primary button. Primary buttons are the ones with full
     * fill background. This reference is created for any future customizations.
     */
    val primaryButton = banPrimaryButton

    /**
     * @property secondaryButton reference to secondary button. Secondary buttons are the ones with white
     * fill background and outline borders. This reference is created for any future customizations.
     */
    val secondaryButton = banSecondaryButton

    /**
     * @constructor creates BanDetailsView from all params either coming from xml or code.
     * */
    constructor(mContext: Context): this(mContext, null)

    init {
        initView()
    }

    /**
     * initialise view with attribute set
     * */
    private fun initView() {

        LayoutInflater.from(context).inflate(R.layout.view_ban_details_layout, this, true)

        attrs?.let {
            val attributes = mContext.obtainStyledAttributes(attrs, R.styleable.ban_details)

            val billReference = attributes.getString(R.styleable.ban_details_billRef)
            val billAmount = attributes.getString(R.styleable.ban_details_billAmount)
            val dueDate = attributes.getString(R.styleable.ban_details_dueDate)

            setBillReference(billReference)
            setBillAmount(billAmount)
            banBillDueDateTextView.text = resources.getText(R.string.due_by, dueDate)
        }
    }

    /**
     * @param billRef A string that contains bill's reference.
     * @return a reference of the object itself. This is made for code readability.
     * Updating view's bill reference text.
     * */
    fun setBillReference(billRef: String):BanDetailsView {
        billRef.let {
            banBillRefTextView.text = billRef
        }
        return this
    }

    /**
     * @param billRef An integer that contains bill's reference.
     * @return a reference of the object itself. This is made for code readability.
     * Updating view's bill reference text.
     * */
    fun setBillReference(billRef: Int): BanDetailsView {
        banBillRefTextView.text = "$billRef"
        return this
    }

    /**
     * @param amount A string that contains bill's amount.
     * @return a reference of the object itself. This is made for code readability.
     * Updating view's bill amount text.
     * */
    fun setBillAmount(amount: String): BanDetailsView {
        amount.let {
            banBillAmountTextView.text = resources.getText(R.string.price_dollar, amount)
        }
        return this
    }

    /**
     * @param amount An integer that contains bill's amount.
     * @return a reference of the object itself. This is made for code readability.
     * Updating view's bill amount text.
     * */
    fun setBillAmount(amount: Float): BanDetailsView {
        banBillAmountTextView.text = resources.getText(R.string.price_dollar, "$amount")
        return this
    }

    /**
     * @param date A string that contains bill's due date.
     * @return a reference of the object itself. This is made for code readability.
     * Updating view's bill due date text.
     * */
    fun setDueDate(date: String): BanDetailsView {
        date.let {
            banBillDueDateTextView.text = resources.getText(R.string.due_by, date)
        }
        return this
    }

    /**
     * @param listener click listener for primary button.
     * @return a reference of the object itself. This is made for code readability.
     * */
    fun addPrimaryButtonClickListener(listener: OnClickListener): BanDetailsView {
        banPrimaryButton.setOnClickListener(listener)
        return this
    }

    /**
     * @param listener click listener for secondary button.
     * @return a reference of the object itself. This is made for code readability.
     * */
    fun addSecondaryButtonClickListener(listener: OnClickListener): BanDetailsView {
        banSecondaryButton.setOnClickListener(listener)
        return this
    }


}