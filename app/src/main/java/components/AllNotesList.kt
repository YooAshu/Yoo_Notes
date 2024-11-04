package components

import androidx.compose.runtime.remember
import com.example.notes.Note

data class NotesList(
    var listName: String

)

var allNotes = mutableListOf<Note>()

var listOfNotesList : MutableList<NotesList> = mutableListOf(
    NotesList("All Notes"),
    NotesList("Star"),
    NotesList("Work"),
)


