package com.example.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import components.NoteCard
import components.conditionalBackground
import dev.chrisbanes.haze.haze

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPage(
    navController: NavController,
    allNotes: MutableState<List<Note>>
) {
    var searText = remember { mutableStateOf("") }
    var searchNotes = remember { mutableStateOf(emptyList<Note>()) }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Black)
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .size(50.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.round_arrow_back_ios_new_24),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White, CircleShape)
                            .padding(5.dp),
                        tint = Color.Black

                    )

                }

                TextField(
                    value = searText.value,
                    onValueChange = {
                        searText.value = it
                    },
                    placeholder = {
                        Text(text = "Search Note", color = Color.White)
                    },
                    maxLines = 1,
                    singleLine = true,
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
                    shape = CircleShape,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                searchNotes.value =
                                    allNotes.value.filter {
                                        it.title.contains(searText.value) || it.descText.contains(
                                            searText.value
                                        )
                                    }
                            },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.search_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(5.dp),
                                tint = Color.White
                            )

                        }
                    }
                )


            }
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalItemSpacing = 10.dp,
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                if (searText.value.isNotEmpty())
                    itemsIndexed(searchNotes.value) { index, note ->

                        NoteCard(note = note,
                            onClick = {
                                var allNotesPosition = allNotes.value.indexOf(note)
                                navController.navigate(EditNote(position = allNotesPosition))
                            },
                            onDelete = {
                                val allNotesPosition = allNotes.value.indexOf(note)
                                allNotes.value =
                                    allNotes.value.filterIndexed { i, _ -> i != allNotesPosition }
                                searchNotes.value = allNotes.value.filter {
                                    it.title.contains(searText.value) || it.descText.contains(
                                        searText.value
                                    )
                                }
                            }
                        )

                    }

            }
        }
    }
}