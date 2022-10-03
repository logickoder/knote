package dev.logickoder.synote.settings.data.model

import dev.logickoder.synote.settings.api.SettingsToggle
import kotlinx.serialization.Serializable

@Serializable
internal data class SettingsToggleEntity(val toggle: SettingsToggle, val value: Boolean)

internal fun List<SettingsToggleEntity>?.toSettingsToggle() = buildMap {
    this@toSettingsToggle?.forEach { entity ->
        put(entity.toggle, entity.value)
    }
    SettingsToggle.values().forEach { toggle ->
        putIfAbsent(toggle, false)
    }
}

internal fun Map<SettingsToggle, Boolean>.toEntity() = entries.map {
    SettingsToggleEntity(it.key, it.value)
}