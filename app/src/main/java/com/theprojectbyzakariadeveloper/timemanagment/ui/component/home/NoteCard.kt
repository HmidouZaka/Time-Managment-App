@file:OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)

package com.theprojectbyzakariadeveloper.timemanagment.ui.component.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theprojectbyzakariadeveloper.timemanagment.R
import com.theprojectbyzakariadeveloper.timemanagment.model.Task
import com.theprojectbyzakariadeveloper.timemanagment.viewmodel.MainViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskCard(note: Task, viewModel: MainViewModel, edit: (Task) -> Unit) {
    val isShowMore = rememberSaveable {
        mutableStateOf(false)
    }
    val isEditOrDeleteVisible = rememberSaveable {
        mutableStateOf(false)
    }
    val ifColorOfTask = if (isSystemInDarkTheme()) MaterialTheme.colors.surface else Color(
        0xFFD6F2FF
    )

    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .animateContentSize(),
        elevation = 3.dp,
        shape = CutCornerShape(10.dp),
        backgroundColor = ifColorOfTask,
        onClick = {
            isShowMore.value = !isShowMore.value
        }
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp, horizontal = 8.dp)
        ) {
            if (note.category.isNotEmpty()) {
                Text(text = note.category, modifier = Modifier.weight(1f))
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "more",
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        isEditOrDeleteVisible.value = !isEditOrDeleteVisible.value
                    }
            )
        }

        AnimatedVisibility(
            visible = !isEditOrDeleteVisible.value,
            enter = slideInVertically() + scaleIn()
        ) {
            CardContent(note) { isShowMore.value }
        }
        AnimatedVisibility(
            visible = isEditOrDeleteVisible.value,
            enter = slideInVertically(initialOffsetY = { it / 2 }) + scaleIn()
        ) {
            CardActions(task = note, {
                viewModel.delete(it)
            }, edit)
        }


    }
}

@Composable
fun CardActions(task: Task, delete: (Task) -> Unit, edit: (Task) -> Unit) {
    Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.confused),
            contentDescription = "Chose",
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 3.dp)
                .fillMaxHeight()
                .weight(1f),
            contentScale = ContentScale.FillHeight,
        )
        Box(
            modifier = Modifier
                .height(70.dp)
                .weight(0.5f)
                .clip(CircleShape)
                .padding(5.dp)
                .background(Color(0xFF00C00A))
                .clickable { edit(task) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "more",
                modifier = Modifier
                    .size(20.dp), tint = Color.White
            )
        }
        Box(
            modifier = Modifier
                .height(70.dp)
                .weight(0.5f)
                .clip(CircleShape)
                .padding(5.dp)
                .background(Color(0xFFF06969))
                .clickable { delete(task) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "more",
                modifier = Modifier
                    .size(20.dp), tint = Color.White
            )
        }
    }
}


@Composable
fun CardContent(task: Task, isShowMore: () -> Boolean) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxSize()
    ) {
        Text(
            text = task.task,
            maxLines = if (!isShowMore()) 5 else Int.MAX_VALUE,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight(400),
            fontSize = 16.sp,
            fontFamily = MaterialTheme.typography.h1.fontFamily,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis
        )

        if (task.time.isNotEmpty() || task.date.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (task.time.isNotEmpty()) {
                    val time = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colors.onBackground,
                                fontSize = 15.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight(600)
                            )
                        ) {
                            append("Time :")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colors.onBackground,
                                fontSize = 14.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight(400)
                            )
                        ) {
                            append(task.time)
                        }
                    }
                    Text(text = time, modifier = Modifier.padding(5.dp))

                }
                if (task.date.isNotEmpty()) {
                    val date = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colors.onBackground,
                                fontSize = 15.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight(600)
                            )
                        ) {
                            append("Date :")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colors.onBackground,
                                fontSize = 14.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight(400)
                            )
                        ) {
                            append(task.date)
                        }
                    }
                    Text(text = date, modifier = Modifier.padding(5.dp))
                }


            }
        }

    }
}