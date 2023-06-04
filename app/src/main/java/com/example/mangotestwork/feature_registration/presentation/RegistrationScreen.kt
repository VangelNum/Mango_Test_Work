package com.example.mangotestwork.feature_registration.presentation

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mangotestwork.R
import com.example.mangotestwork.core.presentation.Screens

@Composable
fun RegistrationScreen(
    registerState: RegisterState,
    phoneNumber: String?,
    navigateToProfile: () -> Unit,
    registerUser: (phone: String, name: String, userName: String, context: Context) -> Unit
) {
    val nameState = remember { mutableStateOf("") }
    val usernameState = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.register), style = MaterialTheme.typography.titleLarge)
        Text(stringResource(id = R.string.phone_number) + ":" + "$phoneNumber")
        OutlinedTextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = { Text(stringResource(id = R.string.name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )

        OutlinedTextField(
            value = usernameState.value,
            onValueChange = { newValue ->
                // Проверка на допустимые символы
                if (newValue.matches(Regex("[A-Za-z0-9\\-_]*"))) {
                    usernameState.value = newValue
                }
            },
            label = { Text(stringResource(id = R.string.user_name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )
        Button(
            onClick = {
                if (phoneNumber != null) {
                    registerUser(
                        phoneNumber,
                        nameState.value,
                        usernameState.value,
                        context
                    )
                }
            },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth().height(60.dp).padding(start = 16.dp, end = 16.dp)
        ) {
            Text(stringResource(id = R.string.confirm_register))
        }
        Text(text = stringResource(id = R.string.user_name_can_be_with))
        Text(text = stringResource(id = R.string.only_lat_AZ))
        Text(text = stringResource(id = R.string.only_lat_az))
        Text(text = stringResource(id = R.string.only_numbers09))
        Text(text = stringResource(id = R.string.only_symbols))

        when (registerState) {
            is RegisterState.Error -> {
                LaunchedEffect(key1 = Unit) {
                    Toast.makeText(
                        context,
                        (registerState as RegisterState.Error).message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            is RegisterState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            RegisterState.Idle -> Unit
            is RegisterState.Success -> {
                LaunchedEffect(key1 = Unit) {
                    val sharedPreferences: SharedPreferences = context.getSharedPreferences("startRoute", Context.MODE_PRIVATE)
                    sharedPreferences.edit().putString("route", Screens.Profile.route).apply()
                    navigateToProfile()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewRegister() {
    RegistrationScreen(
        registerState = RegisterState.Success("", "", ""),
        phoneNumber = "+79999999999",
        navigateToProfile = { /*TODO*/ },
        registerUser = { phone, name, userName, context ->

        }
    )
}