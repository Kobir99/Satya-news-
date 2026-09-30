package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.entity.AiAgentJobEntity
import com.example.data.local.entity.CommentEntity
import com.example.data.local.entity.NewsSourceEntity
import com.example.data.local.entity.StoryEntity
import com.example.data.local.entity.TopicEntity
import com.example.data.model.AppLanguage
import com.example.data.model.ResearchLevel
import com.example.data.repository.NewsRepository
import com.example.ui.theme.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class NavTab(val labelBn: String, val labelEn: String, val labelHi: String) {
    HOME("হোম", "Home", "होम"),
    EXPLORE("অনুসন্ধান", "Explore", "खोजें"),
    FOLLOWING("অনুসরণ", "Following", "फ़ॉलोइंग"),
    SAVED("সংরক্ষিত", "Saved", "सहेजे गए"),
    ADMIN("নিউজ রুম", "Newsroom", "न्यूज़रूम")
}

enum class HomeSubTab(val labelBn: String, val labelEn: String, val labelHi: String) {
    FOR_YOU("আপনার জন্য", "For You", "आपके लिए"),
    LATEST("তাজা খবর", "Latest", "ताज़ा"),
    BREAKING("ব্রেকিং", "Breaking", "ब्रेकिंग"),
    TRENDING("ট্রেন্ডিং", "Trending", "ट्रेंडिंग"),
    FOLLOWING("অনুসরণ", "Following", "फ़ॉलोइंग")
}

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsRepository.getInstance(application)

    // Current app language
    private val _selectedLanguage = MutableStateFlow(AppLanguage.BN)
    val selectedLanguage: StateFlow<AppLanguage> = _selectedLanguage.asStateFlow()

    // Editorial Theme Mode (Dark / Light / System)
    private val _themeMode = MutableStateFlow(ThemeMode.DARK)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    // Navigation Tab
    private val _currentNavTab = MutableStateFlow(NavTab.HOME)
    val currentNavTab: StateFlow<NavTab> = _currentNavTab.asStateFlow()

    // Home Sub Tab
    private val _homeSubTab = MutableStateFlow(HomeSubTab.FOR_YOU)
    val homeSubTab: StateFlow<HomeSubTab> = _homeSubTab.asStateFlow()

    // Active Category Filter
    private val _selectedCategoryKey = MutableStateFlow("all")
    val selectedCategoryKey: StateFlow<String> = _selectedCategoryKey.asStateFlow()

    // Active Location Filter
    private val _selectedLocation = MutableStateFlow("all")
    val selectedLocation: StateFlow<String> = _selectedLocation.asStateFlow()

    // Selected Story Detail (null = in feed)
    private val _activeStoryId = MutableStateFlow<Long?>(null)
    val activeStoryId: StateFlow<Long?> = _activeStoryId.asStateFlow()

    // Search state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchSynthesisResult = MutableStateFlow<String?>(null)
    val searchSynthesisResult: StateFlow<String?> = _searchSynthesisResult.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    // Autonomous Research state
    private val _isResearchingTopic = MutableStateFlow(false)
    val isResearchingTopic: StateFlow<Boolean> = _isResearchingTopic.asStateFlow()

    private val _researchProgressStep = MutableStateFlow<String?>(null)
    val researchProgressStep: StateFlow<String?> = _researchProgressStep.asStateFlow()

    // Story Q&A Assistant state
    private val _storyAssistantAnswer = MutableStateFlow<String?>(null)
    val storyAssistantAnswer: StateFlow<String?> = _storyAssistantAnswer.asStateFlow()

    private val _isAskingAssistant = MutableStateFlow(false)
    val isAskingAssistant: StateFlow<Boolean> = _isAskingAssistant.asStateFlow()

    // Reactions tracking (storyId -> isReacted)
    private val _userReactions = MutableStateFlow<Map<Long, Boolean>>(emptyMap())
    val userReactions: StateFlow<Map<Long, Boolean>> = _userReactions.asStateFlow()

    // Raw sources from Room
    val allStories: StateFlow<List<StoryEntity>> = repository.getAllStories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allTopics: StateFlow<List<TopicEntity>> = repository.getAllTopics()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSources: StateFlow<List<NewsSourceEntity>> = repository.getAllSources()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allAgentJobs: StateFlow<List<AiAgentJobEntity>> = repository.getAllAgentJobs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedStoriesRaw = repository.getSavedStories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Saved story IDs set
    val savedStoryIds: StateFlow<Set<Long>> = savedStoriesRaw.map { list ->
        list.map { it.storyId }.toSet()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    // Active story object
    val activeStory: StateFlow<StoryEntity?> = combine(allStories, _activeStoryId) { stories, id ->
        if (id == null) null else stories.find { it.id == id }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Comments for active story
    private val _activeStoryComments = MutableStateFlow<List<CommentEntity>>(emptyList())
    val activeStoryComments: StateFlow<List<CommentEntity>> = _activeStoryComments.asStateFlow()

    init {
        // Observe active story ID to refresh comments
        viewModelScope.launch {
            _activeStoryId.collect { id ->
                if (id != null) {
                    repository.getCommentsForStory(id).collect {
                        _activeStoryComments.value = it
                    }
                } else {
                    _activeStoryComments.value = emptyList()
                    _storyAssistantAnswer.value = null
                }
            }
        }
    }

    // Filtered Feed Stories based on sub-tab and language/category
    val feedStories: StateFlow<List<StoryEntity>> = combine(
        allStories,
        _homeSubTab,
        _selectedCategoryKey,
        _selectedLocation,
        allTopics
    ) { stories, subTab, category, location, topics ->
        var list = stories

        // Filter by location if specified
        if (location != "all") {
            list = list.filter { it.locationTag.contains(location, ignoreCase = true) }
        }

        // Filter by category if specified
        if (category != "all") {
            list = list.filter { it.categoryKey.equals(category, ignoreCase = true) }
        }

        when (subTab) {
            HomeSubTab.FOR_YOU -> list
            HomeSubTab.LATEST -> list.sortedByDescending { it.publishedAt }
            HomeSubTab.BREAKING -> list.filter { it.isBreaking }
            HomeSubTab.TRENDING -> list.filter { it.isTrending }
            HomeSubTab.FOLLOWING -> {
                val followedCategoryKeys = topics.filter { it.isFollowed }.map { it.category }.toSet()
                list.filter { followedCategoryKeys.contains(it.categoryKey) }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Saved full stories
    val savedFullStories: StateFlow<List<StoryEntity>> = combine(allStories, savedStoryIds) { stories, ids ->
        stories.filter { ids.contains(it.id) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Actions
    fun setLanguage(language: AppLanguage) {
        _selectedLanguage.value = language
    }

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }

    fun toggleThemeMode() {
        _themeMode.value = when (_themeMode.value) {
            ThemeMode.DARK -> ThemeMode.LIGHT
            ThemeMode.LIGHT -> ThemeMode.DARK
            ThemeMode.SYSTEM -> ThemeMode.DARK
        }
    }

    fun setNavTab(tab: NavTab) {
        _currentNavTab.value = tab
    }

    fun setHomeSubTab(subTab: HomeSubTab) {
        _homeSubTab.value = subTab
    }

    fun setCategory(categoryKey: String) {
        _selectedCategoryKey.value = categoryKey
    }

    fun setLocation(loc: String) {
        _selectedLocation.value = loc
    }

    fun openStory(storyId: Long) {
        _activeStoryId.value = storyId
        viewModelScope.launch {
            repository.incrementViews(storyId)
        }
    }

    fun closeStory() {
        _activeStoryId.value = null
        _storyAssistantAnswer.value = null
    }

    fun toggleSave(storyId: Long) {
        viewModelScope.launch {
            repository.toggleSaveStory(storyId)
        }
    }

    fun toggleReaction(storyId: Long) {
        val current = _userReactions.value[storyId] ?: false
        val newMap = _userReactions.value.toMutableMap()
        newMap[storyId] = !current
        _userReactions.value = newMap

        if (!current) {
            viewModelScope.launch {
                repository.incrementReactions(storyId)
            }
        }
    }

    fun toggleFollowTopic(topicId: Long, currentFollowState: Boolean) {
        viewModelScope.launch {
            repository.toggleTopicFollow(topicId, currentFollowState)
        }
    }

    fun postComment(storyId: Long, author: String, text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            repository.addComment(storyId, author, text)
        }
    }

    fun likeComment(commentId: Long) {
        viewModelScope.launch {
            repository.likeComment(commentId)
        }
    }

    fun performSearch(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _searchSynthesisResult.value = null
            return
        }
        viewModelScope.launch {
            _isSearching.value = true
            val synthesis = repository.synthesizeSearchQuery(query, _selectedLanguage.value)
            _searchSynthesisResult.value = synthesis
            _isSearching.value = false
        }
    }

    fun askStoryAssistant(question: String) {
        val currentStory = activeStory.value ?: return
        if (question.isBlank()) return

        viewModelScope.launch {
            _isAskingAssistant.value = true
            val ans = repository.askStoryQuestion(currentStory, question, _selectedLanguage.value)
            _storyAssistantAnswer.value = ans
            _isAskingAssistant.value = false
        }
    }

    fun conductAutonomousResearch(topic: String, depthLevel: ResearchLevel = ResearchLevel.LEVEL_2_STANDARD) {
        if (topic.isBlank()) return
        viewModelScope.launch {
            _isResearchingTopic.value = true
            _researchProgressStep.value = when (_selectedLanguage.value) {
                AppLanguage.BN -> "ধাপ ১/১১: বহু-উৎস তথ্য সন্ধান ও উন্মোচন চলছে..."
                AppLanguage.HI -> "चरण 1/11: बहु-स्रोत खोज व साक्ष्य संकलन जारी..."
                AppLanguage.EN -> "Step 1/11: Discovering & Collecting Multi-Source Records..."
            }
            kotlinx.coroutines.delay(600)

            _researchProgressStep.value = when (_selectedLanguage.value) {
                AppLanguage.BN -> "ধাপ ৫/১১: দাবি বিভাজন, সংঘাত পরীক্ষণ ও সত্যতা যাচাই..."
                AppLanguage.HI -> "चरण 5/11: दावों का वर्गीकरण व सत्यता परीक्षण..."
                AppLanguage.EN -> "Step 5/11: Comparing Claims & Detecting Source Conflicts..."
            }
            kotlinx.coroutines.delay(600)

            _researchProgressStep.value = when (_selectedLanguage.value) {
                AppLanguage.BN -> "ধাপ ৯/১১: নিরপেক্ষ মূল প্রতিবেদন রচনা ও মান নিয়ন্ত্রণ..."
                AppLanguage.HI -> "चरण 9/11: निष्पक्ष रिपोर्ट लेखन व गुणवत्ता जांच..."
                AppLanguage.EN -> "Step 9/11: Synthesizing Original News & Quality Verification..."
            }

            val insertedId = repository.conductAutonomousResearch(topic, _selectedLanguage.value, depthLevel)
            _isResearchingTopic.value = false
            _researchProgressStep.value = null
            // Automatically open the new deeply-researched story!
            openStory(insertedId)
        }
    }

    fun runAgentJob(jobId: String) {
        viewModelScope.launch {
            repository.triggerAgentJob(jobId)
        }
    }
}
