package com.example.ui.components

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.SourceItem
import com.example.data.model.TimelineItem
import com.example.ui.theme.CobaltPrimary
import com.example.ui.theme.VerifiedEmerald

@Composable
fun TimelineView(
    timeline: List<TimelineItem>,
    currentLanguage: AppLanguage,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = "Event Timeline",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (currentLanguage == AppLanguage.BN) "ঘটনাক্রম ও হালনাগাদ টাইমলাইন"
                else if (currentLanguage == AppLanguage.HI) "घटनाक्रम व टाइमलाइन"
                else "Chronological Event Timeline",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        timeline.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.Top
            ) {
                // Time Node
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(72.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = item.time,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    if (index < timeline.size - 1) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(36.dp)
                                .background(MaterialTheme.colorScheme.outlineVariant)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                // Event Description Card
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Text(
                        text = item.event,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SourcesTransparencyView(
    sources: List<SourceItem>,
    currentLanguage: AppLanguage,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = "Source Transparency",
                tint = VerifiedEmerald,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (currentLanguage == AppLanguage.BN) "উৎস স্বচ্ছতা ও তথ্যের প্রমাণপত্র"
                else if (currentLanguage == AppLanguage.HI) "स्रोत पारदर्शिता व प्रमाण"
                else "Source Transparency & Evidence Registry",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        sources.forEach { source ->
            val tierLabel = when (source.tier) {
                1 -> if (currentLanguage == AppLanguage.BN) "টায়ার ১: প্রাথমিক ও প্রাতিষ্ঠানিক নথি" else "Tier 1: Primary & Official Records"
                2 -> if (currentLanguage == AppLanguage.BN) "টায়ার ২: প্রতিষ্ঠিত মূলধারার গণমাধ্যম" else "Tier 2: Established News Organization"
                3 -> if (currentLanguage == AppLanguage.BN) "টায়ার ৩: বিশেষায়িত গবেষণা সাময়িকী" else "Tier 3: Specialized Publication"
                else -> if (currentLanguage == AppLanguage.BN) "টায়ার ৪: স্থানীয় তথ্যসূত্র / লিড" else "Tier 4: Field Leads"
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .clickable {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(source.url))
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            // Ignored if browser not installed
                        }
                    },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(if (source.tier <= 2) VerifiedEmerald else CobaltPrimary)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = source.name,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.OpenInNew,
                            contentDescription = "Visit Source",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = tierLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "\"${source.quoteOrSummary}\"",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Credibility Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (currentLanguage == AppLanguage.BN) "বিশ্বাসযোগ্যতা স্কোর: ${source.credibilityScore}%"
                            else if (currentLanguage == AppLanguage.HI) "विश्वसनीयता: ${source.credibilityScore}%"
                            else "Credibility: ${source.credibilityScore}%",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = VerifiedEmerald
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        LinearProgressIndicator(
                            progress = { source.credibilityScore / 100f },
                            modifier = Modifier
                                .weight(1f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = VerifiedEmerald,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                }
            }
        }
    }
}
