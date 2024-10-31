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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.notes.Note
import com.example.notes.R
import com.example.notes.localBgColor
import com.example.notes.localRichTextDescState
import com.mohamedrejeb.richeditor.model.RichSpanStyle.Default.spanStyle
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import components.BottomOptions
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNote(navController: NavController, notes: MutableList<Note>, position: Int = -1) {

    val titleInitVal = if (position == -1) "" else notes[position].title

    var textFieldTitleState by remember {

        mutableStateOf(titleInitVal)
    }


    val state = localRichTextDescState.current
    val bg = localBgColor.current

    LaunchedEffect(key1 = Unit) {
        if (state.currentSpanStyle.fontSize.value.isNaN()){
            state.addSpanStyle(SpanStyle(fontSize = 20.sp))
        }

        if(state.currentSpanStyle.color.value.toInt() == 16){
            state.addSpanStyle(SpanStyle(color = Color.Black))
        }



    }


    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bg.value)
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
                        backgroundColor = bg.value,
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
                        value = textFieldTitleState,
                        onValueChange = {
                            textFieldTitleState = it
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
                        state = state,
                        placeholder = {

                            Text(
                                "Write your note here2",
                                style = TextStyle(
                                    color = Color.Black.copy(alpha = .5f),
                                    fontSize = 20.sp
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
//                        textStyle = TextStyle(
//                            color = Color.Black,
//                            fontSize = 20.sp
//                        )

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

            BottomOptions(
                hazeState,
                modifier = Modifier.align(Alignment.BottomCenter),
//                style = style,
            )


        }

    }

    DisposableEffect(1) {
        onDispose {
            if (textFieldTitleState.isEmpty() && state.toText().isEmpty()) {
                if (position != -1) {
                    notes.removeAt(position)
                }
                return@onDispose
            }
            if (position == -1) {
                Log.d("bgfromdispose", bg.value.toString())
                notes.add(0, Note(textFieldTitleState, state, bg = bg))

            } else {
                notes[position].title = textFieldTitleState
                notes[position].richTextDescState = state
                notes[position].bg = bg
            }

        }
    }

}

@Preview
@Composable
fun EditNotePreview() {
    EditNote(navController = rememberNavController(), mutableListOf())
}