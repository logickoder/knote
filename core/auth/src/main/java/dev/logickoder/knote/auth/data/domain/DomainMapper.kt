package dev.logickoder.knote.auth.data.domain

import com.google.firebase.auth.FirebaseUser
import dev.logickoder.knote.auth.data.model.User
import dev.logickoder.knote.auth.data.model.UserEntity
import dev.logickoder.knote.auth.data.model.UserId

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