package com.example.notes



import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.notes.ui.theme.EditNote
import com.example.notes.ui.theme.NotesTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import kotlinx.serialization.Serializable
import java.util.Objects
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesTheme {

                val notes = remember {
                    mutableStateListOf<Note>()
                }

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = HomePage) {
                    composable<HomePage> {
                        HomePage(navController,notes)
                    }
                    composable<EditNote> {
                        val args = it.toRoute<EditNote>()
                        EditNote(navController,notes,args.position)
                    }
                }


            }
        }
    }


}

data class Note(
    var title: String = "",
    var desc: String = ""
)




@Composable
fun HomePage( navController: NavController,notes: MutableList<Note>) {



//    val notes = ArrayList<Note>()


    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(innerPadding)
                .padding(5.dp)
        )
        {
            val hazeState = remember {
                HazeState()
            }

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalItemSpacing = 10.dp,
                modifier = Modifier
                    .fillMaxSize()
                    .haze(
                        hazeState,
                        backgroundColor = MaterialTheme.colorScheme.background,
                        tint = Color.Black.copy(alpha = .2f),
                        blurRadius = 30.dp,
                    ),
            ) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    TopPart()
                }
                items(notes.size) { note ->
                    NoteCard(note = notes[note],onClick = {
                        navController.navigate(EditNote(position = note))
                    })

                }


            }
            Button(
                onClick = {
                    navController.navigate(EditNote())

                },
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                contentPadding = PaddingValues(5.dp),
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
                    .align(Alignment.BottomCenter)
                    .hazeChild(state = hazeState, shape = CircleShape)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.round_add_24),
                    contentDescription = "Add Note",
                    modifier = Modifier.fillMaxSize()
                )
            }


        }
    }

}

@Composable
fun TopPart() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {

            Box(
                modifier = Modifier
//                .fillMaxWidth(.7f)
                    .weight(.7f)
                    .fillMaxHeight()


            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "MY", color = Color.White, fontSize = 45.sp)
                    Text(text = "NOTES", color = Color.White, fontSize = 45.sp)
                }


            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(.3f)
                    .padding(10.dp),
                contentAlignment = Alignment.TopEnd

            ) {
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(Color(0xFF161616)),
                    contentPadding = PaddingValues(10.dp),
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bento_menu),
                        contentDescription = "Menu",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

        }

        ScrollableRow(modifier = Modifier.height(100.dp))
    }
}


@Composable
fun ScrollableRow(modifier: Modifier) {

    LazyRow(
        modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,

        ) {

        items(5) { index ->
            Box(
                modifier = Modifier
                    .padding(20.dp, 0.dp)
                    .border(1.dp, Color.White, CircleShape)
                    .padding(20.dp, 5.dp)

            ) {
                Text(text = "ashu", color = Color.White, fontSize = 24.sp)
            }

        }


    }

}




@Composable
fun NoteCard(note: Note,modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp, max = 300.dp)
            .background(
                Color(0xFFECE3C1),
                RoundedCornerShape(25.dp)
            )
            .wrapContentHeight()
            .clickable { onClick() }
    ) {

        Text(text = note.title ,
            modifier = Modifier
                .padding(20.dp),
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Black),
            maxLines = 2)

//        Spacer(modifier = Modifier.height(10.dp))
        Text(text = note.desc ,
            style = TextStyle(fontSize = 14.sp, color = Color.Black),
            modifier = Modifier
                .padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
            maxLines = 9
        )

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotesTheme {
//        HomePage(navController = rememberNavController())
        Box(modifier = Modifier.fillMaxSize()){
            NoteCard(note = Note(),modifier = Modifier.fillMaxWidth(.5f))
        }


    }
}



@Serializable
object HomePage

@Serializable
data class EditNote(
    val position: Int = -1
)


