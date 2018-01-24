package syed.com.wordrepo.services;

import android.app.job.JobParameters;
import android.app.job.JobService;

/**
 * Created by syed on 1/23/18.
 */

public class RecordReminderJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
