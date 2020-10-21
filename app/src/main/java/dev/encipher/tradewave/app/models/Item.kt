package dev.encipher.tradewave.app.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Item constructor(
    @SerializedName("name")
    var name: String?,

    @SerializedName("category")
    @Expose
    var category: String?,

    @SerializedName("supplier_id")
    @Expose
    var supplierId: String?,

    @SerializedName("item_number")
    @Expose
    var itemNumber: String?,

    @SerializedName("description")
    @Expose
    var description: String?,

    @SerializedName("cost_price")
    @Expose
    var costPrice: Double?,

    @SerializedName("unit_price")
    @Expose
    var unitPrice: Double?,

    @SerializedName("reorder_level")
    @Expose
    var reorderLevel: String?,

    @SerializedName("receiving_quantity")
    @Expose
    var receivingQuantity: String?,

    @SerializedName("item_id")
    @Expose
    var itemId: String?,

    @SerializedName("pic_filename")
    @Expose
    var picFileName: String?,

    @SerializedName("allow_alt_description")
    @Expose
    var allowAltDescription: String?,

    @SerializedName("is_serialized")
    @Expose
    var isSerialized: String?,

    @SerializedName("stock_type")
    @Expose
    var stockType: String?,

    @SerializedName("item_type")
    @Expose
    var itemType: String?,

    @SerializedName("tax_category_id")
    @Expose
    var taxCategoryId: String?,

    @SerializedName("deleted")
    @Expose
    var deleted: String?,

    @SerializedName("custom1")
    @Expose
    var custom1: String?,

    @SerializedName("custom2")
    @Expose
    var custom2: String?,

    @SerializedName("custom3")
    @Expose
    var custom3: String?,

    @SerializedName("custom4")
    @Expose
    var custom4: String?,

    @SerializedName("custom5")
    @Expose
    var custom5: String?,

    @SerializedName("custom6")
    @Expose
    var custom6: String?,

    @SerializedName("custom7")
    @Expose
    var custom7: String?,

    @SerializedName("custom9")
    @Expose
    var custom9: String?,

    @SerializedName("custom10")
    @Expose
    var custom10: String?,

    @SerializedName("person_id")
    @Expose
    var personId: String?,

    @SerializedName("company_name")
    @Expose
    var companyName: String?,

    @SerializedName("agency_name")
    @Expose
    var agencyName: String?,

    @SerializedName("account_number")
    @Expose
    var accountNumber: String?
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
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
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(category)
        parcel.writeString(supplierId)
        parcel.writeString(itemNumber)
        parcel.writeString(description)
        parcel.writeValue(costPrice)
        parcel.writeValue(unitPrice)
        parcel.writeString(reorderLevel)
        parcel.writeString(receivingQuantity)
        parcel.writeString(itemId)
        parcel.writeString(picFileName)
        parcel.writeString(allowAltDescription)
        parcel.writeString(isSerialized)
        parcel.writeString(stockType)
        parcel.writeString(itemType)
        parcel.writeString(taxCategoryId)
        parcel.writeString(deleted)
        parcel.writeString(custom1)
        parcel.writeString(custom2)
        parcel.writeString(custom3)
        parcel.writeString(custom4)
        parcel.writeString(custom5)
        parcel.writeString(custom6)
        parcel.writeString(custom7)
        parcel.writeString(custom9)
        parcel.writeString(custom10)
        parcel.writeString(personId)
        parcel.writeString(companyName)
        parcel.writeString(agencyName)
        parcel.writeString(accountNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }

}