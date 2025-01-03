package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notes.R
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.mohamedrejeb.richeditor.model.RichTextState
import kotlin.math.ceil


@Composable
fun FontBottomSheet(
    state: RichTextState
) {

    val controller = rememberColorPickerController()
    val isColorPickerVisible = remember {
        mutableStateOf(false)
    }
    val paraStyleIndex = remember { mutableIntStateOf(1) }

    if (isColorPickerVisible.value) {
        ColorPickerDialog(
            controller = controller,
            onDismiss = {
                isColorPickerVisible.value = false
            },
            state = state
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(color = Color(0xFF272727), shape = RoundedCornerShape(10.dp))
                        .weight(.5f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(
                        onClick = {
                            state.addParagraphStyle(ParagraphStyle(textAlign = TextAlign.Left))
                            paraStyleIndex.intValue = 1
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.align_left),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    if (paraStyleIndex.intValue == 1) Color.White else Color.Transparent,
                                    CircleShape
                                )
                                .padding(8.dp),
                            tint = if (paraStyleIndex.intValue == 1) Color.Black else Color.White
                        )

                    }
                    IconButton(
                        onClick = {
                            state.addParagraphStyle(ParagraphStyle(textAlign = TextAlign.Center))
                            paraStyleIndex.intValue = 2
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.align_centre),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    if (paraStyleIndex.intValue == 2) Color.White else Color.Transparent,
                                    CircleShape
                                )
                                .padding(8.dp),
                            tint = if (paraStyleIndex.intValue == 2) Color.Black else Color.White
                        )

                    }
                    IconButton(
                        onClick = {
                            state.addParagraphStyle(ParagraphStyle(textAlign = TextAlign.Right))
                            paraStyleIndex.intValue = 3
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.align_right),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    if (paraStyleIndex.intValue == 3) Color.White else Color.Transparent,
                                    CircleShape
                                )
                                .padding(8.dp),
                            tint = if (paraStyleIndex.intValue == 3) Color.Black else Color.White
                        )

                    }

                }

//                Row(
//                    modifier = Modifier
//                        .padding(10.dp)
//                        .fillMaxWidth()
//                        .height(50.dp)
//                        .background(color = Color(0xFF272727), shape = RoundedCornerShape(10.dp))
//                        .weight(1f),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceAround
//                ) {
//                    IconButton(
//                        onClick = {
//                            state.toggleOrderedList()
//                        },
//                    ) {
//                        Icon(
//                            painter = painterResource(R.drawable.list_o),
//                            contentDescription = null,
//                            modifier = Modifier
//                                .size(40.dp)
//                                .background(
//                                    if (state.isOrderedList) Color.White else Color.Transparent,
//                                    CircleShape
//                                )
//                                .padding(8.dp),
//                            tint = if (state.isOrderedList) Color.Black else Color.White
//                        )
//
//                    }
//                    IconButton(
//                        onClick = {
//                            state.toggleUnorderedList()
//                        },
//                    ) {
//                        Icon(
//                            painter = painterResource(R.drawable.list_u),
//                            contentDescription = null,
//                            modifier = Modifier
//                                .size(40.dp)
//                                .background(
//                                    if (state.isUnorderedList) Color.White else Color.Transparent,
//                                    CircleShape
//                                )
//                                .padding(8.dp),
//                            tint = if (state.isUnorderedList) Color.Black else Color.White
//                        )
//
//                    }
//                }

            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                        .height(50.dp)
                        .background(color = Color(0xFF272727), shape = RoundedCornerShape(10.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround

                ) {

                    FontStyleButton(
                        clicked = state.currentSpanStyle.fontWeight == FontWeight.Bold,
                        textStyle = TextStyle(fontWeight = FontWeight.Bold),
                        text = "B",
                        onCLick = {
                            state.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                        }

                    )

                    FontStyleButton(
                        clicked = state.currentSpanStyle.fontStyle == FontStyle.Italic,
                        textStyle = TextStyle(fontStyle = FontStyle.Italic),
                        text = "I",
                        onCLick = {
                            state.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                        }

                    )

                    FontStyleButton(
                        clicked = state.currentSpanStyle.textDecoration == TextDecoration.Underline,
                        textStyle = TextStyle(textDecoration = TextDecoration.Underline),
                        text = "U",
                        onCLick = {
                            state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                        }

                    )

                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                        .height(50.dp)
                        .background(color = Color(0xFF272727), shape = RoundedCornerShape(10.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontSize = 28.sp, color = Color.White)) {
                                append("T")
                            }
                            withStyle(style = SpanStyle(fontSize = 20.sp, color = Color.White)) {
                                append("T")
                            }
                        },
                        modifier = Modifier.padding(start = 10.dp),
                    )
                    FontSizeChanger(modifier = Modifier.weight(1f), state = state)
                }
            }
        }


        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(50.dp)
                    .background(color = Color(0xFF272727), shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .width(25.dp)
                            .height(15.dp)
                            .background(state.currentSpanStyle.color)
                    )
                    Text(text = "A", fontSize = 36.sp, color = Color.White)
                }
                Row {
                    val colors = listOf(
                        Color.White,
                        Color.Black,
                        Color.Red,
                        Color.Green,
                        Color.Blue,
                        Color.Yellow,
                    )
                    for (color in colors) {
                        Box(
                            modifier = Modifier
                                .padding(5.dp)
                                .size(30.dp)
                                .background(color, shape = RoundedCornerShape(100))
                                .clickable {
                                    state.addSpanStyle(SpanStyle(color = color))
                                }
                        )
                    }

                }
                Button(
                    onClick = {
                        isColorPickerVisible.value = true
                    },
                    modifier = Modifier.size(30.dp),
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.colorpicker),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }

            }
        }
        // Remaining items in a grid
        item { // Adjust 10 to 30 as per your needs
            Column(modifier = Modifier.fillMaxSize()) {
                // Create 5 rows
                val rows = ceil(fontsList.size / 3.toDouble()).toInt()
                for (row in 0 until rows) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        // Create 3 columns in each row
                        for (col in 0 until 3) {
                            // Calculate the index of the item
                            val index = row * 3 + col
                            // Only display the item if it exists

                            if (index < fontsList.size) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(5.dp)
                                        .height(50.dp)
                                        .background(
                                            if (state.currentSpanStyle.fontFamily == fontsList[index].fontFamily) {
                                                Color.White
                                            } else {
                                                Color(0xFF272727)
                                            },
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .clickable {
                                            state.addSpanStyle(SpanStyle(fontFamily = fontsList[index].fontFamily))
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = fontsList[index].fontName,
                                        color = if (state.currentSpanStyle.fontFamily == fontsList[index].fontFamily) {
                                            Color.Black
                                        } else {
                                            Color.White
                                        },
                                        fontFamily = fontsList[index].fontFamily
                                    )
                                }
                            } else {
                                // Empty box if no item exists
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(60.dp)
                                )// This makes it a square
                                {}
                            }
                        }
                    }
                }
            }
        }
    }

    DisposableEffect(key1 = Unit) {
        onDispose {

        }
    }
}

@Composable
fun FontStyleButton(clicked: Boolean, textStyle: TextStyle, text: String, onCLick: () -> Unit) {


    Button(
        onClick = {
            onCLick()
        },
        colors = ButtonDefaults.buttonColors(containerColor = if (clicked) Color.White else Color.Transparent),
        modifier = Modifier
            .padding(vertical = 5.dp)
            .aspectRatio(1f),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            color = if (clicked) Color.Black else Color.White,
            fontSize = 28.sp,
            style = textStyle

        )
    }


}

@Composable
fun FontSizeChanger(modifier: Modifier, state: RichTextState) {
    Row(
        modifier = modifier
            .fillMaxHeight()
            .padding(vertical = 5.dp, horizontal = 7.dp)
            .background(Color(0xFF4D4D4D), shape = RoundedCornerShape(100)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Button(
            onClick = {
                state.addSpanStyle(SpanStyle(fontSize = (state.currentSpanStyle.fontSize.value - 2).sp))
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A3A3A)),
            modifier = Modifier
                .aspectRatio(1f),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "-",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)
            )
        }
        Text(
            text = state.currentSpanStyle.fontSize.value.toInt().toString(), modifier = Modifier
                .fillMaxHeight()
                .wrapContentHeight(align = Alignment.CenterVertically),
            fontSize = 18.sp,
            color = Color.White
        )
//Log.d("TAG", "FontSizeChanger: ${state.currentSpanStyle.fontSize.value}")
        Button(
            onClick = {
                state.addSpanStyle(SpanStyle(fontSize = (state.currentSpanStyle.fontSize.value + 2).sp))
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A3A3A)),
            modifier = Modifier
                .aspectRatio(1f),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "+",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.W400
            )
        }
    }
}