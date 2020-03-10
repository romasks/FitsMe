package ru.fitsme.android.domain.entities.order;

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import ru.fitsme.android.domain.entities.clothes.ClothesItem

@Parcelize
data class OrderItem(
    @SerializedName("id") var id: Int,
    @SerializedName("order") var orderId: Int,
    @SerializedName("price") var price: Int,
    var quantity: Int,
    @SerializedName("clothe") var clothe: ClothesItem
) : Parcelable {

    constructor() : this(0, 0, 0, 0, ClothesItem())

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
