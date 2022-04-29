package com.ajinkya.bookreaderapp.model

import com.ajinkya.bookreaderapp.utils.Constants

data class UserModel(
    val id: String?,
    val userId: String,
    val displayName: String,
    val profileUrl: String,
    val quote: String,
    val profession: String,
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            Constants.FIRESTORE_USER_ID to userId,
            Constants.FIRESTORE_DISPLAY_NAME to displayName,
            Constants.FIRESTORE_QUOTE to quote,
            Constants.FIRESTORE_PROFESSION to profession,
            Constants.FIRESTORE_PROFILE_URL to profileUrl
        )
    }
}
