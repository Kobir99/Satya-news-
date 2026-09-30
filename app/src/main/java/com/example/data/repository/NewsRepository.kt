package com.example.data.repository

import android.content.Context
import com.example.data.local.database.AppDatabase
import com.example.data.local.entity.AiAgentJobEntity
import com.example.data.local.entity.CommentEntity
import com.example.data.local.entity.NewsSourceEntity
import com.example.data.local.entity.SavedStoryEntity
import com.example.data.local.entity.StoryEntity
import com.example.data.local.entity.TopicEntity
import com.example.data.model.AppLanguage
import com.example.data.model.ResearchLevel
import com.example.data.remote.GeminiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsRepository(
    private val db: AppDatabase,
    private val geminiClient: GeminiClient = GeminiClient()
) {

    private val storyDao = db.storyDao()
    private val sourceDao = db.newsSourceDao()
    private val commentDao = db.commentDao()
    private val savedStoryDao = db.savedStoryDao()
    private val topicDao = db.topicDao()
    private val agentJobDao = db.aiAgentJobDao()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            initializeDatabaseIfNeeded()
        }
    }

    private suspend fun initializeDatabaseIfNeeded() {
        if (storyDao.getStoryCount() == 0) {
            storyDao.insertStories(SeedData.getInitialStories())
            sourceDao.insertSources(SeedData.getInitialSources())
            topicDao.insertTopics(SeedData.getInitialTopics())
            agentJobDao.insertJobs(SeedData.getInitialAgentJobs())

            // Add comments for first story
            SeedData.getInitialComments(1).forEach {
                commentDao.insertComment(it)
            }
        }
    }

    fun getAllStories(): Flow<List<StoryEntity>> = storyDao.getAllStories()

    fun getStoriesByLanguage(lang: String): Flow<List<StoryEntity>> = storyDao.getStoriesByLanguage(lang)

    fun getBreakingStories(): Flow<List<StoryEntity>> = storyDao.getBreakingStories()

    fun getTrendingStories(): Flow<List<StoryEntity>> = storyDao.getTrendingStories()

    fun getStoryById(id: Long): Flow<StoryEntity?> = storyDao.getStoryById(id)

    suspend fun getStoryByIdSync(id: Long): StoryEntity? = storyDao.getStoryByIdSync(id)

    fun searchStories(query: String): Flow<List<StoryEntity>> = storyDao.searchStories(query)

    suspend fun incrementViews(id: Long) = storyDao.incrementViews(id)

    suspend fun incrementReactions(id: Long) = storyDao.incrementReactions(id)

    fun isStorySaved(storyId: Long): Flow<Boolean> = savedStoryDao.isStorySaved(storyId)

    fun getSavedStories(): Flow<List<SavedStoryEntity>> = savedStoryDao.getSavedStories()

    suspend fun toggleSaveStory(storyId: Long) {
        val isSaved = savedStoryDao.isStorySaved(storyId).firstOrNull() ?: false
        if (isSaved) {
            savedStoryDao.removeSaved(storyId)
        } else {
            savedStoryDao.saveStory(SavedStoryEntity(storyId = storyId))
        }
    }

    fun getCommentsForStory(storyId: Long): Flow<List<CommentEntity>> = commentDao.getCommentsForStory(storyId)

    suspend fun addComment(storyId: Long, userName: String, content: String): Long {
        val comment = CommentEntity(
            storyId = storyId,
            userName = userName.ifBlank { "সচেতন পাঠক" },
            userBadge = "যাচাইকৃত পাঠক",
            content = content
        )
        val id = commentDao.insertComment(comment)
        storyDao.incrementComments(storyId)
        return id
    }

    suspend fun likeComment(commentId: Long) = commentDao.likeComment(commentId)

    fun getAllTopics(): Flow<List<TopicEntity>> = topicDao.getAllTopics()

    suspend fun toggleTopicFollow(topicId: Long, currentFollowState: Boolean) {
        topicDao.toggleFollow(topicId, !currentFollowState)
    }

    fun getAllSources(): Flow<List<NewsSourceEntity>> = sourceDao.getAllSources()

    suspend fun addSource(source: NewsSourceEntity) = sourceDao.insertSource(source)

    fun getAllAgentJobs(): Flow<List<AiAgentJobEntity>> = agentJobDao.getAllAgentJobs()

    suspend fun triggerAgentJob(id: String) = withContext(Dispatchers.IO) {
        agentJobDao.updateJobStatus(id, "RUNNING", System.currentTimeMillis(), 0)
        kotlinx.coroutines.delay(1200)
        agentJobDao.updateJobStatus(id, "ACTIVE", System.currentTimeMillis(), 1)
    }

    suspend fun conductAutonomousResearch(
        topic: String,
        language: AppLanguage,
        depthLevel: ResearchLevel
    ): Long = withContext(Dispatchers.IO) {
        // Run AI research pipeline
        val story = geminiClient.researchTopicAutonomous(topic, language, depthLevel)
        val insertedId = storyDao.insertStory(story)
        agentJobDao.updateJobStatus("research", "ACTIVE", System.currentTimeMillis(), 1)
        agentJobDao.updateJobStatus("writing", "ACTIVE", System.currentTimeMillis(), 1)
        insertedId
    }

    suspend fun askStoryQuestion(story: StoryEntity, question: String, language: AppLanguage): String {
        return geminiClient.askStoryQuestion(story, question, language)
    }

    suspend fun synthesizeSearchQuery(query: String, language: AppLanguage): String {
        return geminiClient.synthesizeSearchQuery(query, language)
    }

    companion object {
        @Volatile
        private var INSTANCE: NewsRepository? = null

        fun getInstance(context: Context): NewsRepository {
            return INSTANCE ?: synchronized(this) {
                val db = AppDatabase.getInstance(context)
                val instance = NewsRepository(db)
                INSTANCE = instance
                instance
            }
        }
    }
}
