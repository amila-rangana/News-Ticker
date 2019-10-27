package com.swivelgroup.newsticker.utils

class Constants {
    companion object{
        const val url_news_api_everything = "everything"
        const val url_news_api_top_headlines = "top-headlines"
        const val url_news_api_sources = "sources"

        const val api_key = "ecc049aac7954870b8722466524906c1"

        const val response_ok = "ok"
        const val response_error = "error"

        const val extra_news_url = "news_url"
        const val extra_news_item ="news_item"

        const val data_username = "username"
        const val data_preferences = "preferences"

        //Error codes
        const val error_api_key_diabled = "apiKeyDisabled"
        const val error_api_key_exhausted = "apiKeyExhausted"
        const val error_api_key_invalid = "apiKeyInvalid"
        const val error_api_key_missing = "apiKeyMissing"
        const val error_parameter_invalid = "parameterInvalid"
        const val error_parameter_missing = "parametersMissing"
        const val error_rate_limited = "rateLimited"
        const val error_sources_too_many = "sourcesTooMany"
        const val error_sources_does_not_exist= "sourceDoesNotExist"
        const val error_unexpected = "unexpectedError"
    }
}