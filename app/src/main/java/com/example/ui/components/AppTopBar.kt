package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.theme.BreakingCrimson
import com.example.ui.theme.CobaltPrimary
import com.example.ui.theme.LocalIsDarkTheme
import com.example.ui.theme.ThemeMode

@Composable
fun AppTopBar(
    currentLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    onOpenSearch: () -> Unit,
    onTriggerResearch: () -> Unit,
    themeMode: ThemeMode = ThemeMode.DARK,
    onToggleTheme: () -> Unit = {},
    onSelectThemeMode: (ThemeMode) -> Unit = {},
    hasBreakingNews: Boolean = false,
    onBreakingClicked: () -> Unit = {}
) {
    var showLangMenu by remember { mutableStateOf(false) }
    var showThemeMenu by remember { mutableStateOf(false) }
    val isDark = LocalIsDarkTheme.current

    Column(modifier = Modifier.fillMaxWidth()) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Brand Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Newspaper,
                            contentDescription = "SatyaNews Logo",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (currentLanguage == AppLanguage.BN) "সত্যসন্ধান" else if (currentLanguage == AppLanguage.HI) "सत्यसमाचार" else "SatyaNews",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = "AI NEWSROOM",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                        Text(
                            text = if (currentLanguage == AppLanguage.BN) "স্বতন্ত্র ও বহু-উৎস তথ্যানুসন্ধান"
                            else if (currentLanguage == AppLanguage.HI) "स्वायत्त बहु-स्रोत अनुसंधान"
                            else "Autonomous Fact-Checked Newsroom",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Actions: AI Research trigger + Language Switcher + Search
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Deep Research Trigger Button
                    FilledTonalButton(
                        onClick = onTriggerResearch,
                        modifier = Modifier
                            .height(36.dp)
                            .testTag("btn_trigger_research"),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Research Topic",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (currentLanguage == AppLanguage.BN) "অনুসন্ধান" else if (currentLanguage == AppLanguage.HI) "शोध" else "Research",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Language Selector
                    Box {
                        IconButton(
                            onClick = { showLangMenu = true },
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("btn_language_select")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = "Switch Language",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        DropdownMenu(
                            expanded = showLangMenu,
                            onDismissRequest = { showLangMenu = false }
                        ) {
                            AppLanguage.values().forEach { lang ->
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "${lang.nativeName} (${lang.displayName})",
                                                fontWeight = if (lang == currentLanguage) FontWeight.Bold else FontWeight.Normal,
                                                color = if (lang == currentLanguage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    },
                                    onClick = {
                                        onLanguageSelected(lang)
                                        showLangMenu = false
                                    }
                                )
                            }
                        }
                    }

                    // Search Icon
                    IconButton(
                        onClick = onOpenSearch,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("btn_top_search")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.width(2.dp))

                    // Theme Toggle (Dark / Light / System Mode)
                    Box {
                        IconButton(
                            onClick = onToggleTheme,
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("btn_theme_toggle")
                        ) {
                            Icon(
                                imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = if (isDark) "Switch to Light Mode" else "Switch to Dark Mode",
                                tint = if (isDark) Color(0xFFFFD54F) else MaterialTheme.colorScheme.primary
                            )
                        }

                        DropdownMenu(
                            expanded = showThemeMenu,
                            onDismissRequest = { showThemeMenu = false }
                        ) {
                            ThemeMode.values().forEach { mode ->
                                val label = when (currentLanguage) {
                                    AppLanguage.BN -> mode.labelBn
                                    AppLanguage.HI -> mode.labelHi
                                    AppLanguage.EN -> mode.labelEn
                                }
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = label,
                                            fontWeight = if (mode == themeMode) FontWeight.Bold else FontWeight.Normal,
                                            color = if (mode == themeMode) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                        )
                                    },
                                    onClick = {
                                        onSelectThemeMode(mode)
                                        showThemeMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Breaking Ticker Ribbon (if present)
        if (hasBreakingNews) {
            Surface(
                color = BreakingCrimson,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onBreakingClicked() }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(3.dp),
                        color = Color.White
                    ) {
                        Text(
                            text = if (currentLanguage == AppLanguage.BN) "ব্রেকিং আপডেট" else if (currentLanguage == AppLanguage.HI) "ब्रेकिंग अपडेट" else "BREAKING",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = BreakingCrimson,
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.BN) "চন্দ্রপৃষ্ঠে নতুন বিরল খনিজ আবিষ্কার: ৪টি স্বাধীন উৎসের তথ্য নিশ্চিত"
                        else if (currentLanguage == AppLanguage.HI) "चंद्रमा पर दुर्लभ खनिज खोज: 4 स्वतंत्र स्रोतों से पुष्टि"
                        else "New lunar mineral breakthrough confirmed across 4 independent sources",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
