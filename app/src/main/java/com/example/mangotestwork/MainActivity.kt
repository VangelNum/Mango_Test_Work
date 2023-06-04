package com.example.mangotestwork

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MangoTestWorkTheme {
                val sharedPreferences: SharedPreferences =
                    remember { this.getSharedPreferences("startRoute", Context.MODE_PRIVATE) }
                val startRoute = remember {
                    sharedPreferences.getString("route", Screens.Login.route)
                }
                val navController = rememberNavController()
                val sharedViewModelProfile: SharedViewModelProfile by viewModels()
                val profileState by sharedViewModelProfile.profileState.collectAsStateWithLifecycle()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val drawerState = rememberDrawerState(DrawerValue.Closed)
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination?.route
                    val items: List<DrawerItems> =
                        if (currentDestination == Screens.Login.route || currentDestination == Screens.Registration.route) {
                            listOf(
                                DrawerItems.Share
                            )
                        } else listOf(
                            DrawerItems.Logout,
                            DrawerItems.Share
                        )
                    val scope = rememberCoroutineScope()
                    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
                        ModalDrawerSheet {
                            Card(
                                shape = MaterialTheme.shapes.large,
                                modifier = Modifier.padding(start = 24.dp, top = 24.dp, bottom = 16.dp )
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.mango),
                                    contentDescription = null,
                                    modifier = Modifier.size(150.dp)
                                )
                            }
                            Text(
                                text = stringResource(id = R.string.app_name),
                                modifier = Modifier.padding(start = 24.dp, bottom = 16.dp)
                            )
                            Divider()
                            items.forEach { item ->
                                NavigationDrawerItem(
                                    icon = { Icon(item.icon, contentDescription = null) },
                                    label = { Text(stringResource(id = item.title)) },
                                    selected = false,
                                    onClick = {
                                        when (item) {
                                            DrawerItems.Logout -> {
                                                sharedViewModelProfile.deleteProfile()
                                                sharedPreferences.edit().putString("route", Screens.Login.route).apply()
                                                navController.navigate(Screens.Login.route) {
                                                    popUpTo(0) { inclusive = true }
                                                }
                                            }

                                            DrawerItems.Share -> {
                                                //Todo
                                            }
                                        }
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                )
                            }
                        }
                    }) {
                        Scaffold(topBar = {
                            CenterAlignedTopAppBar(title = {
                                Text(text = stringResource(id = R.string.mango_test))
                            }, navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = null
                                    )
                                }
                            })
                        }) {
                            NavHost(
                                navController = navController,
                                startDestination = startRoute ?: Screens.Login.route,
                                modifier = Modifier.padding(it)
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
                                        },
                                    )
                                }
                                composable(
                                    Screens.Registration.route + "/{phoneNumber}",
                                    arguments = listOf(navArgument("phoneNumber") {
                                        type = NavType.StringType
                                    })
                                ) { backStackEntry ->
                                    val registrationViewModel: RegistrationViewModel =
                                        hiltViewModel()
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
    }
}

sealed class DrawerItems(@StringRes val title: Int, val icon: ImageVector) {
    object Logout : DrawerItems(R.string.logout, Icons.Default.ExitToApp)
    object Share : DrawerItems(R.string.share, Icons.Default.Share)
}
