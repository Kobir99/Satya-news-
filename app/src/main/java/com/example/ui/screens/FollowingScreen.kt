package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.StoryEntity
import com.example.data.local.entity.TopicEntity
import com.example.data.model.AppLanguage
import com.example.ui.components.NewsCard

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FollowingScreen(
    stories: List<StoryEntity>,
    topics: List<TopicEntity>,
    currentLanguage: AppLanguage,
    savedStoryIds: Set<Long>,
    userReactions: Map<Long, Boolean>,
    onToggleFollowTopic: (Long, Boolean) -> Unit,
    onReadStory: (Long) -> Unit,
    onToggleSave: (Long) -> Unit,
    onToggleReaction: (Long) -> Unit,
    onCommentClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Subscriptions,
                    contentDescription = "Following",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (currentLanguage == AppLanguage.BN) "আপনার অনুসৃত বিষয়সমূহ"
                    else if (currentLanguage == AppLanguage.HI) "आपके द्वारा फ़ॉलो किए गए विषय"
                    else "Your Followed Topics",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Topics Chips Row
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                topics.forEach { topic ->
                    val topicName = when (currentLanguage) {
                        AppLanguage.BN -> topic.nameBn
                        AppLanguage.HI -> topic.nameHi
                        AppLanguage.EN -> topic.nameEn
                    }
                    FilterChip(
                        selected = topic.isFollowed,
                        onClick = { onToggleFollowTopic(topic.id, topic.isFollowed) },
                        label = { Text(text = topicName, fontSize = 12.sp) },
                        leadingIcon = {
                            Icon(
                                imageVector = if (topic.isFollowed) Icons.Default.Check else Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = if (currentLanguage == AppLanguage.BN) "অনুসৃত বিষয়ের ব্যক্তিগতকৃত ফিড"
                else if (currentLanguage == AppLanguage.HI) "फ़ॉलो किए गए विषयों का फ़ीड"
                else "Personalized Following Feed",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        if (stories.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (currentLanguage == AppLanguage.BN) "আপনার অনুসৃত বিষয়ে নতুন কোনো প্রতিবেদন নেই। উপরে আরো বিষয় অনুসরণ করুন।"
                        else "No stories for currently followed topics. Follow more topics above.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
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
}
