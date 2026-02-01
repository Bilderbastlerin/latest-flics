package de.franziskaneumeister.recentflics.feature.home.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
public fun ListScreen(modifier: Modifier = Modifier) {
    Scaffold { paddingValues ->
        Text("Hello World", modifier = Modifier.padding(paddingValues))
    }
}

