package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("SatyaNews", appName)
  }

  @Test
  fun `verify initial seed stories exist and have valid structure`() {
    val initialStories = com.example.data.repository.SeedData.getInitialStories()
    assertTrue(initialStories.isNotEmpty())
    val firstStory = initialStories.first()
    assertTrue(firstStory.headline.isNotBlank())
    assertTrue(firstStory.sourceCount > 0)
    assertTrue(firstStory.confidencePercentage in 50..100)
  }

  @Test
  fun `verify theme mode labels and values`() {
    val dark = com.example.ui.theme.ThemeMode.DARK
    val light = com.example.ui.theme.ThemeMode.LIGHT
    val system = com.example.ui.theme.ThemeMode.SYSTEM

    assertEquals("ডার্ক মোড", dark.labelBn)
    assertEquals("Dark Mode", dark.labelEn)
    assertEquals("লাইট মোড", light.labelBn)
    assertEquals("Light Mode", light.labelEn)
    assertEquals("সিস্টেম ডিফল্ট", system.labelBn)
    assertEquals("System Default", system.labelEn)
  }
}
