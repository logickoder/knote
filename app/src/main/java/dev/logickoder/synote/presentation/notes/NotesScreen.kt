package dev.logickoder.synote.presentation.notes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.logickoder.synote.presentation.shared.DefaultAppBar

class NotesScreen(
    buildContext: BuildContext
) : Node(
    buildContext = buildContext
) {
    @Composable
    override fun View(modifier: Modifier) {
        NotesScreenContent(
            modifier = modifier,
        )
    }
}

@Composable
private fun NotesScreenContent(
    modifier: Modifier = Modifier,
) = Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
        DefaultAppBar()
    },
    content = {

    }
)

@Preview(showBackground = true)
@Composable
private fun NotesScreenPreview() = NotesScreenContent()