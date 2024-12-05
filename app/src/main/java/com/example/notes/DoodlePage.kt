package com.example.notes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import components.PaintBrushDialog
import io.ak1.drawbox.DrawBox
import io.ak1.drawbox.DrawBoxPayLoad
import io.ak1.drawbox.rememberDrawController


@Composable
fun DoodleScreen(
//    navController: NavController
    onDismiss: () -> Unit,
    path: MutableState<DrawBoxPayLoad>
) {
    val sketchbookController = rememberDrawController()

    val isPainterDialogShown = remember { mutableStateOf(false) }
    if (isPainterDialogShown.value) {
        PaintBrushDialog(
            sketchbookController = sketchbookController,
            onDismiss = {
                isPainterDialogShown.value = false
            },
        )
    }
    LaunchedEffect(1) {
        sketchbookController.changeColor(Color.Black)
        sketchbookController.importPath(path.value)
    }
    val undoVisibility = remember { mutableStateOf(false) }
    val redoVisibility = remember { mutableStateOf(false) }


    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {
            DrawBox(
                drawController = sketchbookController,
                backgroundColor = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f, fill = false),
                bitmapCallback = { imageBitmap, error ->
                    imageBitmap?.let {

                    }
                }
            ) { undoCount, redoCount ->
                undoVisibility.value = undoCount != 0
                redoVisibility.value = redoCount != 0
            }
            Column(
                modifier = Modifier
                    .weight(.1f)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        Color(0xFF272727),
                        shape = RoundedCornerShape(30.dp)
                    ),
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    IconButton(
                        onClick = {
//                            sketchbookController.setEraseMode(false)
                            isPainterDialogShown.value = true
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.scribble_icon),
                            contentDescription = null,
                            tint = Color.White
                        )

                    }
                    IconButton(
                        onClick = {
//                            sketchbookController.toggleEraseMode()
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.eraser_icon),
                            contentDescription = null,
//                            tint = if (sketchbookController.isEraseMode.value) Color.White else Color.Gray
                        )

                    }
                    IconButton(
                        onClick = {
                            sketchbookController.unDo()
                        },
                        enabled = undoVisibility.value
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.undo),
                            contentDescription = null,
                            tint = if (undoVisibility.value) Color.White else Color.Gray
                        )

                    }
                    IconButton(
                        onClick = {
                            sketchbookController.reDo()
                        },
                        enabled = redoVisibility.value
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.redo),
                            contentDescription = null,
                            tint = if (redoVisibility.value) Color.White else Color.Gray
                        )

                    }
                    IconButton(
                        onClick = {
                            sketchbookController.reset()
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.clear_doodle),
                            contentDescription = null,
                            tint = Color.White
                        )

                    }
                }
            }

        }

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .size(100.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Button(
                onClick = {
                    path.value = sketchbookController.exportPath()
                    onDismiss()
//                navController.popBackStack()

                },
                colors = ButtonDefaults.buttonColors(Color.Black),
                contentPadding = PaddingValues(5.dp),
                modifier = Modifier
                    .size(80.dp)
                    .padding(10.dp)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.round_arrow_back_ios_new_24),
                    contentDescription = "back to homepage",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }


    }

    DisposableEffect(1) {
        onDispose {
            path.value = sketchbookController.exportPath()
        }
    }

}
