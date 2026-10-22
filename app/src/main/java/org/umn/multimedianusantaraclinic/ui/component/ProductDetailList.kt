package org.umn.multimedianusantaraclinic.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.umn.multimedianusantaraclinic.ui.theme.Gray

data class ProductItem(
    val name: String,
    val quantity: Int,
    val price: String,
    val imageUrl: String? = null
)

@Composable
fun ProductDetailList(
    products: List<ProductItem>,
    totalPrice: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        products.forEach { product ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Image thumbnail box
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                )

                Column(horizontalAlignment = Alignment.End) {
                    Text(text = product.name, style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = "${product.quantity} x ${product.price}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray
                    )
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Total", style = MaterialTheme.typography.titleMedium)
            Text(text = totalPrice, style = MaterialTheme.typography.titleMedium)
        }
    }
}