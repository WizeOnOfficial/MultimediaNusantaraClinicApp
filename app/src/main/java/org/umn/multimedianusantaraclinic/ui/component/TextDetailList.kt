package org.umn.multimedianusantaraclinic.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class DetailItem(val label: String, val value: String)

@Composable
fun TextDetailList(items: List<DetailItem>) {
    Column() {
        items.forEach { item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = item.label, style = MaterialTheme.typography.bodyMedium)
                Text(text = item.value, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}