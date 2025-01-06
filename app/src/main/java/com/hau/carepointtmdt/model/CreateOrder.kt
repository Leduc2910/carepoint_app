package com.hau.carepointtmdt.model

import android.health.connect.datatypes.AppInfo
import com.hau.carepointtmdt.network.ApiZaloPay
import com.hau.carepointtmdt.network.HttpProvider.sendPost
import com.hau.carepointtmdt.zalopay.Helpers
import okhttp3.FormBody
import java.util.Date
import org.json.JSONObject;
import okhttp3.RequestBody;

class CreateOrder {
    private inner class CreateOrderData(amount: String) {
        var AppId: String
        var AppUser: String
        var AppTime: String
        var Amount: String
        var AppTransId: String
        var EmbedData: String
        var Items: String
        var BankCode: String
        var Description: String
        var Mac: String

        init {
            val appTime: Long = Date().getTime()
            AppId = java.lang.String.valueOf(ApiZaloPay.APP_ID)
            AppUser = "Android_Demo"
            AppTime = appTime.toString()
            Amount = amount
            AppTransId = Helpers.appTransId
            EmbedData = "{}"
            Items = "[]"
            BankCode = "zalopayapp"
            Description = "Merchant pay for order #" + Helpers.appTransId
            val inputHMac = String.format(
                "%s|%s|%s|%s|%s|%s|%s",
                this.AppId,
                this.AppTransId,
                this.AppUser,
                this.Amount,
                this.AppTime,
                this.EmbedData,
                this.Items
            )

            Mac = Helpers.getMac(ApiZaloPay.MAC_KEY, inputHMac)
        }
    }

    @Throws(Exception::class)
    fun createOrder(amount: String): JSONObject? {
        val input = CreateOrderData(amount)

        val formBody: RequestBody = FormBody.Builder()
            .add("app_id", input.AppId)
            .add("app_user", input.AppUser)
            .add("app_time", input.AppTime)
            .add("amount", input.Amount)
            .add("app_trans_id", input.AppTransId)
            .add("embed_data", input.EmbedData)
            .add("item", input.Items)
            .add("bank_code", input.BankCode)
            .add("description", input.Description)
            .add("mac", input.Mac)
            .build()

        val data: JSONObject? = sendPost(ApiZaloPay.URL_CREATE_ORDER, formBody)
        return data
    }
}
