package syed.com.wordrepo.utility;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;


import syed.com.wordrepo.services.WordNotificationJobService;

/**
 * Created by syed on 1/23/18.
 */

public class SchedulerUtil {
    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOUR = 60 * MINUTE;

    public static void scheduleWordNotificationJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, WordNotificationJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
//        builder.setMinimumLatency(SECOND); // wait at least
//        builder.setOverrideDeadline(1000); // maximum delay
        builder.setPeriodic(MINUTE);

        Log.info(SchedulerUtil.class, "Sheduling WordNotification ...");
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }

    public static void scheduleRecordReminderJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, WordNotificationJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(SECOND); // wait at least
        builder.setOverrideDeadline(5 * MINUTE); // maximum delay
        builder.setPeriodic(24 * HOUR, 15 * MINUTE);

        Log.info(SchedulerUtil.class, "Sheduling Record Notification ...");
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }
}
