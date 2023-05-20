package com.example.mangotestwork.feature_profile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mangotestwork.R

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel) {
    val profileState by profileViewModel.profileState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

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
                Text(stringResource(id = R.string.error_loading_profile))
            }
            ProfileState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }


        }
    }
}