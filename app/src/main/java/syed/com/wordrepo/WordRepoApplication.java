package syed.com.wordrepo;

import android.app.Application;

import syed.com.wordrepo.repo.WordRepository;

/**
 * Created by syed on 1/12/18.
 */

public class WordRepoApplication extends Application {

    private static WordRepository mWordRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        mWordRepository = new WordRepository(this);
    }

    public static WordRepository getWordRepository() {
        return mWordRepository;
    }
}
