package syed.com.wordrepo.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import syed.com.wordrepo.dao.WordDao;
import syed.com.wordrepo.entitiy.Word;

/**
 * Created by syed on 1/11/18.
 */

public class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    public WordRepository(Application application) {
        mWordDao = AppDatabase.getDatabase(application).wordDao();

        mAllWords = mWordDao.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insertWord(Word word){
        new InsertAsyncTask(mWordDao).execute(word);
    }

    private static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao asyncTaskDao;

        public InsertAsyncTask(WordDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            asyncTaskDao.insertWord(words[0]);
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
