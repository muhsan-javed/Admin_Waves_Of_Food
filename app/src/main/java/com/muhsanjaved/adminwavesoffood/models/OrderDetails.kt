package com.muhsanjaved.adminwavesoffood.models

import android.os.Parcel
import android.os.Parcelable

class OrderDetails: Parcelable {

    var userUid:String? = null
    var userName:String? = null
    var foodNames:MutableList<String>? = null
    var foodPrices:MutableList<String>? = null
    var foodImage:MutableList<String>? = null
    var foodQuantities:MutableList<Int>? = null
    var address  :String? = null
    var phone  :String? = null
    var totalPrice  :String? = null

    var orderAccepted: Boolean = false
    var paymentReceived:Boolean = false
    var itemPushKey: String? = null
    var currentTime:Long = 0

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        phone = parcel.readString()
        totalPrice = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

    constructor()

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }
}