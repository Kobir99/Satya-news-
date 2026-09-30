package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.ResearchLevel
import com.example.ui.theme.CobaltPrimary
import com.example.ui.theme.VerifiedEmerald

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AskStoryAssistantDialog(
    storyHeadline: String,
    currentLanguage: AppLanguage,
    assistantAnswer: String?,
    isLoading: Boolean,
    onAskQuestion: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var customQuestion by remember { mutableStateOf("") }

    val quickQuestions = when (currentLanguage) {
        AppLanguage.BN -> listOf(
            "কী হয়েছিল সংক্ষেপে বলুন?",
            "কেন এই ঘটনাটি অত্যন্ত গুরুত্বপূর্ণ?",
            "উভয় পক্ষ বা বিভিন্ন সূত্র কী বলছে?",
            "কোন তথ্যটি প্রাতিষ্ঠানিকভাবে নিশ্চিত?",
            "এখনও কোন তথ্য অজানা বা অস্পষ্ট?",
            "ব্যবহৃত উৎসসমূহ কী কী?"
        )
        AppLanguage.HI -> listOf(
            "संक्षेप में क्या हुआ?",
            "यह घटना क्यों महत्वपूर्ण है?",
            "दोनों पक्ष या विभिन्न स्रोत क्या कह रहे हैं?",
            "कौन सी जानकारी आधिकारिक रूप से पुष्ट है?",
            "अभी क्या अज्ञात या अपुष्ट है?",
            "उपयोग किए गए स्रोत क्या हैं?"
        )
        AppLanguage.EN -> listOf(
            "What happened exactly?",
            "Why is this development important?",
            "What are different sources saying?",
            "What has been officially confirmed?",
            "What is still unknown or unverified?",
            "What original sources were utilized?"
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.testTag("dialog_ask_story_assistant"),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.QuestionAnswer,
                        contentDescription = "Ask Assistant",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.BN) "সংবাদ অনুসন্ধান এআই"
                        else if (currentLanguage == AppLanguage.HI) "समाचार शोध सहायक"
                        else "Story Research Assistant",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = storyHeadline,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (currentLanguage == AppLanguage.BN) "দ্রুত অনুসন্ধানী প্রশ্নসমূহ:"
                    else if (currentLanguage == AppLanguage.HI) "त्वरित प्रश्न:"
                    else "Quick Grounded Inquiries:",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(6.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    quickQuestions.forEach { q ->
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.clickable {
                                customQuestion = q
                                onAskQuestion(q)
                            }
                        ) {
                            Text(
                                text = q,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Custom Question Input
                OutlinedTextField(
                    value = customQuestion,
                    onValueChange = { customQuestion = it },
                    label = {
                        Text(
                            text = if (currentLanguage == AppLanguage.BN) "আপনার নিজস্ব প্রশ্ন লিখুন..."
                            else if (currentLanguage == AppLanguage.HI) "अपना प्रश्न लिखें..."
                            else "Type your specific question..."
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_ask_question"),
                    trailingIcon = {
                        IconButton(
                            onClick = { onAskQuestion(customQuestion) },
                            enabled = customQuestion.isNotBlank() && !isLoading
                        ) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = "Submit")
                        }
                    },
                    singleLine = false,
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Answer Display
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(modifier = Modifier.size(28.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (currentLanguage == AppLanguage.BN) "সংগৃহীত তথ্য ও প্রমাণাদি বিশ্লেষণ করা হচ্ছে..."
                                else if (currentLanguage == AppLanguage.HI) "साक्ष्यों का विश्लेषण जारी है..."
                                else "Analyzing verified evidence...",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else if (!assistantAnswer.isNullOrBlank()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = "AI Answer",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (currentLanguage == AppLanguage.BN) "উৎস-ভিত্তিক উত্তর:"
                                    else if (currentLanguage == AppLanguage.HI) "प्रमाण-आधारित उत्तर:"
                                    else "Evidence-Grounded Answer:",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = assistantAnswer,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = if (currentLanguage == AppLanguage.BN) "বন্ধ করুন" else "Close")
            }
        }
    )
}

@Composable
fun AutonomousResearchDialog(
    currentLanguage: AppLanguage,
    isResearching: Boolean,
    progressStep: String?,
    onStartResearch: (String, ResearchLevel) -> Unit,
    onDismiss: () -> Unit
) {
    var topicInput by remember { mutableStateOf("") }
    var selectedLevel by remember { mutableStateOf(ResearchLevel.LEVEL_2_STANDARD) }

    AlertDialog(
        onDismissRequest = { if (!isResearching) onDismiss() },
        modifier = Modifier.testTag("dialog_autonomous_research"),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "Autonomous Research",
                    tint = CobaltPrimary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (currentLanguage == AppLanguage.BN) "স্বতন্ত্র এআই গভীর অনুসন্ধান"
                    else if (currentLanguage == AppLanguage.HI) "स्वायत्त एआई गहन अनुसंधान"
                    else "Autonomous AI Deep Research",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = if (currentLanguage == AppLanguage.BN)
                        "একটি বিষয় বা গুঞ্জন লিখুন। আমাদের ডিজিটাল নিউজরুম এজেন্ট ৩-৬টি স্বাধীন উৎস সংগ্রহ করে, দাবিগুলো যাচাই করে নিরপেক্ষ মূল প্রতিবেদন রচনা করবে।"
                    else if (currentLanguage == AppLanguage.HI)
                        "कोई विषय या अफ़वाह दर्ज करें। हमारा AI न्यूज़रूम स्वतंत्र स्रोतों से साक्ष्य जुटाकर पुष्ट रिपोर्ट तैयार करेगा।"
                    else
                        "Enter a topic or developing rumor. Our digital newsroom agents will independently discover sources, cross-verify claims, detect conflicts, and synthesize a factual story.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = topicInput,
                    onValueChange = { topicInput = it },
                    label = {
                        Text(
                            text = if (currentLanguage == AppLanguage.BN) "অনুসন্ধানের বিষয় (যেমন: কৃত্রিম অঙ্গ প্রতিস্থাপন)"
                            else if (currentLanguage == AppLanguage.HI) "अनुसंधान विषय..."
                            else "Research Topic or Lead..."
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_research_topic"),
                    enabled = !isResearching
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = if (currentLanguage == AppLanguage.BN) "গবেষণার গভীরতা নির্বাচন:"
                    else if (currentLanguage == AppLanguage.HI) "अनुसंधान स्तर चुनें:"
                    else "Select Research Depth Level:",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                ResearchLevel.values().forEach { level ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = !isResearching) { selectedLevel = level }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedLevel == level,
                            onClick = { selectedLevel = level },
                            enabled = !isResearching
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text(
                                text = when (currentLanguage) {
                                    AppLanguage.BN -> level.labelBn
                                    AppLanguage.HI -> level.labelHi
                                    AppLanguage.EN -> level.labelEn
                                },
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = if (selectedLevel == level) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                if (isResearching) {
                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = progressStep ?: "গবেষণা পাইপলাইন সক্রিয়...",
                        style = MaterialTheme.typography.labelSmall,
                        color = CobaltPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onStartResearch(topicInput, selectedLevel) },
                enabled = topicInput.isNotBlank() && !isResearching,
                modifier = Modifier.testTag("btn_confirm_research")
            ) {
                Text(
                    text = if (currentLanguage == AppLanguage.BN) "গবেষণা শুরু করুন"
                    else if (currentLanguage == AppLanguage.HI) "अनुसंधान शुरू करें"
                    else "Start Autonomous Research"
                )
            }
        },
        dismissButton = {
            if (!isResearching) {
                TextButton(onClick = onDismiss) {
                    Text(text = if (currentLanguage == AppLanguage.BN) "বাতিল" else "Cancel")
                }
            }
        }
    )
}
