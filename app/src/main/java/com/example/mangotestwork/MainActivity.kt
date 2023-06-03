package com.example.mangotestwork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mangotestwork.core.presentation.Screens
import com.example.mangotestwork.core.presentation.SharedViewModelProfile
import com.example.mangotestwork.feature_edit_profile.presentation.EditProfileScreen
import com.example.mangotestwork.feature_edit_profile.presentation.EditProfileState
import com.example.mangotestwork.feature_profile.presentation.ProfileScreen
import com.example.mangotestwork.feature_registration.presentation.RegistrationScreen
import com.example.mangotestwork.feature_registration.presentation.RegistrationViewModel
import com.example.mangotestwork.feauture_authorization.presentation.AuthScreen
import com.example.mangotestwork.feauture_authorization.presentation.AuthViewModel
import com.example.mangotestwork.ui.theme.MangoTestWorkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MangoTestWorkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val sharedViewModelProfile: SharedViewModelProfile by viewModels()
                    val profileState by sharedViewModelProfile.profileState.collectAsStateWithLifecycle()
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Profile.route
                    ) {
                        composable(Screens.Login.route) {
                            val viewModel: AuthViewModel = hiltViewModel()
                            val authState by viewModel.authState.collectAsStateWithLifecycle()
                            AuthScreen(
                                authState = authState,
                                navigateToRegistrationScreen = { arg ->
                                    navController.navigate(Screens.Registration.route + "/$arg")
                                },
                                navigateToProfile = {
                                    navController.navigate(Screens.Profile.route)
                                },
                                onCheckAuthCode = { phone, code, context ->
                                    viewModel.checkAuthCode(phone, code, context)
                                },
                                onSendAuthCode = { phone ->
                                    viewModel.sendAuthCode(phone)
                                }
                            )
                        }
                        composable(
                            Screens.Registration.route + "/{phoneNumber}",
                            arguments = listOf(navArgument("phoneNumber") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val registrationViewModel: RegistrationViewModel = hiltViewModel()
                            val registrationState by registrationViewModel.registerState.collectAsStateWithLifecycle()
                            RegistrationScreen(
                                registerState = registrationState,
                                phoneNumber = backStackEntry.arguments?.getString("phoneNumber"),
                                navigateToProfile = {
                                    navController.navigate(Screens.Profile.route) {
                                        popUpTo(Screens.Profile.route)
                                    }
                                },
                                registerUser = { phone, name, userName, context ->
                                    registrationViewModel.registerUser(
                                        phone,
                                        name,
                                        userName,
                                        context
                                    )
                                }
                            )
                        }
                        composable(Screens.Profile.route) {
                            ProfileScreen(
                                profileState = profileState,
                                getUserProfile = { sharedViewModelProfile.getUserProfile() },
                                navigateToEditProfile = { navController.navigate(Screens.EditProfile.route) }
                            )
                        }
                        composable(Screens.EditProfile.route) {
                            val editProfileState by sharedViewModelProfile.editProfileState.collectAsStateWithLifecycle(
                                EditProfileState.Idle
                            )
                            EditProfileScreen(
                                editState = editProfileState,
                                profileState = profileState,
                                updateProfile = { id, updateProfileRequest ->
                                    sharedViewModelProfile.updateProfile(
                                        id = id,
                                        updateProfileRequest = updateProfileRequest
                                    )
                                }
                            )
                        }
                    }

                }
            }
        }
    }
}
