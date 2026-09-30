package com.example.data.model

data class SourceItem(
    val name: String,
    val tier: Int, // 1: Official/Primary, 2: Established News, 3: Specialized/Local, 4: Community/Leads
    val url: String,
    val quoteOrSummary: String,
    val credibilityScore: Int // 0..100
)

data class TimelineItem(
    val time: String,
    val event: String
)

data class ClaimItem(
    val claimText: String,
    val status: String, // CONFIRMED, SUPPORTED, PARTIALLY_CONFIRMED, DISPUTED, UNVERIFIED, FALSE
    val supportingSources: List<String>,
    val notes: String
)

data class ConflictItem(
    val aspect: String,
    val reportA: String,
    val sourceA: String,
    val reportB: String,
    val sourceB: String,
    val resolutionStatus: String
)

enum class StoryStatus(val labelBn: String, val labelEn: String, val labelHi: String) {
    CONFIRMED("যাচাইকৃত", "Confirmed", "पुष्टीकृत"),
    DEVELOPING("চলমান ঘটনা", "Developing", "विकासशील"),
    RESOLVED("নিষ্পত্তিপ্রাপ্ত", "Resolved", "सुलझा हुआ"),
    INVESTIGATING("অনুসন্ধানাধীন", "Investigating", "जांच जारी")
}

enum class ResearchLevel(val levelNumber: Int, val labelBn: String, val labelEn: String, val labelHi: String) {
    LEVEL_1_QUICK(1, "স্তর ১: দ্রুত অনুসন্ধান (২-৩ উৎস)", "Level 1: Quick News (2-3 Sources)", "स्तर 1: त्वरित समाचार"),
    LEVEL_2_STANDARD(2, "স্তর ২: প্রমিত গবেষণা (৩-৬ উৎস)", "Level 2: Standard (3-6 Sources)", "स्तर 2: मानक अनुसंधान"),
    LEVEL_3_DEEP(3, "স্তর ৩: গভীর অনুসন্ধান ও প্রমাণ মানচিত্র", "Level 3: Deep Research & Evidence Map", "स्तर 3: गहन अनुसंधान")
}

enum class AppLanguage(val code: String, val displayName: String, val nativeName: String) {
    BN("bn", "Bengali", "বাংলা"),
    EN("en", "English", "English"),
    HI("hi", "Hindi", "हिन्दी")
}

enum class AgentStatus(val labelBn: String, val labelEn: String) {
    ACTIVE("সক্রিয়", "Active"),
    RUNNING("চলছে", "Running"),
    IDLE("অপেক্ষমাণ", "Idle"),
    PAUSED("স্থগিত", "Paused")
}
