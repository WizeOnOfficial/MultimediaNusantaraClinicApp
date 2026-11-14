package org.umn.multimedianusantaraclinic.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.umn.multimedianusantaraclinic.ui.theme.Green40

@Composable
fun ProfileCard(
    title: String,
    name: String,
    userId: String,
    imagePainter: Painter,
    modifier: Modifier = Modifier,
    onImageClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Foto Profil + Badge Kamera yang bisa diklik
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .then(
                            if (onImageClick != null) Modifier.clickable { onImageClick() }
                            else Modifier
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = imagePainter,
                        contentDescription = "Profile Picture of $name",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                    )

                    if (onImageClick != null) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(20.dp)
                                .background(Green40, CircleShape)
                                .padding(3.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Change Photo",
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        text = userId,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}