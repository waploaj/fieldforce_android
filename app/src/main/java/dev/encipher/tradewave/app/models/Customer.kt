package dev.encipher.tradewave.app.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Customer constructor(

    @SerializedName("person_id")
    @Expose
    var personId: String?,

    @SerializedName("company_name")
    @Expose
    var companyName: String?,

    @SerializedName("account_number")
    @Expose
    var accountNumber: String?,

    @SerializedName("taxable")
    @Expose
    var taxable: String?,

    @SerializedName("sales_tax_code")
    @Expose
    var salesTaxCode: String?,

    @SerializedName("discount_percent")
    @Expose
    var discountPercent: String?,

    @SerializedName("package_id")
    @Expose
    var packageId: String?,

    @SerializedName("points")
    @Expose
    var points: String?,

    @SerializedName("deleted")
    @Expose
    var deleted: String?,

    @SerializedName("date")
    @Expose
    var date: String?,

    @SerializedName("retail_id")
    @Expose
    var retailId: String?,

    @SerializedName("channel_id")
    @Expose
    var channelId: String?,

    @SerializedName("latitude")
    @Expose
    var latitude: String?,

    @SerializedName("longitude")
    @Expose
    var longitude: String?,

    @SerializedName("location_id")
    @Expose
    var locationId: String?,

    @SerializedName("visit_id")
    @Expose
    var visitId: String?,

    @SerializedName("store_bussiness_name")
    @Expose
    var storeBussinesName: String?,

    @SerializedName("card_id")
    @Expose
    var cardId: String?,

    @SerializedName("retail_name")
    @Expose
    var retailName: String?,

    @SerializedName("channel_name")
    @Expose
    var channelName: String?,

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
        parcel.writeString(taxable)
        parcel.writeString(salesTaxCode)
        parcel.writeString(discountPercent)
        parcel.writeString(packageId)
        parcel.writeString(points)
        parcel.writeString(deleted)
        parcel.writeString(date)
        parcel.writeString(retailId)
        parcel.writeString(channelId)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
        parcel.writeString(locationId)
        parcel.writeString(visitId)
        parcel.writeString(storeBussinesName)
        parcel.writeString(cardId)
        parcel.writeString(retailName)
        parcel.writeString(channelName)
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

    fun getCustomerAddress(): String?{
           return addressTwo
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Customer> {
        override fun createFromParcel(parcel: Parcel): Customer {
            return Customer(parcel)
        }

        override fun newArray(size: Int): Array<Customer?> {
            return arrayOfNulls(size)
        }
    }
}