package com.swivelgroup.newsticker.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class Source(
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "name") val name: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Source> = object : Parcelable.Creator<Source> {
            override fun createFromParcel(source: Parcel): Source = Source(source)
            override fun newArray(size: Int): Array<Source?> = arrayOfNulls(size)
        }
    }
}