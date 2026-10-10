package org.umn.multimedianusantaraclinic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.umn.multimedianusantaraclinic.ui.theme.MultimediaNusantaraClinicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()   // From Splash Screen Package
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Keep track of Splash Screen status
            var isSplashScreenFinished by remember { mutableStateOf(false) }

            // If Splash Screen not done, display Splash Screen
            if(!isSplashScreenFinished) {
                SplashScreen(onTimeout = { isSplashScreenFinished })
            } else {
                // Display Main Content
                MultimediaNusantaraClinicTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MultimediaNusantaraClinicTheme {
        Greeting("Android")
    }
}