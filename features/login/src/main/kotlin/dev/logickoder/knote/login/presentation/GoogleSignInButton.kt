package dev.logickoder.knote.login.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Constraints
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import dev.logickoder.knote.login.R
import dev.logickoder.knote.ui.AppButton
import dev.logickoder.knote.ui.theme.secondaryPadding
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun GoogleSignInButton(
    loading: Boolean,
    modifier: Modifier,
    onClick: () -> Unit,
    onError: (String) -> Unit,
    login: (AuthCredential) -> Unit,
) {
    val context = LocalContext.current
    val auth = remember { auth(context) }
    val scope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                scope.launch {
                    getCredentials(result.data!!)?.let {
                        login(it)
                    } ?: onError(context.getString(R.string.login_credentials_error))
                }
            } else onError(context.getString(R.string.login_no_data))
        }
    )
    AppButton(
        modifier = modifier,
        onClick = {
            onClick()
            launcher.launch(auth.signInIntent)
        },
        isLoading = loading,
        content = {
            Row(
                modifier = Modifier.requiredHeight(IntrinsicSize.Min),
                content = {
                    Image(
                        modifier = Modifier
                            .layout { measurable, constraints ->
                                if (constraints.maxHeight == Constraints.Infinity) {
                                    layout(0, 0) {}
                                } else {
                                    val placeable = measurable.measure(constraints)
                                    layout(placeable.width, placeable.height) {
                                        placeable.place(0, 0)
                                    }
                                }
                            },
                        painter = painterResource(id = R.drawable.login_ic_google),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(secondaryPadding() / 2))
                    Text(stringResource(R.string.login_google))
                }
            )
        }
    )
}

private fun auth(context: Context): GoogleSignInClient {
    val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken(context.getString(R.string.login_gcp_id))
        .requestId()
        .requestProfile()
        .build()
    return GoogleSignIn.getClient(context, options)
}

private suspend fun getCredentials(intent: Intent) = suspendCoroutine { cont ->
    GoogleSignIn.getSignedInAccountFromIntent(intent).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            cont.resume(GoogleAuthProvider.getCredential(task.result.idToken, null))
        } else {
            cont.resume(null)
        }
    }
}