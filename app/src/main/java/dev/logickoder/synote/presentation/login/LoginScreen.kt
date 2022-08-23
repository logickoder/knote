package dev.logickoder.synote.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.synote.R
import dev.logickoder.synote.core.theme.AppTheme
import dev.logickoder.synote.core.theme.padding
import dev.logickoder.synote.core.theme.secondaryPadding

@Preview(showBackground = true)
@Composable
fun LoginScreen() {
    val uiState = rememberLoginState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = padding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = {
            Image(
                modifier = Modifier.fillMaxWidth(0.45f),
                painter = painterResource(id = R.drawable.ic_synote),
                colorFilter = ColorFilter.tint(AppTheme.colors.onSurface),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(padding() + secondaryPadding()))
            LoginCard(uiState)
            Spacer(modifier = Modifier.height(secondaryPadding()))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = AppTheme.typography.body2.toSpanStyle()) {
                        withStyle(style = SpanStyle(color = AppTheme.colors.onBackground)) {
                            append(stringResource(id = R.string.dont_have_account))
                        }
                        append(" ")
                        withStyle(style = SpanStyle(color = AppTheme.colors.primary)) {
                            append(stringResource(id = R.string.register))
                        }
                    }
                }
            )
        }
    )
}