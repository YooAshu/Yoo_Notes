package components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild

@Composable
fun ThemeBottomSheet(hazeState: HazeState) {
    val topItems = List(10) { "Item ${it + 1}" }
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
            items(topItems.size) {
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(5.dp)
                        .background(Color(0xFF272727), RoundedCornerShape(5.dp)),
                    contentAlignment = Alignment.Center

                ) {
                    Text(text = topItems[it], color = Color.White)
                }
            }
        }
    }


}
