package com.example.jetpackcomposeshowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeshowcase.ui.theme.Blue
import com.example.jetpackcomposeshowcase.ui.theme.DarkBlue
import com.example.jetpackcomposeshowcase.ui.theme.Grey
import com.example.jetpackcomposeshowcase.ui.theme.JetpackComposeShowcaseTheme
import com.example.jetpackcomposeshowcase.ui.theme.Yellow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val counterColors = mutableStateListOf(
            Yellow,
            Blue,
            DarkBlue
        )

        setContent {
            MyApp {
                MyScreenContent(counterColors)
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    JetpackComposeShowcaseTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colorScheme.background) {
            content()
        }
    }
}

@Composable
fun MyScreenContent(counterColors: SnapshotStateList<Color>) {
    val counterState = remember {
        mutableStateOf(value = 0)
    }
    val selectedCounterColor = remember {
        mutableStateOf(counterColors.first())
    }

    SmallTopAppBar(
        title = {
            Text(
                text = "Jetpack Compose Showcase",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Blue)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CounterValueText(counterState.value, selectedCounterColor.value)
        Counter(counterState.value, updateCount = { newCount ->
            counterState.value = newCount
        })
        CounterColorSelectorText()
        CounterColorSelector(
            counterColors = counterColors,
            selectedCounterColor.value,
            onSelected = { selectedColor ->
                selectedCounterColor.value = selectedColor
            })
    }
}

@Composable
fun CounterValueText(value: Int, color: Color) {
    Text(
        text = value.toString(),
        fontSize = 80.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = if (value == 0) Grey else color
    )
}

@Composable
fun Counter(value: Int, updateCount: (Int) -> Unit) {
    Button(
        onClick = {
            updateCount(value + 1)
        },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp)
            .padding(horizontal = 60.dp, vertical = 20.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = "Click me!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CounterColorSelectorText() {
    Text(
        text = "Select a counter color",
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        color = Grey,
        modifier = Modifier.padding(top = 40.dp)
    )
}

@Composable
fun CounterColorSelector(
    counterColors: List<Color>,
    selectedColor: Color,
    onSelected: (Color) -> Unit
) {
    LazyRow(
        content = {
            items(items = counterColors, itemContent = { color ->
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(color)
                            .clickable {
                                onSelected(color)
                            }
                    )
                    if (selectedColor == color) {
                        Box(modifier = Modifier.height(6.dp))
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(8.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(color = Grey)
                        )
                    }
                }
            })
        },
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(start = 40.dp, top = 12.dp, end = 40.dp)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        MyScreenContent(
            remember {
                mutableStateListOf(
                    Yellow,
                    Blue,
                    DarkBlue
                )
            }
        )
    }
}