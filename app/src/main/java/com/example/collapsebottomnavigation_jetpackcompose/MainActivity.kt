package com.example.collapsebottomnavigation_jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collapsebottomnavigation_jetpackcompose.navigation.BottomScreen
import com.example.collapsebottomnavigation_jetpackcompose.navigation.bottomNavigationItems
import com.example.collapsebottomnavigation_jetpackcompose.ui.theme.CollapseBottomNavigationjetpackComposeTheme
import com.example.collapsebottomnavigation_jetpackcompose.ui.theme.Purple500
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CollapseBottomNavigationjetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    BottomCollapse()
                }
            }
        }
    }
}

@Composable
fun BottomCollapse() {
    val bottomBarHeight = 55.dp
    val bottomBarHeightPx = with(LocalDensity.current) {
        bottomBarHeight.roundToPx().toFloat()
    }

    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = bottomBarOffsetHeightPx.value + delta
                bottomBarOffsetHeightPx.value = newOffset.coerceIn(-bottomBarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.nestedScroll(nestedScrollConnection),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Collapse Bottom Navigation",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }, backgroundColor = Purple500,
                elevation = AppBarDefaults.TopAppBarElevation
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(bottomBarHeight)
                    .offset {
                        IntOffset(0, -bottomBarOffsetHeightPx.value.roundToInt())
                    }
            ) {
                AppBottomNavigation(navController, bottomNavigationItems)
            }
        },
        content = { innerPadding ->
            LazyColumn(contentPadding = innerPadding) {
                items(count = 20) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(10.dp, 5.dp, 10.dp, 5.dp)
                            .background(Color.White)
                    ) {

                    }
                }
            }
        }
    )


}


@Composable
fun AppBottomNavigation(
    navController: NavHostController,
    bottomNavigationItems: List<BottomScreen>
) {

}





