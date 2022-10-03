package dev.logickoder.synote.settings.data.model

import dev.logickoder.synote.settings.api.SettingsToggle
import kotlinx.serialization.Serializable

@Serializable
internal data class SettingsToggleEntity(val toggle: Int, val value: Boolean)

internal fun List<SettingsToggleEntity>?.toSettingsToggle() = buildMap {
    this@toSettingsToggle?.forEach { entity ->
        if (entity.toggle < SettingsToggle.values().size) {
            put(SettingsToggle.values()[entity.toggle], entity.value)
        }
    }
    SettingsToggle.values().forEach { toggle ->
        putIfAbsent(toggle, false)
    }
}

internal fun Map<SettingsToggle, Boolean>.toEntity() = entries.map {
    SettingsToggleEntity(SettingsToggle.values().indexOf(it.key), it.value)
}
