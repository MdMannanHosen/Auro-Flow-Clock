package com.mannanhosen.auraflowclock.presentation.bedtime.screen
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mannanhosen.auraflowclock.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeakUpTimeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {

                        Text(
                            text = "Bed Time",
                            style = TextStyle(
                                color = Color(0xFFDDDAD5),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "Wake up feeling refreshed",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W900,
                                color = Color(0xFFA0A0A0)
                            )
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF202224),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            contentDescription = null,
                            imageVector = Icons.Default.ArrowBack,
                            tint = Color.White
                        )
                    }
                }

            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF202224))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {

            Spacer(modifier = Modifier.height(50.dp))
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(Color(0x19D6D2D0)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.wakeuptime),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "No sleep data yet",
                style = TextStyle(
                    color = Color(0xFFDDDAD5),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold

                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Set your wake-up time to see \n your recommended bedtime",
                style = TextStyle(
                    fontSize =  16.sp,
                    color = Color(0xFFDDDAD5),
                    fontWeight = FontWeight.SemiBold
                )

            )

            Spacer(modifier = Modifier.height(32.dp))


            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
                    .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00E5A0)
                )
            ) {
                Text("Set  Wake up time",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    ))
            }


        }

    }


}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewWeakUpTimeScreen() {
    WeakUpTimeScreen()
}
