package com.example.foodify.model

import android.os.Parcel
import android.os.Parcelable

data class MenuItemModel(
    val menuItemName: String?,
    val menuItemId: String?,
    val menuItemPrice: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(menuItemName)
        parcel.writeString(menuItemId)
        parcel.writeString(menuItemPrice)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MenuItemModel> {
        override fun createFromParcel(parcel: Parcel): MenuItemModel {
            return MenuItemModel(parcel)
        }

        override fun newArray(size: Int): Array<MenuItemModel?> {
            return arrayOfNulls(size)
        }
    }
}