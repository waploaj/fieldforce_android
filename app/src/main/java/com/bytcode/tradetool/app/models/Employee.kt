package com.bytcode.tradetool.app.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Employee constructor(

    @SerializedName("person_id")
    @Expose
    var personId: Int,

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

    @SerializedName("country")
    @Expose
    var country: String?,

    @SerializedName("zip")
    @Expose
    var zip: String?,

    @SerializedName("comments")
    @Expose
    var comments: String?,

    @SerializedName("username")
    @Expose
    var username: String?,

    @SerializedName("token")
    @Expose
    var token: String?,

    @SerializedName("last_updated")
    @Expose
    var lastUpdated: String?,

    @SerializedName("language_code")
    @Expose
    var languageCode: String?,

    @SerializedName("language")
    @Expose
    var language: String?,

    @SerializedName("hash_version")
    @Expose
    var hashVersion: String?

): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
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
        parcel.writeInt(personId)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(gender)
        parcel.writeString(phoneNumber)
        parcel.writeString(email)
        parcel.writeString(addressOne)
        parcel.writeString(addressTwo)
        parcel.writeString(city)
        parcel.writeString(state)
        parcel.writeString(country)
        parcel.writeString(zip)
        parcel.writeString(comments)
        parcel.writeString(username)
        parcel.writeString(token)
        parcel.writeString(lastUpdated)
        parcel.writeString(languageCode)
        parcel.writeString(language)
        parcel.writeString(hashVersion)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun getFullName(): String{
        return "$firstName $lastName"
    }

    companion object CREATOR : Parcelable.Creator<Employee> {
        override fun createFromParcel(parcel: Parcel): Employee {
            return Employee(parcel)
        }

        override fun newArray(size: Int): Array<Employee?> {
            return arrayOfNulls(size)
        }
    }

}