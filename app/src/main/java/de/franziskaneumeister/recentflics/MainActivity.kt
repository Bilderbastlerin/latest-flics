package de.franziskaneumeister.recentflics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import de.franziskaneumeister.recentflics.core.designsystem.theme.AppTheme
import de.franziskaneumeister.recentflics.feature.home.list.ListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                ListScreen()
            }
        }
    }
}

