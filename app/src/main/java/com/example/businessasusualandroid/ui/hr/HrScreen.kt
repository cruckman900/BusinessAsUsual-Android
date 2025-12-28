package com.example.businessasusualandroid.ui.hr

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun HrScreen(
    viewModel: HrViewModel = koinViewModel()
) {
    val actions by viewModel.actions.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
       Text("HR", style = MaterialTheme.typography.headlineMedium)

       Spacer(Modifier.height(16.dp))

       actions.forEach { action ->
           Card(
               modifier = Modifier
                   .fillMaxSize()
                   .padding(vertical = 8.dp)
           ) {
               Column(Modifier.padding(16.dp)) {
                   Text(action.title, style = MaterialTheme.typography.titleMedium)
                   Text(action.description, style = MaterialTheme.typography.bodySmall)
               }
           }
       }
    }
}