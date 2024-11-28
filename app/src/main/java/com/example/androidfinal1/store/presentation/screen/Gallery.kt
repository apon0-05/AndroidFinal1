package com.example.androidfinal1.store.presentation.screen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.androidfinal1.R
import com.example.androidfinal1.store.data.remote.ImageItem
import com.example.androidfinal1.store.presentation.components.GalleryTopBar
import com.example.androidfinal1.store.presentation.viewmodel.MoviesViewModel
import com.example.androidfinal1.store.presentation.viewmodel.ScreenState


@Composable
fun Gallery(movieId: Int?, navController: NavController) {
    val viewModel: MoviesViewModel = viewModel()
    LaunchedEffect(movieId) {
        movieId?.let {
            viewModel.getFilmImages(movieId)
        }
    }

    val state = viewModel.imagesState.collectAsState()
    when(val currentState = state.value ){
        is ScreenState.ImageLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
        is ScreenState.ImageSuccess -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    GalleryTopBar(navController)

                    GalleryGrid(images = currentState.images)

                    Spacer(modifier = Modifier.weight(1f))

                    GalleryBottomNav()
                }
            }
        }
        is ScreenState.ImageError -> {
            androidx.compose.material3.Text(text = currentState.message, color = Color.Gray)
        }
        else -> Unit
    }

}

@Composable
fun GalleryGrid(images: List<ImageItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(images.chunked(3)) { chunk ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    chunk.take(2).forEach { image ->
                        Image(
                            painter = rememberImagePainter(image.previewUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .height(150.dp)
                                .clip(RoundedCornerShape(4.dp))
                        )
                    }
                }
                if (chunk.size == 3) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = rememberImagePainter(chunk[2].previewUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                }
            }
        }
    }
}


@Composable
private fun GalleryBottomNav() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
//            Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(24.dp))
//            Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(24.dp))
//            Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(24.dp))
        }
    }
}