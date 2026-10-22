package org.umn.multimedianusantaraclinic.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Accessible
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.AssignmentLate
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.umn.multimedianusantaraclinic.data.sampleActivities
import org.umn.multimedianusantaraclinic.ui.component.ActivityListItem
import org.umn.multimedianusantaraclinic.ui.theme.Black
import org.umn.multimedianusantaraclinic.ui.theme.Gray
import org.umn.multimedianusantaraclinic.ui.theme.Green40
import org.umn.multimedianusantaraclinic.ui.theme.MultimediaNusantaraClinicTheme

enum class ActivityCategoryTab(val displayName: String, val icon: ImageVector) {
    ALL("All Activities", Icons.Default.Apps),
    SERVICES("Services", Icons.Default.MedicalServices),
    REQUESTS("Requests", Icons.AutoMirrored.Filled.Assignment),
    REPORTS("Reports", Icons.Default.ReportProblem)
}

data class ActivityItemData(
    val id: String,
    val title: String,
    val category: ActivityCategoryTab,
    val subCategory: String,
    val description: String,
    val status: String,
    val statusColor: Color,
    val statusBgColor: Color,
    val lastUpdated: String,
    val icon: ImageVector,
    val iconBgColor: Color,
    val iconTint: Color
)

@Composable
fun ActivityListScreen(onItemClick: (String) -> Unit = {}) {
    var selectedTab by remember { mutableStateOf(ActivityCategoryTab.ALL) }

    val filteredActivities = remember(selectedTab) {
        if (selectedTab == ActivityCategoryTab.ALL) {
            sampleActivities
        } else {
            sampleActivities.filter { it.category == selectedTab }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Screen Header
            Text(
                text = "Activity List",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(ActivityCategoryTab.entries.toTypedArray()) { tab ->
                    val isSelected = tab == selectedTab

                    Column(
                        modifier = Modifier
                            .width(IntrinsicSize.Max)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                selectedTab = tab
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.displayName,
                                modifier = Modifier.size(18.dp),
                                tint = if (isSelected) Green40 else Color(0xFF666666)
                            )
                            Text(
                                text = tab.displayName,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Green40 else Black
                            )
                        }

                        // Underline below icon and name like Shopee
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(
                                    color = if (isSelected) Green40 else Color.Transparent,
                                    shape = RoundedCornerShape(1.dp)
                                )
                        )
                    }
                }
            }

            HorizontalDivider(color = Gray, thickness = 1.dp)

            Spacer(modifier = Modifier.height(8.dp))

            // Activity List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(filteredActivities, key = { it.id }) { activity ->
                    ActivityListItem(data = activity, onItemClick = onItemClick)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ActivityPreview() {
    MultimediaNusantaraClinicTheme {
        ActivityListScreen()
    }
}