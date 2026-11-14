package org.umn.multimedianusantaraclinic.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.umn.multimedianusantaraclinic.ui.theme.Gray

data class ProgressStep(
    val description: String,
    val color: Color,
    val isCompleted: Boolean = false
)

@Composable
fun ProgressTracker(
    steps: List<ProgressStep>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        steps.forEachIndexed { index, step ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(color = step.color, shape = CircleShape)
                )
                Text(
                    text = step.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if(index < steps.lastIndex) {
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                        .height(24.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(Gray, shape = CircleShape)
                        )
                    }
                }
            }
        }
    }
}