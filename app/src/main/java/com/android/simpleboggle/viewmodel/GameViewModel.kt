package com.android.simpleboggle.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.simpleboggle.entity.GameWord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.ceil


class GameViewModel(private val application: Application) : AndroidViewModel(application) {

    private val allWord = mutableListOf<String>()

    companion object {
        private const val MAX_COLUMN = 4

        private val ALL_WORD = 'A'..'Z'
        val VOWELS = mutableListOf("A", "E", "I", "O", "U")
        val SPECIAL = mutableListOf("S", "Z", "P", "X", "Q")
        val OTHER = mutableListOf("B", "C", "D", "F", "G", "H", "J", "K", "L", "M", "N", "R", "T", "V", "W", "Y")
    }

    fun loadLocalDict() {
        viewModelScope.launch(Dispatchers.IO) {
            val assetManager = application.assets
            assetManager.open("words.txt").use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).use { reader ->
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        allWord.add(line!!)
                    }
                }
            }
        }
    }

    fun loadGameData(action: (List<GameWord>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = mutableListOf<GameWord>()
            for (index in 1..MAX_COLUMN * MAX_COLUMN) {
                val word = ALL_WORD.random().toString()
                val gameWord = GameWord(word)
                gameWord.row = getRow(index)
                gameWord.column = getColumn(index)
                list.add(gameWord)
            }
            withContext(Dispatchers.Main) {
                action.invoke(list)
            }
        }
    }

    private fun getRow(index: Int): Int {
        return ceil((index * 1.0f / MAX_COLUMN).toDouble()).toInt()
    }

    private fun getColumn(index: Int): Int {
        val result = index % MAX_COLUMN
        return if (result == 0) {
            MAX_COLUMN
        } else result
    }

    fun submitData(list: List<GameWord>, action: (Int) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = list.map { it.word }
            val lastWord = java.lang.String.join("", result)



            if (allWord.contains(lastWord)) {
                var vowelsCount = 0
                var specialCount = 0
                var otherCount = 0
                for (s in result) {
                    when {
                        VOWELS.contains(s) -> vowelsCount++
                        SPECIAL.contains(s) -> specialCount++
                        else -> otherCount++
                    }
                }
                val total = vowelsCount * 5 + otherCount + specialCount * 2
                action.invoke(total)
            } else {
                withContext(Dispatchers.Main) {
                    action.invoke(-10)
                }
            }
        }
    }
}