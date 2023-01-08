package dev.logickoder.knote.navigation

import dev.logickoder.knote.note_list.data.model.NoteListScreen
import dev.logickoder.knote.presentation.DrawerItem

internal val Navigation.Route.drawer: DrawerItem?
    get() = when (this) {
        is Navigation.Route.NoteList -> when (screen) {
            NoteListScreen.Archive -> DrawerItem.Archive
            NoteListScreen.Notes -> DrawerItem.Notes
            NoteListScreen.Trash -> DrawerItem.Trash
        }

        else -> null
    }

internal val DrawerItem.route: Navigation.Route
    get() = when (this) {
        DrawerItem.Notes -> Navigation.Route.NoteList(NoteListScreen.Notes)
        DrawerItem.Archive -> Navigation.Route.NoteList(NoteListScreen.Archive)
        DrawerItem.Trash -> Navigation.Route.NoteList(NoteListScreen.Trash)
        DrawerItem.Settings -> Navigation.Route.Settings
        DrawerItem.Logout -> Navigation.Route.Login
    }