package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.StoryEntity
import com.example.data.model.AppLanguage
import com.example.ui.components.NewsCard
import com.example.ui.theme.CobaltPrimary
import com.example.ui.theme.DevelopingAmber

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExploreScreen(
    stories: List<StoryEntity>,
    currentLanguage: AppLanguage,
    searchQuery: String,
    searchSynthesis: String?,
    isSearching: Boolean,
    savedStoryIds: Set<Long>,
    userReactions: Map<Long, Boolean>,
    onSearchQueryChanged: (String) -> Unit,
    onCategorySelected: (String) -> Unit,
    onReadStory: (Long) -> Unit,
    onToggleSave: (Long) -> Unit,
    onToggleReaction: (Long) -> Unit,
    onCommentClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var queryText by remember { mutableStateOf(searchQuery) }

    val sampleInquiries = when (currentLanguage) {
        AppLanguage.BN -> listOf(
            "চন্দ্রপৃষ্ঠে কী আবিষ্কার হয়েছে?",
            "অপটিক্যাল নিউরাল চিপের সুবিধা কী?",
            "ভাসমান সৌর প্যানেলের প্রভাব কী?",
            "নদীভাঙন রোধে এআই কীভাবে সাহায্য করছে?"
        )
        AppLanguage.HI -> listOf(
            "चंद्रमा पर क्या खोज हुई?",
            "ऑप्टिकल चिप कैसे काम करती है?",
            "सौर ऊर्जा ग्रिड के लाभ?",
            "बाढ़ नियंत्रण में नई तकनीक?"
        )
        AppLanguage.EN -> listOf(
            "What was discovered on the Moon?",
            "How do optical neural chips save power?",
            "Benefits of floating solar grid?",
            "Smart sensors for river flood defense?"
        )
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
    ) {
        // Semantic Search Bar
        item {
            OutlinedTextField(
                value = queryText,
                onValueChange = {
                    queryText = it
                    onSearchQueryChanged(it)
                },
                placeholder = {
                    Text(
                        text = if (currentLanguage == AppLanguage.BN) "প্রাকৃতিক ভাষায় প্রশ্ন বা বিষয় খুঁজুন..."
                        else if (currentLanguage == AppLanguage.HI) "प्राकृतिक भाषा में खोजें..."
                        else "Search in natural language...",
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingIcon = {
                    if (queryText.isNotBlank()) {
                        IconButton(onClick = {
                            queryText = ""
                            onSearchQueryChanged("")
                        }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_explore_search"),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Sample inquiry chips
            Text(
                text = if (currentLanguage == AppLanguage.BN) "জনপ্রিয় অনুসন্ধানী জিজ্ঞাসা:"
                else if (currentLanguage == AppLanguage.HI) "लोकप्रिय खोज:"
                else "Popular Research Queries:",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                sampleInquiries.forEach { sample ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.clickable {
                            queryText = sample
                            onSearchQueryChanged(sample)
                        }
                    ) {
                        Text(
                            text = sample,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Semantic Search Synthesis Result Card (Section 18)
        if (isSearching) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = if (currentLanguage == AppLanguage.BN) "বহু-উৎস প্রমাণ বিশ্লেষণ করে সারসংক্ষেপ প্রস্তুত হচ্ছে..."
                            else "Synthesizing executive multi-source answer...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        } else if (!searchSynthesis.isNullOrBlank()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = CobaltPrimary.copy(alpha = 0.12f)
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CobaltPrimary.copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "AI Research Synthesis",
                                tint = CobaltPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (currentLanguage == AppLanguage.BN) "এআই এক্সিকিউটিভ সারসংক্ষেপ ও উত্তর"
                                else if (currentLanguage == AppLanguage.HI) "AI कार्यकारी सारांश"
                                else "AI Executive Synthesis Answer",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = CobaltPrimary
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = searchSynthesis,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (currentLanguage == AppLanguage.BN) "সম্পর্কিত ক্লাস্টার প্রতিবেদনসমূহ নিচে প্রদর্শিত:"
                            else "Related Story Clusters Below:",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Section Title: AI Picks & Top Stories
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "AI Picks",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.BN) "এআই নির্বাচিত প্রতিবেদন (AI Picks)"
                        else if (currentLanguage == AppLanguage.HI) "AI चयनित रिपोर्ट"
                        else "AI Handpicked Research Clusters",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "${stories.size} Stories",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        // Matched Stories List
        items(stories, key = { it.id }) { story ->
            NewsCard(
                story = story,
                currentLanguage = currentLanguage,
                isSaved = savedStoryIds.contains(story.id),
                isReacted = userReactions[story.id] ?: false,
                onReadStory = onReadStory,
                onToggleSave = onToggleSave,
                onToggleReaction = onToggleReaction,
                onCommentClick = onCommentClick,
                modifier = Modifier.padding(horizontal = 0.dp)
            )
        }
    }
}
