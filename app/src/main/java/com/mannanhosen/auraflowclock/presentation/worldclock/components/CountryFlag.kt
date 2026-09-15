package com.mannanhosen.auraflowclock.presentation.worldclock.components
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CountryFlag(
    countryCode: String,
    modifier: Modifier = Modifier,
    flagWidth: Dp = 40.dp,
    flagHeight: Dp = 24.dp
) {
    Canvas(modifier = modifier.size(flagWidth, flagHeight)) {
        when (countryCode.uppercase()) {
            // Tricolor vertical
            "FR", "FRA", "FRANCE" -> { // France
                val third = size.width / 3f
                drawRect(Color(0xFF0055A4), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(third, size.height))
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(third, 0f), androidx.compose.ui.geometry.Size(third, size.height))
                drawRect(Color(0xFFEF4135), androidx.compose.ui.geometry.Offset(2 * third, 0f), androidx.compose.ui.geometry.Size(third, size.height))
            }
            "IT", "ITA", "ITALY" -> { // Italy
                val third = size.width / 3f
                drawRect(Color(0xFF009246), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(third, size.height))
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(third, 0f), androidx.compose.ui.geometry.Size(third, size.height))
                drawRect(Color(0xFFCE2B37), androidx.compose.ui.geometry.Offset(2 * third, 0f), androidx.compose.ui.geometry.Size(third, size.height))
            }
            "IE", "IRL", "IRELAND" -> { // Ireland
                val third = size.width / 3f
                drawRect(Color(0xFF169B62), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(third, size.height))
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(third, 0f), androidx.compose.ui.geometry.Size(third, size.height))
                drawRect(Color(0xFFFF883E), androidx.compose.ui.geometry.Offset(2 * third, 0f), androidx.compose.ui.geometry.Size(third, size.height))
            }
            "BE", "BEL", "BELGIUM" -> { // Belgium
                val third = size.width / 3f
                drawRect(Color(0xFF000000), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(third, size.height))
                drawRect(Color(0xFFFFD90C), androidx.compose.ui.geometry.Offset(third, 0f), androidx.compose.ui.geometry.Size(third, size.height))
                drawRect(Color(0xFFEF3340), androidx.compose.ui.geometry.Offset(2 * third, 0f), androidx.compose.ui.geometry.Size(third, size.height))
            }
            // Tricolor horizontal
            "DE", "DEU", "GERMANY" -> {
                val third = size.height / 3f
                drawRect(Color.Black, androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color(0xFFD00C33), androidx.compose.ui.geometry.Offset(0f, third), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color(0xFFFFCC00), androidx.compose.ui.geometry.Offset(0f, 2 * third), androidx.compose.ui.geometry.Size(size.width, third))
            }
            "RU", "RUS", "RUSSIA" -> {
                val third = size.height / 3f
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color(0xFF0039A6), androidx.compose.ui.geometry.Offset(0f, third), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color(0xFFD52B1E), androidx.compose.ui.geometry.Offset(0f, 2 * third), androidx.compose.ui.geometry.Size(size.width, third))
            }
            "NL", "NLD", "NETHERLANDS" -> {
                val third = size.height / 3f
                drawRect(Color(0xFF21468B), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(0f, third), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color(0xFFAE1C28), androidx.compose.ui.geometry.Offset(0f, 2 * third), androidx.compose.ui.geometry.Size(size.width, third))
            }
            "LU", "LUX", "LUXEMBOURG" -> {
                val third = size.height / 3f
                drawRect(Color(0xFFED2939), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(0f, third), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color(0xFF00A1DE), androidx.compose.ui.geometry.Offset(0f, 2 * third), androidx.compose.ui.geometry.Size(size.width, third))
            }
            // Bicolor vertical
            "PL", "POL", "POLAND" -> {
                val half = size.height / 2f
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, half))
                drawRect(Color(0xFFD4213D), androidx.compose.ui.geometry.Offset(0f, half), androidx.compose.ui.geometry.Size(size.width, half))
            }
            "UA", "UKR", "UKRAINE" -> {
                val half = size.height / 2f
                drawRect(Color(0xFF0057B7), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, half))
                drawRect(Color(0xFFFFD700), androidx.compose.ui.geometry.Offset(0f, half), androidx.compose.ui.geometry.Size(size.width, half))
            }
            "ID", "IDN", "INDONESIA" -> {
                val half = size.height / 2f
                drawRect(Color(0xFFFF0000), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, half))
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(0f, half), androidx.compose.ui.geometry.Size(size.width, half))
            }
            "TH", "THA", "THAILAND" -> {
                val fifth = size.height / 5f
                drawRect(Color.Red, androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, fifth))
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(0f, fifth), androidx.compose.ui.geometry.Size(size.width, fifth))
                drawRect(Color.Blue, androidx.compose.ui.geometry.Offset(0f, 2 * fifth), androidx.compose.ui.geometry.Size(size.width, fifth))
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(0f, 3 * fifth), androidx.compose.ui.geometry.Size(size.width, fifth))
                drawRect(Color.Red, androidx.compose.ui.geometry.Offset(0f, 4 * fifth), androidx.compose.ui.geometry.Size(size.width, fifth))
            }
            // Bicolor horizontal
            "JP", "JPN", "JAPAN" -> {
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, size.height))
                drawCircle(Color(0xFFBC002D), center = androidx.compose.ui.geometry.Offset(size.width / 2f, size.height / 2f), radius = size.height * 0.3f)
            }
            "BD", "BGD", "BANGLADESH" -> {
                drawRect(Color(0xFF006A4E), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, size.height))
                drawCircle(Color(0xFFFF0000), center = androidx.compose.ui.geometry.Offset(size.width * 0.4f, size.height / 2f), radius = size.height * 0.25f)
            }
            "CH", "CHE", "SWITZERLAND" -> {
                drawRect(Color(0xFFD52B1E), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, size.height))
            }
            "NO", "NOR", "NORWAY" -> {
                drawRect(Color(0xFFBA0C2F), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, size.height))
            }
            "SE", "SWE", "SWEDEN" -> {
                drawRect(Color(0xFF006AA7), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, size.height))
            }
            "FI", "FIN", "FINLAND" -> {
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, size.height))
            }
            // Tricolor vertical (continued)
            "RO", "ROU", "ROMANIA" -> {
                val third = size.width / 3f
                drawRect(Color(0xFF002B7F), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(third, size.height))
                drawRect(Color(0xFFFFD700), androidx.compose.ui.geometry.Offset(third, 0f), androidx.compose.ui.geometry.Size(third, size.height))
                drawRect(Color(0xFFCE1126), androidx.compose.ui.geometry.Offset(2 * third, 0f), androidx.compose.ui.geometry.Size(third, size.height))
            }
            // More simple flags
            "ES", "ESP", "SPAIN" -> {
                val third = size.height / 3f
                drawRect(Color(0xFFAA151B), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color(0xFFFFC400), androidx.compose.ui.geometry.Offset(0f, third), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color(0xFFAA151B), androidx.compose.ui.geometry.Offset(0f, 2 * third), androidx.compose.ui.geometry.Size(size.width, third))
            }
            "PT", "PRT", "PORTUGAL" -> {
                val third = size.width / 3f
                drawRect(Color(0xFF006600), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(third, size.height))
                drawRect(Color(0xFFFF0000), androidx.compose.ui.geometry.Offset(third, 0f), androidx.compose.ui.geometry.Size(2 * third, size.height))
            }
            "GR", "GRC", "GREECE" -> {
                val ninth = size.height / 9f
                for (i in 0..8) {
                    drawRect(if (i % 2 == 0) Color(0xFF0D5EAF) else Color.White, androidx.compose.ui.geometry.Offset(0f, i * ninth), androidx.compose.ui.geometry.Size(size.width, ninth))
                }
            }
            "TR", "TUR", "TURKEY" -> {
                drawRect(Color(0xFFE30A17), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, size.height))
            }
            "EG", "EGY", "EGYPT" -> {
                val third = size.height / 3f
                drawRect(Color(0xFFCE1126), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(0f, third), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color(0xFF000000), androidx.compose.ui.geometry.Offset(0f, 2 * third), androidx.compose.ui.geometry.Size(size.width, third))
            }
            "ZA", "ZAF", "SOUTH AFRICA" -> {
                drawRect(Color(0xFF007847), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, size.height))
            }
            "NG", "NGA", "NIGERIA" -> {
                val third = size.width / 3f
                drawRect(Color(0xFF008753), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(third, size.height))
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(third, 0f), androidx.compose.ui.geometry.Size(third, size.height))
                drawRect(Color(0xFF008753), androidx.compose.ui.geometry.Offset(2 * third, 0f), androidx.compose.ui.geometry.Size(third, size.height))
            }
            "KE", "KEN", "KENYA" -> {
                val third = size.height / 3f
                drawRect(Color.Black, androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(0f, third), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color.Red, androidx.compose.ui.geometry.Offset(0f, 2 * third), androidx.compose.ui.geometry.Size(size.width, third))
            }
            "ET", "ETH", "ETHIOPIA" -> {
                val third = size.height / 3f
                drawRect(Color(0xFF078930), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color(0xFFFFD600), androidx.compose.ui.geometry.Offset(0f, third), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color(0xFFDA121A), androidx.compose.ui.geometry.Offset(0f, 2 * third), androidx.compose.ui.geometry.Size(size.width, third))
            }
            // Americas
            "US", "USA", "UNITED STATES" -> {
                val stripeHeight = size.height / 6f
                for (i in 0..5) {
                    drawRect(if (i % 2 == 0) Color(0xFFB22234) else Color.White, androidx.compose.ui.geometry.Offset(0f, i * stripeHeight), androidx.compose.ui.geometry.Size(size.width, stripeHeight))
                }
            }
            "CA", "CAN", "CANADA" -> {
                val third = size.width / 3f
                drawRect(Color.Red, androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(third, size.height))
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(third, 0f), androidx.compose.ui.geometry.Size(third, size.height))
                drawRect(Color.Red, androidx.compose.ui.geometry.Offset(2 * third, 0f), androidx.compose.ui.geometry.Size(third, size.height))
            }
            "MX", "MEX", "MEXICO" -> {
                val third = size.width / 3f
                drawRect(Color.Green, androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(third, size.height))
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(third, 0f), androidx.compose.ui.geometry.Size(third, size.height))
                drawRect(Color.Red, androidx.compose.ui.geometry.Offset(2 * third, 0f), androidx.compose.ui.geometry.Size(third, size.height))
            }
            "BR", "BRA", "BRAZIL" -> {
                drawRect(Color(0xFF009C3B), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, size.height))
            }
            "AR", "ARG", "ARGENTINA" -> {
                val third = size.height / 3f
                drawRect(Color(0xFF74ACDF), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(0f, third), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color(0xFF74ACDF), androidx.compose.ui.geometry.Offset(0f, 2 * third), androidx.compose.ui.geometry.Size(size.width, third))
            }
            "CL", "CHL", "CHILE" -> {
                val half = size.width / 2f
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(half, size.height))
                drawRect(Color(0xFFD52B1E), androidx.compose.ui.geometry.Offset(half, 0f), androidx.compose.ui.geometry.Size(half, size.height))
            }
            // Oceania
            "AU", "AUS", "AUSTRALIA" -> {
                drawRect(Color(0xFF00247D), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, size.height))
            }
            "NZ", "NZL", "NEW ZEALAND" -> {
                drawRect(Color(0xFF00247D), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, size.height))
            }
            // Middle East
            "SA", "SAU", "SAUDI ARABIA" -> {
                drawRect(Color(0xFF006C35), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, size.height))
            }
            "IR", "IRN", "IRAN" -> {
                val third = size.height / 3f
                drawRect(Color(0xFF239F40), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(0f, third), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color(0xFFDA0000), androidx.compose.ui.geometry.Offset(0f, 2 * third), androidx.compose.ui.geometry.Size(size.width, third))
            }
            "IQ", "IRQ", "IRAQ" -> {
                val third = size.height / 3f
                drawRect(Color(0xFFCE1126), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(0f, third), androidx.compose.ui.geometry.Size(size.width, third))
                drawRect(Color(0xFF000000), androidx.compose.ui.geometry.Offset(0f, 2 * third), androidx.compose.ui.geometry.Size(size.width, third))
            }
            // Asia
            "CN", "CHN", "CHINA" -> {
                drawRect(Color(0xFFDE2910), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, size.height))
            }
            "KR", "KOR", "SOUTH KOREA" -> {
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, size.height))
            }
            "SG", "SGP", "SINGAPORE" -> {
                val half = size.height / 2f
                drawRect(Color.Red, androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, half))
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(0f, half), androidx.compose.ui.geometry.Size(size.width, half))
            }
            "MY", "MYS", "MALAYSIA" -> {
                val half = size.height / 2f
                drawRect(Color(0xFF010066), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, half))
                drawRect(Color(0xFFFFD100), androidx.compose.ui.geometry.Offset(0f, half), androidx.compose.ui.geometry.Size(size.width, half))
            }
            "VN", "VNM", "VIETNAM" -> {
                drawRect(Color(0xFFDA251D), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, size.height))
            }
            // Africa
            "DZ", "DZA", "ALGERIA" -> {
                val half = size.width / 2f
                drawRect(Color.White, androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(half, size.height))
                drawRect(Color(0xFF006233), androidx.compose.ui.geometry.Offset(half, 0f), androidx.compose.ui.geometry.Size(half, size.height))
            }
            "MA", "MAR", "MOROCCO" -> {
                drawRect(Color(0xFFc1272d), androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, size.height))
            }
            // Default
            else -> {
                drawRect(Color.LightGray, androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Size(size.width, size.height))
            }
        }
    }
}
