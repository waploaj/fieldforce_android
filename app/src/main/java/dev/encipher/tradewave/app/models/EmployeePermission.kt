package dev.encipher.tradewave.app.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class EmployeePermission constructor(

    @SerializedName("permission_id")
    @Expose
    var permissionId: String?,

    @SerializedName("person_id")
    @Expose
    var personId: String?,

    @SerializedName("menu_group")
    @Expose
    var menuGroup: String?
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(permissionId)
        parcel.writeString(personId)
        parcel.writeString(menuGroup)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EmployeePermission> {
        override fun createFromParcel(parcel: Parcel): EmployeePermission {
            return EmployeePermission(parcel)
        }

        override fun newArray(size: Int): Array<EmployeePermission?> {
            return arrayOfNulls(size)
        }
    }

}