package dev.logickoder.synote.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import dev.logickoder.synote.R
import dev.logickoder.synote.core.Navigation
import dev.logickoder.synote.core.theme.AppTheme
import dev.logickoder.synote.core.theme.padding
import dev.logickoder.synote.core.theme.secondaryPadding


class LoginScreen(
    buildContext: BuildContext,
    private val backStack: BackStack<Navigation.Route>,
) : Node(
    buildContext = buildContext
) {
    @Composable
    override fun View(modifier: Modifier) {
        val viewModel: LoginViewModel = viewModel()
        val context = LocalContext.current
        LoginScreenContent(
            modifier = modifier,
            uiState = viewModel.uiState,
            onLogin = {
                viewModel.performAuth(context, backStack)
            }
        )
    }
}

@Composable
private fun LoginScreenContent(
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
        Image(
            modifier = Modifier.fillMaxWidth(0.45f),
            painter = painterResource(id = R.drawable.ic_synote),
            colorFilter = ColorFilter.tint(AppTheme.colors.onSurface),
            contentDescription = null,
        )
        Spacer(
            modifier = Modifier.height(padding() + secondaryPadding())
        )
        LoginCard(uiState, onLogin)
        Spacer(modifier = Modifier.height(secondaryPadding()))
        Text(
            modifier = Modifier.clickable {
                uiState.isLogin = !uiState.isLogin
            },
            text = buildAnnotatedString {
                withStyle(style = AppTheme.typography.body2.toSpanStyle()) {
                    withStyle(style = SpanStyle(color = AppTheme.colors.onBackground)) {
                        append(
                            stringResource(
                                id = if (uiState.isLogin) {
                                    R.string.dont_have_account
                                } else R.string.already_have_an_account
                            )
                        )
                    }
                    append(" ")
                    withStyle(style = SpanStyle(color = AppTheme.colors.primary)) {
                        append(
                            stringResource(
                                id = if (uiState.isLogin) {
                                    R.string.register
                                } else R.string.login
                            )
                        )
                    }
                }
            }
        )
    }
)

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() = LoginScreenContent(
    uiState = LoginState(),
)