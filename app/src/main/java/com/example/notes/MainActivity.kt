package com.example.notes


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.notes.ui.theme.EditNote
import com.example.notes.ui.theme.NotesTheme
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import components.ButtonIcon
import components.NoteCard
import components.allNotes
import components.listOfNotesList
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import kotlinx.serialization.Serializable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesTheme {

                val listIndex = remember {
                    mutableIntStateOf(0)
                }
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = HomePage) {
                    composable<HomePage> {
                        HomePage(navController, listIndex)
                    }
                    composable<EditNote> {

                        val args = it.toRoute<EditNote>()

                        EditNote(navController, listIndex.intValue, position = args.position)
                    }
                }

            }
        }
    }


}

data class Note(
    var title: MutableState<String>,
    var richTextDescState: RichTextState,
    var bg: MutableState<Color>,
    var category: Int = 0
)

@Composable
fun HomePage(navController: NavController, listIndex: MutableIntState) {

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
                    TopPart(listIndex)
                }

                val notes =
                    if (listIndex.intValue == 0) {
                        allNotes
                    } else {
                        allNotes.filter { it.category == listIndex.intValue }
                    }
                itemsIndexed(notes) { index, note ->

                    NoteCard(note = note, onClick = {

                        navController.navigate(EditNote(position = index))
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
fun TopPart(listIndex: MutableIntState) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
        ) {

            Box(
                modifier = Modifier
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

                var showSubMenu by remember { mutableStateOf(false) }


                ButtonMoveExample(
                    x = -100,
                    painter = R.drawable.sortby_icon,
                    showSubMenu = showSubMenu,
                    setShowMenu = { newShowMenu ->
                        showSubMenu = newShowMenu
                    })

                ButtonMoveExample(
                    x = -70,
                    y = 70,
                    painter = R.drawable.select_icon,
                    showSubMenu = showSubMenu,
                    setShowMenu = { newShowMenu ->
                        showSubMenu = newShowMenu
                    })

                ButtonMoveExample(
                    y = 100,
                    painter = R.drawable.search_icon,
                    showSubMenu = showSubMenu,
                    setShowMenu = { newShowMenu ->
                        showSubMenu = newShowMenu
                    })

                Button(
                    onClick = {
                        showSubMenu = !showSubMenu
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF161616)),
                    contentPadding = PaddingValues(10.dp),
                    modifier = Modifier
                        .size(50.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bento_menu),
                        contentDescription = "Menu",
                        modifier = Modifier.fillMaxSize()
                    )
                }


            }

        }
        ScrollableRow(modifier = Modifier.height(100.dp), listIndex)
    }
}

@Composable
fun ScrollableRow(modifier: Modifier, listIndex: MutableIntState) {

    LazyRow(
        modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,

        ) {


        itemsIndexed(listOfNotesList) { index, listName ->
            Box(
                modifier = Modifier
                    .padding(20.dp, 0.dp)
                    .border(1.dp, Color.White, CircleShape)
                    .background(
                        color = if (index == listIndex.intValue) Color.White else Color.Transparent,
                        shape = CircleShape
                    )
                    .padding(20.dp, 5.dp)
                    .clickable {
                        listIndex.intValue = index
                    }

            ) {
                Text(
                    text = listName.listName,
                    color = if (index == listIndex.intValue) Color.Black else Color.White,
                    fontSize = 24.sp
                )
            }

        }


    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotesTheme {
//        HomePage(navController = rememberNavController())
        Box(modifier = Modifier.fillMaxSize()) {

        }


    }
}

@Serializable
object HomePage

@Serializable
data class EditNote(
    val position: Int = -1,
)


@Composable
fun ButtonMoveExample(
    x: Int = 0,
    y: Int = 0,
    painter: Int,
    showSubMenu: Boolean,
    setShowMenu: (Boolean) -> Unit
) {
    val xOffset by animateDpAsState(
        targetValue = if (showSubMenu) x.dp else 0.dp,
        tween(durationMillis = 500)
    )
    val yOffset by animateDpAsState(
        targetValue = if (showSubMenu) y.dp else 0.dp,
        tween(durationMillis = 500)
    )
    val visibility by animateFloatAsState(
        targetValue = if (showSubMenu) 1f else 0f,
        tween(durationMillis = 500)
    )
    ButtonIcon(
        painter = painter,
        contentPadding = 10.dp,
        modifier = Modifier
            .size(60.dp)
            .offset(x = xOffset, y = yOffset)
            .alpha(visibility)
    ) {
        setShowMenu(!showSubMenu)
    }
}


