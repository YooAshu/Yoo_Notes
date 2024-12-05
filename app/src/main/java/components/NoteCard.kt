package components

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.notes.Note
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(note: Note, modifier: Modifier = Modifier, onClick: () -> Unit = {},onDelete: () -> Unit) {
    val noteLongClicked = remember { mutableStateOf(false) }
    val deleteDialogShown = remember { mutableStateOf(false) }
    if (noteLongClicked.value){
        NoteLongClickDialog(onDismiss = {
            noteLongClicked.value = false
        },
            deleteDialogShown = deleteDialogShown)
    }
    if (deleteDialogShown.value) {
        NoteDeleteDialog(
            onDismiss = {
            deleteDialogShown.value = false
        },
            onDelete = {
                onDelete()
            }
        )
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .conditionalBackground(
                note.themesIndex,
                note.bg,
                note.bgGradient,
                15
            )
            .combinedClickable(
                onClick = onClick,
                onLongClick = {
                    noteLongClicked.value = true
                }
            )
//            .clickable { onClick() }
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 70.dp, max = 300.dp)
                .wrapContentHeight()
        ) {

            if (note.imageList.isNotEmpty()) {
//            Log.d("test","${note.imageList[0] is S}")
                AsyncImage(
                    model = Uri.parse(note.imageList[0]),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = note.title,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                ),
                maxLines = 2
            )

//        Spacer(modifier = Modifier.height(10.dp))
//            RichText(
//                state = rememberRichTextState().setHtml(note.html),
//                modifier = Modifier
//                    .padding(bottom = 10.dp, start = 20.dp, end = 20.dp),
//                fontSize = 12.sp,
//                maxLines = 9
//            )
            Text(
                text = note.descText,
                style = TextStyle(fontSize = 14.sp, color = Color.Black),
                modifier = Modifier
                    .padding(bottom = 10.dp, start = 20.dp, end = 20.dp),
                maxLines = 9
            )


        }
        Text(
            note.dateCreated.toTimeDateString(),
            modifier = Modifier.align(Alignment.End)
                .padding(end = 10.dp),
            color = Color.Black.copy(alpha = .5f),
            fontSize = 12.sp
        )
    }
}