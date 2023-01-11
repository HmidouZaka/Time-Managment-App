package com.theprojectbyzakariadeveloper.timemanagment.ui.component.home

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.theprojectbyzakariadeveloper.timemanagment.R
import com.theprojectbyzakariadeveloper.timemanagment.utils.OptionsMenu
import kotlinx.coroutines.*

@Composable
fun ToolBar(
    ocClickItemMenu: (OptionsMenu) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val targetComponent = rememberSaveable {
        mutableStateOf("toolBar")
    }
    Log.d("lock_recomopsition", "SearchComponent: ToolBar")
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(10.dp),
        backgroundColor = MaterialTheme.colors.background
    ) {
        AnimatedVisibility(
            visible = targetComponent.value == "toolBar",
            enter = slideInHorizontally() + fadeIn()
        ) {
            ToolBarComponent(ocClickItemMenu, {
                targetComponent.value = "search"
            }, modifier)
        }
        AnimatedVisibility(
            visible = targetComponent.value == "search",
            enter = slideInHorizontally() + fadeIn()
        ) {
            SearchComponent(onSearch) {
                targetComponent.value = "toolBar"
            }
        }
    }

}



@Composable
fun SearchComponent(onSearch: (String) -> Unit, hide: () -> Unit) {
    Log.d("lock_recomopsition", "SearchComponent: search")
    var text by remember {
        mutableStateOf("")
    }
    var job:Job? = remember{
        null
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            value = text,
            onValueChange = {
                text = it
                job?.cancel()
                job = MainScope().launch {
                    delay(500)
                    onSearch(text)
                }


            },
            placeholder = { Text(text = "Search") },
            textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
            shape = CutCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFE4E4E4),
                cursorColor = Color.Black,
                disabledIndicatorColor = Color.Unspecified,
                focusedIndicatorColor = Color.Unspecified,
                unfocusedIndicatorColor = Color.Unspecified,
                errorIndicatorColor = Color.Unspecified
            ),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch(text)
            }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "search")
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "search",
                    modifier = Modifier.clickable { hide() })
            },
            maxLines = 1,
            singleLine = true
        )
    }
}

@Composable
fun ToolBarComponent(
    ocClickItemMenu: (OptionsMenu) -> Unit,
    onClickSearch: () -> Unit,
    modifier: Modifier
) {
    Log.d("lock_recomopsition", "SearchComponent: ToolBarComponent")
    var menuIsShow by rememberSaveable {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageLogo()
        Text(
            text = stringResource(id = R.string.app_name),
            color = Color.Black,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 5.dp),
            fontFamily = FontFamily.Cursive,
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )

        IconButton(
            onClick = onClickSearch
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "search")
        }
        IconButton(
            onClick = {
                menuIsShow = true
            }
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_more), contentDescription = "search")
        }
        Box(
            modifier = Modifier.offset(y = ((-40).dp)),
        ) {
            DropdownMenu(
                expanded = menuIsShow,
                onDismissRequest = {
                    menuIsShow = false
                }
            ) {
                DropdownMenuItem(onClick = {
                    ocClickItemMenu(OptionsMenu.SendApp)
                    menuIsShow = false
                }) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "send",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Send App")
                }
                DropdownMenuItem(onClick = {
                    ocClickItemMenu(OptionsMenu.Setting)
                    menuIsShow = false
                }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "send",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Setting")
                }
            }
        }
    }
}

@Composable
private fun ImageLogo() {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "logo",
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center
    )
}

