package com.example.businessasusualandroid

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.rememberNavController
import com.example.businessasusualandroid.navigation.AppNavHost
import com.example.businessasusualandroid.ui.theme.BAUTheme
import com.example.businessasusualandroid.ui.theme.resolveTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Custom Action Bar
        supportActionBar?.apply {
            displayOptions = androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.actionbar_bau)
        }

        setContent {

            // GLOBAL THEME STATE
            var themeName by remember { mutableStateOf("bau") }
            var darkTheme by remember { mutableStateOf(false) }

            val navController = rememberNavController()

            // Access ActionBar views INSIDE Compose
            val actionBarRoot = remember {
                supportActionBar?.customView?.findViewById<View>(R.id.actionBarRoot)
            }
            val actionBarTitle = remember {
                supportActionBar?.customView?.findViewById<TextView>(R.id.actionBarTitle)
            }
            val actionBarSubtitle = remember {
                supportActionBar?.customView?.findViewById<TextView>(R.id.actionBarSubtitle)
            }

            BAUTheme(
                themeName = themeName,
                darkTheme = darkTheme
            ) {

                // UPDATE ACTION BAR COLORS WHEN THEME CHANGES
                LaunchedEffect(themeName, darkTheme) {
                    val colors = resolveTheme(themeName, darkTheme)

                    // Color the custom view (logo + title)
                    actionBarRoot?.setBackgroundColor(Color.Transparent.toArgb())
                    actionBarTitle?.setTextColor(colors.onPrimary.toArgb())
                    actionBarSubtitle?.setTextColor(colors.onPrimary.toArgb())

                    // Color the Action Bar background
                    supportActionBar?.setBackgroundDrawable(
                        ColorDrawable(colors.primary.toArgb())
                    )

                    supportActionBar?.elevation = 0f
                }

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