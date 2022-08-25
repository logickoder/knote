package dev.logickoder.synote.presentation.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.logickoder.synote.core.theme.padding

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
) = Column(
    modifier = modifier
        .fillMaxSize()
        .padding(horizontal = padding()),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    content = {
    }
)

@Preview(showBackground = true)
@Composable
private fun NotesScreenPreview() = NotesScreenContent()