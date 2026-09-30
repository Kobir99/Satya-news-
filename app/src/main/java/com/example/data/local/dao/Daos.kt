package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.AiAgentJobEntity
import com.example.data.local.entity.CommentEntity
import com.example.data.local.entity.NewsSourceEntity
import com.example.data.local.entity.SavedStoryEntity
import com.example.data.local.entity.StoryEntity
import com.example.data.local.entity.TopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao {
    @Query("SELECT * FROM stories ORDER BY publishedAt DESC")
    fun getAllStories(): Flow<List<StoryEntity>>

    @Query("SELECT * FROM stories WHERE language = :lang ORDER BY publishedAt DESC")
    fun getStoriesByLanguage(lang: String): Flow<List<StoryEntity>>

    @Query("SELECT * FROM stories WHERE isBreaking = 1 ORDER BY publishedAt DESC")
    fun getBreakingStories(): Flow<List<StoryEntity>>

    @Query("SELECT * FROM stories WHERE isTrending = 1 ORDER BY publishedAt DESC")
    fun getTrendingStories(): Flow<List<StoryEntity>>

    @Query("SELECT * FROM stories WHERE id = :id")
    fun getStoryById(id: Long): Flow<StoryEntity?>

    @Query("SELECT * FROM stories WHERE id = :id")
    suspend fun getStoryByIdSync(id: Long): StoryEntity?

    @Query("SELECT * FROM stories WHERE headline LIKE '%' || :query || '%' OR summary LIKE '%' || :query || '%' OR category LIKE '%' || :query || '%' ORDER BY publishedAt DESC")
    fun searchStories(query: String): Flow<List<StoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: StoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(stories: List<StoryEntity>)

    @Update
    suspend fun updateStory(story: StoryEntity)

    @Query("DELETE FROM stories WHERE id = :id")
    suspend fun deleteStory(id: Long)

    @Query("UPDATE stories SET viewCount = viewCount + 1 WHERE id = :id")
    suspend fun incrementViews(id: Long)

    @Query("UPDATE stories SET reactionCount = reactionCount + 1 WHERE id = :id")
    suspend fun incrementReactions(id: Long)

    @Query("UPDATE stories SET commentCount = commentCount + 1 WHERE id = :id")
    suspend fun incrementComments(id: Long)

    @Query("SELECT COUNT(*) FROM stories")
    suspend fun getStoryCount(): Int
}

@Dao
interface NewsSourceDao {
    @Query("SELECT * FROM news_sources ORDER BY tier ASC, credibilityScore DESC")
    fun getAllSources(): Flow<List<NewsSourceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSource(source: NewsSourceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSources(sources: List<NewsSourceEntity>)

    @Update
    suspend fun updateSource(source: NewsSourceEntity)

    @Query("DELETE FROM news_sources WHERE id = :id")
    suspend fun deleteSource(id: Long)

    @Query("SELECT COUNT(*) FROM news_sources")
    suspend fun getSourceCount(): Int
}

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments WHERE storyId = :storyId ORDER BY timestamp DESC")
    fun getCommentsForStory(storyId: Long): Flow<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: CommentEntity): Long

    @Query("UPDATE comments SET likes = likes + 1 WHERE id = :commentId")
    suspend fun likeComment(commentId: Long)
}

@Dao
interface SavedStoryDao {
    @Query("SELECT * FROM saved_stories ORDER BY savedAt DESC")
    fun getSavedStories(): Flow<List<SavedStoryEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM saved_stories WHERE storyId = :storyId)")
    fun isStorySaved(storyId: Long): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStory(saved: SavedStoryEntity)

    @Query("DELETE FROM saved_stories WHERE storyId = :storyId")
    suspend fun removeSaved(storyId: Long)
}

@Dao
interface TopicDao {
    @Query("SELECT * FROM topics ORDER BY followerCount DESC")
    fun getAllTopics(): Flow<List<TopicEntity>>

    @Query("UPDATE topics SET isFollowed = :isFollowed WHERE id = :id")
    suspend fun toggleFollow(id: Long, isFollowed: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopics(topics: List<TopicEntity>)

    @Query("SELECT COUNT(*) FROM topics")
    suspend fun getTopicCount(): Int
}

@Dao
interface AiAgentJobDao {
    @Query("SELECT * FROM ai_agent_jobs ORDER BY agentNameEn ASC")
    fun getAllAgentJobs(): Flow<List<AiAgentJobEntity>>

    @Query("UPDATE ai_agent_jobs SET status = :status, lastRunTimestamp = :lastRun, storiesProcessed = storiesProcessed + :processedDelta WHERE id = :id")
    suspend fun updateJobStatus(id: String, status: String, lastRun: Long, processedDelta: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobs(jobs: List<AiAgentJobEntity>)

    @Query("SELECT COUNT(*) FROM ai_agent_jobs")
    suspend fun getJobCount(): Int
}
