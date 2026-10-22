package org.umn.multimedianusantaraclinic.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Accessible
import androidx.compose.material.icons.filled.AssignmentLate
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.ui.graphics.Color
import org.umn.multimedianusantaraclinic.ui.screen.ActivityCategoryTab
import org.umn.multimedianusantaraclinic.ui.screen.ActivityItemData

public val sampleActivities = listOf(
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
//    ActivityItemData(
//        id = "ID-2234-8812",
//        title = "Event Service",
//        category = ActivityCategoryTab.SERVICES,
//        subCategory = "Event Service",
//        description = "Allocating Staff for Event Service at Main Auditorium",
//        status = "Ongoing",
//        statusColor = Color(0xFF2E7D32),
//        statusBgColor = Color(0xFFE8F5E9),
//        lastUpdated = "13:45",
//        icon = Icons.Default.Event,
//        iconBgColor = Color(0xFFE0F7FA),
//        iconTint = Color(0xFF1B9AAA)
//    ),
    ActivityItemData(
        id = "ID-5678-1234",
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
//    ActivityItemData(
//        id = "ID-5612-7721",
//        title = "Reporting Misconduct",
//        category = ActivityCategoryTab.REPORTS,
//        subCategory = "Reporting Misconduct",
//        description = "Report received. Under investigation by clinic administration",
//        status = "In Review",
//        statusColor = Color(0xFFE65100),
//        statusBgColor = Color(0xFFFFF3E0),
//        lastUpdated = "15 Oct, 12:51",
//        icon = Icons.Default.ReportProblem,
//        iconBgColor = Color(0xFFFFEBEE),
//        iconTint = Color(0xFFD32F2F)
//    ),
//    ActivityItemData(
//        id = "ID-6712-4411",
//        title = "Service Denials Report",
//        category = ActivityCategoryTab.REPORTS,
//        subCategory = "Service Denials Report",
//        description = "Reviewing service denial report submitted for John Thor",
//        status = "Pending",
//        statusColor = Color(0xFFE65100),
//        statusBgColor = Color(0xFFFFF3E0),
//        lastUpdated = "14 Oct, 09:20",
//        icon = Icons.Default.AssignmentLate,
//        iconBgColor = Color(0xFFFFEBEE),
//        iconTint = Color(0xFFD32F2F)
//    )
)