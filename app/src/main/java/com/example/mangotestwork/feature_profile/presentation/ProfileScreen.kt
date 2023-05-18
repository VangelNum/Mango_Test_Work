package com.example.mangotestwork.feature_profile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel) {
    val loadingState by profileViewModel.loadingState.collectAsState()
    val profileState by profileViewModel.profileState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (loadingState) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            when (profileState) {
                is ProfileState.Success -> {
                    val userProfile = (profileState as ProfileState.Success).userProfile
                    Text("Аватарка: ${userProfile.avatar}")
                    Text("Номер телефона: ${userProfile.phone}")
                    Text("Никнейм: ${userProfile.nickname}")
                    Text("Город проживания: ${userProfile.city}")
                    Text("Дата рождения: ${userProfile.birthday}")
                    Text("Знак зодиака: ${userProfile.zodiac}")
                    Text("О себе: ${userProfile.about}")
                }
                ProfileState.Error -> {
                    Text("Ошибка при загрузке профиля")
                }
                else -> {
                    Text("Профиль не загружен")
                }
            }
        }
    }


//    val userProfile by profileViewModel.userProfile.collectAsState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(horizontal = 16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        userProfile?.let { profile ->
//            // Отображение аватарки пользователя
//            Image(
//                painter = rememberImagePainter(profile.avatarUrl),
//                contentDescription = "Avatar",
//                modifier = Modifier
//                    .size(100.dp)
//                    .clip(CircleShape)
//                    .padding(vertical = 8.dp)
//            )
//
//            // Отображение данных пользователя
//            Text("Phone: ${profile.phone}", modifier = Modifier.padding(vertical = 4.dp))
//            Text("Username: ${profile.username}", modifier = Modifier.padding(vertical = 4.dp))
//            Text("City: ${profile.city}", modifier = Modifier.padding(vertical = 4.dp))
//            Text("Date of Birth: ${profile.dateOfBirth}", modifier = Modifier.padding(vertical = 4.dp))
//            Text("Zodiac Sign: ${profile.zodiacSign}", modifier = Modifier.padding(vertical = 4.dp))
//            Text("About Me: ${profile.about}", modifier = Modifier.padding(vertical = 4.dp))
//        }
//    }
}