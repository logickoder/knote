package dev.logickoder.synote.settings.api

enum class SettingsToggle {
    AddNewItemsToBottom;

    val text: String = buildString {
        name.forEach { char ->
            if (char.isUpperCase()) append(' ')
            append(char)
        }
    }.trim().lowercase().capitalize()
}