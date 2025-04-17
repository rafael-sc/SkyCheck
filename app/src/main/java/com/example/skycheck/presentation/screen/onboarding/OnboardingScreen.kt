package com.example.skycheck.presentation.screen.onboarding

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.skycheck.MainActivity
import com.example.skycheck.R
import com.example.skycheck.presentation.component.onboarding.OnboardingPermissionComponent
import com.example.skycheck.presentation.component.onboarding.OnboardingStartButton
import com.example.skycheck.presentation.route.CurrentLocation
import com.example.skycheck.presentation.screen.current_location.CurrentLocationViewModel
import com.example.skycheck.presentation.theme.ColorBackground
import com.example.skycheck.presentation.theme.ColorTextAction
import com.example.skycheck.presentation.theme.ColorTextPrimary
import com.example.skycheck.presentation.theme.ColorTextPrimaryVariant
import com.example.skycheck.utils.hasLocationPermission
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingScreen(
    navController: NavController,
    viewModel: OnboardingViewModel = koinViewModel()
) {
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ColorBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = ColorTextAction,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.slogan),
                    style = MaterialTheme.typography.titleLarge,
                    color = ColorTextPrimaryVariant,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.permissao_necessaria),
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = ColorTextPrimary,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                OnboardingPermissionComponent()
            }

            val requestPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { permissions ->
                    if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                        || permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
                    ) {
                        // I HAVE ACCESS TO LOCATION
                        navController.navigate(CurrentLocation)
                    } else {
                        // ASK FOR PERMISSION
                        val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                            context as MainActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) || ActivityCompat.shouldShowRequestPermissionRationale(
                            context,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )

                        if (rationaleRequired) {
                            Toast.makeText(
                                context,
                                "Location permission is required for this feature to work",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "Location permission is required. Please unable it in the Android Settings",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            )

            OnboardingStartButton {
                if (hasLocationPermission(context)) {
                    // Grant already granted, update location
                    navController.navigate(CurrentLocation)
                } else {
                    // Request location permission
                    requestPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun OnboardingScreenPreview() {
    OnboardingScreen(navController = rememberNavController())
}