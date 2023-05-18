package com.example.mangotestwork.feauture_authorization.presentation

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mangotestwork.R
import com.example.mangotestwork.feauture_authorization.data.model.AuthState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    navigateToRegistrationScreen: () -> Unit,
    navigateToProfile: () -> Unit
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }
    val phoneState = rememberSaveable { mutableStateOf("") }
    val codeState = rememberSaveable { mutableStateOf("") }
    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.auth))
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = phoneState.value,
            onValueChange = { phoneState.value = it },
            label = { Text(stringResource(id = R.string.phone_number)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(authState == AuthState.CodeSent) {
            OutlinedTextField(
                value = codeState.value,
                onValueChange = { codeState.value = it },
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        if (!showPassword) {
                            Icon(
                                painter = painterResource(id = R.drawable.closeeyes),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.openeyes),
                                contentDescription = null
                            )
                        }
                    }
                },
                label = { Text(stringResource(id = R.string.code_confirmation)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = if (!showPassword) PasswordVisualTransformation() else VisualTransformation.None,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = {
                if (authState == AuthState.CodeSent) {
                    authViewModel.checkAuthCode(phoneState.value, codeState.value)
                } else {
                    authViewModel.sendAuthCode(phoneState.value)
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                if (authState == AuthState.CodeSent) stringResource(id = R.string.enter) else stringResource(
                    id = R.string.code_send
                )
            )
        }

        when (authState) {
            is AuthState.RegistrationRequired ->{
                LaunchedEffect(key1 = Unit) {
                    navigateToRegistrationScreen()
                }
            }
            is AuthState.Authenticated -> {
                LaunchedEffect(key1 = Unit) {
                    navigateToProfile()
                }
            }
            is AuthState.Error -> {
                val context = LocalContext.current
                LaunchedEffect(key1 = (authState as AuthState.Error).message) {
                    Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            is AuthState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            else -> Unit
        }

    }
}