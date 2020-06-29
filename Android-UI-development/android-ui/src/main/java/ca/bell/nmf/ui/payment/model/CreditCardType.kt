package ca.bell.nmf.ui.payment.model

import com.google.gson.annotations.SerializedName

/**
 * This is a marker interface to mark all accepted models, data classes to deal with cards scroller view.
 * */
interface CardsScrollerObject

class CreditCard: CardsScrollerObject {

    var type: String? = null

    var viewId: Int = 0

    @SerializedName("background_resource")
    var backgroundResource: String? = null

    @SerializedName("card_number_length")
    var cardNumberLength: String? = null

    @SerializedName("starts_with")
    var startsWith = emptyArray<String>()

}

class SupportedCards {
    val cards: Array<CreditCard> = emptyArray()
}

class TopUpAmount: CardsScrollerObject {
    var amount: Int = 0
    var expiryDays: Int = 0
}

/*
* data class CreditCard(var type: String?) {

    @SerializedName("background_resource")
    var backgroundResource: String? = null

    @SerializedName("card_length")
    var cardLength: String? = null

    @SerializedName("starts_with")
    var startsWith = emptyArray<String>()

}
* */