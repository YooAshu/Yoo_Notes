package components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import coil3.compose.AsyncImage
import com.example.notes.DoodleScreen
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.mohamedrejeb.richeditor.model.RichTextState
import io.ak1.drawbox.DrawBoxPayLoad
import io.ak1.drawbox.DrawController
import io.getstream.sketchbook.PaintColorPalette
import io.getstream.sketchbook.PaintColorPaletteTheme
import io.getstream.sketchbook.SketchbookController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryDialog(
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {

    val text = remember { mutableStateOf("") }


    BasicAlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        modifier = Modifier
            .background(Color(0xFF272727), shape = RoundedCornerShape(20.dp))
            .padding(vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Text(
                text = "Add Category",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            TextField(
                value = text.value,
                onValueChange = {
                    text.value = it
                },
                placeholder = {
                    Text(text = "Category Name",color = Color.White)
                },
                maxLines = 1,
                modifier = Modifier
                    .padding(16.dp)
                    .border(1.dp, Color(0xFFA9A9A8), CircleShape)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = CircleShape
            )


            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier,
                    onClick = {
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                ) { Text(text = "Cancel",color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp) }

                Button(
                    modifier = Modifier,
                    onClick = {
                        if (text.value.isNotEmpty() && !listOfNotesList.any { it.listName.lowercase() == text.value.lowercase() }) {
                            listOfNotesList.add(NotesList(text.value))
                        }
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                ) { Text(text = "Add", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp) }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPickerDialog(
    controller: ColorPickerController,
    onDismiss: () -> Unit,
    state: RichTextState
){
    var color: Color = Color.Black
    BasicAlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        modifier = Modifier
            .background(Color(0xFF272727), shape = RoundedCornerShape(20.dp))
            .padding(16.dp)

    ){
        Column {
            Text(text = "Color Picker", fontWeight = FontWeight.Bold, modifier = Modifier.align(
                Alignment.CenterHorizontally))
            HsvColorPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(10.dp),
                controller = controller,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    color = colorEnvelope.color
                }
            )
            AlphaTile(
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(6.dp)),
                controller = controller
            )
            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    state.addSpanStyle(SpanStyle(color = color))
                    onDismiss()
                }
            ) { Text(text = "Done") }

        }



    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageShowDialog(
    uri: Uri,
    onDismiss: () -> Unit,
){
    BasicAlertDialog(
        onDismissRequest = { onDismiss() },
        modifier = Modifier
            .fillMaxWidth(.9f)
            .wrapContentHeight()
    ){
        AsyncImage(
            model = uri,
            contentDescription = null,

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaintBrushDialog(
    onDismiss : ()-> Unit,
    sketchbookController : DrawController,

){
    var controller = rememberColorPickerController()
    var topBarColor = remember { mutableStateOf(sketchbookController.color) }
    var sliderPosition = remember { mutableFloatStateOf(sketchbookController.strokeWidth) }
    BasicAlertDialog(
        onDismissRequest = {
            sketchbookController.changeColor(topBarColor.value)
            onDismiss()
                           },
        modifier = Modifier
            .wrapContentSize()
            .shadow(10.dp, shape = RoundedCornerShape(30.dp))
            .background(Color(0xFF272727), shape = RoundedCornerShape(30.dp))
    ) {
        (LocalView.current.parent as DialogWindowProvider)?.window?.setDimAmount(0f)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(color = topBarColor.value))
            HsvColorPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(20.dp),
                controller = controller,
                initialColor = sketchbookController.color,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
//                    sketchbookController.setPaintColor(colorEnvelope.color)
                    topBarColor.value = colorEnvelope.color
                }
            )
            BrightnessSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(35.dp),
                controller = controller,
                initialColor = sketchbookController.color
            )
//            PaintColorPalette(
//                modifier = Modifier
//                    .padding(vertical = 10.dp, horizontal = 20.dp),
//                controller = sketchbookController,
//                theme = PaintColorPaletteTheme(
//                    shape = CircleShape,
//                    itemSize = 48.dp,
//                    selectedItemSize = 58.dp,
//                    borderWidth = 0.dp
//                ),
//                onColorSelected = {int,color->
//                    topBarColor.value = color
//                    controller.selectByColor(color,true)
//                }
//            )


            Slider(
                value = sliderPosition.floatValue,
                onValueChange = {
                    sliderPosition.floatValue = it
                    sketchbookController.changeStrokeWidth(it)
                                },
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.Gray,
                ),
                steps = 7,
                valueRange = 0f..50f,
                modifier = Modifier.padding(20.dp),
                thumb = {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(Color.White, shape = CircleShape)
                    )
                },
                track = { sliderPositions ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)  // Very thin track
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                        MaterialTheme.colorScheme.primary
                                    )
                                ),
                                shape = RoundedCornerShape(2.dp)
                            )
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoodleDialog(
    onDismiss : ()-> Unit,
    path: MutableState<DrawBoxPayLoad>
){
    BasicAlertDialog(
        onDismissRequest = { onDismiss() },
        modifier = Modifier.fillMaxSize(),
        properties = DialogProperties(usePlatformDefaultWidth = false)
    )
    {

        DoodleScreen(onDismiss,path)
    }
}
