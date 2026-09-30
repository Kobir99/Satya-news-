package com.example.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.AiAgentJobDao
import com.example.data.local.dao.CommentDao
import com.example.data.local.dao.NewsSourceDao
import com.example.data.local.dao.SavedStoryDao
import com.example.data.local.dao.StoryDao
import com.example.data.local.dao.TopicDao
import com.example.data.local.entity.AiAgentJobEntity
import com.example.data.local.entity.CommentEntity
import com.example.data.local.entity.NewsSourceEntity
import com.example.data.local.entity.SavedStoryEntity
import com.example.data.local.entity.StoryEntity
import com.example.data.local.entity.TopicEntity

@Database(
    entities = [
        StoryEntity::class,
        NewsSourceEntity::class,
        CommentEntity::class,
        SavedStoryEntity::class,
        TopicEntity::class,
        AiAgentJobEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun newsSourceDao(): NewsSourceDao
    abstract fun commentDao(): CommentDao
    abstract fun savedStoryDao(): SavedStoryDao
    abstract fun topicDao(): TopicDao
    abstract fun aiAgentJobDao(): AiAgentJobDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "satya_news_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
