package dev.logickoder.auth.impl.data.domain

import com.google.firebase.auth.FirebaseUser
import dev.logickoder.auth.impl.data.model.UserEntity
import dev.logickoder.synote.auth.api.User
import dev.logickoder.synote.auth.api.UserId

internal object DomainMapper {
    fun toUserEntity(user: FirebaseUser) = UserEntity(
        id = user.uid,
        name = user.displayName ?: "User",
        email = user.email ?: "user@synote.com",
    )

    fun toUser(user: UserEntity) = User(
        id = UserId(user.id),
        name = user.name,
        email = user.email,
    )
}