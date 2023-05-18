package com.example.mangotestwork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mangotestwork.core.presentation.Screens
import com.example.mangotestwork.feature_edit_profile.presentation.EditProfileScreen
import com.example.mangotestwork.feature_edit_profile.presentation.EditProfileViewModel
import com.example.mangotestwork.feature_profile.presentation.ProfileScreen
import com.example.mangotestwork.feature_profile.presentation.ProfileViewModel
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
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Login.route
                    ) {
                        composable(Screens.Login.route) {
                            val viewModel: AuthViewModel = hiltViewModel()
                            AuthScreen(
                                viewModel,
                                navigateToRegistrationScreen = { arg ->
                                    navController.navigate(Screens.Registration.route + "/$arg")
                                },
                                { navController.navigate(Screens.Profile.route) })
                        }
                        composable(
                            Screens.Registration.route + "/{phoneNumber}",
                            arguments = listOf(navArgument("phoneNumber") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val viewModel: RegistrationViewModel = hiltViewModel()
                            RegistrationScreen(
                                viewModel,
                                backStackEntry.arguments?.getString("phoneNumber"),
                                navigateToProfile = {
                                    navController.navigate(Screens.Profile.route) {
                                        popUpTo(Screens.Profile.route)
                                    }
                                }
                            )
                        }
                        composable(Screens.Profile.route) {
                            val viewModel: ProfileViewModel = hiltViewModel()
                            ProfileScreen(viewModel)
                        }
                        composable(Screens.EditProfile.route) {
                            val viewModel: EditProfileViewModel = hiltViewModel()
                            EditProfileScreen(viewModel)
                        }
                    }
                }
            }
        }
    }
}
