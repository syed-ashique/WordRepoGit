package syed.com.wordrepo.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import syed.com.wordrepo.dao.WordDao;
import syed.com.wordrepo.entitiy.Word;
import syed.com.wordrepo.utility.Log;

/**
 * Created by syed on 1/11/18.
 */

public class WordRepository {
    public enum DatabaseOperationEvent{
        INSERT,
        UPDATE,
        DELETE
    }

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    public WordRepository(Application application) {
        mWordDao = AppDatabase.getDatabase(application).wordDao();

        mAllWords = mWordDao.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void performOperationWithWord(Word word, DatabaseOperationEvent event){
        Log.info(getClass(), "database async task operation: "+ event);
        new DatabaseOperationsAsyncTask(mWordDao, event).execute(word);
    }

    private static class DatabaseOperationsAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao asyncTaskDao;
        private DatabaseOperationEvent asyncTaskEvent;

        public DatabaseOperationsAsyncTask(WordDao dao, DatabaseOperationEvent event) {
            asyncTaskDao = dao;
            asyncTaskEvent = event;
        }

        @Override
        protected Void doInBackground(Word... words) {
            switch (asyncTaskEvent) {
                case INSERT:
                    asyncTaskDao.insertWord(words[0]);
                    break;
                case UPDATE:
                    asyncTaskDao.updateWord(words[0]);
                    break;
                case DELETE:
                    asyncTaskDao.deleteWord(words[0]);
                    break;
                default:
                    Log.info(getClass(), "No suitable database operation are there.");
            }
            return null;
        }
    }

    public int getWordTableSize() {
        return mWordDao.getWordTableSize();
    }

    public Word getWordById(int id) {
        return mWordDao.getWord(id);//mAllWords.getValue().get(id);
    }
}
