package dev.logickoder.knote.settings.data.model

import java.util.Locale

enum class SettingsToggle {
    AddNewNotesToBottom;

    val text: String = buildString {
        name.forEach { char ->
            if (char.isUpperCase()) append(' ')
            append(char)
        }
    }.trim().lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}