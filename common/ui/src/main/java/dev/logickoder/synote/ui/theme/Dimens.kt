package dev.logickoder.synote.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.dimensionResource
import dev.logickoder.synote.ui.R

@Composable
@ReadOnlyComposable
fun secondaryPadding() = padding() / 4 * 2

@Composable
@ReadOnlyComposable
fun padding() = dimensionResource(id = R.dimen.ui_padding)