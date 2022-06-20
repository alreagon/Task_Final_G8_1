package com.example.challengechapter8binar.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.challengechapter8binar.R
import com.example.challengechapter8binar.model.RequestUser
import com.example.challengechapter8binar.view.ui.theme.ChallengeChapter8BinarTheme
import com.example.challengechapter8binar.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeChapter8BinarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DisplayRegisterUserInterface()
                }
            }
        }
    }
}

@Composable
fun DisplayRegisterUserInterface() {
    val mContext = LocalContext.current
    val mDay: Int
    val mYear: Int
    val mMonth: Int
    val mCalendar = Calendar.getInstance()
    val userViewModel = viewModel(modelClass = UserViewModel::class.java)

    //current year
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        var username by remember {
            mutableStateOf("")
        }
        var password by rememberSaveable {
            mutableStateOf("")
        }
        var email by remember {
            mutableStateOf("")
        }
        var konfirmasiPassword by rememberSaveable {
            mutableStateOf("")
        }
        var passwordVisible by rememberSaveable {
            mutableStateOf(false)
        }
        var konfirmasiPasswordVisible by rememberSaveable {
            mutableStateOf(false)
        }
        var name by remember {
            mutableStateOf("")
        }
        val tanggalLahir = remember {
            mutableStateOf(TextFieldValue())
        }
        var alamat by remember {
            mutableStateOf("")
        }
        val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                tanggalLahir.value = TextFieldValue("$mDayOfMonth/${mMonth + 1}/$mYear")
            }, mYear, mMonth, mDay
        )

        Image(
            painter = painterResource(id = R.drawable.person_icon),
            contentDescription = "img",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .border(5.dp, Color.Gray, CircleShape)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.padding(15.dp))
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Input nama") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )
        TextField(
            value = alamat,
            onValueChange = { alamat = it },
            label = { Text(text = "Input alamat") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )
        ReadonlyTextField(
            value = tanggalLahir.value,
            onValueChange = { tanggalLahir.value = it },
            onClick = { mDatePickerDialog.show() },
            label = { Text(text = "Pilih tanggal lahir") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Input username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )
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
        TextField(
            value = konfirmasiPassword,
            onValueChange = { konfirmasiPassword = it },
            label = { Text(text = "Input konfirmasi password") },
            singleLine = true,
            visualTransformation = if (konfirmasiPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (konfirmasiPasswordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description =
                    if (konfirmasiPasswordVisible) "Hide password" else "Show password"

                IconButton(onClick = { konfirmasiPasswordVisible = !konfirmasiPasswordVisible }) {
                    Icon(imageVector = image, description)
                }
            }
        )

        Button(
            onClick = {
                if (alamat.isNotEmpty() &&
                    email.isNotEmpty() &&
                    name.isNotEmpty() &&
                    password.isNotEmpty() &&
                    tanggalLahir.value.text.isNotEmpty() &&
                    username.isNotEmpty() &&
                    konfirmasiPassword.isNotEmpty()
                ) {
                    userViewModel.insertNewUser(
                        RequestUser(
                            alamat,
                            email,
                            "http://placeimg.com/640/480",
                            name,
                            password,
                            tanggalLahir.value.text,
                            username
                        )
                    )
                    Toast.makeText(mContext, "Register berhasil", Toast.LENGTH_SHORT).show()
                    mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                } else {
                    Toast.makeText(mContext, "Mohon isi semua field", Toast.LENGTH_SHORT).show()
                }

            }, modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "REGISTER")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview3() {
    ChallengeChapter8BinarTheme {
        DisplayRegisterUserInterface()
    }
}

@Composable
fun ReadonlyTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: @Composable () -> Unit
) {
    Box {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            label = label
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = onClick),
        )
    }
}