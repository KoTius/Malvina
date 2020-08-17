package com.kotsu.malvina.model.data.recipients


data class Recipient(
    val name: String,
    val phoneFormatted: String,
    var address: String,
    var commentary: String
)