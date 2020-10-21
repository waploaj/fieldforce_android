package dev.encipher.tradewave.app.utils.api.response

import android.os.Parcel
import android.os.Parcelable
import dev.encipher.tradewave.app.models.Customer
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CustomerResponse constructor(

    @SerializedName("status")
    @Expose
    var status: String?,

    @SerializedName("response")
    @Expose
    var response: ArrayList<Customer>
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createTypedArrayList(Customer)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeTypedList(response)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CustomerResponse> {
        override fun createFromParcel(parcel: Parcel): CustomerResponse {
            return CustomerResponse(parcel)
        }

        override fun newArray(size: Int): Array<CustomerResponse?> {
            return arrayOfNulls(size)
        }
    }
}