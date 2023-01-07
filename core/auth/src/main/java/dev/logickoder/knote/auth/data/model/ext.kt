package dev.logickoder.knote.auth.data.model

import com.google.firebase.auth.FirebaseUser

internal fun FirebaseUser.toEntity() = UserEntity(
    id = uid,
    name = displayName ?: "User",
    email = email ?: "user@knote.com",
)

internal fun UserEntity.toUser() = User(
    id = UserId(id),
    name = name,
    email = email,
)