package com.binar.sqliteapp

import android.os.Parcel
import android.os.Parcelable

data class Inventory(var id: Int?, var name: String?, var total: Int): Parcelable {
    constructor(parcel: Parcel): this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        id?.let { parcel.writeInt(it) }
//        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeInt(total)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Inventory> {
        override fun createFromParcel(parcel: Parcel): Inventory {
            return Inventory(parcel)
        }

        override fun newArray(size: Int): Array<Inventory?> {
            return arrayOfNulls(size)
        }
    }
}