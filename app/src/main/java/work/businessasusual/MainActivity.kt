package work.businessasusual

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import work.businessasusual.navigation.AppNavHost
import work.businessasusual.ui.theme.BAUTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {

            // GLOBAL THEME STATE
            var themeName by remember { mutableStateOf("bau") }
            var darkTheme by remember { mutableStateOf(false) }

            val navController = rememberNavController()

            BAUTheme(
                themeName = themeName,
                darkTheme = darkTheme
            ) {
                AppNavHost(
                    navController = navController,
                    themeName = themeName,
                    darkTheme = darkTheme,
                    onThemeChange = { themeName = it },
                    onDarkThemeChange = { darkTheme = it }
                )
            }
        }
    }
}