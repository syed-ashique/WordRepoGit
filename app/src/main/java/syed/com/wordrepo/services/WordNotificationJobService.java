package syed.com.wordrepo.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.Random;

import syed.com.wordrepo.entitiy.Word;
import syed.com.wordrepo.repo.WordRepository;
import syed.com.wordrepo.utility.Log;
import syed.com.wordrepo.utility.NotificationUtil;

/**
 * Created by syed on 1/23/18.
 */

public class WordNotificationJobService extends JobService implements Handler.Callback {
    private static final int SEND_NOTIFICATION = 1;
    private Handler mHandler;
    private WordRepository mWordRepository;

    @Override
    public boolean handleMessage(Message msg) {

        switch (msg.what) {
            case SEND_NOTIFICATION:
                Word word = (Word) msg.obj;

                NotificationUtil.createWordNotification(getApplicationContext(), word.getWord(), word.getMeaning());
                break;

            default:
                Log.info(this.getClass(), "I didn't get the message! Sorry");
        }
        return false;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.info(this.getClass(), "onStartJob() -> starting job..");
        mWordRepository = new WordRepository(getApplication());

        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper(), this);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Word word = getRandomWord();

                mHandler.obtainMessage(SEND_NOTIFICATION, word).sendToTarget();
            }
        }).start();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    public Word getRandomWord() {
        Word word = null;
        int size = mWordRepository.getWordTableSize();
        Log.info(this.getClass(), "Table size: " + size);

        if (size > 0) {
            Random rand = new Random();
            word = mWordRepository.getWordById(rand.nextInt(size));
            Log.info(this.getClass(), "Selected random word is : " + word.getWord());
        }

        return word;
    }
}
