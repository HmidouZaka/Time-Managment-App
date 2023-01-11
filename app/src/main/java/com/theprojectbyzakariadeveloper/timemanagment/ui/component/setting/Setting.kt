package com.theprojectbyzakariadeveloper.timemanagment.ui.component.setting

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.theprojectbyzakariadeveloper.timemanagment.model.Category
import com.theprojectbyzakariadeveloper.timemanagment.ui.component.detaile.ToopBarBack
import com.theprojectbyzakariadeveloper.timemanagment.viewmodel.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ToopBarBack(
                navController = navHostController,
                modifier = Modifier.fillMaxWidth(),
                title = "Setting"
            )
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            RowSetting(viewModel)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.primaryVariant)
            )
        }
    }
}


@Composable
fun RowSetting(
    viewModel: MainViewModel
) {
    val showMore = rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        Modifier
            .animateContentSize(tween(500))
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .clickable {
                    showMore.value = !showMore.value
                }
                .padding(horizontal = 15.dp, vertical = 25.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "All Category",
                modifier = Modifier
                    .weight(1f)

            )
            Icon(
                imageVector = if (showMore.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "show more",
                tint = MaterialTheme.colors.onBackground
            )
        }
        if (showMore.value)
            ListOfCategory(viewModel)
    }
}

@Composable
fun ListOfCategory(viewModel: MainViewModel) {
    val list = viewModel.categories.observeAsState(emptyList())
    val count = LocalConfiguration.current.orientation + 1
    LazyVerticalGrid(
        columns = GridCells.Fixed(count),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(list.value) { categoryItem ->
            CardCategory(category = categoryItem){
                viewModel.delete(it)
            }
        }
    }
}

@Composable
fun CardCategory(
    category: Category,
    deleteCategory:(Category)->Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(MaterialTheme.colors.onPrimary, CircleShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = category.id.toString(), modifier = Modifier.padding(8.dp), fontFamily = FontFamily.Serif, fontSize = 18.sp)
        Text(text = category.name, modifier = Modifier.weight(1f), fontFamily = MaterialTheme.typography.h1.fontFamily, fontSize = 16.sp)
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "show more",
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier.clickable {
                deleteCategory(category)
            }.padding(8.dp)
        )
    }
}

