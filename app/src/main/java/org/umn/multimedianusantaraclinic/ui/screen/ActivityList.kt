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

private val sampleActivities = listOf(
    ActivityItemData(
        id = "ID-1234-5678",
        title = "Wheelchair Request",
        category = ActivityCategoryTab.SERVICES,
        subCategory = "Wheelchair Service",
        description = "A staff is on their way with the wheelchair!",
        status = "Ongoing",
        statusColor = Color(0xFF2E7D32),
        statusBgColor = Color(0xFFE8F5E9),
        lastUpdated = "14:00",
        icon = Icons.AutoMirrored.Filled.Accessible,
        iconBgColor = Color(0xFFC6EBBE),
        iconTint = Color(0xFF338111)
    ),
    ActivityItemData(
        id = "ID-2234-8812",
        title = "Event Service",
        category = ActivityCategoryTab.SERVICES,
        subCategory = "Event Service",
        description = "Allocating Staff for Event Service at Main Auditorium",
        status = "Ongoing",
        statusColor = Color(0xFF2E7D32),
        statusBgColor = Color(0xFFE8F5E9),
        lastUpdated = "13:45",
        icon = Icons.Default.Event,
        iconBgColor = Color(0xFFE0F7FA),
        iconTint = Color(0xFF1B9AAA)
    ),
    ActivityItemData(
        id = "ID-3412-9011",
        title = "Medicine Request",
        category = ActivityCategoryTab.REQUESTS,
        subCategory = "Medicine Request",
        description = "Paracetamol & Vitamin C package prepared by pharmacy desk",
        status = "Completed",
        statusColor = Color(0xFF0288D1),
        statusBgColor = Color(0xFFE1F5FE),
        lastUpdated = "12:30",
        icon = Icons.Default.Medication,
        iconBgColor = Color(0xFFE8F5E9),
        iconTint = Color(0xFF338111)
    ),
    ActivityItemData(
        id = "ID-4512-3390",
        title = "Clinic Tools Request",
        category = ActivityCategoryTab.REQUESTS,
        subCategory = "Clinic Tools Request",
        description = "First aid kit and BP monitor dispatched to Building C Room 308",
        status = "Ongoing",
        statusColor = Color(0xFF2E7D32),
        statusBgColor = Color(0xFFE8F5E9),
        lastUpdated = "11:15",
        icon = Icons.Default.HomeRepairService,
        iconBgColor = Color(0xFFE0F7FA),
        iconTint = Color(0xFF1B9AAA)
    ),
    ActivityItemData(
        id = "ID-5612-7721",
        title = "Reporting Misconduct",
        category = ActivityCategoryTab.REPORTS,
        subCategory = "Reporting Misconduct",
        description = "Report received. Under investigation by clinic administration",
        status = "In Review",
        statusColor = Color(0xFFE65100),
        statusBgColor = Color(0xFFFFF3E0),
        lastUpdated = "15 Oct, 12:51",
        icon = Icons.Default.ReportProblem,
        iconBgColor = Color(0xFFFFEBEE),
        iconTint = Color(0xFFD32F2F)
    ),
    ActivityItemData(
        id = "ID-6712-4411",
        title = "Service Denials Report",
        category = ActivityCategoryTab.REPORTS,
        subCategory = "Service Denials Report",
        description = "Reviewing service denial report submitted for John Thor",
        status = "Pending",
        statusColor = Color(0xFFE65100),
        statusBgColor = Color(0xFFFFF3E0),
        lastUpdated = "14 Oct, 09:20",
        icon = Icons.Default.AssignmentLate,
        iconBgColor = Color(0xFFFFEBEE),
        iconTint = Color(0xFFD32F2F)
    )
)

@Composable
fun ActivityScreen(onItemClick: () -> Unit = {}) {
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
            .background(Color.White)
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
                color = Black,
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

            HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)

            Spacer(modifier = Modifier.height(8.dp))

            // Activity List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(filteredActivities, key = { it.id }) { activity ->
                    ActivityListItem(item = activity, onItemClick = onItemClick)
                }
            }
        }
    }
}

@Composable
fun ActivityListItem(item: ActivityItemData, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Feature Icon Container
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = item.iconTint,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Title and Status Badge
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Black,
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        // Status with dot
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(item.statusColor, CircleShape)
                            )
                            Text(
                                text = item.status,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = item.statusColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "${item.id} • ${item.subCategory}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Gray,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description Status of Activity
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(color = Color(0xFFEEEEEE))

            Spacer(modifier = Modifier.height(6.dp))

            // Last Updated Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "Last Updated",
                        tint = Gray,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = "Last updated",
                        style = MaterialTheme.typography.labelSmall,
                        color = Gray,
                        fontSize = 11.sp
                    )
                }

                Text(
                    text = item.lastUpdated,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivityPreview() {
    MultimediaNusantaraClinicTheme {
        ActivityScreen()
    }
}