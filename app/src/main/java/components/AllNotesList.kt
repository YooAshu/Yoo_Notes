package components

import android.content.Context
import androidx.compose.ui.text.intl.Locale
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.notes.Note
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat

data class NotesList(
    var listName: String

)


var listOfNotesList: MutableList<NotesList> = mutableListOf(
    NotesList("All Notes"),
    NotesList("Star"),
    NotesList("Work"),
)

val Context.dataStore by preferencesDataStore("notes_store")


object NoteRepository {
    private val NOTES_KEY = stringPreferencesKey("notes_key")
    private val gson = Gson()

    fun saveNotes(context: Context, notes: List<Note>) {
        val json = gson.toJson(notes)
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[NOTES_KEY] = json
            }
        }
    }

    fun getNotes(context: Context): List<Note> {
        val jsonFlow = context.dataStore.data.map { preferences ->
            preferences[NOTES_KEY] ?: "[]"
        }
        return runBlocking {
            val json = jsonFlow.first()
            gson.fromJson(json, Array<Note>::class.java).toList()
        }
    }
}

fun isNoteModified(
    note: Note,
    position: Int,
    allNotes: List<Note>
): Boolean {
    return note.title != allNotes[position].title ||
            note.html != allNotes[position].html ||
            note.descText != allNotes[position].descText ||
            note.imageList != allNotes[position].imageList ||
            note.doodlePath != allNotes[position].doodlePath
}

fun Long.toTimeDateString(): String {
    val dateTime = java.util.Date(this)
    val format = SimpleDateFormat("dd/MM/yyyy")
    return format.format(dateTime)
}