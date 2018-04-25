package syed.com.wordrepo.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import syed.com.wordrepo.entitiy.Word
import syed.com.wordrepo.repo.WordRepository
import syed.com.wordrepo.utility.Log

/**
 * Created by syed on 2/13/18.
 */

class WordViewModel(application: Application) : AndroidViewModel(application) {
    private val mWordRepository: WordRepository
    val mWords: LiveData<List<Word>>

    init {
        mWordRepository = WordRepository(application)
        mWords = mWordRepository.mWords
    }

    fun insert(word: Word) {
        Log.info(javaClass, "Saving data using view model: " + word.word)
        mWordRepository.performOperationWithWord(word, WordRepository.DatabaseOperationEvent.INSERT)
    }

    fun update(word: Word) {
        Log.info(javaClass, "Updating data using view model: " + word.word)
        mWordRepository.performOperationWithWord(word, WordRepository.DatabaseOperationEvent.UPDATE)
    }

    fun delete(word: Word) {
        Log.info(javaClass, "Deleting data using view model: " + word.word)
        mWordRepository.performOperationWithWord(word, WordRepository.DatabaseOperationEvent.DELETE)
    }
}

