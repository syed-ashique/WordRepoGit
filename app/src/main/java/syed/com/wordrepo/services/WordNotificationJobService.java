package syed.com.wordrepo.services;

import android.app.job.JobParameters;
import android.app.job.JobService;

import syed.com.wordrepo.utility.Log;
import syed.com.wordrepo.utility.NotificationUtil;

/**
 * Created by syed on 1/23/18.
 */

public class WordNotificationJobService extends JobService{

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.info(this.getClass(), "onStartJob() -> starting job..");
        NotificationUtil.createWordNotification(getApplicationContext(), "Test", "Test Word");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
