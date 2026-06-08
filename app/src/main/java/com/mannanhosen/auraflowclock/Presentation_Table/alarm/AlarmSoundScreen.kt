package com.mannanhosen.auraflowclock.Presentation_Table.alarm

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


val BackgroundColor = Color(0xFF202224)
val CardBackgroundColor = Color(0xFF2A2D30)
val AccentColor = Color(0xFFE91E63)
val TextPrimary = Color(0xFFFFFFFF)
val TextSecondary = Color(0xFF9E9E9E)

@ExperimentalMaterial3Api
@Composable
fun AlarmSoundScreen(navController: NavController) {

    var isAlarmSoundOn by remember { mutableStateOf(true) } // ডিফল্ট অন রাখা হলো যাতে স্লাইডার বোঝা যায়
    var selectedOption by remember { mutableStateOf("Ringtone") } // "Spotify" অথবা "Ringtone"
    var volumePosition by remember { mutableFloatStateOf(0.7f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Alarm sound",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundColor
                )
            )
        },
        containerColor = BackgroundColor // পুরো স্ক্রিনের ব্যাকগ্রাউন্ড কালার এখান থেকে কাজ করবে
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // ১. প্রধান অন/অফ (Master Switch) কার্ড
            SettingsSwitch(
                title = "Sound",
                checked = isAlarmSoundOn,
                onCheckedChange = { isAlarmSoundOn = it }
            )

            Spacer(modifier = Modifier.height(8.dp))


            SettingsSwitch(
                title = "Read time and alarm name aloud",
                checked = isAlarmSoundOn,
                onCheckedChange = { isAlarmSoundOn = it }
            )

            Spacer(modifier = Modifier.height(8.dp))
            SettingsSwitch(
                title = "Gradually increase volume",
                checked = isAlarmSoundOn,
                onCheckedChange = { isAlarmSoundOn = it }
            )

            Spacer(modifier = Modifier.height(8.dp))
     // ata te click korle ringtoon er jonno akti alada a
            SettingsSwitch(
                title = "Ringtoon",
                checked = isAlarmSoundOn,
                onCheckedChange = { isAlarmSoundOn = it }
            )



            Spacer(modifier = Modifier.height(16.dp))

            // ২. সাউন্ড সোর্স অপশন কার্ড (Ringtone / Spotify)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackgroundColor)
            ) {
                Column {
                    // রিংটোন অপশন
                    SoundOptionItem(
                        title = "Ringtone",
                        subtitle = "Default ringtone",
                        icon = {
                            Icon(
                                imageVector = Icons.Default.MusicNote,
                                contentDescription = "Ringtone",
                                tint = if (isAlarmSoundOn) AccentColor else TextSecondary
                            )
                        },
                        isSelected = selectedOption == "Ringtone",
                        isEnabled = isAlarmSoundOn,
                        onClick = { selectedOption = "Ringtone" }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        color = Color.Gray.copy(alpha = 0.2f)
                    )



                }
            }

            Spacer(modifier = Modifier.weight(1.0f))

            // ৩. ভলিউম স্লাইডার সেকশন
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.VolumeUp,
                    contentDescription = "Volume",
                    tint = if (isAlarmSoundOn) TextPrimary.copy(alpha = 0.7f) else TextSecondary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Slider(
                    value = volumePosition,
                    onValueChange = { if (isAlarmSoundOn) volumePosition = it },
                    enabled = isAlarmSoundOn,
                    colors = SliderDefaults.colors(
                        thumbColor = if (isAlarmSoundOn) AccentColor else TextSecondary,
                        activeTrackColor = AccentColor,
                        inactiveTrackColor = AccentColor.copy(alpha = 0.2f),
                        disabledThumbColor = Color.Gray,
                        disabledActiveTrackColor = Color.DarkGray
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// প্রতিটা অপশনের (Row) জন্য কাস্টম রিইউজেবল কম্পোজেবল
@Composable
fun SoundOptionItem(
    title: String,
    subtitle: String,
    icon: @Composable () -> Unit,
    isSelected: Boolean,
    isEnabled: Boolean,
    onClick: () -> Unit
) {}

@Composable
fun SettingSwitch(
    title : String,
    checked : Boolean,
    onCheckChange : (Boolean) -> Unit,
    onClick: () -> Unit

) {
  Card(
      modifier = Modifier
          .fillMaxWidth()
          .then(if(onClick !=null) Modifier.clickable { onClick() } else Modifier),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2C30))
  ) {

      Row(
          modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 8.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
      ) {

          Text(
              text = title,
              color = Color.White,
              fontSize = 16.sp,
              fontWeight = FontWeight.Normal
          )

          Switch(
              checked = checked,
              onCheckedChange = onCheckChange,
              colors = SwitchDefaults.colors(
                  checkedIconColor = Color(0xFFE91E63),
                  checkedTrackColor = Color(0xFFE91E63) ,//.copy(alpha = 0.5f),
                  uncheckedThumbColor = Color.Gray,
                  uncheckedTrackColor = Color(0xFF3A3C40)
              )
          )

      }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun AlamSoundScreenPreview() {
    val dummyNavController = rememberNavController()
    AlarmSoundScreen(navController = dummyNavController)
}