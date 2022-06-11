package com.getstarted.lesson2

import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class OpenJson : AppCompatActivity() {
    fun openJson() {

        val jsonTxt = assets.readAssetsFile("quiz.json") // works!
        val json = JSONObject(jsonTxt) // works!
        val questions = json.getJSONObject("questions") // works!
        val question = questions.getJSONObject("4")
        val content = question.getJSONObject("content")
        // val type = question.getJSONObject("type")
        // val answers = question.getJSONArray("answers")
        println(content)

    }
    private fun AssetManager.readAssetsFile(fileName : String): String =
        open(fileName).bufferedReader().use{it.readText() }
}