package com.example.ui.screens

import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.StoryEntity
import com.example.data.model.AppLanguage
import com.example.ui.components.NewsCard
import com.example.ui.viewmodel.HomeSubTab

@Composable
fun HomeScreen(
    stories: List<StoryEntity>,
    currentLanguage: AppLanguage,
    currentSubTab: HomeSubTab,
    selectedCategoryKey: String,
    selectedLocation: String,
    savedStoryIds: Set<Long>,
    userReactions: Map<Long, Boolean>,
    onSubTabSelected: (HomeSubTab) -> Unit,
    onCategorySelected: (String) -> Unit,
    onLocationSelected: (String) -> Unit,
    onReadStory: (Long) -> Unit,
    onToggleSave: (Long) -> Unit,
    onToggleReaction: (Long) -> Unit,
    onCommentClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val subTabs = HomeSubTab.values()

    val categories = when (currentLanguage) {
        AppLanguage.BN -> listOf(
            "all" to "সব খবর",
            "science" to "মহাকাশ ও বিজ্ঞান",
            "technology" to "প্রযুক্তি ও এআই",
            "environment" to "সবুজ শক্তি ও পরিবেশ",
            "india" to "ভারত ও দক্ষিণ এশিয়া",
            "local" to "স্থানীয় ও আঞ্চলিক"
        )
        AppLanguage.HI -> listOf(
            "all" to "सभी",
            "science" to "विज्ञान व अंतरिक्ष",
            "technology" to "तकनीक व एआई",
            "environment" to "हरित ऊर्जा व पर्यावरण",
            "india" to "भारत व एशिया",
            "local" to "स्थानीय"
        )
        AppLanguage.EN -> listOf(
            "all" to "All Topics",
            "science" to "Science & Space",
            "technology" to "Tech & AI",
            "environment" to "Clean Energy & Climate",
            "india" to "India & South Asia",
            "local" to "Local & Regional"
        )
    }

    val locations = when (currentLanguage) {
        AppLanguage.BN -> listOf(
            "all" to "সকল অঞ্চল",
            "পশ্চিমবঙ্গ" to "পশ্চিমবঙ্গ ও কলকাতা",
            "দক্ষিণ এশিয়া" to "দক্ষিণ এশিয়া",
            "আন্তর্জাতিক" to "আন্তর্জাতিক"
        )
        AppLanguage.HI -> listOf(
            "all" to "सभी क्षेत्र",
            "पश्चिम बंगाल" to "पश्चिम बंगाल",
            "दक्षिण एशिया" to "दक्षिण एशिया",
            "अंतर्राष्ट्रीय" to "अंतर्राष्ट्रीय"
        )
        AppLanguage.EN -> listOf(
            "all" to "All Locations",
            "West Bengal" to "Bengal & East",
            "South Asia" to "South Asia",
            "Global" to "International"
        )
    }

    Column(modifier = modifier.fillMaxSize()) {
        // Sub-tabs row: For You | Latest | Breaking | Trending | Following
        ScrollableTabRow(
            selectedTabIndex = currentSubTab.ordinal,
            edgePadding = 16.dp,
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[currentSubTab.ordinal]),
                    color = MaterialTheme.colorScheme.primary,
                    height = 3.dp
                )
            },
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            subTabs.forEach { tab ->
                val label = when (currentLanguage) {
                    AppLanguage.BN -> tab.labelBn
                    AppLanguage.HI -> tab.labelHi
                    AppLanguage.EN -> tab.labelEn
                }
                Tab(
                    selected = currentSubTab == tab,
                    onClick = { onSubTabSelected(tab) },
                    text = {
                        Text(
                            text = label,
                            fontWeight = if (currentSubTab == tab) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    },
                    modifier = Modifier.testTag("tab_${tab.name}")
                )
            }
        }

        // Category Filter Chips Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { (key, label) ->
                FilterChip(
                    selected = selectedCategoryKey == key,
                    onClick = { onCategorySelected(key) },
                    label = { Text(text = label, fontSize = 12.sp) },
                    leadingIcon = if (selectedCategoryKey == key) {
                        { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                    } else null,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }

        // Stories Feed List
        if (stories.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "No stories",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.BN) "এই ফিল্টারে কোনো প্রতিবেদন পাওয়া যায়নি"
                        else if (currentLanguage == AppLanguage.HI) "इस फ़िल्टर में कोई रिपोर्ट नहीं मिली"
                        else "No stories found in this section",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(stories, key = { it.id }) { story ->
                    NewsCard(
                        story = story,
                        currentLanguage = currentLanguage,
                        isSaved = savedStoryIds.contains(story.id),
                        isReacted = userReactions[story.id] ?: false,
                        onReadStory = onReadStory,
                        onToggleSave = onToggleSave,
                        onToggleReaction = onToggleReaction,
                        onCommentClick = onCommentClick
                    )
                }
            }
        }
    }
}
