package com.mannanhosen.auraflowclock.presentation.Navigation

sealed class AppRoutes(val routes: String) {

    object Home : AppRoutes("home")
    object AlarmSound : AppRoutes("alarm_sound")
    object Snooze : AppRoutes("snooze_screen")       // ✅ পুরনো ডুপ্লিকেট বাদ দিয়ে এখানেই ঠিক করা হলো
    object Vibration : AppRoutes("vibration_screen")  // ✅ NavGraph-এর সাথে মিলিয়ে ঠিক করা হলো
    object RingtonePicker : AppRoutes("ringtone_picker_screen")
    object ChallengeMethod : AppRoutes("challenge_method_screen")
    object QrSetup : AppRoutes("qr_setup_screen")
    object MathChallengeDismiss : AppRoutes("math_challenge_dismiss")
    object ShakeChallengeDismiss : AppRoutes("shake_challenge_dismiss")
    object WorldClockScreen : AppRoutes("WorldClockScreen")
    object CreateAlarm : AppRoutes("create_alarm")
    object DiscoverDisplay : AppRoutes("DiscoverDisplay")
    object TimeZoneListScreen : AppRoutes("timezone_list")

}