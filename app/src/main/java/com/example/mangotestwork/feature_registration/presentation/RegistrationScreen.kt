package com.example.mangotestwork.feature_registration.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(registrationViewModel: RegistrationViewModel, phone: String?) {
    val nameState = remember { mutableStateOf("") }
    val usernameState = remember { mutableStateOf("") }
    val loadingState by registrationViewModel.loadingState.collectAsState()
    val registerState by registrationViewModel.registerState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Регистрация")
        Spacer(modifier = Modifier.height(16.dp))

        Text("Номер телефона: $phone")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = { Text("Имя") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = usernameState.value,
            onValueChange = { usernameState.value = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (phone != null) {
                    registrationViewModel.registerUser(phone, nameState.value, usernameState.value)
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Зарегистрироваться")
        }

        if (loadingState) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}
//    val nameState = remember { mutableStateOf(TextFieldValue()) }
//    val usernameState = remember { mutableStateOf(TextFieldValue()) }
//    val loading by registrationViewModel.loading.collectAsState()
//    val error by registrationViewModel.error.collectAsState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(horizontal = 16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // Показ номера телефона
//        Text("Phone: +79219999999", modifier = Modifier.padding(vertical = 8.dp))
//
//        // Ввод имени пользователя
//        OutlinedTextField(
//            value = nameState.value,
//            onValueChange = { nameState.value = it },
//            label = { Text("Name") },
//            placeholder = { Text("Enter your name") },
//            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
//            maxLines = 1,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//        )
//
//        // Ввод username пользователя
//        OutlinedTextField(
//            value = usernameState.value,
//            onValueChange = { usernameState.value = it },
//            label = { Text("Username") },
//            placeholder = { Text("Enter your username") },
//            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
//            maxLines = 1,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//        )
//
//        if (error != null) {
//            Text(error, color = Color.Red, modifier = Modifier.padding(vertical = 8.dp))
//        }
//
//        Button(
//            onClick = { registrationViewModel.registerUser(nameState.value.text, usernameState.value.text) },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp)
//        ) {
//            if (loading) {
//                CircularProgressIndicator(color = Color.White)
//            } else {
//                Text("Register")
//            }
//        }
//    }
