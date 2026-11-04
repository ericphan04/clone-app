package com.clone.meetup.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clone.meetup.R

// Extracted verbatim from com.meetup res/values/colors.xml + values-night/colors.xml
object MeetupColors {
    val Surface = Color(0xFFFFFFFF)
    val SurfaceElevated = Color(0xFFF6F7F8)
    val Layer2 = Color(0xFF4267B2)

    val Grey1 = Color(0xFF8E8E93)
    val Grey3 = Color(0xFFC7C7CC)
    val Grey4 = Color(0xFFD1D1D6)
    val Grey5 = Color(0xFFE5E5EA)
    val Grey6 = Color(0xFFF2F2F7)

    val TextPrimary = Color(0xFF212121)
    val TextSecondary = Color(0x99212121)
    val TextTertiary = Color(0x66212121)
    val TextInverse = Color(0xDEFFFFFF)

    val Viridian = Color(0xFF008294)
    val Peach = Color(0xFFF65858)
    val Indigo = Color(0xFF00455D)
    val Lime = Color(0xFF32D74B)
    val OldGold = Color(0xFF7D694A)
    val AlertRed = Color(0xFFC80000)
    val Success = Color(0xFF07B203)

    val TextEntryBg = Color(0xFFEDEDED)
    val TextHint = Color(0x66212121)
    val Highlight = Color(0x66008294)
}

object MeetupColorsDark {
    val Surface = Color(0xFF121212)
    val SurfaceElevated = Color(0xFF232425)
    val Layer2 = Color(0xFF222222)

    val Grey1 = Color(0xFF8E8E93)
    val Grey3 = Color(0xFF48484A)
    val Grey4 = Color(0xFF3A3A3C)
    val Grey5 = Color(0xFF2C2C2E)
    val Grey6 = Color(0xFF1C1C1E)

    val TextPrimary = Color(0xDEFFFFFF)
    val TextSecondary = Color(0x99FFFFFF)
    val TextTertiary = Color(0x66FFFFFF)
    val TextInverse = Color(0xFF212121)

    val Viridian = Color(0xFF008294)
    val Peach = Color(0xFFF65858)
    val Indigo = Color(0xFF00455D)
    val Lime = Color(0xFF32D74B)
    val OldGold = Color(0xFFD6BE98)
    val AlertRed = Color(0xFFC80000)
    val Success = Color(0xFF07B203)

    val TextEntryBg = Color(0xFF2C2C2E)
    val TextHint = Color(0x66FFFFFF)
    val Highlight = Color(0x660098AB)
}

// Graphik — shipped in com.meetup res/font. License: Commercial (Commercial Type).
// Swap for Inter if you ship this anywhere public.
val GraphikRegular = FontFamily(Font(R.font.graphik_regular_font, FontWeight.Normal))
val GraphikMedium = FontFamily(Font(R.font.graphik_medium_font, FontWeight.Medium))
val GraphikSemibold = FontFamily(Font(R.font.graphik_semibold_font, FontWeight.SemiBold))
val GraphikBold = FontFamily(Font(R.font.graphik_bold_font, FontWeight.Bold))

// Mirrors Meetup.Text.* styles exactly: 16/14/12 body ladder, 32/20/16 title ladder.
val MeetupTypography = Typography(
    displayLarge = TextStyle(fontFamily = GraphikSemibold, fontSize = 32.sp, lineHeight = 38.sp, letterSpacing = 0.sp),
    headlineMedium = TextStyle(fontFamily = GraphikSemibold, fontSize = 20.sp, lineHeight = 24.sp, letterSpacing = 0.sp),
    titleMedium = TextStyle(fontFamily = GraphikMedium, fontSize = 16.sp, lineHeight = 22.sp, letterSpacing = 0.sp),
    bodyLarge = TextStyle(fontFamily = GraphikRegular, fontSize = 16.sp, lineHeight = 22.sp, letterSpacing = 0.sp),
    bodyMedium = TextStyle(fontFamily = GraphikRegular, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.sp),
    bodySmall = TextStyle(fontFamily = GraphikRegular, fontSize = 12.sp, lineHeight = 18.sp, letterSpacing = 0.sp),
    labelLarge = TextStyle(fontFamily = GraphikMedium, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.sp),
    labelSmall = TextStyle(fontFamily = GraphikMedium, fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 0.sp),
)

// res/values/dimens.xml — the real spacing scale, not an invented 4/8/12 ladder
object MeetupSpacing {
    val xtiny = 6.dp
    val tiny = 8.dp
    val small = 12.dp
    val middling = 14.dp
    val normal = 16.dp
    val stripe = 24.dp
    val table = 32.dp
    val large = 40.dp
    val xlarge = 80.dp
    val separator = 4.dp
}

object MeetupShape {
    val textEntry = 4.dp
    val button = 8.dp
    val icon = 2.dp
    val divider = 1.dp
}

object MeetupSizes {
    val avatarTiny = 26.dp
    val avatarSmall = 36.dp
    val avatarMedium = 48.dp
    val avatarLarge = 72.dp
    val tabBarHeight = 48.dp
    val touchTarget = 48.dp
    val yourGroupCardWidth = 120.dp
    val homeCategoryWidth = 150.dp
    val eventPreviewRowHeight = 200.dp
}

data class MeetupPalette(
    val surface: Color,
    val surfaceElevated: Color,
    val layer2: Color,
    val grey1: Color,
    val grey3: Color,
    val grey4: Color,
    val grey5: Color,
    val grey6: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textInverse: Color,
    val viridian: Color,
    val peach: Color,
    val indigo: Color,
    val lime: Color,
    val oldGold: Color,
    val alertRed: Color,
    val success: Color,
    val textEntryBg: Color,
    val textHint: Color,
    val highlight: Color,
)

val LightPalette = MeetupPalette(
    surface = MeetupColors.Surface,
    surfaceElevated = MeetupColors.SurfaceElevated,
    layer2 = MeetupColors.Layer2,
    grey1 = MeetupColors.Grey1,
    grey3 = MeetupColors.Grey3,
    grey4 = MeetupColors.Grey4,
    grey5 = MeetupColors.Grey5,
    grey6 = MeetupColors.Grey6,
    textPrimary = MeetupColors.TextPrimary,
    textSecondary = MeetupColors.TextSecondary,
    textTertiary = MeetupColors.TextTertiary,
    textInverse = MeetupColors.TextInverse,
    viridian = MeetupColors.Viridian,
    peach = MeetupColors.Peach,
    indigo = MeetupColors.Indigo,
    lime = MeetupColors.Lime,
    oldGold = MeetupColors.OldGold,
    alertRed = MeetupColors.AlertRed,
    success = MeetupColors.Success,
    textEntryBg = MeetupColors.TextEntryBg,
    textHint = MeetupColors.TextHint,
    highlight = MeetupColors.Highlight,
)

val DarkPalette = MeetupPalette(
    surface = MeetupColorsDark.Surface,
    surfaceElevated = MeetupColorsDark.SurfaceElevated,
    layer2 = MeetupColorsDark.Layer2,
    grey1 = MeetupColorsDark.Grey1,
    grey3 = MeetupColorsDark.Grey3,
    grey4 = MeetupColorsDark.Grey4,
    grey5 = MeetupColorsDark.Grey5,
    grey6 = MeetupColorsDark.Grey6,
    textPrimary = MeetupColorsDark.TextPrimary,
    textSecondary = MeetupColorsDark.TextSecondary,
    textTertiary = MeetupColorsDark.TextTertiary,
    textInverse = MeetupColorsDark.TextInverse,
    viridian = MeetupColorsDark.Viridian,
    peach = MeetupColorsDark.Peach,
    indigo = MeetupColorsDark.Indigo,
    lime = MeetupColorsDark.Lime,
    oldGold = MeetupColorsDark.OldGold,
    alertRed = MeetupColorsDark.AlertRed,
    success = MeetupColorsDark.Success,
    textEntryBg = MeetupColorsDark.TextEntryBg,
    textHint = MeetupColorsDark.TextHint,
    highlight = MeetupColorsDark.Highlight,
)

val LocalMeetupColors = staticCompositionLocalOf { LightPalette }

@Composable
fun MeetupTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val palette = if (darkTheme) DarkPalette else LightPalette

    val scheme = if (darkTheme) {
        darkColorScheme(
            primary = palette.viridian,
            onPrimary = palette.surface,
            secondary = palette.peach,
            background = palette.surface,
            onBackground = palette.textPrimary,
            surface = palette.surface,
            onSurface = palette.textPrimary,
            surfaceVariant = palette.surfaceElevated,
            error = palette.alertRed,
        )
    } else {
        lightColorScheme(
            primary = palette.viridian,
            onPrimary = Color.White,
            secondary = palette.peach,
            background = palette.surface,
            onBackground = palette.textPrimary,
            surface = palette.surface,
            onSurface = palette.textPrimary,
            surfaceVariant = palette.surfaceElevated,
            error = palette.alertRed,
        )
    }

    CompositionLocalProvider(LocalMeetupColors provides palette) {
        MaterialTheme(
            colorScheme = scheme,
            typography = MeetupTypography,
            content = content,
        )
    }
}

object MeetupTheme {
    val colors: MeetupPalette
        @Composable get() = LocalMeetupColors.current
}
