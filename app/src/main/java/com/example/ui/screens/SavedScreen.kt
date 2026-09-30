package com.example.ui.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.local.entity.StoryEntity
import com.example.data.model.AppLanguage
import com.example.ui.components.NewsCard

@Composable
fun SavedScreen(
    savedStories: List<StoryEntity>,
    currentLanguage: AppLanguage,
    savedStoryIds: Set<Long>,
    userReactions: Map<Long, Boolean>,
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
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = "Saved Stories",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (currentLanguage == AppLanguage.BN) "সংরক্ষিত গবেষণাপত্র ও প্রতিবেদন (${savedStories.size})"
                    else if (currentLanguage == AppLanguage.HI) "सहेजी गई रिपोर्टें (${savedStories.size})"
                    else "Saved Research Stories (${savedStories.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
        }

        if (savedStories.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.BookmarkBorder,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (currentLanguage == AppLanguage.BN) "আপনি এখনও কোনো প্রতিবেদন সংরক্ষণ করেননি।"
                            else if (currentLanguage == AppLanguage.HI) "आपने अभी तक कोई रिपोर्ट सहेजी नहीं है।"
                            else "You haven't saved any research stories yet.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(savedStories, key = { it.id }) { story ->
                NewsCard(
                    story = story,
                    currentLanguage = currentLanguage,
                    isSaved = true,
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
