package dev.logickoder.synote.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> Flow<T>.state(initial: T): T {
    return collectAsState(initial = initial).value
}