@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tt
import androidx.compose.material3.CenterAlignedTopAppBar

import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect

import android.content.Intent
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import android.os.Bundle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tt.ui.theme.TtTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TtTheme {
                MainAppScreen()
            }
        }
    }
}

@Composable
fun MainAppScreen() {
    // Navigation controller for handling different screens
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Kumbh Sarthi", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("emergency") { EmergencyResponseScreen() }
            composable("serviceAccess") { ServiceAccessScreen() }
            composable("lostFound") { LostFoundScreen() }
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    // Main home screen with buttons to navigate to different features
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to Kumbh Sarthi",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // Button to navigate to Emergency Response Screen
        Button(
            onClick = { navController.navigate("emergency") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Swift Emergency Response")
        }

        // Button to navigate to Area-Wide Service Access Screen
        Button(
            onClick = { navController.navigate("serviceAccess") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Area-Wide Service Access")
        }

        // Button to navigate to Lost & Found Screen
        Button(
            onClick = { navController.navigate("lostFound") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Effortless Lost & Found")
        }
    }
}

@Composable
fun EmergencyResponseScreen() {
    // Emergency response screen implementation
    var showLoading by remember { mutableStateOf(false) }
    var navigateToCallLog by remember { mutableStateOf(false) }

    if (navigateToCallLog) {
        // Use an Intent to open the call log
        val intent = Intent(Intent.ACTION_VIEW).apply {
            type = "vnd.android.cursor.dir/calls"
        }
        LocalContext.current.startActivity(intent)
    } else if (showLoading) {
        LoadingScreen(onLoadingFinished = { navigateToCallLog = true })
    } else {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Emergency Response") },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { showLoading = true }) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = "Emergency",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "In case of an emergency, press the button to alert nearby services.",
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ServiceAccessScreen() {
    // Area-Wide Service Access screen implementation
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Service Access") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Find nearby services quickly and easily.", fontSize = 18.sp, color = Color.Gray)
            // Additional UI components such as service categories or map can be added here
        }
    }
}

@Composable
fun LostFoundScreen() {
    // Lost & Found screen implementation
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lost & Found") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Lost & Found Reporting", fontSize = 18.sp, color = Color.Gray)
            Button(
                onClick = { /* Functionality to open report form */ },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Report Lost Person")
            }
        }
    }
}

@Composable
fun LoadingScreen(onLoadingFinished: () -> Unit) {
    // A simple loading screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Loading...", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        // Simulate loading
        LaunchedEffect(Unit) {
            delay(2000) // Simulate loading delay
            onLoadingFinished()
        }
    }
}
//push


@Preview(showBackground = true)
@Composable
fun MainAppPreview() {
    TtTheme {
        MainAppScreen()
    }
}
