package dev.encipher.tradewave.app.utils.api.response

import android.os.Parcel
import android.os.Parcelable
import dev.encipher.tradewave.app.models.CallCard
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CallCardResponse  constructor(

    @SerializedName("status")
    @Expose
    var status: String?,

    @SerializedName("response")
    @Expose
    var response: ArrayList<CallCard>?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createTypedArrayList(CallCard)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeTypedList(response)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CallCardResponse> {
        override fun createFromParcel(parcel: Parcel): CallCardResponse {
            return CallCardResponse(parcel)
        }

        override fun newArray(size: Int): Array<CallCardResponse?> {
            return arrayOfNulls(size)
        }
    }
}