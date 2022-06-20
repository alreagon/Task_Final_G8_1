package com.example.challengechapter8binar.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.challengechapter8binar.R
import com.example.challengechapter8binar.datastore.UserLoginManager
import com.example.challengechapter8binar.model.Movie
import com.example.challengechapter8binar.ui.theme.ChallengeChapter8BinarTheme
import com.example.challengechapter8binar.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChallengeChapter8BinarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModelMovie = viewModel(modelClass = MovieViewModel::class.java)
                    val dataMovie by viewModelMovie.dataMovieState.collectAsState()
                    val mContext = LocalContext.current
                    val userLoginManager = UserLoginManager(mContext)
                    var username by remember {
                        mutableStateOf("")
                    }
                    val isShowAlert = remember { mutableStateOf(false) }

                    userLoginManager.username.asLiveData().observe(this) {
                        username = it
                    }

                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painterResource(id = R.drawable.ic_baseline_person_24),
                                contentDescription = "",
                            )
                            Text(
                                text = "Hello, $username",
                                color = Color.Black,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            Text(
                                text = "LOGOUT",
                                color = Color.Black,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterVertically)
                                    .clickable {
                                        isShowAlert.value = true
                                    }
                            )
                        }
                        LazyVerticalGrid(cells = GridCells.Adaptive(170.dp),
                            contentPadding = PaddingValues(
                                start = 8.dp,
                                top = 12.dp,
                                end = 8.dp,
                                bottom = 12.dp
                            ),
                            content = {
                                if (dataMovie.isEmpty()) {
                                    item {
                                    }
                                } else {
                                    items(dataMovie) {
                                        DisplayFilmList(movie = it)
                                    }
                                }
                            }
                        )
                    }
                    if (isShowAlert.value) {
                        AlertDialogView(state = isShowAlert)
                    }
                }
            }
        }
    }
}

@Composable
fun AlertDialogView(state: MutableState<Boolean>) {
    CommonDialog(title = "Logout", state = state) {
        Text(text = "Anda yakin ingin logout?")
    }
}

@Composable
fun CommonDialog(
    title: String?,
    state: MutableState<Boolean>,
    content: @Composable (() -> Unit)? = null
) {
    val mContext = LocalContext.current
    val userLoginManager = UserLoginManager(mContext)

    AlertDialog(
        onDismissRequest = { state.value = false },
        title = title?.let {
            {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = title)
                    Divider(modifier = Modifier.padding(bottom = 8.dp))
                }
            }
        },
        text = content,
        dismissButton = {
            Button(onClick = {
                state.value = false
            }) {
                Text(text = "Tidak")
            }
        },
        confirmButton = {
            Button(onClick = {
                GlobalScope.launch {
                    userLoginManager.clearDataLogin()
                }
                Toast.makeText(mContext, "Berhasil logout", Toast.LENGTH_SHORT).show()
                state.value = false
                mContext.startActivity(Intent(mContext, LoginActivity::class.java))
            }) {
                Text(text = "Ya")
            }
        }
    )
}

@Composable
fun DisplayFilmList(movie: Movie) {
    val posterBaseUrl = "https://image.tmdb.org/t/p/w500/"
    val mContext = LocalContext.current
    Column(modifier = Modifier.padding(3.dp)) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clickable {
                    val intent = Intent(mContext, DetailActivity::class.java)
                    intent.putExtra("DATAMOVIE", movie)
                    mContext.startActivity(intent)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_card),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.6F
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Image(
                    painter = rememberImagePainter(data = posterBaseUrl + movie.posterPath),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.FillWidth
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = movie.title,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp),
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Score: ${movie.voteAverage}",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ChallengeChapter8BinarTheme {
        DisplayFilmList(
            movie = Movie(
                "overview",
                "title",
                "posterpath",
                "backdroppath",
                "release date",
                1232.3,
                7.89,
                32,
                1089
            )
        )
    }
}