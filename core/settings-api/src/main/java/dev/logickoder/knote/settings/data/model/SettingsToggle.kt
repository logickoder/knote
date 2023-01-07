package dev.logickoder.knote.settings.data.model

enum class SettingsToggle {
    AddNewNotesToBottom;

    val text: String = buildString {
        name.forEach { char ->
            if (char.isUpperCase()) append(' ')
            append(char)
        }
    }.trim().lowercase().capitalize()
}