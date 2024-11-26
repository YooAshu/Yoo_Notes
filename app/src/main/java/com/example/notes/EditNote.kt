package com.example.notes

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil3.compose.AsyncImage
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import components.BottomOptions
import components.DropDownList
import components.ImageShowDialog
import components.allNotes
import components.conditionalBackground
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import io.ak1.drawbox.DrawBoxPayLoad
import kotlin.math.ceil

var isGradient: Int = 0
var imageIndex: Int = 0

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNote(navController: NavController, listIndex: Int, position: Int) {

    val notes = if (listIndex == 0) {
        allNotes
    } else {
        allNotes.filter { it.category == listIndex }
    }
    var allNotesPosition: Int = -1
    if (position != -1) {
        allNotesPosition = allNotes.indexOf(notes[position])
    }
    val textFieldTitleState =
        if (position == -1) remember {
            mutableStateOf("")
        } else allNotes[allNotesPosition].title

    val richTextDescState =
        if (position == -1) rememberRichTextState() else allNotes[allNotesPosition].richTextDescState

    val bgColor =
        if (position == -1) {
            remember {
                mutableStateOf(Color(0xFFECE3C1))
            }
        } else allNotes[allNotesPosition].bg

    val bgGradient =
        if (position == -1) {
            remember {
                mutableStateOf(listOf(Color(0xFFECE3C1), Color(0xFFECE3C1)))
            }
        } else allNotes[allNotesPosition].bgGradient

    var imageUriList =
        if (position == -1) {
            remember { mutableStateOf<List<Uri>>(emptyList<Uri>()) }
        } else {
            allNotes[allNotesPosition].imageList
        }

    var path=
        if (position == -1) {
            remember { mutableStateOf<DrawBoxPayLoad>(DrawBoxPayLoad(Color.White,emptyList())) }
        } else {
            allNotes[allNotesPosition].doodlePath
        }


    val category = if (position == -1) listIndex else allNotes[allNotesPosition].category
    LaunchedEffect(1) {
        isGradient = if (position != -1) allNotes[allNotesPosition].themesIndex else 0
    }


    if (richTextDescState.currentSpanStyle.fontSize.value.isNaN()) {
        richTextDescState.addSpanStyle(SpanStyle(fontSize = 20.sp))
    }

    if (richTextDescState.currentSpanStyle.color.value.toInt() == 16) {
        richTextDescState.addSpanStyle(SpanStyle(color = Color.Black))
    }

    val selectedIndex = remember { mutableIntStateOf(category) }
    var isImageDialogShown by remember { mutableStateOf(false) }


    if (isImageDialogShown) {
        ImageShowDialog(
            onDismiss = {
                isImageDialogShown = false
            },
            uri = imageUriList.value[imageIndex]
        )
    }



    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .conditionalBackground(
                    isGradient = isGradient,
                    bgColor.value,
                    bgGradient.value,
                    0
                )
                .padding(innerPadding)
                .padding(5.dp)
        )
        {
            val hazeState = remember {
                HazeState()
            }


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .haze(
                        state = hazeState,
                        backgroundColor = if (isGradient == 0) bgColor.value else bgGradient.value[1],
                        tint = Color.Black.copy(alpha = .1f),
                        blurRadius = 10.dp,
                    ),
            ) {

                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {

                    TextField(
                        value = textFieldTitleState.value,
                        onValueChange = {
                            textFieldTitleState.value = it
                        },
                        placeholder = {
                            Text(
                                "Title",
                                style = TextStyle(
                                    color = Color.Black.copy(alpha = .5f),
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),

                        modifier = Modifier.padding(top = 100.dp),
                        maxLines = 2,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                    )

//                    image grid

                    Column {
                        val rows = ceil(imageUriList.value.size / 2.toDouble()).toInt()
                        for (row in 0 until rows) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                for (col in 0 until 2) {
                                    val index = row * 2 + col
                                    if (index < imageUriList.value.size) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(1f)
                                                .aspectRatio(1f)
                                                .padding(10.dp)

                                        ) {
                                            AsyncImage(
                                                model = imageUriList.value[index],
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .clickable {
                                                        imageIndex = index
                                                        isImageDialogShown = true
                                                    },
                                                contentScale = ContentScale.Crop
                                            )
                                            Button(
                                                onClick = {
                                                    imageUriList.value =
                                                        imageUriList.value.toMutableList().apply {
                                                            removeAt(index)
                                                        }
                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    Color(
                                                        0x9F6C6A6A
                                                    )
                                                ),
                                                contentPadding = PaddingValues(0.dp),
                                                modifier = Modifier
                                                    .align(Alignment.TopEnd)
                                                    .size(40.dp)
                                                    .padding(5.dp)
                                            ) {
                                                Icon(
                                                    painter = painterResource(R.drawable.cross_icon),
                                                    tint = Color.White,
                                                    contentDescription = null,

                                                    )
                                            }
                                        }

                                    }
                                }
                            }

                        }
                    }


                    RichTextEditor(
                        state = richTextDescState,
                        placeholder = {
                            Text(
                                "Write your note here",
                                style = TextStyle(
                                    color = Color.Black.copy(alpha = .5f),
                                    fontSize = 20.sp,
                                )
                            )
                        },
                        colors = RichTextEditorDefaults.richTextEditorColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(bottom = 100.dp)
                            .fillMaxWidth(),

                        )


                }
            }
            Button(
                onClick = {

                    navController.popBackStack()

                },
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                contentPadding = PaddingValues(5.dp),
                modifier = Modifier
                    .size(80.dp)
                    .padding(10.dp)
                    .align(Alignment.TopStart)
                    .hazeChild(state = hazeState, shape = CircleShape)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.round_arrow_back_ios_new_24),
                    contentDescription = "back to homepage",
                    modifier = Modifier.fillMaxSize()
                )
            }


            DropDownList(
                modifier = Modifier.align(Alignment.TopEnd),
                selectedIndex = selectedIndex
            )

            BottomOptions(
                hazeState,
                modifier = Modifier.align(Alignment.BottomCenter),
                state = richTextDescState,
                bg = bgColor,
                bgGradient = bgGradient,
                selectedImages = imageUriList,
                path = path
            )


        }

    }


    DisposableEffect(1) {
        onDispose {

            if (textFieldTitleState.value.isNotEmpty() || richTextDescState.toText()
                    .isNotEmpty() || imageUriList.value.isNotEmpty()
            ) {


                if (position == -1) {
                    val note = Note(
                        textFieldTitleState,
                        richTextDescState,
                        bgColor,
                        bgGradient,
                        selectedIndex.intValue,
                        isGradient,
                        imageUriList,
                        path
                    )
                    allNotes.add(0, note)
                } else {
                    allNotes[allNotesPosition].category = selectedIndex.intValue
                    allNotes[allNotesPosition].themesIndex = isGradient

                }

            }
        }
    }


}

