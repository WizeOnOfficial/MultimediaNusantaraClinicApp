package org.umn.multimedianusantaraclinic.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.umn.multimedianusantaraclinic.ui.screen.MenuItemData
import org.umn.multimedianusantaraclinic.ui.theme.Black

@Composable
fun HomeMenuItem(
    data: MenuItemData,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(62.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFD9D9D9))
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = data.title,
            fontSize = 12.sp,
            color = Black
        )
    }
}