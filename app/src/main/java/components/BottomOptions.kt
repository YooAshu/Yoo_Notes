package components

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notes.R
import com.mohamedrejeb.richeditor.model.RichTextState
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import io.ak1.drawbox.DrawBoxPayLoad


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomOptions(
    hazeState: HazeState,
    modifier: Modifier,
    state: RichTextState,
    bg: MutableState<Color>,
    bgGradient: MutableState<List<Color>>,
    selectedImages: MutableState<List<Uri>>,
    path: MutableState<DrawBoxPayLoad>,
    themeIndex: MutableState<Int>
) {


    var fontSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var themeSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var doodleDialog by rememberSaveable {
        mutableStateOf(false)
    }
    if (doodleDialog) {
        DoodleDialog(path = path, onDismiss = { doodleDialog = false })
    }

    val yOffset by animateDpAsState(
        targetValue = if (fontSheetOpen || themeSheetOpen) 100.dp else 0.dp,
        tween(durationMillis = 100)
    )

//    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.PickMultipleVisualMedia(),
//        onResult = { uris ->
//            selectedImages.value = selectedImages.value + uris
//            Log.d("picker", "${selectedImages.value}")
//        }
//    )
    val context = LocalContext.current
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            if (uris != null && uris.isNotEmpty()) {
                // Persist permissions for each URI
                uris.forEach { uri ->
                    try {
                        context.contentResolver.takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                    } catch (e: Exception) {
                        Log.e("Picker", "Failed to persist permissions for $uri: $e")
                    }
                }

                // Update the state and save URIs as strings
                selectedImages.value = selectedImages.value + uris
//                saveUrisToPreferences(context, uris)
//                Log.d("picker", "Selected images: ${selectedImages.value}")
            }
        }
    )
    Box(
        modifier = modifier
            .offset(x = 0.dp, y = yOffset)
            .padding(bottom = 10.dp)
            .wrapContentWidth()
            .height(80.dp)
            .hazeChild(state = hazeState, shape = CircleShape)


    ) {
        Row {


            ButtonIcon(
                painter = R.drawable.fonts_icon,
                modifier = Modifier.size(80.dp),
                bgColor = Color(0xFF272727)
            ) {
                fontSheetOpen = true
            }
            ButtonIcon(
                painter = R.drawable.drawicon,
                modifier = Modifier.size(80.dp),
                bgColor = Color(0xFF272727)
            ) {
                doodleDialog = true
//                navController.navigate(DoodlePage)
//                Toast.makeText(navController.context, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
            ButtonIcon(
                painter = R.drawable.add_photo_icon,
                modifier = Modifier.size(80.dp),
                bgColor = Color(0xFF272727)
            ) {
                multiplePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )

            }
            ButtonIcon(
                painter = R.drawable.change_theme_icon,
                modifier = Modifier.size(80.dp),
                bgColor = Color(0xFF272727)
            ) {
                themeSheetOpen = true
            }
        }


    }
    val fontSheetState = rememberModalBottomSheetState()

    if (fontSheetOpen || themeSheetOpen) {
        ModalBottomSheet(
            modifier = Modifier,
            containerColor = Color(0xFF1A1A1A),
            sheetState = fontSheetState,
            onDismissRequest = { fontSheetOpen = false; themeSheetOpen = false },
            content = {
                when {
                    fontSheetOpen -> FontBottomSheet(
                        state = state
                    )

                    themeSheetOpen -> ThemeBottomSheet(
                        bg = bg,
                        bgGradient = bgGradient,
                        themesSelectedIndex = themeIndex

                    )
                }
            }
        )


    }

}

@Composable
fun CustomDragHandle(hazeState: HazeState) {
    // Design a custom drag handle as a small rounded rectangle at the top of the sheet
    Row(
        modifier = Modifier
//            .hazeChild(hazeState, shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,// Padding to ensure it's not too close to the top
        verticalAlignment = Alignment.CenterVertically


    ) {
        ButtonIcon(
            painter = R.drawable.cross_icon,
            modifier = Modifier.size(60.dp),
            contentPadding = 10.dp,
            bgColor = Color(
                0xFF272727
            )
        ) {
            Log.d("CustomDragHandle", "Button clicked")
        }
        Box(
            modifier = Modifier
                .width(50.dp)  // Width of the handle
                .height(6.dp)  // Height of the handle
                .clip(RoundedCornerShape(3.dp))  // Rounded corners for the handle
                .background(Color(0xFF272727))
        )  // Background color of the handle (can be customized))
        ButtonIcon(
            painter = R.drawable.round_check_24,
            modifier = Modifier.size(60.dp),
            contentPadding = 10.dp,
            bgColor = Color(0xFF272727)
        ) {
            Log.d("CustomDragHandle", "Button clicked")
        }
    }


}