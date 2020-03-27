package com.bytcode.tradetool.app.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CallCard constructor(

    @SerializedName("card_id")
    @Expose
    var cardId: String?,

    @SerializedName("location_id")
    @Expose
    var locationId: String?,

    @SerializedName("visit_id")
    @Expose
    var visitId: String?,

    @SerializedName("employee_id")
    @Expose
    var employeeId: String?,

    @SerializedName("visit1")
    @Expose
    var visitOne: String?,

    @SerializedName("visit2")
    @Expose
    var visitTwo: String?,

    @SerializedName("deleted")
    @Expose
    var deleted: String?
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cardId)
        parcel.writeString(locationId)
        parcel.writeString(visitId)
        parcel.writeString(employeeId)
        parcel.writeString(visitOne)
        parcel.writeString(visitTwo)
        parcel.writeString(deleted)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CallCard> {
        override fun createFromParcel(parcel: Parcel): CallCard {
            return CallCard(parcel)
        }

        override fun newArray(size: Int): Array<CallCard?> {
            return arrayOfNulls(size)
        }
    }

}