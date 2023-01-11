

package com.theprojectbyzakariadeveloper.timemanagment.ui.component.detaile

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.theprojectbyzakariadeveloper.timemanagment.R
import com.theprojectbyzakariadeveloper.timemanagment.model.Category
import com.theprojectbyzakariadeveloper.timemanagment.model.Task
import com.theprojectbyzakariadeveloper.timemanagment.ui.activity.MainActivity
import com.theprojectbyzakariadeveloper.timemanagment.ui.theme.colorGray
import com.theprojectbyzakariadeveloper.timemanagment.utils.isNull
import com.theprojectbyzakariadeveloper.timemanagment.viewmodel.MainViewModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

private val task = Task(0, "", "", "", "")

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    mainActivity: MainActivity,
    viewModel: MainViewModel,
    navController: NavHostController,
    saveTask: (Task) -> Unit,
    saveCategory: (Category) -> Unit,
    taskEdit: Task? = null
) {
    val colorHelper = if (isSystemInDarkTheme()) colorGray else MaterialTheme.colors.background
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        topBar = {
            val title = if (taskEdit.isNull()) "Add Task" else "Edit Task"
            ToopBarBack(navController, Modifier,title)
        }
    )
    {
        taskEdit?.let {
            task.id = it.id
            task.category = it.category
            task.task = it.task
            task.time = it.time
            task.date = it.date
        }
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 20.dp)
        ) {
            SpaceComponent()
            Header("What is to be Done ?")
            InputEnterTask(mainActivity, Modifier,colorHelper,taskEdit)
            SpaceComponent()
            Header("Date for the Task")
            SelectDate(colorHelper,taskEdit)
            SpaceComponent()
            Header("Time for the Task")
            SelectTime(Modifier,colorHelper,taskEdit)
            SpaceComponent()
            Header("Category for the task")
            CategoryComponent(viewModel, Modifier, saveCategory,colorHelper,taskEdit)
        }
        SaveBtn(navController, Modifier, saveTask,taskEdit)

    }
}


@Composable
fun ToopBarBack(navController: NavHostController, modifier: Modifier,title: String) {
        TopAppBar(
            modifier = modifier
                .fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.background, elevation = 2.dp
        ) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back to home",
                    tint = MaterialTheme.colors.onBackground
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = title, color = MaterialTheme.colors.onBackground, fontSize = 20.sp)
        }
}

@Composable
fun SaveBtn(
    navHostController: NavHostController,
    modifier: Modifier,
    saveTask: (Task) -> Unit,
    taskEdit: Task?
) {
    Box(modifier = modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = {
                if (task.task.isNotEmpty()) {
                    saveTask(task.copy())
                    task.category = ""
                    task.time = ""
                    task.date = ""
                    task.task = ""
                    navHostController.popBackStack()
                } else {
                    Toast.makeText(navHostController.context, "Enter task", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.BottomEnd),
            backgroundColor = MaterialTheme.colors.onSecondary
        ) {
            Icon(
                imageVector = if (taskEdit.isNull()) Icons.Default.Check else Icons.Default.Edit,
                contentDescription = "add an task",
                tint = Color.White
            )
        }
    }
}

@Composable
fun CategoryComponent(
    viewModel: MainViewModel,
    modifier: Modifier,
    saveCategory: (Category) -> Unit,
    colorHelper: Color,
    taskEdit: Task?
) {
    val isDialogShowed = rememberSaveable { mutableStateOf(false) }
    val listOfCategory = viewModel.categories.observeAsState()
    Row(modifier.fillMaxWidth(), verticalAlignment = CenterVertically) {
        SelectCategory({},
            Modifier
                .weight(1f)
                .padding(start = 8.dp),
            listOfCategory.value?.map { it.name } ?: listOf("Default")
        ,colorHelper,taskEdit)
        IconButton(onClick = {
            isDialogShowed.value = true
        }) {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = "back to home",
                modifier = Modifier.size(22.dp)
            )
        }
    }
    if (isDialogShowed.value) {
        ShowDialog({
            isDialogShowed.value = it
        }, {
            val category = Category(0, it.trimEnd().trimStart())
            saveCategory(category)
        },colorHelper)
    }

}

@Composable
fun SelectTime(modifier: Modifier, colorHelper: Color, taskEdit: Task?) {
    val timeState = rememberSheetState()
    val time = rememberSaveable {
        val taskState = if (taskEdit.isNull()) "" else taskEdit!!.time
        mutableStateOf(taskState)
    }



    ClockDialog(
        state = timeState,
        selection = ClockSelection.HoursMinutes(onPositiveClick = { hours: Int, minutes: Int ->
            time.value = "${hours}h ${minutes}min"
            task.time = "${hours}:${minutes}"
        })
    )




    CardTime({ time.value }, {
        CoroutineScope(Dispatchers.Main).launch {
            timeState.show()
        }
    }, R.drawable.timer, "Select time",colorHelper)
}

@Composable
fun SelectDate(colorHelper: Color, taskEdit: Task?) {
    val dateState = rememberSheetState()
    val date = rememberSaveable {
        val taskState = if (taskEdit.isNull()) "" else taskEdit!!.date
        mutableStateOf(taskState)
    }

    CalendarDialog(
        state = dateState,
        selection = CalendarSelection.Date(
            onSelectDate = {
                date.value = "${it.dayOfMonth}-${it.month}-${it.year}"
                task.date = "${it.dayOfMonth}-${it.monthValue}-${it.year}"
            },
        ), config = CalendarConfig()
    )






    CardTime({ date.value }, {
        CoroutineScope(Dispatchers.Main).launch {
            dateState.show()
        }
    }, R.drawable.today, "Select Date",colorHelper=colorHelper)
}

@Composable
fun InputEnterTask(
    mainActivity: MainActivity,
    modifier: Modifier,
    colorHelper: Color,
    taskEdit: Task?
) {
    Row(modifier.fillMaxWidth()) {
        InputTask(
            onTextChange = {
                task.task = it
            }, modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),colorHelper=colorHelper,taskEdit = taskEdit
        )
    }
}


@Composable
fun InputTask(
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    padding: Dp = 8.dp,
    isSingeLine: Boolean = false,
    colorHelper: Color = MaterialTheme.colors.background,
    taskEdit: Task?
) {

    val text = rememberSaveable {
        val taskState = if (taskEdit.isNull()) "" else taskEdit!!.task
        mutableStateOf(taskState)
    }
    val textSelectoreColor =
        TextSelectionColors(
            handleColor = MaterialTheme.colors.primary,
            backgroundColor = MaterialTheme.colors.surface
        )
    CompositionLocalProvider(LocalTextSelectionColors provides textSelectoreColor) {
        OutlinedTextField(
            value = text.value,
            onValueChange = {
                text.value = it
                onTextChange(it)
            },
            maxLines = maxLines, singleLine = isSingeLine,
            modifier = modifier
                .padding(start = padding)
                .animateContentSize(tween(durationMillis = 700)),
            placeholder = { Text(text = "Enter Task Hear") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor =colorHelper,
                cursorColor = Color.Black,
                textColor = MaterialTheme.colors.onBackground,
                focusedIndicatorColor = MaterialTheme.colors.primary
            ),
            trailingIcon = {
                if (text.value.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "clear text",
                        modifier = Modifier.clickable { text.value = "" })
                }
            }
        )
    }
}


@Composable
fun CardTime(data: () -> String, onClick: () -> Unit, icon: Int, hint: String,colorHelper:Color = MaterialTheme.colors.background) {

    Box(
        modifier = Modifier
            .height(65.dp)
            .padding(horizontal = 8.dp)
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .padding(2.dp)
            .clickable { onClick() }
            .background(colorHelper)
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp), verticalAlignment = CenterVertically
        ) {
            Text(
                text = data().ifEmpty { hint },
                modifier = Modifier
                    .align(alignment = CenterVertically)
                    .alignByBaseline(),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "clear text",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun SelectCategory(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    listOfCategory: List<String> = listOf("Default"),
    colorHelper: Color,
    taskEdit: Task?
) {
    val category = rememberSaveable {

        val list = listOfCategory.ifEmpty { listOf("Default") }
        val categoryName = if(taskEdit.isNull()) list[0] else taskEdit!!.category
        mutableStateOf(categoryName)
    }
    task.category = category.value
    val isShowMenu = rememberSaveable {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier
            .height(65.dp)
            .clickable {
                isShowMenu.value = true
                onClick()
            }
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(colorHelper)

    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp), verticalAlignment = CenterVertically
        ) {
            Text(text = category.value)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "clear text",
                modifier = Modifier.size(20.dp)
            )
        }
        DropdownMenu(
            expanded = isShowMenu.value,
            onDismissRequest = { isShowMenu.value = false },
            modifier = Modifier
                .wrapContentWidth()
                .background(MaterialTheme.colors.background)
        ) {
            listOfCategory.forEach {
                DropdownMenuItem(
                    onClick = {
                        category.value = it
                        isShowMenu.value = false
                    },
                    modifier = Modifier
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.onPrimary)
                ) {
                    Text(
                        text = it,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    }
}


@Composable
fun Header(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(horizontal = 8.dp),
        fontSize = 16.sp,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight(500)
    )
}

@Composable
fun SpaceComponent() {
    Spacer(modifier = Modifier.height(30.dp))
}


@Composable
fun ShowDialog(change: (Boolean) -> Unit, onClickAdd: (String) -> Unit,colorHelper:Color = MaterialTheme.colors.background) {
    var categoryName = ""
    Dialog(
        onDismissRequest = { change(false) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 8.dp, vertical = 20.dp)
        ) {
            Text(
                text = "Add Category",
                modifier = Modifier.padding(bottom = 10.dp),
                color = MaterialTheme.colors.onBackground,
                fontSize = 16.sp,
                fontFamily = FontFamily.Serif
            )

            InputTask(
                { categoryName = it },
                Modifier.fillMaxWidth(),
                maxLines = 1,
                padding = 0.dp,
                true, colorHelper = colorHelper, taskEdit = null
            )
            Row(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Button(
                    border = BorderStroke(0.5.dp, MaterialTheme.colors.secondary),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    onClick = { change(false) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White
                    ), shape = CircleShape
                ) {
                    Text(text = "Cancel", color = MaterialTheme.colors.secondary)
                }
                Button(colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.onSecondary
                ), shape = CircleShape,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    onClick = {
                        onClickAdd(categoryName.trim())
                        change(false)
                    }) {
                    Text(text = "Add", color = Color.White)
                }
            }
        }
    }
}











