package ca.bell.nmf.ui.utility

import android.content.Context
import ca.bell.nmf.ui.payment.model.SupportedCards
import com.google.gson.GsonBuilder

class ResourceParser(mContext: Context) {

    private val mContext = mContext

    fun fromJsonFile(resourceId: Int, resourceType: Class<SupportedCards>): Any {
        val json = mContext.resources.openRawResource(resourceId)
                .bufferedReader().use { it.readText() }
        return GsonBuilder().create().fromJson(json, resourceType)
    }
}