package syed.com.wordrepo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import syed.com.wordrepo.entitiy.Word;
import syed.com.wordrepo.repo.WordRepository;

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
        mWordRepository.insertWord(word);
    }

    public LiveData<List<Word>> getAllWords() {
        return mWords;
    }
}

