package com.example.challengechapter8binar.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.challengechapter8binar.R
import com.example.challengechapter8binar.datastore.UserLoginManager
import com.example.challengechapter8binar.ui.theme.ChallengeChapter8BinarTheme
import com.example.challengechapter8binar.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeChapter8BinarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val mContext = LocalContext.current
                    val userLoginManager = UserLoginManager(mContext)
                    userLoginManager.boolean.asLiveData().observe(this) {
                        if (it == true) {
                            mContext.startActivity(Intent(mContext, MainActivity::class.java))
                        }
                    }
                    DisplayLoginUserInterface()
                }
            }
        }
    }
}

@Composable
fun DisplayLoginUserInterface() {
    val mContext = LocalContext.current
    val viewModelUser = viewModel(modelClass = UserViewModel::class.java)
    val dataUser by viewModelUser.dataUserState.collectAsState()
    val userLoginManager = UserLoginManager(mContext)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var email by remember {
            mutableStateOf("")
        }
        var password by rememberSaveable {
            mutableStateOf("")
        }
        var passwordVisible by rememberSaveable {
            mutableStateOf(false)
        }
        Image(
            painter = painterResource(id = R.drawable.person_icon),
            contentDescription = "img",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .border(5.dp, Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.padding(15.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Input email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Input password") },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            }
        )
        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    for (i in dataUser.indices) {
                        if (email == dataUser[i].email && password == dataUser[i].password) {
                            GlobalScope.launch {
                                userLoginManager.setBoolean(true)
                                userLoginManager.saveDataLogin(
                                    dataUser[i].alamat,
                                    dataUser[i].email,
                                    dataUser[i].name,
                                    dataUser[i].password,
                                    dataUser[i].tanggal_lahir,
                                    dataUser[i].username
                                )
                            }
                            Toast.makeText(mContext, "Login berhasil", Toast.LENGTH_SHORT).show()
                            mContext.startActivity(Intent(mContext, MainActivity::class.java))
                        } else if (i == dataUser.lastIndex && email != dataUser[i].email && password != dataUser[i].password) {
                            Toast.makeText(
                                mContext,
                                "Email/password tidak sesuai",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            },
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(text = "Login")
        }
        Text(
            text = "Register here",
            Modifier.clickable {
                mContext.startActivity(
                    Intent(
                        mContext,
                        RegisterActivity::class.java
                    )
                )
            })
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview2() {
    ChallengeChapter8BinarTheme {
        DisplayLoginUserInterface()
    }
}