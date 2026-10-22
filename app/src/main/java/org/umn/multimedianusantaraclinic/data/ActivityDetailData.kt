package org.umn.multimedianusantaraclinic.data

import org.umn.multimedianusantaraclinic.R
import org.umn.multimedianusantaraclinic.ui.component.DetailItem
import org.umn.multimedianusantaraclinic.ui.component.ProductItem
import org.umn.multimedianusantaraclinic.ui.component.ProgressStep
import org.umn.multimedianusantaraclinic.ui.screen.ActivityDetailContent
import org.umn.multimedianusantaraclinic.ui.screen.ActivityDetailData
import org.umn.multimedianusantaraclinic.ui.screen.ActivityStatus
import org.umn.multimedianusantaraclinic.ui.theme.Cyan40
import org.umn.multimedianusantaraclinic.ui.theme.Green40
import org.umn.multimedianusantaraclinic.ui.theme.Green80

val defaultActivityDetail = ActivityDetailData(
    requestId = "ID-9999-9999",
    title = "ERROR SOMETHING OCCURRED",
    date = "31 Dec 2099, 23:59",
    status = ActivityStatus.COMPLETED,
    bannerResId = R.drawable.wheelchairbanner,
    bannerDescription = "Wheelchair Banner",
    bannerBgColor = Green80,
    profileTitle = "Staff Profile",
    profileName = "Sumthing Wong",
    profileId = "Staff-999999",
    profileResId = R.drawable.profilepictureplaceholder,
    detailContent = ActivityDetailContent.TextDetails(
        items = listOf(
            DetailItem("Location", "C308"),
            DetailItem("Require Assistance", "False")
        )
    ),
    progressSteps = listOf(
        ProgressStep("ERROR", Green40)
    )
)

val wheelchairRequestMock = ActivityDetailData(
    requestId = "ID-1234-5678",
    title = "Wheelchair Request",
    date = "15 Oct 2026, 12:51",
    status = ActivityStatus.ONGOING,
    bannerResId = R.drawable.wheelchairbanner,
    bannerDescription = "Wheelchair Banner",
    bannerBgColor = Green80,
    profileTitle = "Staff Profile",
    profileName = "John Sera",
    profileId = "Staff-123456",
    profileResId = R.drawable.profilepictureplaceholder,
    detailContent = ActivityDetailContent.TextDetails(
        items = listOf(
            DetailItem("Location", "C308"),
            DetailItem("Require Assistance", "False")
        )
    ),
    progressSteps = listOf(
        ProgressStep("Request has been delivered!", Green40),
        ProgressStep("A staff is on their way!", Cyan40)
    )
)

val medicineRequestMock = ActivityDetailData(
    requestId = "ID-5678-1234",
    title = "Medicine Request",
    date = "12 Sept 2026, 14:07",
    status = ActivityStatus.COMPLETED,
    bannerResId = R.drawable.wheelchairbanner,
    bannerDescription = "Wheelchair Banner",
    bannerBgColor = Green80,
    profileTitle = "Staff Profile",
    profileName = "Finn Hueman",
    profileId = "Staff-654321",
    profileResId = R.drawable.profilepictureplaceholder,
    detailContent = ActivityDetailContent.ProductDetails(
        products = listOf(
            ProductItem("Procold Flu & Batuk", 1, "Rp25.000"),
            ProductItem("Methylprednisolone", 1, "Rp100.000")
        ),
        totalPrice = "Rp125.000"
    ),
    progressSteps = listOf(
        ProgressStep("Request has been delivered!", Green40),
        ProgressStep("Ready for pickup!", Cyan40),
        ProgressStep("Request completed", Green80)
    )
)

val mockActivityDetailMap = mapOf(
    "ID-1234-5678" to wheelchairRequestMock,
    "ID-5678-1234" to medicineRequestMock
)
