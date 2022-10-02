package dev.logickoder.synote.notes.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.logickoder.synote.notes.R
import dev.logickoder.synote.ui.theme.SynoteTheme
import dev.logickoder.synote.ui.theme.padding
import dev.logickoder.synote.ui.theme.secondaryPadding

enum class NotesDrawerItem(val icon: ImageVector) {
    Notes(Icons.Outlined.Notes),
    Archive(Icons.Outlined.Archive),
    Trash(Icons.Outlined.Delete)
}

@Composable
internal fun NotesDrawer(
    scope: ColumnScope,
    selected: NotesDrawerItem,
    itemClicked: (NotesDrawerItem) -> Unit,
) = with(scope) {
    Text(
        modifier = Modifier
            .padding(vertical = secondaryPadding())
            .padding(start = padding()),
        text = stringResource(id = R.string.notes_app_name),
        style = MaterialTheme.typography.h5,
    )
    NotesDrawerItem.values().forEach { item ->
        NotesDrawerItem(
            item = item,
            selected = item == selected,
            onClick = {
                itemClicked(item)
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun NotesDrawerItem(
    item: NotesDrawerItem,
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = modifier
            .padding(end = dimensionResource(id = dev.logickoder.synote.ui.R.dimen.ui_padding))
            .clip(RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp))
            .background(
                color = if (selected) {
                    MaterialTheme.colors.onBackground.copy(alpha = 0.1f)
                } else Color.Unspecified,
            )
            .clickable { onClick() },
        icon = {
            IconButton(
                onClick = {},
                content = {
                    Icon(
                        item.icon, null,
                        tint = MaterialTheme.colors.onBackground,
                    )
                }
            )
        },
        text = {
            Text(
                item.name,
                color = MaterialTheme.colors.onBackground,
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun NotesDrawerPreview() = SynoteTheme {
    Column(
        content = {
            NotesDrawer(scope = this, itemClicked = {}, selected = NotesDrawerItem.Notes)
        }
    )
}