package com.swivelgroup.newsticker.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class NewsItem(
    @field:Json(name = "source") val source: Source?,
    @field:Json(name = "author") val author: String?,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "urlToImage") val urlToImage: String?,
    @field:Json(name = "publishedAt") val publishedAt: String?,
    @field:Json(name = "content") val content: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readParcelable<Source>(Source::class.java.classLoader),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(source, 0)
        writeString(author)
        writeString(title)
        writeString(description)
        writeString(url)
        writeString(urlToImage)
        writeString(publishedAt)
        writeString(content)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<NewsItem> = object : Parcelable.Creator<NewsItem> {
            override fun createFromParcel(source: Parcel): NewsItem = NewsItem(source)
            override fun newArray(size: Int): Array<NewsItem?> = arrayOfNulls(size)
        }
    }
}