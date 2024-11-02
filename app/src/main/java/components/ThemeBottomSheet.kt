package components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ThemeBottomSheet(
    bg:MutableState<Color>
) {
    val topItems = List(10) { "Item ${it + 1}" }

    val colors = listOf(
        Color(0xFFECE3C1),
        Color(0xFFCC5500),
        Color(0xFFC49B2C),
        Color(0xFF556B2F),
        Color(0xFFB04A42),
        Color(0xFFD2B48C),
        Color(0xFF4682B4),
        Color(0xFF3B666A),
        Color(0xFF8D8D8D),
        Color(0xFFC5AFA4)
    )

    Column(modifier = Modifier
        .fillMaxHeight()
//        .hazeChild(hazeState)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(topItems.size) {
                Text(text = topItems[it], modifier = Modifier.padding(10.dp),color = Color.White)
            }
        }


        LazyVerticalGrid(columns = GridCells.Fixed(4)) {

            fun changeTheme(color: Color) {
                bg.value = color
            }

            items(colors) {color->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(5.dp)
                        .background(color, RoundedCornerShape(5.dp))
                        .clickable { changeTheme(color) },
                    contentAlignment = Alignment.Center

                ) {
                }
            }
        }
    }


}
