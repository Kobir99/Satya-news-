package com.example.ui.screens

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.local.entity.CommentEntity
import com.example.data.local.entity.StoryEntity
import com.example.data.model.AppLanguage
import com.example.data.util.JsonUtils
import com.example.ui.components.SourcesTransparencyView
import com.example.ui.components.TimelineView
import com.example.ui.components.VerificationMatrixView
import com.example.ui.components.formatRelativeTime
import com.example.ui.components.getDrawableResource
import com.example.ui.components.shareStory
import com.example.ui.theme.CobaltPrimary
import com.example.ui.theme.VerifiedEmerald

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryDetailScreen(
    story: StoryEntity,
    comments: List<CommentEntity>,
    currentLanguage: AppLanguage,
    isSaved: Boolean,
    isReacted: Boolean,
    onBack: () -> Unit,
    onToggleSave: (Long) -> Unit,
    onToggleReaction: (Long) -> Unit,
    onOpenAskAssistant: () -> Unit,
    onPostComment: (Long, String, String) -> Unit,
    onLikeComment: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    // Intercept physical or system back press
    BackHandler { onBack() }

    val context = LocalContext.current
    val keyFacts = JsonUtils.jsonToStringList(story.keyFactsJson)
    val whatWeKnow = JsonUtils.jsonToStringList(story.whatWeKnowJson)
    val whatWeDontKnow = JsonUtils.jsonToStringList(story.whatWeDontKnowJson)
    val timeline = JsonUtils.jsonToTimeline(story.timelineJson)
    val sources = JsonUtils.jsonToSources(story.sourcesJson)
    val claims = JsonUtils.jsonToClaims(story.claimsJson)
    val conflicts = JsonUtils.jsonToConflicts(story.conflictJson)
    val whatSourcesSay = JsonUtils.jsonToStringList(story.whatSourcesSayJson)

    var commentAuthor by remember { mutableStateOf("") }
    var commentText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = story.category,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("btn_story_back")) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onToggleReaction(story.id) }) {
                        Icon(
                            imageVector = if (isReacted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (isReacted) Color.Red else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = { onToggleSave(story.id) }) {
                        Icon(
                            imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Save",
                            tint = if (isSaved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = { shareStory(context, story, currentLanguage) }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Hero Image with credit
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                val drawableId = getDrawableResource(story.imageUrl)
                if (drawableId != null) {
                    Image(
                        painter = painterResource(id = drawableId),
                        contentDescription = story.headline,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(230.dp)
                    )
                } else if (story.imageUrl.startsWith("http")) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(story.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = story.headline,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(230.dp)
                    )
                }

                // Credit badge at bottom right
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color.Black.copy(alpha = 0.65f),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "📷 ${story.imageCredit}",
                        fontSize = 10.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                // Meta & Verification confidence badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${story.locationTag} • ${formatRelativeTime(story.publishedAt, currentLanguage)}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = VerifiedEmerald.copy(alpha = 0.15f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Confidence",
                                tint = VerifiedEmerald,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${story.confidencePercentage}% ${if (currentLanguage == AppLanguage.BN) "যাচাইকৃত তথ্য" else "Confidence"}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = VerifiedEmerald
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Headline
                Text(
                    text = story.headline,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subheadline
                Text(
                    text = story.subheadline,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // AI Executive Summary Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                    )
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "AI Summary",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (currentLanguage == AppLanguage.BN) "দ্রুত সারসংক্ষেপ (AI প্রস্তুতকৃত)"
                                else if (currentLanguage == AppLanguage.HI) "त्वरित सारांश (AI तैयार)"
                                else "Quick Executive Summary (AI Synthesized)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = story.summary,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = story.aiDisclosure,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Ask the Story Assistant Callout Button (Section 19)
                FilledTonalButton(
                    onClick = onOpenAskAssistant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("btn_ask_story_assistant"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.QuestionAnswer,
                        contentDescription = "Ask Assistant",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.BN) "এই ঘটনার ওপর এআই-কে প্রশ্ন করুন..."
                        else if (currentLanguage == AppLanguage.HI) "इस रिपोर्ट पर AI से प्रश्न पूछें..."
                        else "Ask AI About This Story & Evidence...",
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                // WHAT HAPPENED? (বিস্তারিত বিবরণী)
                Text(
                    text = if (currentLanguage == AppLanguage.BN) "ঘটনার বিস্তারিত বিবরণ"
                    else if (currentLanguage == AppLanguage.HI) "विस्तृत विवरण"
                    else "What Happened?",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = story.whatHappened,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 22.sp
                )

                // KEY FACTS
                if (keyFacts.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.BN) "মূল তথ্যসমূহ (Key Facts)"
                        else if (currentLanguage == AppLanguage.HI) "मुख्य तथ्य"
                        else "Key Facts",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    keyFacts.forEach { fact ->
                        Row(modifier = Modifier.padding(vertical = 3.dp)) {
                            Text(text = "• ", fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                            Text(
                                text = fact,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }

                // WHAT WE KNOW vs WHAT WE DON'T KNOW
                Spacer(modifier = Modifier.height(18.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    // What we know
                    ElevatedCard(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = VerifiedEmerald.copy(alpha = 0.08f)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = VerifiedEmerald, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (currentLanguage == AppLanguage.BN) "যা নিশ্চিত" else "What We Know",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = VerifiedEmerald
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            whatWeKnow.forEach {
                                Text(text = "✓ $it", fontSize = 11.sp, lineHeight = 16.sp, color = MaterialTheme.colorScheme.onSurface)
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // What we don't know
                    ElevatedCard(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.HelpOutline, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (currentLanguage == AppLanguage.BN) "যা এখনও অজানা" else "What We Don't Know",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            whatWeDontKnow.forEach {
                                Text(text = "? $it", fontSize = 11.sp, lineHeight = 16.sp, color = MaterialTheme.colorScheme.onSurface)
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }
                    }
                }

                // CLAIMS VERIFICATION & CONFLICT MATRIX
                if (claims.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))
                    VerificationMatrixView(claims = claims, conflicts = conflicts, currentLanguage = currentLanguage)
                }

                // BACKGROUND & CONTEXT
                if (story.background.isNotBlank()) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.BN) "প্রেক্ষাপট ও পটভূমি"
                        else if (currentLanguage == AppLanguage.HI) "पृष्ठभूमि व संदर्भ"
                        else "Background & Context",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = story.background,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 22.sp
                    )
                }

                // TIMELINE
                if (timeline.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))
                    TimelineView(timeline = timeline, currentLanguage = currentLanguage)
                }

                // WHAT SOURCES SAY
                if (whatSourcesSay.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.BN) "বিভিন্ন সূত্র কী বলছে?"
                        else if (currentLanguage == AppLanguage.HI) "विभिन्न स्रोत क्या कह रहे हैं?"
                        else "What Sources Say",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    whatSourcesSay.forEach { quote ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = "💬 $quote",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }

                // WHY IT MATTERS
                if (story.whyItMatters.isNotBlank()) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.BN) "কেন এটি গুরুত্বপূর্ণ?"
                        else if (currentLanguage == AppLanguage.HI) "यह क्यों महत्वपूर्ण है?"
                        else "Why It Matters",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = story.whyItMatters,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 22.sp
                    )
                }

                // SOURCES TRANSPARENCY
                if (sources.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))
                    SourcesTransparencyView(sources = sources, currentLanguage = currentLanguage)
                }

                // LAST UPDATED TIMESTAMP & FOOTER
                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = if (currentLanguage == AppLanguage.BN) "সর্বশেষ হালনাগাদ: ${formatRelativeTime(story.updatedAt, currentLanguage)}"
                    else if (currentLanguage == AppLanguage.HI) "अंतिम अपडेट: ${formatRelativeTime(story.updatedAt, currentLanguage)}"
                    else "Last Updated: ${formatRelativeTime(story.updatedAt, currentLanguage)}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "AI-generated original synthesis based on verified public reporting and listed sources.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // COMMENTS & COMMUNITY DISCUSSION (Section 23)
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = if (currentLanguage == AppLanguage.BN) "পাঠক মতামত ও আলোচনা (${comments.size})"
                    else if (currentLanguage == AppLanguage.HI) "पाठक चर्चा (${comments.size})"
                    else "Reader Discussion (${comments.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Post a Comment input
                OutlinedTextField(
                    value = commentAuthor,
                    onValueChange = { commentAuthor = it },
                    label = { Text(text = if (currentLanguage == AppLanguage.BN) "আপনার নাম / পরিচয়" else "Your Name") },
                    modifier = Modifier.fillMaxWidth().testTag("input_comment_author"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    label = { Text(text = if (currentLanguage == AppLanguage.BN) "শালীন ও যুক্তিপূর্ণ মন্তব্য লিখুন..." else "Write a constructive perspective...") },
                    modifier = Modifier.fillMaxWidth().testTag("input_comment_text"),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        onPostComment(story.id, commentAuthor, commentText)
                        commentText = ""
                    },
                    enabled = commentText.isNotBlank(),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.align(Alignment.End).testTag("btn_submit_comment")
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = "Submit Comment", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = if (currentLanguage == AppLanguage.BN) "মন্তব্য পোস্ট করুন" else "Post Comment")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Existing comments list
                comments.forEach { comment ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = comment.userName,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = MaterialTheme.colorScheme.primaryContainer
                                    ) {
                                        Text(
                                            text = comment.userBadge,
                                            fontSize = 9.sp,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = formatRelativeTime(comment.timestamp, currentLanguage),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = comment.content,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = { onLikeComment(comment.id) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ThumbUp,
                                        contentDescription = "Like",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                                Text(
                                    text = "${comment.likes}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
