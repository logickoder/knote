package dev.logickoder.knote.auth.impl.data.domain

import com.google.firebase.auth.FirebaseUser
import dev.logickoder.knote.auth.api.User
import dev.logickoder.knote.auth.api.UserId
import dev.logickoder.knote.auth.impl.data.model.UserEntity

internal object DomainMapper {
    fun toUserEntity(user: FirebaseUser) = UserEntity(
        id = user.uid,
        name = user.displayName ?: "User",
        email = user.email ?: "user@knote.com",
    )

    fun toUser(user: UserEntity) = User(
        id = UserId(user.id),
        name = user.name,
        email = user.email,
    )
}