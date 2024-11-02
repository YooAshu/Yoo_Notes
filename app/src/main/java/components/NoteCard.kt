package components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notes.Note

@Composable
fun NoteCard(note: Note, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp, max = 300.dp)
            .background(
                note.bg.value,
                RoundedCornerShape(15.dp)
            )
            .wrapContentHeight()
            .clickable { onClick() }
    ) {

        Text(text = note.title.value ,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp),
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Black),
            maxLines = 2)

//        Spacer(modifier = Modifier.height(10.dp))
        Text(text = note.richTextDescState.toText() ,
            style = TextStyle(fontSize = 14.sp, color = Color.Black),
            modifier = Modifier
                .padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
            maxLines = 9
        )

    }
}