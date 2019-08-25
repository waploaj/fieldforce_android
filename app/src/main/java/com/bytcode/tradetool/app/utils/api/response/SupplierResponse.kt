package com.bytcode.tradetool.app.utils.api.response

import android.os.Parcel
import android.os.Parcelable
import com.bytcode.tradetool.app.models.Customer
import com.bytcode.tradetool.app.models.Supplier
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SupplierResponse constructor(

    @SerializedName("status")
    @Expose
    var status: String?,

    @SerializedName("response")
    @Expose
    var response: ArrayList<Supplier>?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createTypedArrayList(Supplier)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeTypedList(response)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SupplierResponse> {
        override fun createFromParcel(parcel: Parcel): SupplierResponse {
            return SupplierResponse(parcel)
        }

        override fun newArray(size: Int): Array<SupplierResponse?> {
            return arrayOfNulls(size)
        }
    }
}