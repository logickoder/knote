package dev.logickoder.synote.login.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.synote.login.R
import dev.logickoder.synote.ui.AppLogo
import dev.logickoder.synote.ui.theme.SynoteTheme
import dev.logickoder.synote.ui.theme.padding
import dev.logickoder.synote.ui.theme.secondaryPadding

@Composable
internal fun LoginScreen(
    uiState: LoginState,
    modifier: Modifier = Modifier,
    onLogin: () -> Unit = {},
) = Column(
    modifier = modifier
        .fillMaxSize()
        .padding(horizontal = padding()),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    content = {
        AppLogo()
        Spacer(modifier = Modifier.height(padding() + secondaryPadding()))
        LoginCard(uiState, onLogin)
        Spacer(modifier = Modifier.height(secondaryPadding()))
        BottomLoginText(
            isLogin = uiState.isLogin,
            onIsLoginChanged = { uiState.isLogin = it }
        )
    }
)

@Composable
private fun BottomLoginText(
    isLogin: Boolean,
    onIsLoginChanged: (Boolean) -> Unit,
) = Text(
    modifier = Modifier.clickable {
        onIsLoginChanged(!isLogin)
    },
    text = buildAnnotatedString {
        withStyle(style = MaterialTheme.typography.body2.toSpanStyle()) {
            withStyle(style = SpanStyle(color = MaterialTheme.colors.onBackground)) {
                append(
                    stringResource(
                        id = if (isLogin) {
                            R.string.login_dont_have_account
                        } else R.string.login_already_have_an_account
                    )
                )
            }
            append(" ")
            withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                append(
                    stringResource(
                        id = if (isLogin) {
                            R.string.login_register
                        } else R.string.login
                    )
                )
            }
        }
    }
)

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() = SynoteTheme {
    LoginScreen(
        uiState = LoginState(),
    )
}