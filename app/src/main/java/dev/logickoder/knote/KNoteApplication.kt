package dev.logickoder.knote

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

@HiltAndroidApp
class KNoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())
    }
}