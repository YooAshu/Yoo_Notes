package com.example.notes


import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.animation.OvershootInterpolator
import android.widget.Toast
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.notes.ui.theme.NotesTheme
import com.mohamedrejeb.richeditor.model.RichTextState
import components.AddCategoryDialog
import components.ButtonIcon
import components.NoteCard
import components.NoteRepository
import components.SortDialog
import components.listOfNotesList
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import io.ak1.drawbox.DrawBoxPayLoad
import kotlinx.serialization.Serializable


class MainActivity : ComponentActivity() {
    lateinit var allNotes: List<Note>
    lateinit var allNotesState: MutableState<List<Note>>
    override fun onCreate(savedInstanceState: Bundle?) {
        allNotes = NoteRepository.getNotes(this)
        installSplashScreen().apply {
            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    "scaleX",
                    0.4f,
                    0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500L
                zoomX.doOnEnd { screen.remove() }
                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    "scaleY",
                    0.4f,
                    0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesTheme {

                val listIndex = remember {
                    mutableIntStateOf(0)
                }
                val navController = rememberNavController()
                allNotesState = remember { mutableStateOf<List<Note>>(allNotes) }

                NavHost(navController = navController, startDestination = HomePage) {
                    composable<HomePage> {
                        HomePage(navController, listIndex, allNotesState)
                    }
                    composable<EditNote> {

                        val args = it.toRoute<EditNote>()

                        EditNote(
                            navController,
                            listIndex.intValue,
                            position = args.position,
                            allNotesState
                        )
                    }
                    composable<SearchPage> {
                        SearchPage(navController,allNotesState)
                    }
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onStop() {
        super.onStop()

        NoteRepository.saveNotes(this, allNotesState.value.sortedByDescending { it.dateModified })
    }


}

data class Note(
    var title: String,
    var bg: Color,
    var bgGradient: List<Color>,
    var category: Int,
    var themesIndex: Int = 0,
    var imageList: List<String>,
    var doodlePath: DrawBoxPayLoad,
    var html: String,
    var descText: String,
    var dateCreated: Long,
    var dateModified: Long
)

@Composable
fun HomePage(
    navController: NavController,
    listIndex: MutableIntState,
    allNotes: MutableState<List<Note>>
) {
    val sortOptionIndex = rememberSaveable() {
        mutableIntStateOf(2)
    }

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

            when (sortOptionIndex.intValue) {
                0 -> {
                    allNotes.value = allNotes.value.sortedByDescending { it.dateModified }
                }

                1 -> {
                    allNotes.value = allNotes.value.sortedBy { it.dateModified }
                }

                2 -> {
                    allNotes.value = allNotes.value.sortedByDescending { it.dateCreated }
                }

                3 -> {
                    allNotes.value = allNotes.value.sortedBy { it.dateCreated }
                }
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
                    TopPart(listIndex, allNotes, sortOptionIndex, navController)
                }

                val notes = if (listIndex.intValue == 0) {
                    allNotes.value
                } else {
                    allNotes.value.filter { it.category == listIndex.intValue }
                }
                itemsIndexed(notes) { index, note ->

                    NoteCard(note = note,
                        onClick = {
                            navController.navigate(EditNote(position = index))
                        },
                        onDelete = {
                            val allNotesPosition = allNotes.value.indexOf(note)
                            allNotes.value =
                                allNotes.value.filterIndexed { i, _ -> i != allNotesPosition }
                        }
                    )

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
fun TopPart(
    listIndex: MutableIntState,
    allNotes: MutableState<List<Note>>,
    sortOptionIndex: MutableIntState,
    navController: NavController
) {
    val context = LocalContext.current
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
                var showSortDialog by remember { mutableStateOf(false) }

                if (showSortDialog) {
                    SortDialog(
                        onDismiss = {
                            showSortDialog = false
                        },
                        allNotes = allNotes,
                        optionIndex = sortOptionIndex

                    )
                }



                ButtonMoveExample(
                    x = -100,
                    painter = R.drawable.sortby_icon,
                    showSubMenu = showSubMenu,
                    setShowMenu = { newShowMenu ->
                        showSubMenu = newShowMenu
                    },
                    onClick = {
                        showSortDialog = true
                    }
                )

                ButtonMoveExample(
                    x = -70,
                    y = 70,
                    painter = R.drawable.rounded_info_24,
                    showSubMenu = showSubMenu,
                    setShowMenu = { newShowMenu ->
                        showSubMenu = newShowMenu
                    },
                    onClick = {
                        Toast.makeText(context, "Made by Asghar Hussain", Toast.LENGTH_SHORT).show()
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/YooAshu"))
                        context.startActivity(intent)
                    }
                )

                ButtonMoveExample(
                    y = 100,
                    painter = R.drawable.search_icon,
                    showSubMenu = showSubMenu,
                    setShowMenu = { newShowMenu ->
                        showSubMenu = newShowMenu
                    },
                    onClick = {
                        listIndex.intValue = 0
                        navController.navigate(SearchPage)
                    }
                )

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

    var isDialogShown by remember { mutableStateOf(false) }

    if (isDialogShown) {
        AddCategoryDialog(
            onDismiss = {
                isDialogShown = false
            }, onSubmit = {
                isDialogShown = false
            }
        )
    }

    LazyRow(
        modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,

        )
    {


        itemsIndexed(listOfNotesList) { index, listName ->
            Box(
                modifier = Modifier
                    .padding(10.dp, 0.dp)
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

        item() {
            Box(
                modifier = Modifier
                    .padding(10.dp, 0.dp)
                    .border(1.dp, Color.White, CircleShape)
                    .background(
                        color = Color.Transparent,
                        shape = CircleShape
                    )
                    .padding(20.dp, 5.dp)
                    .clickable {
                        isDialogShown = true
                    }

            ) {
                Text(
                    text = "+ Add",
                    color = Color.White,
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
object SearchPage

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
    setShowMenu: (Boolean) -> Unit,
    onClick: () -> Unit
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
        onClick()
    }
}


