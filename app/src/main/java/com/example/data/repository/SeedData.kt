package com.example.data.repository

import com.example.data.local.entity.AiAgentJobEntity
import com.example.data.local.entity.CommentEntity
import com.example.data.local.entity.NewsSourceEntity
import com.example.data.local.entity.StoryEntity
import com.example.data.local.entity.TopicEntity
import com.example.data.model.ClaimItem
import com.example.data.model.ConflictItem
import com.example.data.model.SourceItem
import com.example.data.model.TimelineItem
import com.example.data.util.JsonUtils

object SeedData {

    fun getInitialSources(): List<NewsSourceEntity> = listOf(
        NewsSourceEntity(
            name = "Press Information Bureau (PIB)",
            domain = "pib.gov.in",
            tier = 1,
            credibilityScore = 98,
            category = "সরকারি গ্যাজেট ও প্রাতিষ্ঠানিক প্রেস বিজ্ঞপ্তি"
        ),
        NewsSourceEntity(
            name = "ISRO & National Space Archive",
            domain = "isro.gov.in",
            tier = 1,
            credibilityScore = 99,
            category = "বিজ্ঞান ও মহাকাশ গবেষণা"
        ),
        NewsSourceEntity(
            name = "Nature Science Journal",
            domain = "nature.com",
            tier = 1,
            credibilityScore = 97,
            category = "পিয়ার-রিভিউড বিজ্ঞান ও প্রযুক্তি"
        ),
        NewsSourceEntity(
            name = "World Health Organization (WHO)",
            domain = "who.int",
            tier = 1,
            credibilityScore = 96,
            category = "আন্তর্জাতিক স্বাস্থ্য ও নীতি"
        ),
        NewsSourceEntity(
            name = "Reuters Editorial Desk",
            domain = "reuters.com",
            tier = 2,
            credibilityScore = 95,
            category = "আন্তর্জাতিক নিরপেক্ষ সংবাদ"
        ),
        NewsSourceEntity(
            name = "BBC News",
            domain = "bbc.com",
            tier = 2,
            credibilityScore = 94,
            category = "বৈশ্বিক ও আঞ্চলিক সংবাদ"
        ),
        NewsSourceEntity(
            name = "Anandabazar Patrika",
            domain = "anandabazar.com",
            tier = 2,
            credibilityScore = 91,
            category = "আঞ্চলিক ও বাংলা মূলধারার গণমাধ্যম"
        ),
        NewsSourceEntity(
            name = "The Hindu",
            domain = "thehindu.com",
            tier = 2,
            credibilityScore = 93,
            category = "জাতীয় ও আইনি পর্যালোচনা"
        ),
        NewsSourceEntity(
            name = "MIT Technology Review",
            domain = "technologyreview.com",
            tier = 3,
            credibilityScore = 92,
            category = "উন্নত প্রযুক্তি ও এআই নীতিমালা"
        )
    )

    fun getInitialTopics(): List<TopicEntity> = listOf(
        TopicEntity(
            nameBn = "মহাকাশ ও বিজ্ঞান",
            nameEn = "Space & Science",
            nameHi = "अंतरिक्ष और विज्ञान",
            category = "science",
            followerCount = 1420,
            isFollowed = true
        ),
        TopicEntity(
            nameBn = "কৃত্রিম বুদ্ধিমত্তা ও প্রযুক্তি",
            nameEn = "AI & Technology",
            nameHi = "आर्टिफिशियल इंटेलिजेंस व तकनीक",
            category = "technology",
            followerCount = 2890,
            isFollowed = true
        ),
        TopicEntity(
            nameBn = "সবুজ শক্তি ও জলবায়ু",
            nameEn = "Clean Energy & Climate",
            nameHi = "हरित ऊर्जा और जलवायु",
            category = "environment",
            followerCount = 980,
            isFollowed = false
        ),
        TopicEntity(
            nameBn = "চিকিৎসা ও বায়োটেক",
            nameEn = "Biotech & Medicine",
            nameHi = "चिकित्सा व बायोटेक",
            category = "science",
            followerCount = 850,
            isFollowed = false
        ),
        TopicEntity(
            nameBn = "ভারত ও দক্ষিণ এশিয়া",
            nameEn = "India & South Asia",
            nameHi = "भारत और दक्षिण एशिया",
            category = "india",
            followerCount = 3100,
            isFollowed = true
        ),
        TopicEntity(
            nameBn = "বৈশ্বিক অর্থনীতি",
            nameEn = "Global Economy",
            nameHi = "वैश्विक अर्थव्यवस्था",
            category = "business",
            followerCount = 1750,
            isFollowed = false
        )
    )

    fun getInitialAgentJobs(): List<AiAgentJobEntity> = listOf(
        AiAgentJobEntity(
            id = "discovery",
            agentNameBn = "ডিসকভারি এজেন্ট (সন্ধান)",
            agentNameEn = "Discovery Agent",
            descriptionBn = "অফিসিয়াল প্রেস রিলিজ, গবেষণা জার্নাল এবং অনুমোদিত আরএসএস ফিড সার্বক্ষণিক পর্যবেক্ষণ করে নতুন প্রসঙ্গের সন্ধান করে।",
            descriptionEn = "Continuously monitors primary RSS feeds, official press desks, and research publications for breaking developments.",
            status = "ACTIVE",
            lastRunTimestamp = System.currentTimeMillis() - 4 * 60 * 1000,
            storiesProcessed = 42,
            errorsEncountered = 0,
            avgDurationMs = 1200
        ),
        AiAgentJobEntity(
            id = "research",
            agentNameBn = "রিসার্চ এজেন্ট (বহু-উৎস অনুসন্ধান)",
            agentNameEn = "Research Agent",
            descriptionBn = "একটি ঘটনা চিহ্নিত হলে ৩ থেকে ৬টি স্বাধীন ও বিশ্বস্ত উৎস থেকে নথিপত্র এবং প্রত্যক্ষ প্রমাণ সংগ্রহ করে।",
            descriptionEn = "Orchestrates multi-source collection across tier-1 to tier-3 vetted registries and official records.",
            status = "ACTIVE",
            lastRunTimestamp = System.currentTimeMillis() - 9 * 60 * 1000,
            storiesProcessed = 38,
            errorsEncountered = 0,
            avgDurationMs = 2800
        ),
        AiAgentJobEntity(
            id = "verification",
            agentNameBn = "ভেরিফিকেশন এজেন্ট (দাবি যাচাই ও সংঘাত পরীক্ষণ)",
            agentNameEn = "Verification Agent",
            descriptionBn = "প্রতিবেদনকে পৃথক দাবি (Claims) আকারে বিভক্ত করে নিশ্চিত, অসমর্থিত ও বিরোধপূর্ণ তথ্যের তুলনামূলক টেবিল তৈরি করে।",
            descriptionEn = "Deconstructs stories into claims, detects source contradictions, and marks verification confidence.",
            status = "ACTIVE",
            lastRunTimestamp = System.currentTimeMillis() - 14 * 60 * 1000,
            storiesProcessed = 38,
            errorsEncountered = 0,
            avgDurationMs = 1950
        ),
        AiAgentJobEntity(
            id = "writing",
            agentNameBn = "রাইটিং এজেন্ট (মূল প্রতিবেদন ও সারসংক্ষেপ)",
            agentNameEn = "Writing Agent",
            descriptionBn = "কোনো কপি না করে নিজস্ব প্রাঞ্জল ও নিরপেক্ষ ভাষায় বহু-উৎস সমন্বিত মূল সংবাদ গল্প রচনা করে।",
            descriptionEn = "Synthesizes original, neutral, non-copied news stories with timeline, context, and transparent citations.",
            status = "ACTIVE",
            lastRunTimestamp = System.currentTimeMillis() - 18 * 60 * 1000,
            storiesProcessed = 35,
            errorsEncountered = 0,
            avgDurationMs = 2100
        ),
        AiAgentJobEntity(
            id = "trend",
            agentNameBn = "ট্রেন্ড ও গুরুত্ব মূল্যায়ন এজেন্ট",
            agentNameEn = "Trend & Significance Agent",
            descriptionBn = "শুধুমাত্র ভাইরাল হওয়া নয়, জনস্বার্থে গুরুত্বপূর্ণ কম আলোচিত বিষয়সমূহ চিহ্নিত করে অগ্রাধিকার দেয়।",
            descriptionEn = "Identifies emerging and critical public-interest topics without relying solely on social media algorithms.",
            status = "ACTIVE",
            lastRunTimestamp = System.currentTimeMillis() - 28 * 60 * 1000,
            storiesProcessed = 29,
            errorsEncountered = 0,
            avgDurationMs = 1400
        ),
        AiAgentJobEntity(
            id = "moderation",
            agentNameBn = "মডারেশন ও তথ্য সুরক্ষা এজেন্ট",
            agentNameEn = "Moderation & Safety Agent",
            descriptionBn = "পাঠক মতামত বিশ্লেষণ, ভুল তথ্য প্রতিরোধ ও কপিরাইট সুরক্ষা কঠোরভাবে নিশ্চিত করে।",
            descriptionEn = "Performs automated toxicity, copyright compliance, and misinformation filtering on reader feedback.",
            status = "ACTIVE",
            lastRunTimestamp = System.currentTimeMillis() - 32 * 60 * 1000,
            storiesProcessed = 112,
            errorsEncountered = 0,
            avgDurationMs = 600
        )
    )

    fun getInitialStories(): List<StoryEntity> {
        val now = System.currentTimeMillis()

        // 1. Space Science Story (Bengali)
        val spaceSources = listOf(
            SourceItem("ISRO Scientific Registry", 1, "https://isro.gov.in/chandrayaan-data", "চন্দ্রপৃষ্ঠের আল্ট্রা-স্পেকট্রোমিটার খনিজ উপাত্ত নথিবদ্ধকরণ", 99),
            SourceItem("Nature Astronomy Publication", 1, "https://nature.com/articles/s41550-026", "পিয়ার-রিভিউড স্বাধীন ল্যাবরেটরি স্পেকট্রাল বিশ্লেষণ", 98),
            SourceItem("Reuters Space Desk", 2, "https://reuters.com/science/space-minerals-2026", "আন্তর্জাতিক বিজ্ঞানীদের বক্তব্য ও তুলনামূলক ডেটা", 95),
            SourceItem("The Hindu Science Review", 2, "https://thehindu.com/sci-tech/lunar-ice-composition", "জাতীয় ল্যাবরেটরি গবেষকদের প্রাথমিক পর্যবেক্ষণ", 92)
        )
        val spaceTimeline = listOf(
            TimelineItem("০৮:১৫ AM", "মহাকাশ গবেষণা সংস্থার ডেটাসেন্টার থেকে প্রাথমিক বর্ণালী সংকেত গ্রহণ।"),
            TimelineItem("১১:৩০ AM", "স্বাধীন তিনটি অ্যাস্ট্রোফিজিক্স সেন্টারে উপাত্তের সত্যতা পরীক্ষণ সম্পন্ন।"),
            TimelineItem("০২:৪৫ PM", "অফিসিয়াল প্রেস কনফারেন্সে হাইড্রেটেড মিনারেল আবিষ্কারের আনুষ্ঠানিক স্বীকৃতি।"),
            TimelineItem("০৫:০০ PM", "SatyaNews রিসার্চ ডেস্কের মাধ্যমে পিয়ার-রিভিউড জার্নাল ও স্পেস এজেন্সির তথ্যের মিল চূড়ান্তকরণ।")
        )
        val spaceClaims = listOf(
            ClaimItem("চন্দ্রপৃষ্ঠের দক্ষিণ মেরুর শিলাখণ্ডে টাইটানিয়াম ও হাইড্রেটেড সিলিকেটের উপস্থিতি পাওয়া গেছে।", "CONFIRMED", listOf("ISRO Scientific Registry", "Nature Astronomy"), "দুইটি স্বাধীন স্পেকট্রোমিটার ডেটা দ্বারা সম্পূর্ণ সমর্থিত।"),
            ClaimItem("এই খনিজ ভবিষ্যতে স্থায়ী মানব বসতির জন্য অক্সিজেন ও পানি আহরণে সরাসরি ব্যবহারযোগ্য।", "SUPPORTED", listOf("Nature Astronomy", "Reuters Space Desk"), "ল্যাব মডেলিং সফল হলেও সরাসরি ফিল্ড এক্সট্রাকশন পরীক্ষা এখনো বাকি।"),
            ClaimItem("বেসরকারি খনির কার্যক্রম আগামী ৬ মাসের মধ্যে শুরু হবে।", "UNVERIFIED", listOf(), "সামাজিক মাধ্যমে ছড়িয়ে পড়া এই দাবির পক্ষে কোনো সরকারি অনুমোদন বা আইনি ছাড়পত্র নেই।")
        )
        val spaceConflicts = listOf(
            ConflictItem(
                aspect = "জলাবদ্ধতার ঘনত্ব ও গভীরতা",
                reportA = "উৎস ১ (রিসার্চ জার্নাল) অনুযায়ী বরফের ঘনত্ব প্রতি কেজিতে সর্বোচ্চ ১২০ গ্রাম।",
                sourceA = "Nature Astronomy",
                reportB = "উৎস ২ (আন্তর্জাতিক সংস্থা) এর প্রাথমিক মডেলে ঘনত্ব প্রায় ৮০-৯০ গ্রাম বলা হয়েছে।",
                sourceB = "Reuters Space Desk",
                resolutionStatus = "আসন্ন ড্রিলিং রোবটের মূল কোর-নমুনা হাতে পাওয়ার পর চূড়ান্ত সংখ্যা নিশ্চিত হবে।"
            )
        )
        val spaceWhatSourcesSay = listOf(
            "ISRO: 'স্পেকট্রোমিটারের উপাত্ত সন্দেহাতীতভাবে প্রমাণ করেছে যে গভীর খাদে সুরক্ষিত বরফ ও টাইটানিয়াম সমৃদ্ধ খনিজের স্তর অক্ষত রয়েছে।'",
            "Nature Editorial: 'স্বাধীন বিশ্লেষণের মাধ্যমে উপাত্তের মান যাচাই করা হয়েছে এবং এটি সাম্প্রতিক চন্দ্র অভিযানের অন্যতম নিখুঁত আবিষ্কার।'"
        )

        val story1 = StoryEntity(
            headline = "চন্দ্রপৃষ্ঠে নতুন বিরল খনিজ ও হাইড্রেটেড বরফের সন্ধান: ৪টি স্বাধীন উৎসের তথ্য যাচাই",
            subheadline = "মহাকাশ গবেষণা সংস্থার আনুষ্ঠানিক প্রেস বিজ্ঞপ্তি এবং পিয়ার-রিভিউড বৈজ্ঞানিক উপাত্ত পর্যালোচনা করে তৈরি প্রতিবেদন",
            summary = "চন্দ্রাভিযানের সর্বাধুনিক রোভারের পাঠানো স্পেকট্রাল উপাত্ত বিশ্লেষণ করে চাঁদের দক্ষিণ মেরুর অন্ধকার খাদে প্রচুর পরিমাণে টাইটানিয়াম-সমৃদ্ধ হাইড্রেটেড খনিজের অস্তিত্ব নিশ্চিত করা গেছে। স্যাটেলাইট ডাটা ও ভূ-তাত্ত্বিক ল্যাবরেটরির স্বাধীন গবেষণায় এই দাবির সত্যতা পাওয়া গেছে।",
            whatHappened = "চন্দ্রপৃষ্ঠের তাপমাত্রা যখন মাইনাস ১৮০ ডিগ্রি সেলসিয়াস ছিল, তখন স্বয়ংক্রিয় রোবোটিক বাহনটি একটি গভীর গর্তের প্রান্তদেশে ড্রিল করে রাসায়নিক উপাদান বিশ্লেষণ করে। প্রাপ্ত নমুনার সাথে পৃথিবীর অনুরূপ খনিজের আণবিক গঠন মিলিয়ে দেখা হয়েছে।",
            keyFactsJson = JsonUtils.stringListToJson(listOf(
                "চন্দ্রপৃষ্ঠের গভীর খাদে টাইটানিয়াম ও সিলিকেটের অনন্য সংমিশ্রণ শনাক্ত।",
                "মহাকাশ সংস্থা এবং আন্তর্জাতিক বিজ্ঞান সাময়িকী উভয় পক্ষ থেকেই ডেটা স্বাধীনভাবে সমর্থিত।",
                "ভবিষ্যতের গভীর মহাকাশ মিশনের জন্য এটি অত্যন্ত গুরুত্বপূর্ণ জ্বালানি ও জীবনধারণের উৎস হতে পারে।"
            )),
            whatWeKnowJson = JsonUtils.stringListToJson(listOf(
                "স্পেকট্রোমিটার ডেটা সঠিক এবং ডিভাইসের কোনো ক্যালিব্রেশন ত্রুটি ছিল না।",
                "প্রাপ্ত উপাত্ত দুইটি পৃথক মহাকাশ সংস্থা দ্বারা পুনরায় গণনা করা হয়েছে।"
            )),
            whatWeDontKnowJson = JsonUtils.stringListToJson(listOf(
                "খনিজগুলো কত গভীর পর্যন্ত বিস্তৃত এবং বাণিজ্যিকভাবে আহরণের প্রযুক্তিগত খরচ কত হবে তা এখনও অজানা।"
            )),
            background = "২০২৪ থেকে শুরু হওয়া আধুনিক চন্দ্রাভিযান প্রকল্পের অংশ হিসেবে এই গবেষণাটি পরিচালিত হচ্ছে। এর আগে সামাজিক মাধ্যমে বিভিন্ন অপ্রমাণিত দাবি ছড়িয়ে পড়লেও আজকের প্রাতিষ্ঠানিক ডেটা রিলিজের পর বিষয়টি প্রাতিষ্ঠানিক ভিত্তি পেল।",
            timelineJson = JsonUtils.timelineToJson(spaceTimeline),
            whatSourcesSayJson = JsonUtils.stringListToJson(spaceWhatSourcesSay),
            whyItMatters = "মহাকাশে দীর্ঘমেয়াদী অবস্থান এবং পৃথিবী থেকে ভারী সম্পদ পরিবহনের খরচ নাটকীয়ভাবে কমাতে চাঁদের স্থানীয় সম্পদ ব্যবহারই বিজ্ঞানীদের মূল লক্ষ্য।",
            sourcesJson = JsonUtils.sourcesToJson(spaceSources),
            claimsJson = JsonUtils.claimsToJson(spaceClaims),
            conflictJson = JsonUtils.conflictsToJson(spaceConflicts),
            category = "মহাকাশ ও বিজ্ঞান",
            categoryKey = "science",
            locationTag = "আন্তর্জাতিক",
            status = "CONFIRMED",
            researchLevel = "LEVEL_3_DEEP",
            language = "bn",
            isBreaking = true,
            isTrending = true,
            isAiPick = true,
            sourceCount = 4,
            confidencePercentage = 97,
            imageUrl = "news_space_rover_1790729095622",
            imageCredit = "SatyaNews Verified Scientific Archive / Deep Space Imaging",
            viewCount = 1840,
            reactionCount = 342,
            commentCount = 18,
            publishedAt = now - 22 * 60 * 1000,
            updatedAt = now - 5 * 60 * 1000
        )

        // 2. Tech / Optical Neural Chips (Bengali)
        val techSources = listOf(
            SourceItem("MIT & Stanford Research Repositories", 1, "https://arxiv.org/abs/2026.opt-chip", "সিলিকন ফোটোনিক্স ইন্টারকানেক্ট পরীক্ষা বিবরণী", 98),
            SourceItem("IEEE Microelectronics Letters", 1, "https://ieee.org/papers/optical-ai", "বিদ্যুৎ সাশ্রয়ী কম্পিউটিং মাপকাঠি বিশ্লেষণ", 97),
            SourceItem("Reuters Technology Desk", 2, "https://reuters.com/tech/photonic-ai-processors", "সেমিকন্ডাক্টর শিল্প বিশেষজ্ঞদের সাক্ষাৎকার", 94),
            SourceItem("Anandabazar Patrika Tech", 2, "https://anandabazar.com/tech/new-computer-chips", "বাজার প্রতিক্রিয়া ও সম্ভাব্য ডিভাইস প্রভাব", 90)
        )
        val techTimeline = listOf(
            TimelineItem("০৯:০০ AM", "আন্তর্জাতিক ফোটোনিক্স সম্মেলনে প্রোটোটাইপ চিপের উন্মোচন।"),
            TimelineItem("০১:১৫ PM", "বেঞ্চমার্ক ফলাফলের উন্মুক্ত কোড ও ডেটাসেট গবেষকদের জন্য উন্মুক্ত।"),
            TimelineItem("০৩:৩০ PM", "বিদ্যুৎ ব্যবহার ও তাপমাত্রার স্বাধীন মেজারমেন্ট ডেটা প্রকাশ।")
        )
        val techClaims = listOf(
            ClaimItem("নতুন ফোটোনিক চিপ সাধারণ ইলেকট্রনিক চিপের চেয়ে ৮০ শতাংশ কম শক্তিতে কাজ করে।", "CONFIRMED", listOf("IEEE Microelectronics Letters", "MIT Research"), "স্বাধীন ল্যাবরেটরি মেজারমেন্টে নিশ্চিত।"),
            ClaimItem("এই বছরই সব সাধারণ স্মার্টফোনে এই চিপ যুক্ত হবে।", "FALSE", listOf(), "প্রযুক্তিটি বর্তমানে ডাটা সেন্টার ও হাই-এন্ড ক্লাউড সার্ভারের জন্য সীমাবদ্ধ; কনজিউমার ফোনের জন্য এখনও বাণিজ্যিকভাবে তৈরি নয়।")
        )
        val techConflicts = listOf(
            ConflictItem(
                aspect = "উৎপাদন খরচ ও বাণিজ্যিকীকরণ সময়কাল",
                reportA = "গবেষক দল দাবি করেছে ২ বছরের মধ্যে বাণিজ্যিক উৎপাদন সম্ভব।",
                sourceA = "MIT Research Papers",
                reportB = "সেমিকন্ডাক্টর ফাউন্ড্রি বিশ্লেষকরা বলছেন পূর্ণাঙ্গ বাণিজ্যিকীকরণ হতে অন্তত ৪ বছর সময় লাগবে।",
                sourceB = "Reuters Technology Desk",
                resolutionStatus = "শিল্প অংশীদারদের চুক্তি স্বাক্ষরের পর উৎপাদন রোডম্যাপ স্পষ্ট হবে।"
            )
        )

        val story2 = StoryEntity(
            headline = "আলোক-তরঙ্গ নির্ভর নতুন নিউরাল চিপ উদ্ভাবন: কম্পিউটিং জগতে যুগান্তকারী বিদ্যুৎ সাশ্রয়",
            subheadline = "ইলেকট্রনের পরিবর্তে লেজার রশ্মির মাধ্যমে ডেটা স্থানান্তর করে প্রোটোটাইপ প্রসেসরের সফল ল্যাবরেটরি পরীক্ষা সম্পন্ন",
            summary = "বিশ্বের শীর্ষস্থানীয় গবেষক দল এমন এক মাইক্রোপ্রসেসর উদ্ভাবন করেছে যা বিদ্যুতের পরিবাহী তারের বদলে আলোর কণার (ফোটন) মাধ্যমে কম্পিউটেশন সম্পন্ন করে। প্রাথমিক স্বাধীন পরীক্ষায় দেখা গেছে এটি বিদ্যমান আধুনিক গ্রাফিক্স প্রসেসরের তুলনায় বহুগুণ দ্রুত অথচ অতিমাত্রায় শীতল অবস্থায় পরিচালিত হয়।",
            whatHappened = "সেমিকন্ডাক্টর গবেষণাগারে সিলিকন চিপের ওপর মাইক্রো-লেজার প্রযুক্তি স্থাপন করে নিউরাল নেটওয়ার্কের জটিল গণনা পরিচালনা করা হয়। প্রচলিত চিপ অতিরিক্ত গরম হওয়ার যে সীমা ছিল, অপটিক্যাল পদ্ধতিতে তা এড়ানো সম্ভব হয়েছে।",
            keyFactsJson = JsonUtils.stringListToJson(listOf(
                "আলোর গতিতে ডেটা স্থানান্তর করায় ল্যাটেন্সি প্রায় শূন্যের কোঠায় নেমে এসেছে।",
                "বিদ্যুৎ সাশ্রয় ৮০% পর্যন্ত নিশ্চিত করা গেছে, যা গ্রিন কম্পিউটিংয়ের জন্য বড় অর্জন।",
                "প্রাথমিক পর্যায়ে এটি বৃহৎ কৃত্রিম বুদ্ধিমত্তা ডাটা সেন্টারসমূহে স্থাপন করা হবে।"
            )),
            whatWeKnowJson = JsonUtils.stringListToJson(listOf(
                "ল্যাবরেটরি প্রোটোটাইপ সম্পূর্ণ কার্যকর এবং একাধিক স্বাধীন গবেষক দল একই ফলাফল পর্যবেক্ষণ করেছে।",
                "আইইইই এবং সংশ্লিষ্ট প্রযুক্তি সাময়িকীতে গবেষণাপত্র প্রকাশিত হয়েছে।"
            )),
            whatWeDontKnowJson = JsonUtils.stringListToJson(listOf(
                "সাধারণ ল্যাপটপ বা গ্রাহক পর্যায়ে পৌঁছাতে কত বছর সময় লাগবে তা এখনো সুনির্দিষ্ট নয়।"
            )),
            background = "সাম্প্রতিক বছরগুলোতে এআই মডেল প্রশিক্ষণে বিদ্যুতের চাহিদা ব্যাপকভাবে বৃদ্ধি পাওয়ায় বিকল্প কম্পিউটিং আর্কিটেকচার নিয়ে বিশ্বজুড়ে জোর গবেষণা চলছিল।",
            timelineJson = JsonUtils.timelineToJson(techTimeline),
            whatSourcesSayJson = JsonUtils.stringListToJson(listOf(
                "IEEE পর্যালোচক: 'অপটিক্যাল আন্তঃসংযোগের এই ফলাফল প্রচলিত সিলিকন আর্কিটেকচারের একটি স্থায়ী বিকল্প রূপরেখা প্রদান করে।'",
                "রয়টার্স প্রতিনিধি: 'শীর্ষ চিপ প্রস্তুতকারক কোম্পানিগুলো ইতিমধ্যে পেটেন্ট পর্যালোচনায় অংশ নিয়েছে।'"
            )),
            whyItMatters = "বিশ্বব্যাপী ডাটা সেন্টারের কার্বন নিঃসরণ হ্রাস করতে এবং আরও শক্তিশালী কম্পিউটেশন পরিবেশবান্ধব উপায়ে বজায় রাখতে এই উদ্ভাবন ভূমিকা রাখবে।",
            sourcesJson = JsonUtils.sourcesToJson(techSources),
            claimsJson = JsonUtils.claimsToJson(techClaims),
            conflictJson = JsonUtils.conflictsToJson(techConflicts),
            category = "প্রযুক্তি",
            categoryKey = "technology",
            locationTag = "বৈশ্বিক",
            status = "CONFIRMED",
            researchLevel = "LEVEL_2_STANDARD",
            language = "bn",
            isBreaking = false,
            isTrending = true,
            isAiPick = true,
            sourceCount = 4,
            confidencePercentage = 95,
            imageUrl = "news_ai_chip_1790729113452",
            imageCredit = "SatyaNews Tech Desk / Laboratory Imaging",
            viewCount = 1290,
            reactionCount = 210,
            commentCount = 12,
            publishedAt = now - 55 * 60 * 1000,
            updatedAt = now - 15 * 60 * 1000
        )

        // 3. Green Energy / Clean Floating Grid (Bengali)
        val energySources = listOf(
            SourceItem("Ministry of New & Renewable Energy", 1, "https://mnre.gov.in/press-release", "জাতীয় সৌর গ্রিড সম্প্রসারণের সরকারি গেজেট", 99),
            SourceItem("Central Electricity Authority Report", 1, "https://cea.nic.in/capacity-report", "গ্রিড সংযোগ ও উৎপাদন সক্ষমতা অডিট", 98),
            SourceItem("The Hindu Economic Bureau", 2, "https://thehindu.com/business/green-energy-expansion", "উৎপাদন ও পরিবেশগত প্রভাব বিশ্লেষণ", 93),
            SourceItem("Local District Environmental Board", 3, "https://wbpcb.gov.in/monitoring", "জলাশয়ের জীববৈচিত্র্য ও পানির গুণমান নিরীক্ষা", 91)
        )
        val energyTimeline = listOf(
            TimelineItem("১০:০০ AM", "উপকূলীয় জলাধারে আধুনিক ভাসমান সৌর প্যানেল সংযোগের অনুমোদন।"),
            TimelineItem("০২:০০ PM", "প্রথম ১০০ মেগাওয়াট বিদ্যুৎ সরাসরি আঞ্চলিক বিদ্যুৎ গ্রিডে সফলভাবে সরবরাহ।"),
            TimelineItem("০৪:৩০ PM", "পরিবেশ সুরক্ষা কর্তৃপক্ষের পানির গুণমান মনিটরিং প্রতিবেদন পেশ।")
        )
        val energyClaims = listOf(
            ClaimItem("ভাসমান সোলার প্যানেল বাষ্পীভবন কমিয়ে পানির অপচয় উল্লেখযোগ্যভাবে হ্রাস করে।", "CONFIRMED", listOf("Ministry of New & Renewable Energy", "Environmental Board"), "পানির গুণমান ও বাষ্পীভবন পরিমাপে প্রমাণিত।"),
            ClaimItem("প্রকল্পের কারণে স্থানীয় মৎস্য চাষ ব্যাহত হবে।", "DISPUTED", listOf("The Hindu", "Environmental Board"), "সরকারি রিপোর্টে ইকোলজিক্যাল ভারসাম্যের কথা বলা হলেও কিছু স্থানীয় প্রতিনিধির আপত্তি রয়েছে।")
        )
        val energyConflicts = listOf(
            ConflictItem(
                aspect = "বিদ্যুৎ সরবরাহের ইউনিট প্রতি চূড়ান্ত ব্যয়",
                reportA = "কর্তৃপক্ষ বলছে প্রতি ইউনিট মাত্র ২.৪০ রুপিতে উৎপাদিত হবে।",
                sourceA = "Central Electricity Authority",
                reportB = "স্বতন্ত্র অর্থনীতিবিদরা বলছেন রক্ষনাবেক্ষণ ও লবণাক্ততা প্রতিরোধী খরচে তা ৩.১০ রুপি দাঁড়াতে পারে।",
                sourceB = "The Hindu Economic Bureau",
                resolutionStatus = "১ বছর পূর্ণ বাণিজ্যিক উৎপাদনের পর সঠিক হিসাব পাওয়া যাবে।"
            )
        )

        val story3 = StoryEntity(
            headline = "বৃহৎ ভাসমান সৌরবিদ্যুৎ গ্রিড সফলভাবে চালু: বাষ্পীভবন হ্রাস ও পরিচ্ছন্ন শক্তির সমন্বয়",
            subheadline = "সরকারি বিদ্যুৎ কর্তৃপক্ষ এবং পরিবেশ অডিট টিমের যৌথ প্রতিবেদনে কার্বন হ্রাসের প্রমাণ নিশ্চিত",
            summary = "উপকূলীয় ও অভ্যন্তরীণ জলাধারে স্থাপিত অত্যাধুনিক ভাসমান সৌরবিদ্যুৎ প্রকল্পের প্রথম ধাপ আজ আনুষ্ঠানিকভাবে জাতীয় গ্রিডের সাথে যুক্ত হয়েছে। কৃত্রিম বুদ্ধিমত্তা চালিত সেন্সরের মাধ্যমে সৌর প্যানেলের কোণ স্বয়ংক্রিয়ভাবে নিয়ন্ত্রিত হচ্ছে যা প্রচলিত সৌর পার্কের তুলনায় প্রায় ১৫ শতাংশ বেশি উৎপাদনশীলতা এনে দিয়েছে।",
            whatHappened = "জমি অধিগ্রহণের জটিলতা এড়িয়ে বিশাল জলাধারের ওপর পরিবেশবান্ধব পন্টুন বসিয়ে এই প্যানেলগুলো স্থাপন করা হয়। পানির স্বাভাবিক শীতলীকরণ প্রভাবের কারণে প্যানেলগুলো অতিরিক্ত উত্তপ্ত হয় না, ফলে শক্তির অপচয় সর্বনিম্ন থাকে।",
            keyFactsJson = JsonUtils.stringListToJson(listOf(
                "জমি অধিগ্রহণ ছাড়াই বিশাল জলাধারে প্রকল্প বাস্তবায়ন।",
                "বাষ্পীভবন হ্রাস পাওয়ায় শুষ্ক মৌসুমে সেচের পানির সঞ্চয় বাড়বে।",
                "অটোমেটেড আইওটি সেন্সরের সাহায্যে দুর্যোগপূর্ণ আবহাওয়ায় প্যানেল স্বয়ংক্রিয়ভাবে সুরক্ষা গ্রহণ করে।"
            )),
            whatWeKnowJson = JsonUtils.stringListToJson(listOf(
                "গ্রিডে বিদ্যুৎ প্রবাহ শুরু হয়েছে এবং বিদ্যুৎ সঞ্চালন পুরোপুরি স্থিতিশীল রয়েছে।"
            )),
            whatWeDontKnowJson = JsonUtils.stringListToJson(listOf(
                "ঘূর্ণিঝড় বা তীব্র জলোচ্ছ্বাসের সময় দীর্ঘস্থায়ী কাঠামোগত স্থিতিশীলতা কেমন থাকবে তা বাস্তব দুর্যোগ ছাড়া পুরোপুরি পরিমাপযোগ্য নয়।"
            )),
            background = "২০৩০ সালের মধ্যে অ-জীবাশ্ম শক্তির লক্ষ্যমাত্রা অর্জনে ভারত ও দক্ষিণ এশিয়ায় নবায়নযোগ্য জ্বালানি ক্ষেত্রে বিশাল বিনিয়োগ চলমান রয়েছে।",
            timelineJson = JsonUtils.timelineToJson(energyTimeline),
            whatSourcesSayJson = JsonUtils.stringListToJson(listOf(
                "বিদ্যুৎ মন্ত্রণালয়: 'এটি কেবল বিদ্যুৎ উৎপাদন নয়, একই সাথে মূল্যবান মিষ্টি পানি সংরক্ষণের একটি বৈজ্ঞানিক সাফল্য।'",
                "পরিবেশ অধিদপ্তর: 'পানির নিচের সূর্যালোক প্রবেশাধিকার ও মাছের বংশবৃদ্ধি নিয়মিত পর্যবেক্ষণ করা হচ্ছে।'"
            )),
            whyItMatters = "ঘনবসতিপূর্ণ অঞ্চলে কৃষি জমি নষ্ট না করে পরিচ্ছন্ন বিদ্যুৎ উৎপাদনের জন্য এই মডেলটি একটি বৈশ্বিক দৃষ্টান্ত হতে পারে।",
            sourcesJson = JsonUtils.sourcesToJson(energySources),
            claimsJson = JsonUtils.claimsToJson(energyClaims),
            conflictJson = JsonUtils.conflictsToJson(energyConflicts),
            category = "সবুজ শক্তি ও জলবায়ু",
            categoryKey = "environment",
            locationTag = "ভারত ও দক্ষিণ এশিয়া",
            status = "CONFIRMED",
            researchLevel = "LEVEL_2_STANDARD",
            language = "bn",
            isBreaking = false,
            isTrending = false,
            isAiPick = true,
            sourceCount = 4,
            confidencePercentage = 94,
            imageUrl = "news_clean_energy_1790729127235",
            imageCredit = "SatyaNews Eco Desk / Green Grid Surveillance",
            viewCount = 920,
            reactionCount = 145,
            commentCount = 6,
            publishedAt = now - 90 * 60 * 1000,
            updatedAt = now - 25 * 60 * 1000
        )

        // 4. Bio-Science / DNA Targeted Therapy (English)
        val bioSources = listOf(
            SourceItem("WHO Clinical Trials Registry", 1, "https://who.int/trials/gene-therapy-2026", "Phase III International Multi-Center Trial Data", 99),
            SourceItem("The Lancet Medical Journal", 1, "https://thelancet.com/journals/oncology/article", "Double-Blind Peer-Reviewed Clinical Efficacy", 98),
            SourceItem("Reuters Health Desk", 2, "https://reuters.com/health/molecular-targeted-study", "Interviews with Global Clinical Oncology Leads", 95),
            SourceItem("BBC Global Health", 2, "https://bbc.com/news/health-precision-medicine", "Patient Safety Monitoring and Accessibility Review", 93)
        )
        val bioTimeline = listOf(
            TimelineItem("08:00 AM", "Global clinical oncology network released 5-year longitudinal follow-up metrics."),
            TimelineItem("11:30 AM", "WHO panel on therapeutic guidelines initiated review of molecular delivery mechanism."),
            TimelineItem("03:00 PM", "Editorial board of Lancet released complete safety profile and patient response distribution.")
        )
        val bioClaims = listOf(
            ClaimItem("Targeted molecular vector successfully neutralizes resistant cellular mutations with minimal side effects.", "CONFIRMED", listOf("WHO Clinical Trials Registry", "The Lancet"), "Supported by 1,200 patient multi-center trial documentation."),
            ClaimItem("Treatment eliminates all future healthcare recurrences permanently.", "DISPUTED", listOf("The Lancet"), "Long-term monitoring shows high remission, but claiming permanent zero-recurrence is medically unsupported.")
        )
        val bioConflicts = listOf(
            ConflictItem(
                aspect = "Estimated Time to Broad Hospital Adoption",
                reportA = "Trial investigators expect standard regulatory clearance within 12 months.",
                sourceA = "The Lancet Editorial",
                reportB = "Regulatory specialists project a 24-month horizon due to cold-chain storage logistics.",
                sourceB = "Reuters Health Desk",
                resolutionStatus = "Awaiting formal regional drug authority timeline submissions."
            )
        )

        val story4 = StoryEntity(
            headline = "Precision Molecular Therapy Shows High Remission in Resistant Cellular Diseases: Lancet Multi-Center Trial",
            subheadline = "Five-year longitudinal data published following rigorous double-blind clinical trials across 14 countries",
            summary = "An international team of medical scientists has unveiled clinical trial results demonstrating that programmable molecular carriers can accurately target malignant cell structures while sparing healthy tissue. The findings have been cross-verified by independent global health reviewers.",
            whatHappened = "Unlike conventional systemic treatments that affect whole organ groups, the bio-engineered vector responds specifically to chemical markers unique to diseased cells, delivering therapeutic agents with microscopic precision.",
            keyFactsJson = JsonUtils.stringListToJson(listOf(
                "Over 84% sustained remission rate observed in the primary cohort.",
                "Zero grade-4 adverse events documented during the multi-year study.",
                "Double-blind peer-review verified through the World Health Organization trials registry."
            )),
            whatWeKnowJson = JsonUtils.stringListToJson(listOf(
                "Efficacy metrics and molecular stability have been independently reproduced in university medical centers."
            )),
            whatWeDontKnowJson = JsonUtils.stringListToJson(listOf(
                "Cost accessibility and scalable mass cold-storage manufacturing for low-income regions remain unfinalized."
            )),
            background = "Over the past decade, oncology researchers focused heavily on cellular immunotherapy. This new hybrid molecular vector addresses previous delivery limitations.",
            timelineJson = JsonUtils.timelineToJson(bioTimeline),
            whatSourcesSayJson = JsonUtils.stringListToJson(listOf(
                "Lead Clinician: 'This marks a shift from broad chemotherapy toward surgical molecular precision.'",
                "WHO Observer: 'We are closely evaluating manufacturing standards to prevent global access disparity.'"
            )),
            whyItMatters = "Provides strong therapeutic hope for patients who have developed resistance to primary treatments.",
            sourcesJson = JsonUtils.sourcesToJson(bioSources),
            claimsJson = JsonUtils.claimsToJson(bioClaims),
            conflictJson = JsonUtils.conflictsToJson(bioConflicts),
            category = "Health & Science",
            categoryKey = "science",
            locationTag = "Global",
            status = "CONFIRMED",
            researchLevel = "LEVEL_3_DEEP",
            language = "en",
            isBreaking = false,
            isTrending = true,
            isAiPick = true,
            sourceCount = 4,
            confidencePercentage = 98,
            imageUrl = "news_bio_science_1790729140841",
            imageCredit = "SatyaNews Medical Desk / Clinical Genomic Imaging",
            viewCount = 1530,
            reactionCount = 280,
            commentCount = 14,
            publishedAt = now - 110 * 60 * 1000,
            updatedAt = now - 30 * 60 * 1000,
            aiDisclosure = "This report was synthesized from verified multi-source research and clinical trial registries by SatyaNews AI."
        )

        // 5. Regional Infrastructure / Smart Flood Resilience in Bengal & South Asia (Bengali)
        val floodSources = listOf(
            SourceItem("সেচ ও নদী গবেষণা পরিদপ্তর", 1, "https://wbiwd.gov.in/telemetry-bulletin", "নদীর পানির স্তর ও স্বয়ংক্রিয় টেলিমেট্রি তথ্য", 98),
            SourceItem("সেন্ট্রাল ওয়াটার কমিশন (CWC)", 1, "https://cwc.gov.in/flood-forecast", "আঞ্চলিক বৃষ্টিপাত ও বাঁধ ডিসচার্জ নিরীক্ষণ", 97),
            SourceItem("Anandabazar Patrika Local Desk", 2, "https://anandabazar.com/state/river-barrage-upgrade", "উপকূলীয় জেলা ও তীরবর্তী বাসিন্দাদের সাক্ষাৎকার", 92),
            SourceItem("দুর্যোগ ব্যবস্থাপনা কর্তৃপক্ষ", 1, "https://wbsdma.gov.in/bulletin", "জরুরি ত্রাণ ও বাঁধ নজরদারি প্রটোকল", 96)
        )
        val floodTimeline = listOf(
            TimelineItem("০৬:০০ AM", "নদী অববাহিকায় স্বয়ংক্রিয় রাডার ও ড্রোন নজরদারি শুরু।"),
            TimelineItem("১০:৪৫ AM", "নিয়ন্ত্রিত স্লুইস গেটের মাধ্যমে অতিরিক্ত পানির নিরাপদ নিষ্কাশন।"),
            TimelineItem("০২:০০ PM", "উপকূলীয় সংবেদনশীল বাঁধে রিয়েল-টাইম প্রেসার সেন্সর ডাটা ইতিবাচক ঘোষণা।")
        )
        val floodClaims = listOf(
            ClaimItem("নদীর বাঁধ উপচে লোকালয়ে পানি প্রবেশের গুজব ভিত্তিহীন।", "CONFIRMED", listOf("দুর্যোগ ব্যবস্থাপনা কর্তৃপক্ষ", "Anandabazar Patrika"), "সরাসরি মাঠ পর্যায়ের পরিদর্শনে দেখা গেছে পানি বিপদসীমার ০.৮ মিটার নিচে রয়েছে।"),
            ClaimItem("নদীর বাঁধে ডিজিটাল সেন্সর বসিয়ে আগাম ৩ দিন আগে ভাঙনের সতর্কতা পাওয়া সম্ভব হচ্ছে।", "CONFIRMED", listOf("সেচ ও নদী গবেষণা পরিদপ্তর", "CWC"), "টেলিমেট্রি স্টেশনের কৃত্রিম বুদ্ধিমত্তা মডেল সফলভাবে পরিচালিত।")
        )
        val floodConflicts = listOf(
            ConflictItem(
                aspect = "জল ছাড়ার পূর্বাভাস পরিমাণ",
                reportA = "স্থানীয় কিছু অনির্ভরশীল বার্তায় ৫০,০০০ কিউসেক পানি ছাড়ার ভীতি ছড়ানো হয়।",
                sourceA = "সামাজিক মাধ্যম রিউমার",
                reportB = "সরকারি সেচ দপ্তর নিশ্চিত করেছে মাত্র ১৫,২০০ কিউসেক স্বাভাবিক ড্রেনেজ ওয়াটার ছাড়ার সিদ্ধান্ত হয়েছে।",
                sourceB = "সেচ ও নদী গবেষণা পরিদপ্তর",
                resolutionStatus = "সরকারি অফিসিয়াল ডেটা দ্বারা বিভ্রান্তি সম্পূর্ণ দূর হয়েছে।"
            )
        )

        val story5 = StoryEntity(
            headline = "আঞ্চলিক নদী অববাহিকায় স্মার্ট সেন্সর ও ড্রোন নজরদারি: ভাঙন রোধে কৃত্রিম বুদ্ধিমত্তার প্রয়োগ",
            subheadline = "সেচ দপ্তর ও সেন্ট্রাল ওয়াটার কমিশনের টেলিমেট্রি উপাত্ত পর্যালোচনায় স্বাভাবিক পানি নিষ্কাশন নিশ্চিত",
            summary = "বর্ষা মৌসুমের আগে উপকূলীয় ও প্রধান নদী বাঁধসমূহের সার্বক্ষণিক সুরক্ষায় রিয়েল-টাইম সেন্সর নেটওয়ার্ক স্থাপন করা হয়েছে। সামাজিক মাধ্যমে বাঁধ উপচে প্লাবনের যে ভীতি ছড়ানো হয়েছিল, সেচ পরিদপ্তরের সরাসরি ফিল্ড অডিটে তা সম্পূর্ণ অসত্য প্রমাণিত হয়েছে। বর্তমানে পানি স্বাভাবিক নিষ্কাশন সীমার নিরাপদ স্তরে রয়েছে।",
            whatHappened = "প্রতিটি সংবেদনশীল বাঁধে মাটির আর্দ্রতা এবং পানির চাপ পরিমাপক ডিজিটাল সেন্সর বসানো হয়েছে, যা স্বয়ংক্রিয়ভাবে স্যাটেলাইটের মাধ্যমে কন্ট্রোল রুমে সতর্কবার্তা পাঠায়। এর ফলে আকস্মিক ভাঙনের ঝুঁকি এড়ানো সম্ভব হচ্ছে।",
            keyFactsJson = JsonUtils.stringListToJson(listOf(
                "পানি বিপদসীমার ০.৮ মিটার নিচ দিয়ে প্রবাহিত হচ্ছে।",
                "অফিসিয়াল টেলিমেট্রি স্টেশন সার্বক্ষণিক উপাত্ত বিশ্লেষণ করছে।",
                "ভুল তথ্যের প্রচার রোধে দুর্যোগ ব্যবস্থাপনা টিম নিয়মিত বুলেটিন প্রকাশ করছে।"
            )),
            whatWeKnowJson = JsonUtils.stringListToJson(listOf(
                "বাঁধের কোনো অংশ ক্ষতিগ্রস্ত হয়নি এবং স্বাভাবিক জল নিষ্কাশন অব্যাহত রয়েছে।"
            )),
            whatWeDontKnowJson = JsonUtils.stringListToJson(listOf(
                "আগামী সপ্তাহের সম্ভাব্য বঙ্গোপসাগরীয় নিম্নচাপের তীব্রতা ও সুনির্দিষ্ট বৃষ্টিপাতের পরিমাণ।"
            )),
            background = "পূর্বে প্রতিবছর পাহাড়ি ঢল ও অতিবৃষ্টিতে সময়মত তথ্য না পাওয়ায় নদীভাঙনে বহু মানুষ ক্ষতিগ্রস্ত হতো। এই প্রেক্ষাপটে আধুনিক সেন্সর গ্রিড চালু করা হয়।",
            timelineJson = JsonUtils.timelineToJson(floodTimeline),
            whatSourcesSayJson = JsonUtils.stringListToJson(listOf(
                "প্রধান সেচ প্রকৌশলী: 'আমাদের স্বয়ংক্রিয় সেন্সরগুলো ২৪ ঘণ্টা বাঁধে মাটির চাপ পরিমাপ করছে। কোনো ভয়ের কারণ নেই।'",
                "দুর্যোগ ব্যবস্থাপনা কর্তৃপক্ষ: 'সামাজিক যোগাযোগ মাধ্যমের যাচাইহীন খবরে কান না দিয়ে অফিসিয়াল কন্ট্রোল রুমের সাথে যোগাযোগ করার অনুরোধ জানানো হচ্ছে।'"
            )),
            whyItMatters = "স্থানীয় জনগণের জানমাল রক্ষা এবং সময়মত প্রস্তুতি গ্রহণের মাধ্যমে হাজার হাজার মানুষের ক্ষয়ক্ষতি শূন্যে নামিয়ে আনা সম্ভব।",
            sourcesJson = JsonUtils.sourcesToJson(floodSources),
            claimsJson = JsonUtils.claimsToJson(floodClaims),
            conflictJson = JsonUtils.conflictsToJson(floodConflicts),
            category = "স্থানীয় ও পরিবেশ",
            categoryKey = "local",
            locationTag = "পশ্চিমবঙ্গ ও দক্ষিণ এশিয়া",
            status = "CONFIRMED",
            researchLevel = "LEVEL_2_STANDARD",
            language = "bn",
            isBreaking = false,
            isTrending = false,
            isAiPick = false,
            sourceCount = 4,
            confidencePercentage = 96,
            imageUrl = "news_clean_energy_1790729127235",
            imageCredit = "SatyaNews Regional Flood Telemetry Network",
            viewCount = 760,
            reactionCount = 110,
            commentCount = 5,
            publishedAt = now - 180 * 60 * 1000,
            updatedAt = now - 45 * 60 * 1000
        )

        return listOf(story1, story2, story3, story4, story5)
    }

    fun getInitialComments(storyId: Long): List<CommentEntity> = listOf(
        CommentEntity(
            storyId = storyId,
            userName = "অধ্যাপক শুভ্র সেনগুপ্ত",
            userBadge = "বিজ্ঞান গবেষক",
            content = "প্রতিবেদনটিতে তথ্যের উৎস ও গবেষণাপত্রের সরাসরি রেফারেন্স উল্লেখ থাকায় বিষয়টি অত্যন্ত নির্ভরযোগ্য মনে হচ্ছে। বিশেষ করে অসমর্থিত দাবিগুলোকে আলাদা করার বিষয়টি প্রশংসনীয়।",
            timestamp = System.currentTimeMillis() - 25 * 60 * 1000,
            likes = 14,
            moderationStatus = "CLEAN"
        ),
        CommentEntity(
            storyId = storyId,
            userName = "অনন্যা বন্দ্যোপাধ্যায়",
            userBadge = "যাচাইকৃত পাঠক",
            content = "ঘটনার পেছনের প্রেক্ষাপট এবং কী জানা গেছে আর কী এখনো অজানা—এই বিভাজনটি বিভ্রান্তি দূর করতে সবচেয়ে বেশি সাহায্য করে।",
            timestamp = System.currentTimeMillis() - 15 * 60 * 1000,
            likes = 8,
            moderationStatus = "CLEAN"
        )
    )
}
