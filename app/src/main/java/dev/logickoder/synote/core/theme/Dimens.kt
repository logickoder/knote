package dev.logickoder.synote.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.dimensionResource
import dev.logickoder.synote.R

@Composable
@ReadOnlyComposable
fun secondaryPadding() = padding() / 4 * 2

@Composable
@ReadOnlyComposable
fun padding() = dimensionResource(id = R.dimen.padding)