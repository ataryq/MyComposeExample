package com.example.mycomposeexample

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mycomposeexample.ui.theme.MyComposeExampleTheme
import com.example.mycomposeexample.ui.theme.lightGreen
import com.example.mycomposeexample.ui.theme.lightRed
import kotlin.math.max
import kotlin.math.min

data class UserInfo constructor(var name: String = "Jhon Dou",
                    var isOnline: Boolean = false,
                    var imageId: Int = R.drawable.photo_small,
                    var hobbies: String = "playing computer games, watching anime, playing football")

val users = arrayListOf(UserInfo(), UserInfo(),
    UserInfo("Igor K", true), UserInfo(), UserInfo(),
    UserInfo(), UserInfo(), UserInfo(),
)

@Composable
fun ProfileDetailedScreen(profileIndex: Int) {
    val userInfo: UserInfo = users[profileIndex]
    var isImageExpanded by remember { mutableStateOf(false) }
    val lazyScrollState = rememberLazyListState()
    val offsetLazyScroll: Float = lazyScrollState.firstVisibleItemScrollOffset / 300f +
            lazyScrollState.firstVisibleItemIndex
    val offsetCoefficient = max(min(1f, 1f - offsetLazyScroll), 0.5f)

    val imageSizeDp: Dp by animateDpAsState(
        targetValue = (if (isImageExpanded) 200.dp else 100.dp) * offsetCoefficient
    )

    println("imageSizeDp: $imageSizeDp")

    Surface(modifier = Modifier.fillMaxSize())
    {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            horizontalAlignment = Alignment.Start
        )
        {
            Surface(elevation = 4.dp) {
                Column(Modifier.padding(4.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    ProfileImage(imageSizeDp, userInfo.isOnline) {
                        isImageExpanded = !isImageExpanded
                    }
                    Text(userInfo.name, style = MaterialTheme.typography.h5)
                    IsUserOnlineText(userInfo.isOnline)
                }
            }

            val listItems = mutableListOf<String>()
            for(i in 0 .. 20) {
                listItems.add("$i")
            }

            LazyColumn(state = lazyScrollState, modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(listItems) { _, item ->
                    Text(item, style = MaterialTheme.typography.h5)
                }
            }
        }
        
    }
}


@Composable
fun ProfilesListScreen(navController: NavHostController? = null) {
    Surface(modifier = Modifier.fillMaxSize())
    {
        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            itemsIndexed(items = users, itemContent = {index, item ->
                CardProfile(item) {
                    navController?.navigate("detailed_user/${index}")
                }
            })
        }
    }
}

@Composable
fun CardProfile(userInfo: UserInfo, onClick: () -> Unit) {

    var isExpanded by remember { mutableStateOf(false) }

    Card(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(align = Alignment.Top)
        .padding(8.dp)
        .clickable { onClick() },
        elevation = 8.dp,
        backgroundColor = Color.White,
        shape = CutCornerShape(topEnd = 24.dp)
    )
    {
        Row(modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
            verticalAlignment = Alignment.CenterVertically)
        {
            ProfileImage(70.dp, userInfo.isOnline)
            Column(modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp)) {
                Text(userInfo.name, style = MaterialTheme.typography.h5)
                IsUserOnlineText(userInfo.isOnline)
                if(isExpanded) {
                    Text("Hobbies: ${userInfo.hobbies}", style = MaterialTheme.typography.body2)
                }
            }

            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End) {
                Image(if(!isExpanded)
                        Icons.Default.KeyboardArrowDown
                    else
                        Icons.Default.KeyboardArrowUp,
                    contentDescription = "expand card",
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clickable { isExpanded = !isExpanded }
                )
            }
        }
    }
}


@Composable
fun IsUserOnlineText(isOnline: Boolean) {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium)
    {
        if(isOnline) {
            Text("Online", style = MaterialTheme.typography.body2)
        }
        else {
            Text("Offline", style = MaterialTheme.typography.body2)
        }
    }
}

@Composable 
fun ProfileImage(size: Dp, isOnline: Boolean, onClick: () -> Unit = {}) {
    val color = if(isOnline) MaterialTheme.colors.lightGreen
        else MaterialTheme.colors.lightRed

    Card(shape = CircleShape,
        border = BorderStroke(2.dp, color),
        modifier = Modifier
            .padding(16.dp)
            .clickable { onClick() },
        elevation = 4.dp
    ) {
        Image(painter = painterResource(id = R.drawable.photo_small),
            contentDescription = "dummy",
            modifier = Modifier.size(size),
            contentScale = ContentScale.Crop)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilesListPreview() {
    MyComposeExampleTheme {
        ProfilesListScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileDetailedPreview() {
    MyComposeExampleTheme {
        ProfileDetailedScreen(0)
    }
}