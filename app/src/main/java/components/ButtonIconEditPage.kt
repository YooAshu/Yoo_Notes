package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ButtonIcon(painter: Int,modifier: Modifier,contentPadding: Dp = 20.dp,bgColor: Color = Color(0xFF161616),onClick : ()->Unit){

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(bgColor),
        contentPadding = PaddingValues(contentPadding),
        modifier = modifier
//            .size(80.dp)
            .padding(5.dp)

    ) {
        Image(
            painter = painterResource(id = painter),
            contentDescription = "Change Font Style",
            modifier = Modifier
                .fillMaxSize()
//                .padding(10.dp)
        )
    }
}