package org.umn.multimedianusantaraclinic.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.umn.multimedianusantaraclinic.data.defaultActivityDetail
import org.umn.multimedianusantaraclinic.data.wheelchairRequestMock
import org.umn.multimedianusantaraclinic.ui.component.ActivityCard
import org.umn.multimedianusantaraclinic.ui.component.ActivityDetailHeader
import org.umn.multimedianusantaraclinic.ui.component.BannerCard
import org.umn.multimedianusantaraclinic.ui.component.DetailItem
import org.umn.multimedianusantaraclinic.ui.component.ProductDetailList
import org.umn.multimedianusantaraclinic.ui.component.ProductItem
import org.umn.multimedianusantaraclinic.ui.component.ProfileCard
import org.umn.multimedianusantaraclinic.ui.component.ProgressStep
import org.umn.multimedianusantaraclinic.ui.component.ProgressTracker
import org.umn.multimedianusantaraclinic.ui.component.TextDetailList
import org.umn.multimedianusantaraclinic.ui.theme.Green80
import org.umn.multimedianusantaraclinic.ui.theme.MultimediaNusantaraClinicTheme
import org.umn.multimedianusantaraclinic.ui.theme.Red
import org.umn.multimedianusantaraclinic.ui.theme.White
import org.umn.multimedianusantaraclinic.ui.theme.Yellow

enum class ActivityStatus {
    CANCELLED, ONGOING, COMPLETED
}

val ActivityStatus.displayText: String
    get() = when (this) {
        ActivityStatus.CANCELLED -> "Cancelled"
        ActivityStatus.ONGOING -> "Ongoing"
        ActivityStatus.COMPLETED -> "Completed"
    }

val ActivityStatus.color: Color
    get() = when (this) {
        ActivityStatus.CANCELLED -> Red
        ActivityStatus.ONGOING -> Yellow
        ActivityStatus.COMPLETED -> Green80
    }


data class ActivityDetailData(
    val requestId: String,
    val title: String,
    val date: String,
    val status: ActivityStatus,
    @DrawableRes val bannerResId: Int,
    val bannerDescription: String,
    val bannerBgColor: Color,
    val profileTitle: String,
    val profileName: String,
    val profileId: String,
    @DrawableRes val profileResId: Int,
    val detailContent: ActivityDetailContent,
    val progressSteps: List<ProgressStep>
)

sealed interface ActivityDetailContent {
    data class TextDetails(
        val items: List<DetailItem>
    ) : ActivityDetailContent

    data class ProductDetails(
        val products: List<ProductItem>,
        val totalPrice: String
    ) : ActivityDetailContent
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityDetailsScreen(
    data: ActivityDetailData,
    onBackClick: () -> Unit = {},
    onReportIssueClick: () -> Unit = {},
    onCancelRequestClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Activity Details",
                        style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ))},
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 8.dp, end = 8.dp)
                    ) {
                    Icon(Icons.Filled.ArrowBack,
                        contentDescription = "Back Button")
                } }
            )
        },
        bottomBar = {
            Surface (
                modifier = Modifier.wrapContentHeight(),
                color = MaterialTheme.colorScheme.surface,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(onClick = {},
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text("Report an issue", style = MaterialTheme.typography.bodyLarge)
                    }
                    Button(onClick = {},
                        colors = ButtonDefaults
                            .buttonColors(Red),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text("Cancel Request", style = MaterialTheme.typography.bodyLarge, color = White)
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            ActivityDetailHeader(
                title = data.title,
                date = data.date,
                status = data.status,
                requestId = data.requestId,
            )
            BannerCard(
                imagePainter = painterResource(data.bannerResId),
                contentDescription = data.bannerDescription,
                modifier = Modifier,
                backgroundColor = data.bannerBgColor
            )
            ProfileCard(
                title = data.profileTitle,
                name = data.profileName,
                userId = data.profileId,
                imagePainter = painterResource(data.profileResId),
                modifier = Modifier,
            )
            ActivityCard(title = "Request Details", modifier = Modifier) {
                when(val content = data.detailContent) {
                    is ActivityDetailContent.TextDetails -> {
                        TextDetailList(items = content.items)
                    }
                    is ActivityDetailContent.ProductDetails -> {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            ProductDetailList(products = content.products, totalPrice = content.totalPrice)
                        }
                    }
                }
            }
            ActivityCard(title = "Activity Progress", modifier = Modifier) {
                ProgressTracker(steps = data.progressSteps)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivityPreviewA() {
    MultimediaNusantaraClinicTheme {
        ActivityDetailsScreen(data = wheelchairRequestMock)
    }
}