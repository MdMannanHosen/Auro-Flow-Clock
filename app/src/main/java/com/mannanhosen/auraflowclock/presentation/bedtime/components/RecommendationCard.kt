package com.mannanhosen.auraflowclock.presentation.bedtime.components
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.test.isFocused
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mannanhosen.auraflowclock.data.model.entity.BedTime
import java.time.LocalTime

@Composable
fun RecommendationCard(
    rec : BedTime,
    iconImageVector: ImageVector,
    onClick: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    is24hour : Boolean = false
) {

    Card (
     colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.primaryContainer
     ),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
            .clickable { onClick(rec.localTime) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = iconImageVector,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier= Modifier.weight(1f))
          Row(
          verticalAlignment = Alignment.CenterVertically
          ) {
              Column(
                 horizontalAlignment = Alignment.End
              ) {
                  Text(
                      text = "${rec.minute / 60} hr ${rec.minute % 60} min",
                      style = MaterialTheme.typography.bodySmall
                  )
              }

             Icon(
                 imageVector = Icons.Default.ChevronRight,
                 contentDescription = null
             )
          }
         }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RecommendationCardPreview() {

    RecommendationCard(
        rec = BedTime(
            time = LocalTime.of(22, 30),
            minute = 7,
            cycle =5
        ),
        iconImageVector = Icons.Default.Alarm,
        onClick = {},
        is24hour = true

    )
}

