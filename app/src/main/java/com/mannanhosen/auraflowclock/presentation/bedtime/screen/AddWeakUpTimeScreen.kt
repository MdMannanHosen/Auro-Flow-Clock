package com.mannanhosen.auraflowclock.presentation.bedtime.screen
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mannanhosen.auraflowclock.presentation.bedtime.components.TimeDial

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
private val Bg        = Color(0xFF202224)
private val Card      = Color(0xFF2A2C30)
private val Accent    = Color(0xFFE91E63)
private val Border    = Color(0xFF3A3C40)
private val TextPri   = Color(0xFFDDDAD5)
private val TextSec   = Color(0xFFE91E63)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWakeUpTimeScreen() {

    val hour by remember { mutableStateOf(7) }
    var isPm by remember { mutableStateOf(false) }
    val amPm = if(isPm) "PM" else "AM"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Bedtime", // ঠিক করা হয়েছে (Bed Time থেকে Bedtime)
                            style = TextStyle(
                                fontSize = 24.sp,
                                color = Color(0xFFDDDAD5),
                                fontWeight = FontWeight.SemiBold
                            )
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Wake up feeling refreshed", // বানান ভুল ঠিক করা হয়েছে ('Weak up felling' থেকে)
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color(0xFFA0A0A0),
                                fontWeight = FontWeight.Normal // W900 থেকে নরমাল করা হয়েছে যাতে প্রফেশনাল দেখায়
                            )
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            modifier = Modifier.size(24.dp),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF17191B) // ১ নম্বরের মতো পারফেক্ট ডার্ক শেড
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF17191B))
                .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF25282B) // কার্ডের কালার ১ নম্বরের সাথে ম্যাচ করা হয়েছে
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {

                    // Top row: icon + "Wake up at"
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFFA0A0A0)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Wake up at",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFA0A0A0)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Spacer(modifier = Modifier.width(80.dp))

                        Text(
                            text = "7:00 AM",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Light, // ১ নম্বরের ফন্ট ওয়েট অনুযায়ী স্লিম লুক
                            color = Color(0xFFDDDAD5)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(
                                onClick = {},
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowUp,
                                    contentDescription = null,
                                    tint = Color(0xFFB8B8B8),
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            IconButton(
                                onClick = {},
                                modifier = Modifier.size(24.dp)
                            ) {

                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Color(0xFFB8B8B8),
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(48.dp))
            TimeDial(
                hour = 7,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            // ---- AM / PM segmented control ----
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(32.dp))
                    .border(2.dp, Border, RoundedCornerShape(32.dp))
                    .padding(4.dp)

            ) {
                listOf(false to "AM", true to "PM").forEach { (pm, label) ->
                    val selected = isPm == pm
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .background(
                                if (selected) Color(0xFF00E5A0) else Color.Transparent,
                                RoundedCornerShape(9.dp)
                            )
                            .clickable { isPm = pm }
                    ) {
                        Text(
                            label,
                            color = if (selected) Color.White else Color(0xFF00E5A0),
                            fontSize = 16.sp, fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

      // Done Button
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E5A0)),
                ){
                Text("Done", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }



        }
         }
        }



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAddWakeUpTimeScreen() {
    AddWakeUpTimeScreen()
}