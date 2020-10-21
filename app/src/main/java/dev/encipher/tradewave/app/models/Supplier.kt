package dev.encipher.tradewave.app.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Supplier constructor(

    @SerializedName("person_id")
    @Expose
    var personId: String?,

    @SerializedName("company_name")
    @Expose
    var companyName: String?,

    @SerializedName("account_number")
    @Expose
    var accountNumber: String?,

    @SerializedName("agency_name")
    @Expose
    var agencyName: String?,

    @SerializedName("deleted")
    @Expose
    var deleted: String?,

    @SerializedName("first_name")
    @Expose
    var firstName: String?,

    @SerializedName("last_name")
    @Expose
    var lastName: String?,

    @SerializedName("gender")
    @Expose
    var gender: String?,

    @SerializedName("phone_number")
    @Expose
    var phoneNumber: String?,

    @SerializedName("email")
    @Expose
    var email: String?,

    @SerializedName("address_1")
    @Expose
    var addressOne: String?,

    @SerializedName("address_2")
    @Expose
    var addressTwo: String?,

    @SerializedName("city")
    @Expose
    var city: String?,

    @SerializedName("state")
    @Expose
    var state: String?,

    @SerializedName("zip")
    @Expose
    var zip: String?,

    @SerializedName("country")
    @Expose
    var country: String?,

    @SerializedName("comments")
    @Expose
    var comments: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(personId)
        parcel.writeString(companyName)
        parcel.writeString(accountNumber)
        parcel.writeString(agencyName)
        parcel.writeString(deleted)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(gender)
        parcel.writeString(phoneNumber)
        parcel.writeString(email)
        parcel.writeString(addressOne)
        parcel.writeString(addressTwo)
        parcel.writeString(city)
        parcel.writeString(state)
        parcel.writeString(zip)
        parcel.writeString(country)
        parcel.writeString(comments)
    }

    fun getSupplierAddress(): String?{
        return city
    }

    fun getSupplierName(): String{
        return "$firstName $lastName"
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Supplier> {
        override fun createFromParcel(parcel: Parcel): Supplier {
            return Supplier(parcel)
        }

        override fun newArray(size: Int): Array<Supplier?> {
            return arrayOfNulls(size)
        }
    }
}