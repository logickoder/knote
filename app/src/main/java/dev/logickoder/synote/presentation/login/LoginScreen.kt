package dev.logickoder.synote.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.synote.R
import dev.logickoder.synote.core.theme.AppTheme
import dev.logickoder.synote.core.theme.padding
import dev.logickoder.synote.core.theme.secondaryPadding
import dev.logickoder.synote.utils.collectAsState

@Preview(showBackground = true)
@Composable
fun LoginScreen() {
    val viewModel: LoginViewModel = viewModel()
    val isLogin by viewModel.isLogin.collectAsState()

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
            Spacer(
                modifier = Modifier.height(padding() + secondaryPadding())
            )
            LoginCard(viewModel)
            Spacer(modifier = Modifier.height(secondaryPadding()))
            Text(
                modifier = Modifier.clickable {
                    viewModel.isLogin.emit(!isLogin)
                },
                text = buildAnnotatedString {
                    withStyle(style = AppTheme.typography.body2.toSpanStyle()) {
                        withStyle(style = SpanStyle(color = AppTheme.colors.onBackground)) {
                            append(
                                stringResource(
                                    id = if (isLogin) {
                                        R.string.dont_have_account
                                    } else R.string.already_have_an_account
                                )
                            )
                        }
                        append(" ")
                        withStyle(style = SpanStyle(color = AppTheme.colors.primary)) {
                            append(
                                stringResource(
                                    id = if (isLogin) {
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
}