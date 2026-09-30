package com.example.data.util

import com.example.data.model.ClaimItem
import com.example.data.model.ConflictItem
import com.example.data.model.SourceItem
import com.example.data.model.TimelineItem
import org.json.JSONArray
import org.json.JSONObject

object JsonUtils {

    fun stringListToJson(list: List<String>): String {
        val array = JSONArray()
        list.forEach { array.put(it) }
        return array.toString()
    }

    fun jsonToStringList(json: String?): List<String> {
        if (json.isNullOrEmpty()) return emptyList()
        return try {
            val array = JSONArray(json)
            val result = mutableListOf<String>()
            for (i in 0 until array.length()) {
                result.add(array.getString(i))
            }
            result
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun sourcesToJson(sources: List<SourceItem>): String {
        val array = JSONArray()
        sources.forEach { s ->
            val obj = JSONObject()
            obj.put("name", s.name)
            obj.put("tier", s.tier)
            obj.put("url", s.url)
            obj.put("quoteOrSummary", s.quoteOrSummary)
            obj.put("credibilityScore", s.credibilityScore)
            array.put(obj)
        }
        return array.toString()
    }

    fun jsonToSources(json: String?): List<SourceItem> {
        if (json.isNullOrEmpty()) return emptyList()
        return try {
            val array = JSONArray(json)
            val result = mutableListOf<SourceItem>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                result.add(
                    SourceItem(
                        name = obj.optString("name", "Unknown Source"),
                        tier = obj.optInt("tier", 2),
                        url = obj.optString("url", "https://news.google.com"),
                        quoteOrSummary = obj.optString("quoteOrSummary", ""),
                        credibilityScore = obj.optInt("credibilityScore", 85)
                    )
                )
            }
            result
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun timelineToJson(timeline: List<TimelineItem>): String {
        val array = JSONArray()
        timeline.forEach { t ->
            val obj = JSONObject()
            obj.put("time", t.time)
            obj.put("event", t.event)
            array.put(obj)
        }
        return array.toString()
    }

    fun jsonToTimeline(json: String?): List<TimelineItem> {
        if (json.isNullOrEmpty()) return emptyList()
        return try {
            val array = JSONArray(json)
            val result = mutableListOf<TimelineItem>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                result.add(
                    TimelineItem(
                        time = obj.optString("time", ""),
                        event = obj.optString("event", "")
                    )
                )
            }
            result
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun claimsToJson(claims: List<ClaimItem>): String {
        val array = JSONArray()
        claims.forEach { c ->
            val obj = JSONObject()
            obj.put("claimText", c.claimText)
            obj.put("status", c.status)
            obj.put("notes", c.notes)
            val sourcesArr = JSONArray()
            c.supportingSources.forEach { sourcesArr.put(it) }
            obj.put("supportingSources", sourcesArr)
            array.put(obj)
        }
        return array.toString()
    }

    fun jsonToClaims(json: String?): List<ClaimItem> {
        if (json.isNullOrEmpty()) return emptyList()
        return try {
            val array = JSONArray(json)
            val result = mutableListOf<ClaimItem>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                val sourcesArr = obj.optJSONArray("supportingSources")
                val sources = mutableListOf<String>()
                if (sourcesArr != null) {
                    for (j in 0 until sourcesArr.length()) {
                        sources.add(sourcesArr.getString(j))
                    }
                }
                result.add(
                    ClaimItem(
                        claimText = obj.optString("claimText", ""),
                        status = obj.optString("status", "CONFIRMED"),
                        supportingSources = sources,
                        notes = obj.optString("notes", "")
                    )
                )
            }
            result
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun conflictsToJson(conflicts: List<ConflictItem>): String {
        val array = JSONArray()
        conflicts.forEach { c ->
            val obj = JSONObject()
            obj.put("aspect", c.aspect)
            obj.put("reportA", c.reportA)
            obj.put("sourceA", c.sourceA)
            obj.put("reportB", c.reportB)
            obj.put("sourceB", c.sourceB)
            obj.put("resolutionStatus", c.resolutionStatus)
            array.put(obj)
        }
        return array.toString()
    }

    fun jsonToConflicts(json: String?): List<ConflictItem> {
        if (json.isNullOrEmpty()) return emptyList()
        return try {
            val array = JSONArray(json)
            val result = mutableListOf<ConflictItem>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                result.add(
                    ConflictItem(
                        aspect = obj.optString("aspect", ""),
                        reportA = obj.optString("reportA", ""),
                        sourceA = obj.optString("sourceA", ""),
                        reportB = obj.optString("reportB", ""),
                        sourceB = obj.optString("sourceB", ""),
                        resolutionStatus = obj.optString("resolutionStatus", "")
                    )
                )
            }
            result
        } catch (e: Exception) {
            emptyList()
        }
    }
}
