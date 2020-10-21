package dev.encipher.tradewave.app.utils.api.response

import android.os.Parcel
import android.os.Parcelable
import dev.encipher.tradewave.app.models.Item
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LocationItemsResponse constructor(

    @SerializedName("status")
    @Expose
    var status: String?,

    @SerializedName("response")
    @Expose
    var response: ArrayList<Item>?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createTypedArrayList(Item)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeTypedList(response)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocationItemsResponse> {
        override fun createFromParcel(parcel: Parcel): LocationItemsResponse {
            return LocationItemsResponse(parcel)
        }

        override fun newArray(size: Int): Array<LocationItemsResponse?> {
            return arrayOfNulls(size)
        }
    }
}