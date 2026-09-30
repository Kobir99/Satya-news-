package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.ClaimItem
import com.example.data.model.ConflictItem
import com.example.ui.theme.BreakingCrimson
import com.example.ui.theme.CobaltPrimary
import com.example.ui.theme.DevelopingAmber
import com.example.ui.theme.VerifiedEmerald

@Composable
fun VerificationMatrixView(
    claims: List<ClaimItem>,
    conflicts: List<ConflictItem>,
    currentLanguage: AppLanguage,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Section Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Verification Matrix",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (currentLanguage == AppLanguage.BN) "দাবি যাচাই ও তথ্য প্রমাণ ম্যাট্রিক্স"
                else if (currentLanguage == AppLanguage.HI) "दावा सत्यापन व साक्ष्य मैट्रिक्स"
                else "Claims Verification & Evidence Matrix",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Claims list cards
        claims.forEachIndexed { index, claim ->
            val statusColor = when (claim.status) {
                "CONFIRMED" -> VerifiedEmerald
                "SUPPORTED" -> CobaltPrimary
                "PARTIALLY_CONFIRMED" -> DevelopingAmber
                "DISPUTED" -> Color(0xFF8B5CF6)
                "UNVERIFIED" -> DevelopingAmber
                "FALSE" -> BreakingCrimson
                else -> MaterialTheme.colorScheme.primary
            }

            val statusText = when (claim.status) {
                "CONFIRMED" -> if (currentLanguage == AppLanguage.BN) "যাচাইকৃত সত্য" else if (currentLanguage == AppLanguage.HI) "पुष्ट" else "Confirmed"
                "SUPPORTED" -> if (currentLanguage == AppLanguage.BN) "সমর্থিত" else if (currentLanguage == AppLanguage.HI) "समर्थित" else "Supported"
                "PARTIALLY_CONFIRMED" -> if (currentLanguage == AppLanguage.BN) "আংশিক নিশ্চিত" else if (currentLanguage == AppLanguage.HI) "आंशिक पुष्ट" else "Partially Confirmed"
                "DISPUTED" -> if (currentLanguage == AppLanguage.BN) "বিতর্কিত / অমীমাংসিত" else if (currentLanguage == AppLanguage.HI) "विवादित" else "Disputed"
                "UNVERIFIED" -> if (currentLanguage == AppLanguage.BN) "অযাচাইকৃত / গুঞ্জন" else if (currentLanguage == AppLanguage.HI) "अपुष्ट" else "Unverified"
                "FALSE" -> if (currentLanguage == AppLanguage.BN) "মিথ্যা / বিভ্রান্তিকর" else if (currentLanguage == AppLanguage.HI) "असत्य" else "False"
                else -> claim.status
            }

            val statusIcon = when (claim.status) {
                "CONFIRMED" -> Icons.Default.CheckCircle
                "SUPPORTED" -> Icons.Default.Info
                "DISPUTED" -> Icons.Default.Warning
                "UNVERIFIED" -> Icons.Default.HelpOutline
                "FALSE" -> Icons.Default.Cancel
                else -> Icons.Default.Info
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, statusColor.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (currentLanguage == AppLanguage.BN) "দাবি #${index + 1}"
                            else if (currentLanguage == AppLanguage.HI) "दावा #${index + 1}"
                            else "Claim #${index + 1}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = statusColor.copy(alpha = 0.15f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Icon(
                                    imageVector = statusIcon,
                                    contentDescription = statusText,
                                    tint = statusColor,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = statusText,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = statusColor
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = claim.claimText,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    if (claim.supportingSources.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (currentLanguage == AppLanguage.BN) "সমর্থনকারী উৎস: "
                                else if (currentLanguage == AppLanguage.HI) "समर्थक स्रोत: "
                                else "Corroborated by: ",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = claim.supportingSources.joinToString(", "),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    if (claim.notes.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "💡 ${claim.notes}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Conflict Detection Warning (Section 7)
        if (conflicts.isNotEmpty()) {
            Spacer(modifier = Modifier.height(14.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = DevelopingAmber.copy(alpha = 0.1f)
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, DevelopingAmber.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Conflict Detected",
                            tint = DevelopingAmber,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (currentLanguage == AppLanguage.BN) "উৎসগত তথ্যের অসংগতি / সংঘাত চিহ্নিতকরণ"
                            else if (currentLanguage == AppLanguage.HI) "स्रोतों में मतभेद / विरोधाभास पहचान"
                            else "Source Discrepancy & Conflict Detected",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = DevelopingAmber
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    conflicts.forEach { conflict ->
                        Text(
                            text = "• ${conflict.aspect}:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${conflict.sourceA}: \"${conflict.reportA}\"",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                        Text(
                            text = "${conflict.sourceB}: \"${conflict.reportB}\"",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                        Text(
                            text = "⚖️ ${if (currentLanguage == AppLanguage.BN) "বর্তমান অবস্থা" else "Status"}: ${conflict.resolutionStatus}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = DevelopingAmber,
                            modifier = Modifier.padding(start = 10.dp, top = 2.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}
