package com.example.notes.ui.theme

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notes.Note
import com.example.notes.R
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import components.BottomOptions
import components.DropDownList
import components.allNotes
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNote(navController: NavController, listIndex: Int, position: Int) {

    val notes = if (listIndex == 0) {
        allNotes
    } else {
        allNotes.filter { it.category == listIndex }
    }
    var allNotesPosition:Int=-1
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
    val category = if (position == -1) listIndex else allNotes[allNotesPosition].category

    if (richTextDescState.currentSpanStyle.fontSize.value.isNaN()) {
        richTextDescState.addSpanStyle(SpanStyle(fontSize = 20.sp))
    }

    if (richTextDescState.currentSpanStyle.color.value.toInt() == 16) {
        richTextDescState.addSpanStyle(SpanStyle(color = Color.Black))
    }

    val selectedIndex = remember { mutableIntStateOf(category) }


    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bgColor.value)
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
                        backgroundColor = bgColor.value,
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


                    RichTextEditor(
                        state = richTextDescState,
                        placeholder = {

                            Text(
                                "Write your note here2",
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
                bg = bgColor
            )


        }

    }


    DisposableEffect(1) {
        onDispose {
            if (textFieldTitleState.value.isNotEmpty() || richTextDescState.toText().isNotEmpty()) {
                if (position == -1) {
                    val note = Note(
                        textFieldTitleState,
                        richTextDescState,
                        bgColor,
                        selectedIndex.intValue
                    )
                    allNotes.add(0, note)
                }
                else{
                    allNotes[allNotesPosition].category = selectedIndex.intValue
                }
            }
        }
    }


}

