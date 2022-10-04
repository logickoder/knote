package dev.logickoder.synote.settings.data.model

import dev.logickoder.synote.settings.api.Theme
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
internal value class ThemeEntity(val theme: Theme)

internal fun ThemeEntity?.toTheme() = this?.theme ?: Theme.System

internal fun Theme.toEntity() = ThemeEntity(this)