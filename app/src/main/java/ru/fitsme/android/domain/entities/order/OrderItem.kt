package ru.fitsme.android.domain.entities.order;

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import ru.fitsme.android.domain.entities.clothes.ClothesItem

@Parcelize
data class OrderItem(
    @SerializedName("id") var id: Int,
    @SerializedName("order") var orderId: Int,
    @SerializedName("clothe") var clothe: ClothesItem,
    @SerializedName("price") var price: Int,
    @SerializedName("in_returns") var in_returns: Boolean
) : Parcelable {

    @IgnoredOnParcel
    @Expose
    var quantity = 1

    constructor() : this(0, 0, ClothesItem(), 0, false)

    companion object {
        @JvmField
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<OrderItem>() {
            override fun areItemsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
