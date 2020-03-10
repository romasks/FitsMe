package ru.fitsme.android.domain.entities.returns;

import android.os.Parcelable
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import kotlinx.android.parcel.Parcelize

import ru.fitsme.android.domain.entities.order.OrderItem;

@Parcelize
data class ReturnsOrderItem(
    @SerializedName("id") val id: Int,
    @SerializedName("orderitems") val orderItem: OrderItem,
    @SerializedName("returns") val returns: Int
): Parcelable {

    constructor() : this(0, OrderItem(), 0)

    @Expose
    val indicationNumber: String = ""

    @Expose
    val cardNumber: String = ""
}
