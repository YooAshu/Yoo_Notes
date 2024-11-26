package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.notes.R

@Composable
fun DropDownList(modifier: Modifier, selectedIndex: MutableIntState) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    var isDialogShown by remember { mutableStateOf(false) }

    if (isDialogShown) {
        AddCategoryDialog(
            onDismiss = {
                isDialogShown = false
            }, onSubmit = {
                isDialogShown = false
            }
        )
    }


    Box(
        modifier = modifier.padding(vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .width(150.dp)
                .background(Color(0xFF272727), shape = RoundedCornerShape(5.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .clickable {
                    isExpanded = !isExpanded
                },
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(listOfNotesList[selectedIndex.intValue].listName, color = Color.White)
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                contentDescription = "drop down"
            )
        }

        MaterialTheme(
            shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(5.dp))
        ) {

            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {
                    isExpanded = false
                },
                modifier = Modifier.width(150.dp).background(Color(0xFF272727))
            ) {

                listOfNotesList.forEachIndexed { index, notesList ->
                    DropdownMenuItem(
                        text = { Text(notesList.listName,color = if(selectedIndex.intValue == index) Color.Black else Color.White) },
                        onClick = {
                            selectedIndex.intValue = index
                            isExpanded = false
                        },
                        modifier = Modifier.height(30.dp).background(if(selectedIndex.intValue == index) Color.White else Color.Transparent)
                    )
                }
                DropdownMenuItem(
                    text = {Text("+ Add Category",color = Color.White)},
                    onClick = {
                        isDialogShown = true
                    },
                    modifier = Modifier.height(30.dp)
                )

            }

        }

    }

}
