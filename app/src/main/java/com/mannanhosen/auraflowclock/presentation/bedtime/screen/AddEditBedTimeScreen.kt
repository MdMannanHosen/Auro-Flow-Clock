package com.mannanhosen.auraflowclock.presentation.bedtime.screen
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.Alarm
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
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mannanhosen.auraflowclock.R
import com.mannanhosen.auraflowclock.presentation.bedtime.components.SleepCycleRing
import com.mannanhosen.auraflowclock.presentation.bedtime.components.TimeDial


// Background
private val AppBackground = Color(0xFF0B0F14)

// Cards / Bottom Sheets
private val CardBackground = Color(0xFF25282B)

// Primary Text / Icons
private val TextPrimary = Color(0xFFDDDAD5)

// Secondary Text
private val TextSecondary = Color(0xFF9CA3AF)

// Accent
private val AccentMint = Color(0xFF00BFA5)


// Border / Stroke
val BorderColor = Color(0xFF2A2F36)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditBedTimeScreen() {
    //scaffold
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Bedtime",
                            fontSize = 26.sp,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = "Wake up feeling refreshed ",
                            color = TextSecondary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal

                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppBackground,
                    titleContentColor = TextPrimary

                ),
                navigationIcon = {
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

            )
        },
        containerColor = AppBackground,

        ) { paddingValues ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
            .padding(horizontal= 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // Wakeup time card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 20.dp
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp),

                    ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = "Sun Icon",
                            tint = TextPrimary,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Wake up time",
                            color = TextPrimary,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = TextPrimary,
                                modifier = Modifier.size(24.dp)

                            )
                        }


                    }

                 // BedTime Circle design ui start



                    // BedTime Circle design ui end


//                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    )
                    {
                        Text(
                            text = "7:00",
                            fontSize = 26.sp,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "AM",
                            fontSize = 26.sp,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.width(4.dp))


                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(48.dp),
                                contentDescription = null,
                                imageVector = Icons.Default.KeyboardArrowDown,
                                tint = TextPrimary

                            )
                        }
                    }

                }
            }

            Spacer(Modifier.height(16.dp))
            SleepCycleRing(
                bedtimeText = "10:00 PM",
                cyclesText = "6 sleep cycles",
                durationText = "9h 00m"
            )

         Spacer(modifier = Modifier.height(16.dp))


            // Sleep cycle card
            Card(
                modifier = Modifier
                     .clickable(
                        indication = ripple(
                            color = AccentMint
                        ),
                interactionSource = remember { MutableInteractionSource() }
            ) {
                         println("Card clicked")// Card click
            }
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 20.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp),

                    ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Icon(
                            modifier = Modifier.background(
                                shape = CircleShape,
                                color = Color(0xFF141A20).copy(0.5f)
                            ).border(
                                width = 1.dp,
                                color = Color(0xFFD8D8D8).copy(alpha = 0.3f),
                                shape = CircleShape
                            ).padding(5.dp)
                                .size(24.dp),
                            painter = painterResource(R.drawable.sleepcycle),
                            contentDescription = "Sun Icon",
                            tint = TextPrimary,
                        )
                        Spacer(modifier = Modifier.width(12.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Sleep cycle",
                                color = TextPrimary,
                                fontSize = 16.sp
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth()

                            ) {
                                Text(
                                    text = "6 cycles",
                                    color = TextSecondary,
                                    fontSize = 10.sp
                                )

                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = ".",
                                    color = TextPrimary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold

                                )



                                Text(
                                    modifier = Modifier.padding(start = 5.dp),
                                    text = "10.00",
                                    color = TextSecondary,
                                    fontSize = 10.sp
                                )

                                Text(
                                    modifier = Modifier.padding(start = 5.dp),
                                    text = "PM",
                                    color = TextSecondary,
                                    fontSize = 10.sp
                                )

                                Spacer(modifier = Modifier.width(5.dp))


                                Text(
                                    text = ".",
                                    color = TextPrimary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    modifier = Modifier.padding(start = 5.dp),
                                    text = "9h 00m",
                                    color = TextSecondary,
                                    fontSize = 10.sp
                                )
                            }
                        }



                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = null,
                                tint = TextPrimary,
                                modifier = Modifier.size(24.dp)

                            )
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            // Asleep time card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        indication = ripple(
                            color = AccentMint
                        ),
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        println("Card clicked")// Card click
                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 20.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp),

                    ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Icon(
                            modifier = Modifier.background(
                                shape = CircleShape,
                                color = Color(0xFF141A20).copy(0.5f)
                            ).border(
                                width = 1.dp,
                                color = Color.White
                                .copy(alpha = 0.3f),
                                shape = CircleShape
                            ).padding(5.dp)
                                .size(24.dp),
                            painter = painterResource(R.drawable.moonstart),
                            contentDescription = "Sun Icon",
                            tint = Color.White,

                        )
                        Spacer(modifier = Modifier.width(12.dp))


                        Text(
                            text = "Time to fall asleep",
                            color = TextPrimary,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(32.dp)


                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = null,
                                tint = TextPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }


                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sleep Window card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        indication = ripple(
                            color = AccentMint),
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        println("Card clicked")// Card click
                    }, shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 20.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        Icon(
                            modifier = Modifier.background(
                                shape = CircleShape,
                                color = Color(0xFF141A20).copy(0.5f)
                            ).border(
                                width = 1.dp,
                                color = Color(0xFFD8D8D8).copy(alpha = 0.3f),
                                shape = CircleShape
                            ).padding(5.dp),
                            imageVector = Icons.Outlined.Alarm,
                            contentDescription = "Sun Icon",
                            tint = TextPrimary,
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(
                            modifier = Modifier.weight(1f)

                        ) {


                            Text(
                                text = "My sleep window",
                                color = TextPrimary,
                                fontSize = 16.sp,
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = "Going to bad around 10.00 PM and waking up around 7.00 AM gives you a complete sleep cycles before 7.00 AM",
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(36.dp))

                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = ripple(
                                color = AccentMint
                            ),
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            println("Card clicked")// Card click
                        }

                        .padding(start = 20.dp, end = 20.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(36.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentMint,
                        contentColor = TextPrimary
                    )
                ) {
                    Text(
                        text = "Done",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,

                        )
                }

        }

    }
}



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewAddEditBedTimeScreen() {
    AddEditBedTimeScreen()
}