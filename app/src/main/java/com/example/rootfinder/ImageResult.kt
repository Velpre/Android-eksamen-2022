package com.example.rootfinder

import android.os.Parcel
import android.os.Parcelable

class ImageResult(
    val url :String,
    val store_link: String
) : Parcelable{
    constructor(parcel : Parcel):this(
        parcel.readString() ?:"",
        parcel.readString() ?: ""

    )
    override fun describeContents(): Int {
       return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeString(store_link)
    }

    companion object CREATOR : Parcelable.Creator<ImageResult> {
        override fun createFromParcel(parcel: Parcel): ImageResult {
            return ImageResult(parcel)
        }

        override fun newArray(size: Int): Array<ImageResult?> {
            return arrayOfNulls(size)
        }
    }
}
