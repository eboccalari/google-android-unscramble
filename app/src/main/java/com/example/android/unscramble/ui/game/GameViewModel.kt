package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

/**
 * Warning: Never expose mutable data fields from your ViewModel—make sure this data can't be modified from another class.
 * Mutable data inside the ViewModel should always be private.
 */
class GameViewModel: ViewModel() {

    /*
        Backing properties:
        Declare private mutable variable that can only be modified within the class it is declared.
     */
    private var _score = 0

    /*
        Backing properties:
        Declare another public immutable field and override its getter method.
        Return the private property's value in the getter method.
        When score is accessed, the get() function is called and the value of _score is returned.
     */
    val score: Int
        get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int
        get() =  _currentWordCount

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord

    private lateinit var currentWord: String
    private var wordList = mutableListOf<String>()

    init {
        Log.d("GameFragment", "GameViewModel Created!")
        getNextWord()
    }

    private fun getNextWord(){
        do{
            currentWord = allWordsList.random()
            val tempWord = currentWord.toCharArray()
            tempWord.shuffle()
            _currentScrambledWord = String(tempWord)
        } while (currentWord == _currentScrambledWord || wordList.contains(currentWord))

        wordList.add(currentWord)
        _currentWordCount ++
    }

    /*
    * Returns true if the current word count is less than MAX_NO_OF_WORDS.
    * Updates the next word.
    */
    fun nextWord(): Boolean{
        return if(_currentWordCount < MAX_NO_OF_WORDS){
            getNextWord()
            true
        }else false
    }

    private fun increaseScore(){
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(playerWord: String): Boolean{
       return if(playerWord.equals(currentWord, true)){
            increaseScore()
            true
        }else false
    }

    fun reinitializeData(){
        _score = 0
        _currentWordCount = 0
        wordList.clear()
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }
}