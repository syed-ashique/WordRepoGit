package syed.com.wordrepo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import syed.com.wordrepo.entitiy.Word;
import syed.com.wordrepo.repo.WordRepository;
import syed.com.wordrepo.utility.Log;

/**
 * Created by syed on 1/11/18.
 */

public class WordViewModel extends AndroidViewModel {
    private WordRepository mWordRepository;
    private LiveData<List<Word>> mWords;
    public WordViewModel(@NonNull Application application) {
        super(application);

        mWordRepository = new WordRepository(application);
        mWords = mWordRepository.getAllWords();
    }

    public void insert(Word word) {
        Log.info(getClass(), "Saving data using view model: "+ word.getWord());
        mWordRepository.performOperationWithWord(word, WordRepository.DatabaseOperationEvent.INSERT);
    }

    public void update(Word word) {
        Log.info(getClass(), "Updating data using view model: "+ word.getWord());
        mWordRepository.performOperationWithWord(word, WordRepository.DatabaseOperationEvent.UPDATE);
    }

    public void delete(Word word) {
        Log.info(getClass(), "Deleting data using view model: "+ word.getWord());
        mWordRepository.performOperationWithWord(word, WordRepository.DatabaseOperationEvent.DELETE);
    }

    public LiveData<List<Word>> getAllWords() {
        return mWords;
    }
}

