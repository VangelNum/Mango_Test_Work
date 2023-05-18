package com.example.mangotestwork.feature_registration.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mangotestwork.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    registrationViewModel: RegistrationViewModel,
    phoneNumber: String?,
    navigateToProfile: () -> Unit
) {
    val nameState = remember { mutableStateOf("") }
    val usernameState = remember { mutableStateOf("") }
    val registerState by registrationViewModel.registerState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.register), style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Text(stringResource(id = R.string.phone_number) + ":" + "$phoneNumber")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = { Text(stringResource(id = R.string.name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = usernameState.value,
            onValueChange = { usernameState.value = it },
            label = { Text(stringResource(id = R.string.user_name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (phoneNumber != null) {
                    registrationViewModel.registerUser(
                        phoneNumber,
                        nameState.value,
                        usernameState.value
                    )
                }
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 16.dp)
        ) {
            Text(stringResource(id = R.string.confirm_register))
        }
        when (registerState) {
            is RegisterState.Error -> {
                val context = LocalContext.current
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
                    navigateToProfile()
                }
            }
        }
    }
}
