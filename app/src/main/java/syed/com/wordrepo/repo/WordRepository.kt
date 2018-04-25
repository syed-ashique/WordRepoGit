package syed.com.wordrepo.repo

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import syed.com.wordrepo.dao.WordDao
import syed.com.wordrepo.entitiy.Word
import syed.com.wordrepo.utility.Log

/**
 * Created by syed on 2/13/18.
 */
class WordRepository(application: Application) {

    private val mWordDao: WordDao
    val mWords: LiveData<List<Word>>

    val wordTableSize: Int
        get() = mWordDao.getWordTableSize()

    enum class DatabaseOperationEvent {
        INSERT,
        UPDATE,
        DELETE
    }

    init {
        mWordDao = AppDatabase.getDatabase(application).wordDao()
        mWords = mWordDao.getAllWords()
    }

    fun performOperationWithWord(word: Word, event: DatabaseOperationEvent) {
        Log.info(javaClass, "database async task operation: " + event)
        DatabaseOperationsAsyncTask(mWordDao, event).execute(word)
    }

    private class DatabaseOperationsAsyncTask(private val asyncTaskDao: WordDao, private val asyncTaskEvent: DatabaseOperationEvent)
        : AsyncTask<Word, Void, Void>() {

        override fun doInBackground(vararg words: Word): Void? {
            when (asyncTaskEvent) {
                DatabaseOperationEvent.INSERT -> asyncTaskDao.insertWord(words[0])
                DatabaseOperationEvent.UPDATE -> asyncTaskDao.updateWord(words[0])
                DatabaseOperationEvent.DELETE -> asyncTaskDao.deleteWord(words[0])
                else -> Log.info(javaClass, "No suitable database operation are there.")
            }
            return null
        }
    }

    fun getWordById(id: Int): Word? {
        return mWordDao.getWord(id)
    }
}

