package com.mytestprogram.films

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val email: String? = "",
    val uid: String? = "",
)
