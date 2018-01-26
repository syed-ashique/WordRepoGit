package syed.com.wordrepo;

import android.app.Application;

import syed.com.wordrepo.entitiy.Word;

/**
 * Created by syed on 1/12/18.
 */

public class WordRepoApplication extends Application {
    private Word mWord;

    public void setWord(Word word) {
        mWord = word;
    }

    public Word getWord() {
        return mWord;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
