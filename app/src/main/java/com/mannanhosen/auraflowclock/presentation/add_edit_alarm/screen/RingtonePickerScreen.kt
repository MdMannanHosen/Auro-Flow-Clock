package com.mannanhosen.auraflowclock.presentation.add_edit_alarm.screen
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class RingtoneItem(val title: String, val uri: Uri)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RingtonePickerScreen(
    currentUri: String?,
    onNavigateBack: () -> Unit,
    onRingtoneSelected: (title: String, uriString: String) -> Unit
) {
    val context = LocalContext.current

    // ✅ সিস্টেমের সব অ্যালার্ম টোন লোড করা হচ্ছে
    val ringtones = remember {
        val manager = RingtoneManager(context).apply {
            setType(RingtoneManager.TYPE_ALARM)
        }
        val cursor = manager.cursor
        val list = mutableListOf<RingtoneItem>()
        while (cursor.moveToNext()) {
            val title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX)
            val uri = manager.getRingtoneUri(cursor.position)
            list.add(RingtoneItem(title, uri))
        }
        list
    }

    var selectedUri by remember { mutableStateOf(currentUri) }
    var playingUri by remember { mutableStateOf<String?>(null) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    fun stopPreview() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        playingUri = null
    }

    fun playPreview(item: RingtoneItem) {
        stopPreview()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(context, item.uri)
            setAudioStreamType(AudioManager.STREAM_ALARM)
            prepare()
            start()
            setOnCompletionListener { playingUri = null }
        }
        playingUri = item.uri.toString()
    }

    // ✅ স্ক্রিন ছাড়লে preview বন্ধ হবে, নাহলে ব্যাকগ্রাউন্ডে বাজতেই থাকবে
    DisposableEffect(Unit) {
        onDispose { stopPreview() }
    }

    Scaffold(
        containerColor = Color(0xFF202224),
        topBar = {
            TopAppBar(
                title = { Text("Choose Ringtone", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        stopPreview()
                        onNavigateBack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    TextButton(onClick = {
                        val chosen = ringtones.find { it.uri.toString() == selectedUri }
                        stopPreview()
                        if (chosen != null) {
                            onRingtoneSelected(chosen.title, chosen.uri.toString())
                        }
                        onNavigateBack()
                    }) {
                        Text("Save", color = Color(0xFF00E5A0))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF202224))
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(ringtones) { item ->
                val uriString = item.uri.toString()
                val isSelected = uriString == selectedUri
                val isPlaying = uriString == playingUri

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedUri = uriString
                            playPreview(item) // ✅ সিলেক্ট করলেই প্রিভিউ বাজবে
                        }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        if (isPlaying) stopPreview() else playPreview(item)
                    }) {
                        Icon(
                            if (isPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                            contentDescription = "Preview",
                            tint = Color(0xFF9E9E9E)
                        )
                    }
                    Text(
                        item.title,
                        color = Color.White,
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f).padding(start = 8.dp)
                    )
                    if (isSelected) {
                        Icon(Icons.Default.Check, contentDescription = "Selected", tint = Color(0xFF00E5A0))
                    }
                }
            }
        }
    }
}