package com.swivelgroup.newsticker.api.response

import com.squareup.moshi.Json
import com.swivelgroup.newsticker.model.NewsItem

data class NewsResponse(@field:Json(name = "status")val status: String,
                        @field:Json(name = "code")val code: String?,
                        @field:Json(name = "message")val message: String?,
                        @field:Json(name = "totalResults")val totalResults:Int?,
                        @field:Json(name = "articles")val articles:List<NewsItem>?)