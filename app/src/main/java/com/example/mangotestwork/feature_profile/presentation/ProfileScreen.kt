package com.example.mangotestwork.feature_profile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.mangotestwork.R

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel, navigateToEditProfile: () -> Unit) {
    val profileState by profileViewModel.profileState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (profileState) {
            is ProfileState.Success -> {
                Spacer(modifier = Modifier.height(16.dp))
                val userProfile = (profileState as ProfileState.Success).userProfile
                if (userProfile.avatar?.isNotEmpty() == true) {
                    AsyncImage(
                        model = userProfile.avatar,
                        contentDescription = stringResource(id = R.string.avatar),
                        modifier = Modifier.size(300.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = stringResource(id = R.string.avatar),
                        modifier = Modifier.size(200.dp)
                    )
                }
                Text("Номер телефона: ${userProfile.phone}")
                Text("Никнейм: ${userProfile.nickname}")
                Text("Город проживания: ${userProfile.city}")
                Text("Дата рождения: ${userProfile.birthday}")
                Text("Знак зодиака: ${userProfile.zodiac}")
                Text("О себе: ${userProfile.about}")
                OutlinedButton(onClick = { navigateToEditProfile() }) {
                    Text(text = stringResource(id = R.string.edit_profile))
                }
            }

            ProfileState.Error -> {
                Text(stringResource(id = R.string.error_loading_profile))
            }

            ProfileState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            ProfileState.Idle -> {
                profileViewModel.getUserProfile()
            }
        }
    }
}