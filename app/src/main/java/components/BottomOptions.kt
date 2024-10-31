package components

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.notes.R
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomOptions(hazeState: HazeState,modifier: Modifier) {


    var fontSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var themeSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val visibility by animateFloatAsState(
        targetValue = if (fontSheetOpen) 0f else 1f,
        tween(durationMillis = 200)
    )
    val yOffset by animateDpAsState(
        targetValue = if (fontSheetOpen || themeSheetOpen) 100.dp else 0.dp,
        tween(durationMillis = 100)
    )
    Box(
        modifier = modifier
//            .alpha(visibility)
            .offset(x = 0.dp, y = yOffset)
            .padding(bottom = 10.dp)
            .wrapContentWidth()
            .height(80.dp)
            .hazeChild(state = hazeState, shape = CircleShape)


    ) {
        Row {

            val context = LocalContext.current


            ButtonIcon(painter = R.drawable.fonts_icon, modifier = Modifier.size(80.dp), bgColor = Color(0xFF272727)){
                fontSheetOpen = true
            }
            ButtonIcon(painter = R.drawable.mic_ion,modifier = Modifier.size(80.dp), bgColor = Color(0xFF272727)) {
                Toast.makeText(context, "feature not added yet", Toast.LENGTH_SHORT).show()
            }
            ButtonIcon(painter = R.drawable.add_photo_icon,modifier = Modifier.size(80.dp), bgColor = Color(0xFF272727)) {
                Toast.makeText(context, "feature not added yet", Toast.LENGTH_SHORT).show()
            }
            ButtonIcon(painter = R.drawable.change_theme_icon,modifier = Modifier.size(80.dp), bgColor = Color(0xFF272727)) {
                themeSheetOpen = true
            }
        }


    }
    val fontSheetState = rememberModalBottomSheetState()

    if (fontSheetOpen || themeSheetOpen) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(.5f),
            containerColor = Color(0xFF1A1A1A),
            sheetState = fontSheetState,
            onDismissRequest = { fontSheetOpen = false ; themeSheetOpen = false},
//            scrimColor = Color.Transparent,
            content = {
                when{
                    fontSheetOpen -> FontBottomSheet(
//                        hazeState = hazeState
                    )
                    themeSheetOpen -> ThemeBottomSheet()
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


    ){
        ButtonIcon(painter = R.drawable.cross_icon,modifier = Modifier.size(60.dp),contentPadding = 10.dp, bgColor = Color(
            0xFF272727
        )
        ) {
            Log.d("CustomDragHandle", "Button clicked")
        }
        Box(modifier = Modifier
            .width(50.dp)  // Width of the handle
            .height(6.dp)  // Height of the handle
            .clip(RoundedCornerShape(3.dp))  // Rounded corners for the handle
            .background(Color(0xFF272727)))  // Background color of the handle (can be customized))
        ButtonIcon(painter = R.drawable.round_check_24,modifier = Modifier.size(60.dp),contentPadding = 10.dp,bgColor = Color(0xFF272727)) {
            Log.d("CustomDragHandle", "Button clicked")
        }
    }



}