package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.components.AppTopBar
import com.example.ui.components.AskStoryAssistantDialog
import com.example.ui.components.AutonomousResearchDialog
import com.example.ui.screens.AdminControlScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.FollowingScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.SavedScreen
import com.example.ui.screens.StoryDetailScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.NavTab
import com.example.ui.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by viewModel.themeMode.collectAsState()
            MyApplicationTheme(themeMode = themeMode) {
                MainAppContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppContent(viewModel: NewsViewModel) {
    val themeMode by viewModel.themeMode.collectAsState()
    val currentLanguage by viewModel.selectedLanguage.collectAsState()
    val currentNavTab by viewModel.currentNavTab.collectAsState()
    val homeSubTab by viewModel.homeSubTab.collectAsState()
    val selectedCategoryKey by viewModel.selectedCategoryKey.collectAsState()
    val selectedLocation by viewModel.selectedLocation.collectAsState()
    val activeStoryId by viewModel.activeStoryId.collectAsState()
    val activeStory by viewModel.activeStory.collectAsState()
    val activeStoryComments by viewModel.activeStoryComments.collectAsState()

    val feedStories by viewModel.feedStories.collectAsState()
    val allStories by viewModel.allStories.collectAsState()
    val savedFullStories by viewModel.savedFullStories.collectAsState()
    val allTopics by viewModel.allTopics.collectAsState()
    val allSources by viewModel.allSources.collectAsState()
    val allAgentJobs by viewModel.allAgentJobs.collectAsState()
    val savedStoryIds by viewModel.savedStoryIds.collectAsState()
    val userReactions by viewModel.userReactions.collectAsState()

    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchSynthesis by viewModel.searchSynthesisResult.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    val isResearching by viewModel.isResearchingTopic.collectAsState()
    val researchProgressStep by viewModel.researchProgressStep.collectAsState()

    val storyAssistantAnswer by viewModel.storyAssistantAnswer.collectAsState()
    val isAskingAssistant by viewModel.isAskingAssistant.collectAsState()

    var showResearchDialog by remember { mutableStateOf(false) }
    var showAskAssistantDialog by remember { mutableStateOf(false) }

    // If active story is open, show StoryDetailScreen
    if (activeStory != null) {
        StoryDetailScreen(
            story = activeStory!!,
            comments = activeStoryComments,
            currentLanguage = currentLanguage,
            isSaved = savedStoryIds.contains(activeStory!!.id),
            isReacted = userReactions[activeStory!!.id] ?: false,
            onBack = { viewModel.closeStory() },
            onToggleSave = { viewModel.toggleSave(it) },
            onToggleReaction = { viewModel.toggleReaction(it) },
            onOpenAskAssistant = { showAskAssistantDialog = true },
            onPostComment = { id, author, text -> viewModel.postComment(id, author, text) },
            onLikeComment = { viewModel.likeComment(it) }
        )
    } else {
        Scaffold(
            topBar = {
                AppTopBar(
                    currentLanguage = currentLanguage,
                    onLanguageSelected = { viewModel.setLanguage(it) },
                    onOpenSearch = { viewModel.setNavTab(NavTab.EXPLORE) },
                    onTriggerResearch = { showResearchDialog = true },
                    themeMode = themeMode,
                    onToggleTheme = { viewModel.toggleThemeMode() },
                    onSelectThemeMode = { viewModel.setThemeMode(it) },
                    hasBreakingNews = allStories.any { it.isBreaking },
                    onBreakingClicked = {
                        val breaking = allStories.firstOrNull { it.isBreaking }
                        if (breaking != null) {
                            viewModel.openStory(breaking.id)
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 4.dp
                ) {
                    NavTab.values().forEach { tab ->
                        val label = when (currentLanguage) {
                            AppLanguage.BN -> tab.labelBn
                            AppLanguage.HI -> tab.labelHi
                            AppLanguage.EN -> tab.labelEn
                        }
                        val icon = when (tab) {
                            NavTab.HOME -> Icons.Default.Home
                            NavTab.EXPLORE -> Icons.Default.Explore
                            NavTab.FOLLOWING -> Icons.Default.Subscriptions
                            NavTab.SAVED -> Icons.Default.Bookmark
                            NavTab.ADMIN -> Icons.Default.AdminPanelSettings
                        }

                        NavigationBarItem(
                            selected = currentNavTab == tab,
                            onClick = { viewModel.setNavTab(tab) },
                            icon = { Icon(imageVector = icon, contentDescription = label) },
                            label = { Text(text = label, fontSize = 11.sp, fontWeight = if (currentNavTab == tab) FontWeight.Bold else FontWeight.Normal) },
                            modifier = Modifier.testTag("nav_${tab.name}"),
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AnimatedContent(
                    targetState = currentNavTab,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "tab_switch"
                ) { tab ->
                    when (tab) {
                        NavTab.HOME -> {
                            HomeScreen(
                                stories = feedStories,
                                currentLanguage = currentLanguage,
                                currentSubTab = homeSubTab,
                                selectedCategoryKey = selectedCategoryKey,
                                selectedLocation = selectedLocation,
                                savedStoryIds = savedStoryIds,
                                userReactions = userReactions,
                                onSubTabSelected = { viewModel.setHomeSubTab(it) },
                                onCategorySelected = { viewModel.setCategory(it) },
                                onLocationSelected = { viewModel.setLocation(it) },
                                onReadStory = { viewModel.openStory(it) },
                                onToggleSave = { viewModel.toggleSave(it) },
                                onToggleReaction = { viewModel.toggleReaction(it) },
                                onCommentClick = { viewModel.openStory(it) }
                            )
                        }

                        NavTab.EXPLORE -> {
                            ExploreScreen(
                                stories = if (searchQuery.isNotBlank()) {
                                    allStories.filter {
                                        it.headline.contains(searchQuery, ignoreCase = true) ||
                                            it.summary.contains(searchQuery, ignoreCase = true) ||
                                            it.category.contains(searchQuery, ignoreCase = true)
                                    }
                                } else {
                                    allStories
                                },
                                currentLanguage = currentLanguage,
                                searchQuery = searchQuery,
                                searchSynthesis = searchSynthesis,
                                isSearching = isSearching,
                                savedStoryIds = savedStoryIds,
                                userReactions = userReactions,
                                onSearchQueryChanged = { viewModel.performSearch(it) },
                                onCategorySelected = { viewModel.setCategory(it) },
                                onReadStory = { viewModel.openStory(it) },
                                onToggleSave = { viewModel.toggleSave(it) },
                                onToggleReaction = { viewModel.toggleReaction(it) },
                                onCommentClick = { viewModel.openStory(it) }
                            )
                        }

                        NavTab.FOLLOWING -> {
                            val followedTopics = allTopics.filter { it.isFollowed }
                            val followedCategories = followedTopics.map { it.category }.toSet()
                            val followingStories = allStories.filter { followedCategories.contains(it.categoryKey) }

                            FollowingScreen(
                                stories = followingStories,
                                topics = allTopics,
                                currentLanguage = currentLanguage,
                                savedStoryIds = savedStoryIds,
                                userReactions = userReactions,
                                onToggleFollowTopic = { id, state -> viewModel.toggleFollowTopic(id, state) },
                                onReadStory = { viewModel.openStory(it) },
                                onToggleSave = { viewModel.toggleSave(it) },
                                onToggleReaction = { viewModel.toggleReaction(it) },
                                onCommentClick = { viewModel.openStory(it) }
                            )
                        }

                        NavTab.SAVED -> {
                            SavedScreen(
                                savedStories = savedFullStories,
                                currentLanguage = currentLanguage,
                                savedStoryIds = savedStoryIds,
                                userReactions = userReactions,
                                onReadStory = { viewModel.openStory(it) },
                                onToggleSave = { viewModel.toggleSave(it) },
                                onToggleReaction = { viewModel.toggleReaction(it) },
                                onCommentClick = { viewModel.openStory(it) }
                            )
                        }

                        NavTab.ADMIN -> {
                            AdminControlScreen(
                                stories = allStories,
                                sources = allSources,
                                agentJobs = allAgentJobs,
                                currentLanguage = currentLanguage,
                                currentThemeMode = themeMode,
                                onSelectThemeMode = { viewModel.setThemeMode(it) },
                                onTriggerResearch = { showResearchDialog = true },
                                onRunJob = { viewModel.runAgentJob(it) }
                            )
                        }
                    }
                }
            }
        }
    }

    // Dialogs
    if (showResearchDialog || isResearching) {
        AutonomousResearchDialog(
            currentLanguage = currentLanguage,
            isResearching = isResearching,
            progressStep = researchProgressStep,
            onStartResearch = { topic, level ->
                viewModel.conductAutonomousResearch(topic, level)
            },
            onDismiss = { showResearchDialog = false }
        )
    }

    if (showAskAssistantDialog && activeStory != null) {
        AskStoryAssistantDialog(
            storyHeadline = activeStory!!.headline,
            currentLanguage = currentLanguage,
            assistantAnswer = storyAssistantAnswer,
            isLoading = isAskingAssistant,
            onAskQuestion = { viewModel.askStoryAssistant(it) },
            onDismiss = { showAskAssistantDialog = false }
        )
    }
}
