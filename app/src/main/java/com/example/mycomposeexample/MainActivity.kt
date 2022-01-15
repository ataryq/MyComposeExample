package com.example.mycomposeexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mycomposeexample.ui.theme.MyComposeExampleTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen(viewModel)
        }
    }
}

@Composable
fun MainScreen(viewModel: MainActivityViewModel) {
    MyComposeExampleTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "users_list") {
            composable("users_list") {
                Scaffold(topBar = {
                    AppBar("Home", Icons.Default.Home)}) {
                    ProfilesListScreen(navController)
                }
            }

            composable(route = "detailed_user/{profileIndex}",
            arguments = listOf(navArgument("profileIndex") {
                type = NavType.IntType
            })) { navBackStackEntry ->
                Scaffold(topBar = {AppBar("User info", Icons.Default.ArrowBack) {
                    navController.navigateUp()
                } }) {
                    ProfileDetailedScreen(navBackStackEntry.arguments!!.getInt("profileIndex"))
                }
            }
        }
    }
}

@Composable
fun AddedHelloUserScreen(viewModel: MainActivityViewModel) {
    Surface(modifier = Modifier.fillMaxSize())
    {
        val names = remember {
            mutableStateListOf("Goshan", "Anna")
        }
        val inName = viewModel.inputName.observeAsState("")

        Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly)
        {
            for(name in names) {
                Greeting(name)
            }

            TextField(value = inName.value,
                onValueChange = { viewModel.inputName.value = it})

            GreetingButton {
                names.add(viewModel.inputName.value!!)
            }
        }
    }
}

@Composable
fun GreetingButton(onClick: () -> Unit = {}) {
    Button(onClick = onClick,
        modifier = Modifier.wrapContentSize(
            align = Alignment.TopCenter))
    {
        Greeting(name = "button")
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Scaffold(topBar = {AppBar("Home", Icons.Default.Home)}) {
        ProfilesListScreen()
    }
}