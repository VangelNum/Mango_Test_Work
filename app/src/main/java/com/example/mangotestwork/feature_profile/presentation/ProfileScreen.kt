package com.example.mangotestwork.feature_profile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.mangotestwork.R
import com.example.mangotestwork.feature_profile.data.model.ProfileResponse
import com.example.mangotestwork.feature_profile.data.model.UserProfileResponse

@Composable
fun ProfileScreen(
    profileState: ProfileState,
    getUserProfile: () -> Unit,
    navigateToEditProfile: () -> Unit
) {
    when (profileState) {
        is ProfileState.Success -> {
            val userProfile = profileState.userProfile.profileData
            val profileFields = listOf(
                R.string.phone_number to userProfile.phone,
                R.string.name to userProfile.name,
                R.string.user_name to userProfile.username,
                R.string.city to userProfile.city,
                R.string.birthday to userProfile.birthday,
                R.string.vk to userProfile.vk,
                R.string.instagram to userProfile.instagram,
            ).filter { (_, value) -> value != null }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                item {
                    if (userProfile.avatars?.avatar?.isNotEmpty() == true) {
                        val serverBaseUrl = "https://plannerok.ru/"
                        Card(
                            shape = RoundedCornerShape(15.dp),
                        ) {
                            SubcomposeAsyncImage(
                                modifier = Modifier.size(150.dp),
                                model = serverBaseUrl + userProfile.avatars.avatar,
                                loading = {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                },
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = null,
                            modifier = Modifier
                                .size(150.dp)
                                .padding(top = 16.dp)
                        )
                    }
                }
                items(profileFields) { (label, value) ->
                    value?.let {
                        Text(text = "${stringResource(id = label)}: $it")
                    }
                }
                item {
                    Button(
                        onClick = { navigateToEditProfile() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(text = stringResource(id = R.string.edit_profile))
                    }
                }
            }
        }

        is ProfileState.Error -> {
            Text(stringResource(id = R.string.error_loading_profile))
        }

        ProfileState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        ProfileState.Idle -> {
            getUserProfile()
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewProfile() {
    ProfileScreen(
        ProfileState.Success(
            userProfile = ProfileResponse(
                UserProfileResponse(
                    "my name",
                    "my username",
                    "01.06.2002",
                    city = "Moscow",
                    vk = "vk.com",
                    instagram = "instagram.com",
                    status = null,
                    avatar = null,
                    id = 0,
                    last = null,
                    online = false,
                    created = null,
                    phone = "+79999999999",
                    completedTask = null,
                    avatars = null
                )
            )
        ),
        getUserProfile = {

        },
        navigateToEditProfile = {

        }
    )
}
