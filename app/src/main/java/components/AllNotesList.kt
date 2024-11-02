package components

import androidx.compose.runtime.remember
import com.example.notes.Note

data class NotesList(
    var listName: String,
    var notesList: MutableList<Note>

)

var listOfNotesList : MutableList<NotesList> = mutableListOf(
    NotesList("All Notes", mutableListOf<Note>()),
    NotesList("Star", mutableListOf<Note>()),
    NotesList("Work", mutableListOf<Note>()),
)


