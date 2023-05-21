package com.example.mangotestwork.feature_edit_profile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberImagePainter
import com.example.mangotestwork.feature_profile.presentation.ProfileState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(editProfileViewModel: EditProfileViewModel) {

      val user = editProfileViewModel.editProfileState.collectAsStateWithLifecycle()
//    val userProfile = (user as EditProfileState.Success)
//    val name = remember { mutableStateOf(TextFieldValue(user?. ?: "")) }
//    val city = remember { mutableStateOf(TextFieldValue(user?.city ?: "")) }
//    val dateOfBirth = remember { mutableStateOf(TextFieldValue(user?.dateOfBirth ?: "")) }
//    val about = remember { mutableStateOf(TextFieldValue(user?.about ?: "")) }
//
//
//    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
//        user?.let { userProfile ->
//            Image(
//                painter = rememberImagePainter(userProfile.avatarUrl),
//                contentDescription = "Avatar",
//                modifier = Modifier.size(100.dp).padding(16.dp).clip(CircleShape)
//            )
//
//            TextField(
//                value = name.value,
//                onValueChange = { name.value = it },
//                modifier = Modifier.padding(16.dp),
//                label = { Text("Name") },
//                singleLine = true
//            )
//
//            TextField(
//                value = city.value,
//                onValueChange = { city.value = it },
//                modifier = Modifier.padding(16.dp),
//                label = { Text("City") },
//                singleLine = true
//            )
//
//            TextField(
//                value = dateOfBirth.value,
//                onValueChange = { dateOfBirth.value = it },
//                modifier = Modifier.padding(16.dp),
//                label = { Text("Date of Birth") },
//                singleLine = true
//            )
//
//            TextField(
//                value = about.value,
//                onValueChange = { about.value = it },
//                modifier = Modifier.padding(16.dp),
//                label = { Text("About Me") },
//                singleLine = true
//            )
//
//            Button(
//                onClick = {
//                    // Сохранить изменения профиля
//                    editProfileViewModel.updateProfile(name.value.text, city.value.text, dateOfBirth.value.text, about.value.text)
//                },
//                modifier = Modifier.padding(16.dp)
//            ) {
//                Text(text = "Save Changes")
//            }
//        }
//
//        if (loading) {
//            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
//        }
//
//        if (!error.isNullOrEmpty()) {
//            Toast.makeText(ContextAmbient.current, error, Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    val nameState = remember { mutableStateOf(TextFieldValue()) }
//    val cityState = remember { mutableStateOf(TextFieldValue()) }
//    val dateOfBirthState = remember { mutableStateOf(TextFieldValue()) }
//    val aboutMeState = remember { mutableStateOf(TextFieldValue()) }
//    val loading by editProfileViewModel.loading.collectAsState()
//    val error by editProfileViewModel.error.collectAsState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(horizontal = 16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
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
//        // Ввод города проживания
//        OutlinedTextField(
//            value = cityState.value,
//            onValueChange = { cityState.value = it },
//            label = { Text("City") },
//            placeholder = { Text("Enter your city") },
//            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
//            maxLines = 1,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//        )
//
//        // Ввод даты рождения
//        OutlinedTextField(
//            value = dateOfBirthState.value,
//            onValueChange = { dateOfBirthState.value = it },
//            label = { Text("Date of Birth") },
//            placeholder = { Text("Enter your date of birth") },
//            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
//            maxLines = 1,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//        )
//
//        // Ввод информации о себе
//        OutlinedTextField(
//            value = aboutMeState.value,
//            onValueChange = { aboutMeState.value = it },
//            label = { Text("About Me") },
//            placeholder = { Text("Enter information about yourself") },
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
//            onClick = {
//                editProfileViewModel.editProfile(
//                    nameState.value.text,
//                    cityState.value.text,
//                    dateOfBirthState.value.text,
//                    aboutMeState.value.text
//                )
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp)
//        ) {
//            if (loading) {
//                CircularProgressIndicator(color = Color.White)
//            } else {
//                Text("Save")
//            }
//        }
//    }
}