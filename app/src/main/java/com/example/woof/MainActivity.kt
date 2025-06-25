package com.example.woof

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.woof.data.Dog
import com.example.woof.data.dogs
import com.example.woof.ui.theme.WoofTheme

// color scheme
val OrangePrimary = Color(0xFFFF6F00)
val OrangeSecondary = Color(0xFFFFA040)
val OrangeLight = Color(0xFFFFCC80)
val OrangeDark = Color(0xFFE65100)
val OrangeAccent = Color(0xFFFFAB40)
val OrangeBackground = Color(0xFFFFF3E0)
val OrangeSurface = Color(0xFFFFE0B2)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WoofTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    WoofApp()
                }
            }
        }
    }
}

@Composable
fun WoofApp() {
    var searchQuery by remember { mutableStateOf("") }
    var sortAscending by remember { mutableStateOf(true) }
    var expandedSortMenu by remember { mutableStateOf(false) }

    val displayDogs = dogs
        .map { dog -> dog to stringResource(dog.name) } // hasilkan Pair<Dog, String>
        .filter { (_, name) -> name.contains(searchQuery, ignoreCase = true) }
        .sortedBy { (_, name) -> if (sortAscending) name else name.reversed() }

    Scaffold(
        topBar = {
            WoofTopAppBar()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            OrangeLight.copy(alpha = 0.6f),
                            OrangeBackground,
                            OrangeSurface
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 16.dp)
            ) {
                // Search Box
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search dog..") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(Color.White, shape = MaterialTheme.shapes.medium),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = OrangePrimary,
                        unfocusedBorderColor = OrangeSecondary,
                        focusedLabelColor = OrangePrimary,
                        cursorColor = OrangePrimary
                    )
                )

                // Sort Dropdown
                Box(modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 8.dp)) {
                    Button(onClick = { expandedSortMenu = true }) {
                        Text(if (sortAscending) "Sort A-Z" else "Sort Z-A")
                    }

                    DropdownMenu(
                        expanded = expandedSortMenu,
                        onDismissRequest = { expandedSortMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Sort A-Z") },
                            onClick = {
                                sortAscending = true
                                expandedSortMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Sort Z-A") },
                            onClick = {
                                sortAscending = false
                                expandedSortMenu = false
                            }
                        )
                    }
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(displayDogs) { (dog, _) ->
                        DogItem(
                            dog = dog,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogItem(
    dog: Dog,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .shadow(
                elevation = 12.dp,
                shape = MaterialTheme.shapes.large
            )
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = { expanded = !expanded }
    ) {
        Column {
            // Main content row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                OrangeSurface.copy(alpha = 0.3f),
                                Color.White
                            )
                        )
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Dog image with Orange circular border
                Box {
                    Box(
                        modifier = Modifier
                            .size(84.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(OrangePrimary, OrangeSecondary)
                                ),
                                shape = CircleShape
                            )
                            .padding(2.dp)
                    ) {
                        DogIcon(
                            dogIcon = dog.imageResourceId,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Dog information
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(dog.name),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = OrangeDark
                    )
                    Text(
                        text = stringResource(R.string.years_old, dog.age),
                        style = MaterialTheme.typography.bodyMedium,
                        color = OrangeSecondary
                    )
                }

                // Favorite button
                IconButton(
                    onClick = { isFavorite = !isFavorite }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) OrangePrimary else OrangeLight
                    )
                }

                // Expand button
                IconButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess
                        else Icons.Filled.ExpandMore,
                        contentDescription = if (expanded) "Show less" else "Show more",
                        tint = OrangePrimary
                    )
                }
            }

            // Expanded content
            if (expanded) {
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = OrangeLight.copy(alpha = 0.7f),
                    thickness = 2.dp
                )

                Column(
                    modifier = Modifier
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.White,
                                    OrangeSurface.copy(alpha = 0.2f)
                                )
                            )
                        )
                        .padding(16.dp)
                ) {
                    Text(
                        text = "About ${stringResource(dog.name)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = OrangePrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(dog.hobbies),
                        style = MaterialTheme.typography.bodyMedium,
                        color = OrangeDark.copy(alpha = 0.8f),
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.2
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Fun facts row with Orange theme
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        InfoChip(
                            label = "Umur",
                            value = "${dog.age} tahun"
                        )
                        InfoChip(
                            label = "Status",
                            value = "Anjing Baik"
                        )
                        InfoChip(
                            label = "Energi",
                            value = if (dog.age < 3) "Tinggi" else "Sedang"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoChip(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        color = OrangeAccent.copy(alpha = 0.2f)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = OrangePrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = OrangeDark
            )
        }
    }
}

@Composable
fun WoofTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp),
                    painter = painterResource(R.drawable.ic_woof_logo),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = OrangePrimary,
            titleContentColor = Color.White
        ),
        modifier = modifier
    )
}

@Composable
fun DogIcon(
    @DrawableRes dogIcon: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier,
        contentScale = ContentScale.Crop,
        painter = painterResource(dogIcon),
        contentDescription = null
    )
}

@Preview
@Composable
fun WoofPreview() {
    WoofTheme(darkTheme = false) {
        WoofApp()
    }
}

@Preview
@Composable
fun WoofDarkThemePreview() {
    WoofTheme(darkTheme = true) {
        WoofApp()
    }
}