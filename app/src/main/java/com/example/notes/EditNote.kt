package com.example.notes.ui.theme

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.notes.Note
import com.example.notes.R
import com.example.notes.TopPart
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import kotlin.random.Random


@Composable
fun EditNote(navController: NavController,notes: MutableList<Note>,position: Int=-1){

    val titleInitVal = if (position ==-1)  "" else notes[position].title
    val descInitVal = if (position ==-1)  "" else notes[position].desc

    var textFieldTitleState by remember {

        mutableStateOf(titleInitVal)
    }
    var textFieldNoteState by remember {
        mutableStateOf(descInitVal)
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->



        Box(
            modifier = Modifier
                .fillMaxSize()
//                .background(Color.Red)
                .background(Color(0xFFECE3C1))
                .padding(innerPadding)
                .padding(5.dp)
        )
        {
            val hazeState = remember {
                HazeState()
            }

            var fontSize by remember {
                mutableStateOf(20.sp)
            }

            Box(

                modifier = Modifier
                    .fillMaxSize()
                    .haze(
                        hazeState,
                        backgroundColor = Color(0xFFECE3C1),
                        tint = Color.Black.copy(alpha = .1f),
                        blurRadius = 10.dp,
                    ),
            ) {

                val scrollState = rememberScrollState()


                Column(modifier = Modifier
                    .fillMaxSize()
//                    .padding(top = 100.dp)
                    .verticalScroll(scrollState)
                ){

                    TextField(
                        value = textFieldTitleState,
                        onValueChange = {
                            textFieldTitleState = it
                        },
                        placeholder = { Text("Title", style = TextStyle( color = Color.Black.copy(alpha = .5f), fontSize = 30.sp,fontWeight = FontWeight.SemiBold)) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor =  Color.Transparent,
                            unfocusedIndicatorColor =Color.Transparent,
                        ),

                        modifier = Modifier.padding(top = 100.dp),
                        maxLines = 2,
                        textStyle = TextStyle(color = Color.Black, fontSize = 30.sp,fontWeight = FontWeight.SemiBold)

                    )

//                    Spacer(modifier = Modifier.height(100.dp))
                    TextField(
                        value = textFieldNoteState,
                        onValueChange = {
                            textFieldNoteState = it
                        },
                        placeholder = { Text("Write your note here", style = TextStyle( color = Color.Black.copy(alpha = .5f), fontSize = 20.sp)) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor =  Color.Transparent,
                            unfocusedIndicatorColor =Color.Transparent,
                        ),

                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        textStyle = TextStyle(color = Color.Black,
//                            fontSize = 20.sp,
                            fontSize = fontSize
                        )

                    )




                }


            }
            Button(
                onClick = {
//                    notes.add(Note(textFieldTitleState,textFieldNoteState))
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

            Box(modifier = Modifier
                .padding(bottom = 10.dp)
                .wrapContentWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter)
//                .background(Color.White, shape = RoundedCornerShape(50.dp) )
                .hazeChild(state = hazeState, shape = CircleShape)

            ){

                Row {
//                    ButtonIcon(painter = R.drawable.fonts_icon)
                    Button(
                        onClick = {
                                  fontSize = 30.sp



                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF161616)),
                        contentPadding = PaddingValues(10.dp),
                        modifier = Modifier
                            .size(80.dp)
                            .padding(5.dp)

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.fonts_icon),
                            contentDescription = "Change Font Style",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        )
                    }
                    ButtonIcon(painter = R.drawable.mic_ion)
                    ButtonIcon(painter = R.drawable.add_photo_icon)
                    ButtonIcon(painter = R.drawable.change_theme_icon)
                }

            }


        }
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            if (position == -1){
                notes.add(0,Note(textFieldTitleState,textFieldNoteState))
            }
            else{
                notes[position].title = textFieldTitleState
                notes[position].desc = textFieldNoteState
//                Note(textFieldTitleState,textFieldNoteState)
            }

        }
    }


}

@Preview
@Composable
fun EditNotePreview(){
    EditNote(navController = rememberNavController(), mutableListOf())
}

@Composable
fun ButtonIcon(painter: Int){

    Button(
        onClick = {

        },
        colors = ButtonDefaults.buttonColors(Color(0xFF161616)),
        contentPadding = PaddingValues(10.dp),
        modifier = Modifier
            .size(80.dp)
            .padding(5.dp)

    ) {
        Image(
            painter = painterResource(id = painter),
            contentDescription = "Change Font Style",
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        )
    }


}


