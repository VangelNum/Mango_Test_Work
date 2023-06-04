package com.example.mangotestwork.feature_edit_profile.presentation

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.mangotestwork.R
import com.example.mangotestwork.feature_edit_profile.data.model.AvatarData
import com.example.mangotestwork.feature_edit_profile.data.model.UpdateProfileRequest
import com.example.mangotestwork.feature_profile.data.model.ProfileResponse
import com.example.mangotestwork.feature_profile.data.model.UserProfileResponse
import com.example.mangotestwork.feature_profile.presentation.ProfileState
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.io.File


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    editState: EditProfileState,
    profileState: ProfileState,
    updateProfile: (id: Int, updateProfileRequest: UpdateProfileRequest) -> Unit,
) {
    val context = LocalContext.current
    var galleryUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            galleryUri = uri
        }
    )

    if (editState is EditProfileState.Loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    LaunchedEffect(editState) {
        when (editState) {
            is EditProfileState.Error -> {
                showToast(editState.errorMessage, context)
            }

            EditProfileState.Success -> {
                showToast("Success update Profile", context)
            }

            else -> Unit
        }
    }

    if (profileState is ProfileState.Success) {
        val userProfile = profileState.userProfile.profileData
        val birthday = remember {
            mutableStateOf(userProfile.birthday)
        }
        val (city, setCity) = remember { mutableStateOf(userProfile.city) }
        val (vk, setVk) = remember { mutableStateOf(userProfile.vk) }
        val (instagram, setInstagram) = remember { mutableStateOf(userProfile.instagram) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (userProfile.avatars?.avatar != null && galleryUri == null) {
                val serverBaseUrl = "https://plannerok.ru/"
                Card(
                    modifier = Modifier.clickable {
                        pickMedia(context, launcher = launcher)
                    },
                    shape = RoundedCornerShape(15.dp),
                ) {
                    SubcomposeAsyncImage(
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
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(150.dp)
                    )
                }
            } else {
                if (galleryUri == null) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .clickable {
                                pickMedia(context, launcher = launcher)
                            }
                    )
                } else {
                    Card(
                        modifier = Modifier
                            .size(150.dp)
                            .clickable {
                                pickMedia(context, launcher = launcher)
                            },
                        shape = RoundedCornerShape(15.dp),
                    ) {
                        SubcomposeAsyncImage(
                            model = galleryUri,
                            loading = {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    CircularProgressIndicator()
                                }
                            },
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            userProfile.name?.let { Text(text = "Name: $it") }
            userProfile.username?.let { Text(text = "UserName: $it") }

            val calenderState = rememberSheetState()
            CalendarDialog(config = CalendarConfig(
                monthSelection = true,
                yearSelection = true,
            ), state = calenderState, selection = CalendarSelection.Date { date ->
                birthday.value = date.toString()
            })

            OutlinedTextField(
                value = birthday.value ?: "2000-01-01",
                onValueChange = {
                    birthday.value = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text(text = "Birthday") },
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = {
                        calenderState.show()
                    }) {
                        Icon(Icons.Default.DateRange, contentDescription = null)
                    }
                }
            )
            OutlinedTextField(
                value = city ?: "Moscow",
                onValueChange = setCity,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "City") },
                singleLine = true
            )
            OutlinedTextField(
                value = vk ?: "https://vk.com/",
                onValueChange = setVk,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "VK") },
                singleLine = true
            )
            OutlinedTextField(
                value = instagram ?: "https://instagram.com/",
                onValueChange = setInstagram,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Instagram") },
                singleLine = true
            )
            Button(
                onClick = {
                    val filePath = galleryUri?.let { uri ->
                        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                            val columnIndex =
                                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                            cursor.moveToFirst()
                            cursor.getString(columnIndex)
                        }
                    }
                    val file = filePath?.let { File(it) }
                    val fileBytes = file?.readBytes()
                    if (fileBytes != null) {
                        val base64String = Base64.encodeToString(fileBytes, Base64.DEFAULT)
                        updateProfile(
                            userProfile.id,
                            UpdateProfileRequest(
                                name = userProfile.name,
                                username = userProfile.username,
                                birthday = birthday.value,
                                city = city,
                                vk = vk,
                                instagram = instagram,
                                avatar = AvatarData(
                                    filename = "example_file_name",
                                    base_64 = base64String
                                )
                            )
                        )
                    } else {
                        updateProfile(
                            userProfile.id,
                            UpdateProfileRequest(
                                name = userProfile.name,
                                username = userProfile.username,
                                birthday = birthday.value,
                                city = city,
                                vk = vk,
                                instagram = instagram,
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = stringResource(R.string.save_profile_changes))
            }
        }
    }

}


fun showToast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun pickMedia(
    context: Context,
    launcher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>
) {
    try {
        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    } catch (e: Exception) {
        Toast
            .makeText(context, e.message.toString(), Toast.LENGTH_SHORT)
            .show()
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewEditProfile() {
    EditProfileScreen(
        editState = EditProfileState.Error("error message"),
        profileState = ProfileState.Success(
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
        updateProfile = { id, updateProfileRequest ->

        }
    )
}