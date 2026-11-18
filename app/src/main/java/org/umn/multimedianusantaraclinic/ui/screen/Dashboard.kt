package org.umn.multimedianusantaraclinic.ui.screen

import android.net.Uri
import androidx.compose.foundation.Image
import kotlin.collections.chunked
import kotlin.collections.forEach
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.MedicalInformation
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import org.umn.multimedianusantaraclinic.Screen
import org.umn.multimedianusantaraclinic.ui.component.DashboardMenuItem
import org.umn.multimedianusantaraclinic.ui.theme.Gray
import org.umn.multimedianusantaraclinic.ui.theme.MultimediaNusantaraClinicTheme
import org.umn.multimedianusantaraclinic.ui.theme.White

data class DashboardItemData(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val route: String? = null
)

val dashboardItems = listOf(
    DashboardItemData("doctor", "Doctor", Icons.Default.MedicalServices, Screen.Hotline.route),
    DashboardItemData("pharmacy", "Pharmacy", Icons.Default.LocalPharmacy, Screen.ActivityList.route),
    DashboardItemData("ambulance", "Ambulance", Icons.Default.MedicalInformation, Screen.Hotline.route),
    DashboardItemData("schedule", "Schedule", Icons.Default.Event, Screen.ActivityList.route),
    DashboardItemData("records", "Records", Icons.Default.ReceiptLong, Screen.ActivityList.route),
    DashboardItemData("lab", "Lab Test", Icons.Default.Science, Screen.ActivityList.route),
    DashboardItemData("counseling", "Counseling", Icons.Default.Psychology, Screen.Hotline.route),
    DashboardItemData("facility", "Facility", Icons.Default.LocalHospital, Screen.ActivityList.route)
)

@Composable
fun DashboardScreen(
    userName: String = "Barbara",
    profileImageUri: Uri? = null,
    onNavigateToLogin: () -> Unit = {},
    onNavigateToRoute: (String) -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // Single root Box container
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Background Decorative Accent
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .offset(x = 60.dp, y = (-40).dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .align(Alignment.TopEnd)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Header Profile
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Hello",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = userName,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        // Profile Avatar
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(Gray)
                                .clickable { onNavigateToLogin() },
                            contentAlignment = Alignment.Center
                        ) {
                            if (profileImageUri != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = profileImageUri),
                                    contentDescription = "Profile Avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Text(
                                    text = userName.take(1).uppercase(),
                                    style = MaterialTheme.typography.displaySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }

                // Grid Menu Items
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        dashboardItems.chunked(4).forEach { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                rowItems.forEach { item ->
                                    DashboardMenuItem(
                                        data = item,
                                        onClick = {
                                            item.route?.let { targetRoute ->
                                                onNavigateToRoute(targetRoute)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // Highlight Card
                item {
                    Text(
                        text = "Highlight",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(144.dp)
                            .clickable { onNavigateToRoute(Screen.ActivityList.route) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Gray)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "HEY WHATSUP!",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    MultimediaNusantaraClinicTheme {
        DashboardScreen(
            onNavigateToRoute = {},
            onNavigateToLogin = {}
        )
    }
}

//package org.umn.multimedianusantaraclinic.ui.screen
//
//import android.content.Intent
//import android.net.Uri
//import androidx.compose.animation.core.animateDpAsState
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccessTime
//import androidx.compose.material.icons.filled.Emergency
//import androidx.compose.material.icons.filled.Event
//import androidx.compose.material.icons.filled.LocalHospital
//import androidx.compose.material.icons.filled.LocalPharmacy
//import androidx.compose.material.icons.filled.LocationOn
//import androidx.compose.material.icons.filled.MedicalServices
//import androidx.compose.material.icons.filled.Phone
//import androidx.compose.material.icons.filled.Psychology
//import androidx.compose.material.icons.filled.ReceiptLong
//import androidx.compose.material.icons.filled.Science
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.hapticfeedback.HapticFeedbackType
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalHapticFeedback
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.rememberAsyncImagePainter
//import kotlinx.coroutines.delay
//import org.umn.multimedianusantaraclinic.Screen
//import org.umn.multimedianusantaraclinic.data.sampleActivities
//import org.umn.multimedianusantaraclinic.data.sampleHighlights
//import org.umn.multimedianusantaraclinic.ui.component.DashboardMenuItem
//import org.umn.multimedianusantaraclinic.ui.component.HighlightBannerCard
//import org.umn.multimedianusantaraclinic.ui.component.activity.ActivityListItem
//import org.umn.multimedianusantaraclinic.ui.screen.activity.ActivityStatus
//import org.umn.multimedianusantaraclinic.ui.theme.Gray
//import org.umn.multimedianusantaraclinic.ui.theme.Green40
//import org.umn.multimedianusantaraclinic.ui.theme.Red
//import java.util.Calendar
//
//data class DashboardItemData(
//    val id: String,
//    val title: String,
//    val icon: ImageVector,
//    val route: String? = null
//)
//
//val homeMenuItems = listOf(
//    DashboardItemData("doctor", "Doctor", Icons.Default.MedicalServices, Screen.Hotline.route),
//    DashboardItemData("pharmacy", "Pharmacy", Icons.Default.LocalPharmacy, Screen.ActivityList.route),
//    DashboardItemData("ambulance", "Ambulance", Icons.Default.Emergency, Screen.Hotline.route),
//    DashboardItemData("schedule", "Schedule", Icons.Default.Event, Screen.ActivityList.route),
//    DashboardItemData("records", "Records", Icons.Default.ReceiptLong, Screen.ActivityList.route),
//    DashboardItemData("lab", "Lab Test", Icons.Default.Science, Screen.ActivityList.route),
//    DashboardItemData("counseling", "Counseling", Icons.Default.Psychology, Screen.Hotline.route),
//    DashboardItemData("facility", "Facility", Icons.Default.LocalHospital, Screen.ActivityList.route)
//)
//
//@Composable
//fun DashboardScreen(
//    userName: String = "Barbara",
//    profileImageUri: Uri? = null,
//    onNavigateToLogin: () -> Unit = {},
//    onNavigateToRoute: (String) -> Unit = {}
//) {
//    val context = LocalContext.current
//    val haptic = LocalHapticFeedback.current
//
//    val ongoingActivities = remember {
//        sampleActivities.filter { it.status == ActivityStatus.ONGOING }.take(2)
//    }
//
//    // Dynamic Greeting & Real-time Clinic Status
//    val (greetingText, isClinicOpen) = remember {
//        val cal = Calendar.getInstance()
//        val hour = cal.get(Calendar.HOUR_OF_DAY)
//        val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
//
//        val greeting = when (hour) {
//            in 4..11 -> "Good Morning,"
//            in 12..17 -> "Good Afternoon,"
//            else -> "Good Evening,"
//        }
//        val isWeekday = dayOfWeek in Calendar.MONDAY..Calendar.FRIDAY
//        val isOpen = isWeekday && (hour in 8..16)
//        greeting to isOpen
//    }
//
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colorScheme.background
//    ) {
//        Box(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            // Background Decorative Accent
//            Box(
//                modifier = Modifier
//                    .size(160.dp)
//                    .offset(x = 60.dp, y = (-40).dp)
//                    .background(Color(0xFF2E7D32), CircleShape)
//                    .align(Alignment.TopEnd)
//            )
//
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(horizontal = 20.dp),
//                verticalArrangement = Arrangement.spacedBy(20.dp)
//            ) {
//                // Header Profile dengan Dynamic Greeting
//                item {
//                    Spacer(modifier = Modifier.height(24.dp))
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Column {
//                            Text(
//                                text = greetingText,
//                                fontSize = 18.sp,
//                                fontWeight = FontWeight.Medium,
//                                color = Color(0xFF2E7D32)
//                            )
//                            Text(
//                                text = userName,
//                                fontSize = 26.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = Color(0xFF1B5E20)
//                            )
//                        }
//
//                        // Profile Avatar
//                        Box(
//                            modifier = Modifier
//                                .size(75.dp)
//                                .clip(CircleShape)
//                                .background(Color(0xFFCFD8DC))
//                                .clickable { onNavigateToRoute(Screen.Profile.route) },
//                            contentAlignment = Alignment.Center
//                        ) {
//                            if (profileImageUri != null) {
//                                Image(
//                                    painter = rememberAsyncImagePainter(model = profileImageUri),
//                                    contentDescription = "Profile Avatar",
//                                    contentScale = ContentScale.Crop,
//                                    modifier = Modifier.fillMaxSize()
//                                )
//                            } else {
//                                Text(
//                                    text = userName.take(1).uppercase(),
//                                    fontSize = 28.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    color = Color.White
//                                )
//                            }
//                        }
//                    }
//                }
//
//                // Grid Menu Items
//                item {
//                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
//                        homeMenuItems.chunked(4).forEach { rowItems ->
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween
//                            ) {
//                                rowItems.forEach { item ->
//                                    DashboardMenuItem(
//                                        data = item,
//                                        onClick = {
//                                            item.route?.let { targetRoute ->
//                                                onNavigateToRoute(targetRoute)
//                                            }
//                                        }
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//
//                // Highlight Carousel Section dengan Auto-Slide Penuh
//                item {
//                    val pagerState = rememberPagerState(pageCount = { sampleHighlights.size })
//
//                    LaunchedEffect(Unit) {
//                        while (true) {
//                            delay(4000L)
//                            if (!pagerState.isScrollInProgress) {
//                                val nextPage = (pagerState.currentPage + 1) % sampleHighlights.size
//                                pagerState.animateScrollToPage(nextPage)
//                            }
//                        }
//                    }
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = "Highlight",
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = MaterialTheme.colorScheme.onBackground
//                        )
//
//                        // Indikator halaman animasi (dots)
//                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
//                            repeat(sampleHighlights.size) { index ->
//                                val isSelected = pagerState.currentPage == index
//                                val dotWidth by animateDpAsState(
//                                    targetValue = if (isSelected) 18.dp else 6.dp,
//                                    label = "dotWidth"
//                                )
//                                Box(
//                                    modifier = Modifier
//                                        .height(6.dp)
//                                        .width(dotWidth)
//                                        .clip(CircleShape)
//                                        .background(
//                                            if (isSelected) Green40
//                                            else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.25f)
//                                        )
//                                )
//                            }
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(10.dp))
//
//                    HorizontalPager(
//                        state = pagerState,
//                        pageSpacing = 12.dp,
//                        modifier = Modifier.fillMaxWidth()
//                    ) { page ->
//                        val highlightItem = sampleHighlights[page]
//                        HighlightBannerCard(
//                            item = highlightItem,
//                            onClick = {
//                                onNavigateToRoute(Screen.HighlightDetail.createRoute(highlightItem.id))
//                            }
//                        )
//                    }
//                }
//
//                // Ongoing Activity Section
//                if (ongoingActivities.isNotEmpty()) {
//                    item {
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Text(
//                                text = "Ongoing Activity",
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = MaterialTheme.colorScheme.onBackground
//                            )
//                            Text(
//                                text = "See All",
//                                fontSize = 13.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                color = Green40,
//                                modifier = Modifier.clickable {
//                                    onNavigateToRoute(Screen.ActivityList.route)
//                                }
//                            )
//                        }
//
//                        Spacer(modifier = Modifier.height(10.dp))
//
//                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
//                            ongoingActivities.forEach { activity ->
//                                ActivityListItem(
//                                    data = activity,
//                                    onItemClick = { activityId ->
//                                        onNavigateToRoute(Screen.ActivityDetail.createRoute(activityId))
//                                    }
//                                )
//                            }
//                        }
//                    }
//                }
//
//                // Clinic Operational Info Card dengan Real-Time Status & Direct Dial
//                item {
//                    Text(
//                        text = "Clinic Information",
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                    Spacer(modifier = Modifier.height(10.dp))
//                    Card(
//                        modifier = Modifier.fillMaxWidth(),
//                        shape = RoundedCornerShape(12.dp),
//                        colors = CardDefaults.cardColors(
//                            containerColor = MaterialTheme.colorScheme.surface
//                        ),
//                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//                    ) {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp),
//                            verticalArrangement = Arrangement.spacedBy(12.dp)
//                        ) {
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Row(verticalAlignment = Alignment.CenterVertically) {
//                                    Icon(
//                                        imageVector = Icons.Default.AccessTime,
//                                        contentDescription = "Operating Hours",
//                                        tint = Green40,
//                                        modifier = Modifier.size(20.dp)
//                                    )
//                                    Spacer(modifier = Modifier.width(10.dp))
//                                    Column {
//                                        Text(
//                                            text = "Operating Hours",
//                                            fontSize = 12.sp,
//                                            color = Gray
//                                        )
//                                        Text(
//                                            text = "Mon - Fri, 08:00 - 17:00 WIB",
//                                            fontSize = 13.sp,
//                                            fontWeight = FontWeight.SemiBold,
//                                            color = MaterialTheme.colorScheme.onSurface
//                                        )
//                                    }
//                                }
//
//                                Surface(
//                                    shape = RoundedCornerShape(50),
//                                    color = if (isClinicOpen) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
//                                ) {
//                                    Text(
//                                        text = if (isClinicOpen) "OPEN NOW" else "CLOSED",
//                                        color = if (isClinicOpen) Green40 else Red,
//                                        fontSize = 10.sp,
//                                        fontWeight = FontWeight.Bold,
//                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
//                                    )
//                                }
//                            }
//
//                            HorizontalDivider(color = Color(0xFFEEEEEE))
//
//                            Row(verticalAlignment = Alignment.CenterVertically) {
//                                Icon(
//                                    imageVector = Icons.Default.LocationOn,
//                                    contentDescription = "Clinic Location",
//                                    tint = Green40,
//                                    modifier = Modifier.size(20.dp)
//                                )
//                                Spacer(modifier = Modifier.width(10.dp))
//                                Column {
//                                    Text(
//                                        text = "Location",
//                                        fontSize = 12.sp,
//                                        color = Gray
//                                    )
//                                    Text(
//                                        text = "Building A, 1st Floor - UMN Campus",
//                                        fontSize = 13.sp,
//                                        fontWeight = FontWeight.SemiBold,
//                                        color = MaterialTheme.colorScheme.onSurface
//                                    )
//                                }
//                            }
//
//                            Button(
//                                onClick = {
//                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
//                                    val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:02154220808"))
//                                    context.startActivity(dialIntent)
//                                },
//                                modifier = Modifier.fillMaxWidth(),
//                                shape = RoundedCornerShape(10.dp),
//                                colors = ButtonDefaults.buttonColors(
//                                    containerColor = Green40,
//                                    contentColor = Color.White
//                                )
//                            ) {
//                                Icon(
//                                    imageVector = Icons.Default.Phone,
//                                    contentDescription = null,
//                                    modifier = Modifier.size(18.dp)
//                                )
//                                Spacer(modifier = Modifier.width(8.dp))
//                                Text(
//                                    text = "Call Clinic Desk (021-54220808)",
//                                    fontSize = 13.sp,
//                                    fontWeight = FontWeight.SemiBold
//                                )
//                            }
//                        }
//                    }
//                }
//
//                item {
//                    Spacer(modifier = Modifier.height(20.dp))
//                }
//            }
//        }
//    }
//}