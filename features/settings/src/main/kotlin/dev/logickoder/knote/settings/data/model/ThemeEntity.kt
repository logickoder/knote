package dev.logickoder.knote.settings.data.model

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
internal value class ThemeEntity(val theme: Theme)

internal fun ThemeEntity?.toTheme() = this?.theme ?: Theme.System

internal fun Theme.toEntity() = ThemeEntity(this)