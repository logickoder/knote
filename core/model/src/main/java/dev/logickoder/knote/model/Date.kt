package dev.logickoder.knote.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

val LocalDateTime.formatted: String
    get() = format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))