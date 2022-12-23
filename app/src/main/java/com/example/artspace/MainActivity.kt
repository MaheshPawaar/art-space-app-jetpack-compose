package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.data.Artwork
import com.example.artspace.data.DataSource
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        var index by remember {
            mutableStateOf(0)
        }
        val artwork = DataSource.artworks[index]

        ArtworkWall(artwork = artwork)
        ArtworkDescriptor(artwork = artwork)
        DisplayController(
            onNext = { index = (index + 1) % DataSource.artworks.size },
            onPrevious = {
                index = if (index == 0) DataSource.artworks.size - 1 else index - 1
            }
        )
    }
}

@Composable
fun ArtworkWall(
    artwork: Artwork
) {
    val image = painterResource(id = artwork.imageId)
    Surface(
        elevation = 16.dp,
    ) {
        Image(
            painter = image,
            contentDescription = artwork.title,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = Color.Gray,
                )
                .padding(32.dp)
        )
    }
}

@Composable
fun ArtworkDescriptor(artwork: Artwork) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 32.dp),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = artwork.title,
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(artwork.artist)
                    }
                    append(" (")
                    append("${artwork.year}")
                    append(")")
                }
            )
        }
    }
}

@Composable
fun DisplayController(onPrevious: () -> Unit, onNext: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 32.dp)
    ) {
        Button(
            onClick = onPrevious,
            content = { Text(text = "Previous") },
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        )
        Button(
            onClick = onNext,
            content = { Text(text = "Next") },
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}