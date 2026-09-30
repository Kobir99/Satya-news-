package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

enum class ThemeMode(val labelBn: String, val labelEn: String, val labelHi: String) {
    SYSTEM("সিস্টেম ডিফল্ট", "System Default", "सिस्टम डिफ़ॉल्ट"),
    LIGHT("লাইট মোড", "Light Mode", "लाइट मोड"),
    DARK("ডার্ক মোড", "Dark Mode", "डार्क मोड")
}

val LocalThemeMode = staticCompositionLocalOf { ThemeMode.SYSTEM }
val LocalIsDarkTheme = staticCompositionLocalOf { false }

private val EditorialDarkColorScheme = darkColorScheme(
    primary = CobaltPrimaryLight,
    onPrimary = EditorialDarkBg,
    primaryContainer = CobaltContainerDark,
    onPrimaryContainer = CobaltContainerLight,

    secondary = DevelopingAmberLight,
    onSecondary = EditorialDarkBg,
    secondaryContainer = AmberContainerDark,
    onSecondaryContainer = AmberContainerLight,

    tertiary = VerifiedEmeraldLight,
    onTertiary = EditorialDarkBg,
    tertiaryContainer = EmeraldContainerDark,
    onTertiaryContainer = EmeraldContainerLight,

    error = BreakingCrimsonLight,
    onError = EditorialDarkBg,
    errorContainer = CrimsonContainerDark,
    onErrorContainer = CrimsonContainerLight,

    background = EditorialDarkBg,
    onBackground = TextPrimaryDark,

    surface = EditorialDarkSurface,
    onSurface = TextPrimaryDark,
    surfaceVariant = EditorialDarkSurfaceVariant,
    onSurfaceVariant = TextSecondaryDark,

    outline = EditorialDarkBorder,
    outlineVariant = EditorialDarkBorderSubtle,
    scrim = Color(0x99000000)
)

private val EditorialLightColorScheme = lightColorScheme(
    primary = CobaltPrimary,
    onPrimary = EditorialLightSurface,
    primaryContainer = CobaltContainerLight,
    onPrimaryContainer = CobaltContainerDark,

    secondary = DevelopingAmber,
    onSecondary = EditorialLightSurface,
    secondaryContainer = AmberContainerLight,
    onSecondaryContainer = AmberContainerDark,

    tertiary = VerifiedEmerald,
    onTertiary = EditorialLightSurface,
    tertiaryContainer = EmeraldContainerLight,
    onTertiaryContainer = EmeraldContainerDark,

    error = BreakingCrimson,
    onError = EditorialLightSurface,
    errorContainer = CrimsonContainerLight,
    onErrorContainer = CrimsonContainerDark,

    background = EditorialLightBg,
    onBackground = TextPrimaryLight,

    surface = EditorialLightSurface,
    onSurface = TextPrimaryLight,
    surfaceVariant = EditorialLightSurfaceVariant,
    onSurfaceVariant = TextSecondaryLight,

    outline = EditorialLightBorder,
    outlineVariant = EditorialLightBorderSubtle,
    scrim = Color(0x66000000)
)

@Composable
fun MyApplicationTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val isSystemDark = isSystemInDarkTheme()
    val isDark = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemDark
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val colorScheme = if (isDark) EditorialDarkColorScheme else EditorialLightColorScheme

    CompositionLocalProvider(
        LocalThemeMode provides themeMode,
        LocalIsDarkTheme provides isDark
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
