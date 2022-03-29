package com.example.minutestest.domain.model

data class ApiPlacesResponse(
    val html_attributions: List<Any>?,
    val next_page_token: String?,
    val results: List<Result>?,
    val status: String?
)