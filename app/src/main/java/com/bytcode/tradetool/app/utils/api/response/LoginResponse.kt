package com.bytcode.tradetool.app.utils.api.response

import android.os.Parcel
import android.os.Parcelable
import com.bytcode.tradetool.app.models.Employee
import com.bytcode.tradetool.app.models.EmployeePermission
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse constructor(
    @SerializedName("status")
    @Expose
    var status: String?,

    @SerializedName("response")
    @Expose
    var response: String?,

    @SerializedName("employee")
    @Expose
    var employee: Employee,

    @SerializedName("permissions")
    @Expose
    var permission: ArrayList<EmployeePermission>?
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Employee::class.java.classLoader),
        parcel.createTypedArrayList(EmployeePermission)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeString(response)
        parcel.writeParcelable(employee, flags)
        parcel.writeTypedList(permission)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginResponse> {
        override fun createFromParcel(parcel: Parcel): LoginResponse {
            return LoginResponse(parcel)
        }

        override fun newArray(size: Int): Array<LoginResponse?> {
            return arrayOfNulls(size)
        }
    }
}

