package components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.conditionalBackground(
    isGradient: Int,
    solidColor: Color = Color(0xFFECE3C1),
    gradient: List<Color> = listOf(Color(0xFFECE3C1), Color(0xFFECE3C1)),
    radius: Int
): Modifier {
    return this.then(
        if (isGradient == 1) {
            // Linear gradient when isGradient is true
            background(
                brush = Brush.linearGradient(
                    colors = gradient
                ),
                shape = RoundedCornerShape(radius.dp)
            )
        } else {
            // Solid color when isGradient is false
            background(solidColor, shape = RoundedCornerShape(radius.dp))
        }
    )
}

@Composable
fun ThemeBottomSheet(
    bg: MutableState<Color>,
    bgGradient: MutableState<List<Color>>,
    themesSelectedIndex : MutableState<Int>
) {
    val bgTypes = listOf(
        "Solid Color",
        "Gradient"
    )

    var bgTypeIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth().wrapContentHeight(),
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            items(bgTypes.size) { index ->
                Text(
                    text = bgTypes[index],
                    modifier = Modifier
                        .background(if (bgTypeIndex == index) Color.White else Color.Transparent , shape = RoundedCornerShape(5.dp))
                        .padding(5.dp)
                        .clickable {
                            bgTypeIndex = index
                        }, color = if (bgTypeIndex == index) Color.Black else Color.White
                )
            }
        }



        LazyVerticalGrid(columns = GridCells.Fixed(4)) {

            fun changeTheme(color: Color) {
                bg.value = color
            }

            fun changeGradient(colors: List<Color>) {
                bgGradient.value = colors

            }
            items(if (bgTypeIndex == 0) colors else gradients) { item ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(5.dp)
                        .conditionalBackground(
                            bgTypeIndex,
                            if (bgTypeIndex == 0) item as Color else Color.Transparent,
                            if (bgTypeIndex == 1) item as List<Color> else listOf(
                                Color.Transparent,
                                Color.Transparent
                            ),
                            radius = 5
                        )

                        .clickable {
                            if (bgTypeIndex == 0) {
                                themesSelectedIndex.value=0
//                                bgGradient.value = listOf(Color.Transparent, Color.Transparent)
                                changeTheme(item as Color)
                            }
                            else{
                                themesSelectedIndex.value = 1
//                                bg.value = Color.Transparent
                                changeGradient(item as List<Color>)
                            }
                        },
                    contentAlignment = Alignment.Center

                ) {
                }
            }

        }
    }


}
