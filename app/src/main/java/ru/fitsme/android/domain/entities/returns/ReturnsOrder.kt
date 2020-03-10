package ru.fitsme.android.domain.entities.returns;

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import ru.fitsme.android.R
import java.text.ParseException
import java.text.SimpleDateFormat

@Parcelize
data class ReturnsOrder(
    @SerializedName("id") var id: Int,
    @SerializedName("order") var order: Int,
    @SerializedName("payment_details") var paymentDetails: String?,
    @SerializedName("delivery_details") var deliveryDetails: String?,
    @SerializedName("delivery_details_return") var deliveryDetailsReturn: String?,
    @SerializedName("created") var createdDate: String,
    @SerializedName("updated") var updatedDate: String,
    @SerializedName("date") var date: String?,
    @SerializedName("status") var status: String,
    @SerializedName("returnitems") var returnItemsList: List<ReturnsOrderItem>,
    @SerializedName("summ") var summ: Int,
    @SerializedName("count") var count: Int,
    @SerializedName("days_to_return") var _daysToReturn: String
) : Parcelable {

    constructor() : this(
        0, 0, "", "", "", "",
        "", "", "", emptyList(), 0, 0, ""
    )

    var daysToReturn: String
        get() = try {
            val days = Integer.parseInt(_daysToReturn)
            val daysStr = when {
                days in 11..19 -> "дней"
                days % 10 == 1 -> "день"
                days % 10 in 2..4 -> "дня"
                else -> "дней"
            }
            "$days $daysStr"
        } catch (ex: NumberFormatException) {
            _daysToReturn
        }
        set(value) {
            _daysToReturn = value
        }

    fun getHiddenCardNumber() =
        try {
            val lastQuarter = deliveryDetails?.let { it.split("-")[3] } ?: ""
            "**** **** **** $lastQuarter"
        } catch (ex: ArrayIndexOutOfBoundsException) {
            deliveryDetails
        }

    @SuppressLint("SimpleDateFormat")
    fun getFormattedDate(): String? {
        return try {
            val dt = SimpleDateFormat("yyyy-MM-dd").parse(date) ?: return date
            val df = SimpleDateFormat("dd.MM.yyyy")
            df.format(dt)
        } catch (ex: ParseException) {
            date
        } catch (ex: NullPointerException) {
            date
        }
    }

    fun getStatusName() =
        when (status) {
            "FM" -> "черновик"
            "ISU" -> "в обработке"
            "CNC" -> "отказ"
            "RDY" -> "выполнено"
            else -> ""
        }

    fun getStatusColor() =
        when (status) {
            "FM" -> R.color.colorStatusFM
            "ISU" -> R.color.colorStatusISU
            "CNC" -> R.color.colorStatusCNC
            "RDY" -> R.color.colorStatusRDY
            else -> R.color.colorStatusFM
        }

    companion object {
        @JvmField
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReturnsOrder>() {
            override fun areItemsTheSame(oldItem: ReturnsOrder, newItem: ReturnsOrder): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ReturnsOrder, newItem: ReturnsOrder): Boolean {
                return oldItem == newItem
            }

        }
    }
}
