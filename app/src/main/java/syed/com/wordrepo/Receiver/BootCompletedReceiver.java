package syed.com.wordrepo.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import syed.com.wordrepo.utility.Log;
import syed.com.wordrepo.utility.SchedulerUtil;

/**
 * Created by syed on 1/23/18.
 */

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.info(this.getClass(), "Boot Completed...");
        SchedulerUtil.scheduleWordNotificationJob(context);
    }
}
