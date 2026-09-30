package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.AiAgentJobEntity
import com.example.data.local.entity.NewsSourceEntity
import com.example.data.local.entity.StoryEntity
import com.example.data.model.AppLanguage
import com.example.ui.components.formatRelativeTime
import com.example.ui.theme.CobaltPrimary
import com.example.ui.theme.DevelopingAmber
import com.example.ui.theme.ThemeMode
import com.example.ui.theme.VerifiedEmerald

@Composable
fun AdminControlScreen(
    stories: List<StoryEntity>,
    sources: List<NewsSourceEntity>,
    agentJobs: List<AiAgentJobEntity>,
    currentLanguage: AppLanguage,
    currentThemeMode: ThemeMode = ThemeMode.DARK,
    onSelectThemeMode: (ThemeMode) -> Unit = {},
    onTriggerResearch: () -> Unit,
    onRunJob: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val totalProcessed = agentJobs.sumOf { it.storiesProcessed }
    val breakingCount = stories.count { it.isBreaking }
    val developingCount = stories.count { it.status == "DEVELOPING" }
    val confirmedCount = stories.count { it.status == "CONFIRMED" }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        // Newsroom Header
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(CobaltPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = "Newsroom Control",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (currentLanguage == AppLanguage.BN) "ডিজিটাল নিউজরুম কন্ট্রোল সেন্টার"
                            else if (currentLanguage == AppLanguage.HI) "डिजिटल न्यूज़रूम कंट्रोल सेंटर"
                            else "Digital Newsroom Control Center",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = if (currentLanguage == AppLanguage.BN) "স্বতন্ত্র এআই এজেন্ট ও অনুসন্ধান প্রশাসন"
                            else "Autonomous Agents & Verification Operations",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Button(
                    onClick = onTriggerResearch,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.testTag("btn_admin_start_research")
                ) {
                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = if (currentLanguage == AppLanguage.BN) "+ নতুন অনুসন্ধান" else "+ New Research", fontSize = 11.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Editorial Theming & Display Mode Control (Material 3 Dark/Light)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("card_theme_settings"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (currentThemeMode == ThemeMode.DARK) Icons.Default.DarkMode else if (currentThemeMode == ThemeMode.LIGHT) Icons.Default.LightMode else Icons.Default.BrightnessMedium,
                                contentDescription = null,
                                tint = CobaltPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (currentLanguage == AppLanguage.BN) "সম্পাদকীয় ইন্টারফেস থিম"
                                else if (currentLanguage == AppLanguage.HI) "संपादकीय इंटरफ़ेस थीम"
                                else "Editorial Interface Theme",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = when (currentThemeMode) {
                                    ThemeMode.DARK -> if (currentLanguage == AppLanguage.BN) "ডার্ক সক্রিয়" else "DARK ACTIVE"
                                    ThemeMode.LIGHT -> if (currentLanguage == AppLanguage.BN) "লাইট সক্রিয়" else "LIGHT ACTIVE"
                                    ThemeMode.SYSTEM -> if (currentLanguage == AppLanguage.BN) "সিস্টেম স্বয়ংক্রিয়" else "SYSTEM AUTO"
                                },
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ThemeMode.values().forEach { mode ->
                            val label = when (currentLanguage) {
                                AppLanguage.BN -> mode.labelBn
                                AppLanguage.HI -> mode.labelHi
                                AppLanguage.EN -> mode.labelEn
                            }
                            val isSelected = currentThemeMode == mode
                            FilterChip(
                                selected = isSelected,
                                onClick = { onSelectThemeMode(mode) },
                                label = {
                                    Text(
                                        text = label,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                leadingIcon = {
                                    val icon = when (mode) {
                                        ThemeMode.DARK -> Icons.Default.DarkMode
                                        ThemeMode.LIGHT -> Icons.Default.LightMode
                                        ThemeMode.SYSTEM -> Icons.Default.BrightnessMedium
                                    }
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp)
                                    )
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("chip_theme_${mode.name.lowercase()}"),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = CobaltPrimary,
                                    selectedLabelColor = Color.White,
                                    selectedLeadingIconColor = Color.White
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stat Metrics Grid
            Row(modifier = Modifier.fillMaxWidth()) {
                MetricBox(
                    title = if (currentLanguage == AppLanguage.BN) "আজকের প্রতিবেদন" else "Stories",
                    value = "${stories.size}",
                    color = CobaltPrimary,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                MetricBox(
                    title = if (currentLanguage == AppLanguage.BN) "ব্রেকিং ইভেন্ট" else "Breaking",
                    value = "$breakingCount",
                    color = Color.Red,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                MetricBox(
                    title = if (currentLanguage == AppLanguage.BN) "যাচাইকৃত সত্য" else "Confirmed",
                    value = "$confirmedCount",
                    color = VerifiedEmerald,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Autonomous Editorial Pipeline Overview Banner (Section 3 & 45)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = CobaltPrimary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (currentLanguage == AppLanguage.BN) "স্বয়ংক্রিয় অনুসন্ধান লুপ (১১ ধাপ)"
                            else "Autonomous Editorial Loop (11-Step Pipeline)",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = CobaltPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "DISCOVER → COLLECT → READ → EXTRACT → COMPARE → VERIFY → CONTEXTUALIZE → SYNTHESIZE → WRITE → FACT CHECK → PUBLISH",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Section: AI Control Agents (Section 25)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (currentLanguage == AppLanguage.BN) "এআই অনুসন্ধান ও সম্পাদনা এজেন্টসমূহ (${agentJobs.size})"
                    else if (currentLanguage == AppLanguage.HI) "एआई न्यूज़रूम एजेंट्स (${agentJobs.size})"
                    else "Newsroom AI Agent Workforce (${agentJobs.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = VerifiedEmerald.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "6/6 ACTIVE",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = VerifiedEmerald,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        // List of Newsroom Agent Jobs
        items(agentJobs, key = { it.id }) { agent ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(if (agent.status == "RUNNING") DevelopingAmber else VerifiedEmerald)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (currentLanguage == AppLanguage.BN) agent.agentNameBn else agent.agentNameEn,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // Run Now Button
                        FilledTonalButton(
                            onClick = { onRunJob(agent.id) },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.height(32.dp).testTag("btn_run_agent_${agent.id}")
                        ) {
                            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = if (agent.status == "RUNNING") "চলছে..." else "Run Now", fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = if (currentLanguage == AppLanguage.BN) agent.descriptionBn else agent.descriptionEn,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "সর্বশেষ রান: ${formatRelativeTime(agent.lastRunTimestamp, currentLanguage)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "প্রক্রিয়াজাত প্রতিবেদন: ${agent.storiesProcessed}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        // Section: Monitored News Sources & Hierarchy (Section 5)
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (currentLanguage == AppLanguage.BN) "অনুমোদিত উৎস ও টায়ার হায়ারার্কি (${sources.size})"
                    else "Approved Source Registry (${sources.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "TIER 1 - 4",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        items(sources, key = { it.id }) { src ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = src.name,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${src.domain} • ${src.category}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = if (src.tier == 1) VerifiedEmerald.copy(alpha = 0.2f) else CobaltPrimary.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "Tier ${src.tier}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (src.tier == 1) VerifiedEmerald else CobaltPrimary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${src.credibilityScore}% Trust",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MetricBox(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.12f)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black, color = color)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = title, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
