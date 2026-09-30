package com.example.ui.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Source
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.data.local.entity.StoryEntity
import com.example.data.model.AppLanguage
import com.example.data.util.JsonUtils
import com.example.ui.theme.BreakingCrimson
import com.example.ui.theme.DevelopingAmber
import com.example.ui.theme.VerifiedEmerald

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewsCard(
    story: StoryEntity,
    currentLanguage: AppLanguage,
    isSaved: Boolean,
    isReacted: Boolean,
    onReadStory: (Long) -> Unit,
    onToggleSave: (Long) -> Unit,
    onToggleReaction: (Long) -> Unit,
    onCommentClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sources = JsonUtils.jsonToSources(story.sourcesJson)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onReadStory(story.id) }
            .testTag("news_card_${story.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Hero Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                val drawableId = getDrawableResource(story.imageUrl)
                if (drawableId != null) {
                    Image(
                        painter = painterResource(id = drawableId),
                        contentDescription = story.headline,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(200.dp)
                    )
                } else if (story.imageUrl.startsWith("http")) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(story.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = story.headline,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(200.dp)
                    )
                } else {
                    // Fallback visual
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = story.category,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                // Top badges overlay
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Category & Breaking badge
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (story.isBreaking) BreakingCrimson else MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)
                        ) {
                            Text(
                                text = if (story.isBreaking) {
                                    if (currentLanguage == AppLanguage.BN) "ব্রেকিং" else if (currentLanguage == AppLanguage.HI) "ब्रेकिंग" else "BREAKING"
                                } else story.category,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (story.isBreaking) Color.White else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        if (story.isAiPick) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.92f)
                            ) {
                                Text(
                                    text = "AI PICKS",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }

                    // Verification Pill
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = when (story.status) {
                            "CONFIRMED" -> VerifiedEmerald.copy(alpha = 0.92f)
                            "DEVELOPING" -> DevelopingAmber.copy(alpha = 0.92f)
                            else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.92f)
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Verification Status",
                                tint = Color.White,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (currentLanguage == AppLanguage.BN) "${story.confidencePercentage}% যাচাইকৃত"
                                else if (currentLanguage == AppLanguage.HI) "${story.confidencePercentage}% पुष्ट"
                                else "${story.confidencePercentage}% Verified",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            // Card Body Content
            Column(modifier = Modifier.padding(16.dp)) {
                // Meta info: Category • Time • Research Level
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${story.locationTag} • ${formatRelativeTime(story.publishedAt, currentLanguage)}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = if (story.researchLevel == "LEVEL_3_DEEP") {
                                if (currentLanguage == AppLanguage.BN) "গভীর অনুসন্ধান" else if (currentLanguage == AppLanguage.HI) "गहन अनुसंधान" else "Deep Research"
                            } else {
                                if (currentLanguage == AppLanguage.BN) "প্রমিত গবেষণা" else if (currentLanguage == AppLanguage.HI) "मानक शोध" else "Standard"
                            },
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Headline
                Text(
                    text = story.headline,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 22.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Subheadline / Short AI Summary
                Text(
                    text = story.summary,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 20.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Sources Count & Chips
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Source,
                        contentDescription = "Source Count",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.BN) "যাচাইকৃত উৎস: ${story.sourceCount}টি"
                        else if (currentLanguage == AppLanguage.HI) "सत्यापित स्रोत: ${story.sourceCount}"
                        else "Verified Sources: ${story.sourceCount}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Small chips for primary sources
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        sources.take(3).forEach { s ->
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = s.name,
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Action buttons row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Read Story Button
                    Button(
                        onClick = { onReadStory(story.id) },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.testTag("btn_read_story_${story.id}")
                    ) {
                        Text(
                            text = if (currentLanguage == AppLanguage.BN) "প্রতিবেদন পড়ুন"
                            else if (currentLanguage == AppLanguage.HI) "पूरी रिपोर्ट"
                            else "Read Story",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Interactive Action Icons
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Like/React
                        IconButton(
                            onClick = { onToggleReaction(story.id) },
                            modifier = Modifier.size(38.dp)
                        ) {
                            Icon(
                                imageVector = if (isReacted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "React",
                                tint = if (isReacted) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Comment
                        IconButton(
                            onClick = { onCommentClick(story.id) },
                            modifier = Modifier.size(38.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ChatBubbleOutline,
                                contentDescription = "Comments",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Save
                        IconButton(
                            onClick = { onToggleSave(story.id) },
                            modifier = Modifier.size(38.dp)
                        ) {
                            Icon(
                                imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Save Story",
                                tint = if (isSaved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Share
                        IconButton(
                            onClick = { shareStory(context, story, currentLanguage) },
                            modifier = Modifier.size(38.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

fun shareStory(context: Context, story: StoryEntity, lang: AppLanguage) {
    val shareText = """
        📰 ${story.headline}
        
        ${story.summary}
        
        🔍 ${if (lang == AppLanguage.BN) "যাচাইকরণ" else "Verified"}: ${story.confidencePercentage}% | ${story.sourceCount} Sources
        
        📲 SatyaNews AI Newsroom
    """.trimIndent()

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, story.headline)
        putExtra(Intent.EXTRA_TEXT, shareText)
    }
    context.startActivity(Intent.createChooser(intent, "Share Verified Story"))
}

fun formatRelativeTime(timestamp: Long, lang: AppLanguage): String {
    val diffMs = System.currentTimeMillis() - timestamp
    val minutes = (diffMs / (1000 * 60)).coerceAtLeast(1)
    val hours = minutes / 60
    val days = hours / 24

    return when (lang) {
        AppLanguage.BN -> when {
            minutes < 60 -> "$minutes মিনিট আগে"
            hours < 24 -> "$hours ঘণ্টা আগে"
            else -> "$days দিন আগে"
        }
        AppLanguage.HI -> when {
            minutes < 60 -> "$minutes मिनट पहले"
            hours < 24 -> "$hours घंटे पहले"
            else -> "$days दिन पहले"
        }
        AppLanguage.EN -> when {
            minutes < 60 -> "${minutes}m ago"
            hours < 24 -> "${hours}h ago"
            else -> "${days}d ago"
        }
    }
}

fun getDrawableResource(imageName: String): Int? {
    return when {
        imageName.contains("news_space_rover") -> R.drawable.news_space_rover_1790729095622
        imageName.contains("news_ai_chip") -> R.drawable.news_ai_chip_1790729113452
        imageName.contains("news_clean_energy") -> R.drawable.news_clean_energy_1790729127235
        imageName.contains("news_bio_science") -> R.drawable.news_bio_science_1790729140841
        imageName.contains("satya_logo") -> R.drawable.satya_logo_1790729019677
        else -> null
    }
}
