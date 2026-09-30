package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stories")
data class StoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val headline: String,
    val subheadline: String,
    val summary: String,
    val whatHappened: String,
    val keyFactsJson: String,
    val whatWeKnowJson: String,
    val whatWeDontKnowJson: String,
    val background: String,
    val timelineJson: String,
    val whatSourcesSayJson: String,
    val whyItMatters: String,
    val sourcesJson: String,
    val claimsJson: String,
    val conflictJson: String = "[]",
    val category: String,
    val categoryKey: String,
    val locationTag: String = "বৈশ্বিক",
    val status: String = "CONFIRMED",
    val researchLevel: String = "LEVEL_2_STANDARD",
    val language: String = "bn",
    val isBreaking: Boolean = false,
    val isTrending: Boolean = false,
    val isAiPick: Boolean = false,
    val sourceCount: Int = 3,
    val confidencePercentage: Int = 92,
    val imageUrl: String = "",
    val imageCredit: String = "AI Editorial Archive / Public Record",
    val viewCount: Int = 0,
    val reactionCount: Int = 0,
    val commentCount: Int = 0,
    val publishedAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val aiDisclosure: String = "এই প্রতিবেদনটি বিভিন্ন নির্ভরযোগ্য উৎসের তথ্য বিশ্লেষণ করে AI দ্বারা প্রস্তুত করা হয়েছে।"
)

@Entity(tableName = "news_sources")
data class NewsSourceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val domain: String,
    val tier: Int, // 1 to 4
    val credibilityScore: Int, // 0..100
    val category: String,
    val isApproved: Boolean = true,
    val lastScannedAt: Long = System.currentTimeMillis(),
    val totalStoriesContributed: Int = 0
)

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val storyId: Long,
    val userName: String,
    val userBadge: String = "পাঠক",
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val likes: Int = 0,
    val moderationStatus: String = "CLEAN" // CLEAN, FLAGGED
)

@Entity(tableName = "saved_stories")
data class SavedStoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val storyId: Long,
    val savedAt: Long = System.currentTimeMillis(),
    val notes: String = ""
)

@Entity(tableName = "topics")
data class TopicEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nameBn: String,
    val nameEn: String,
    val nameHi: String,
    val category: String,
    val followerCount: Int = 120,
    val isFollowed: Boolean = false
)

@Entity(tableName = "ai_agent_jobs")
data class AiAgentJobEntity(
    @PrimaryKey
    val id: String, // discovery, research, verification, writing, trend, moderation
    val agentNameBn: String,
    val agentNameEn: String,
    val descriptionBn: String,
    val descriptionEn: String,
    val status: String = "ACTIVE", // ACTIVE, RUNNING, IDLE, PAUSED
    val lastRunTimestamp: Long = System.currentTimeMillis(),
    val storiesProcessed: Int = 0,
    val errorsEncountered: Int = 0,
    val avgDurationMs: Long = 1450
)
