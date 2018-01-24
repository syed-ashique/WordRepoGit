package syed.com.wordrepo.utility;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import syed.com.wordrepo.R;
import syed.com.wordrepo.activity.WordListActivity;

/**
 * Created by syed on 1/23/18.
 */

public class NotificationUtil {

    private static final String CHANNEL_ID = "channel_01";
    private static final String CHANNEL_NAME = "word_channel";
    private static final String CHANNEL_DESCRIPTION = "word channel demo";
    private static final int SMALL_ICON_ID = R.drawable.ic_notifications_black_24dp;
    private static final int NOTIFICATION_ID = 1564;

    private static NotificationCompat.Builder getNotificationBuilder(Context context, String title, String text) {
        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(SMALL_ICON_ID)
                .setContentTitle(title)
                .setSound(null)
                .setContentText(text);
    }

    private static PendingIntent getPendingIntent(Context context, Class destinationClass) {
        Intent resultIntent = new Intent(context, destinationClass);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(destinationClass);

        stackBuilder.addNextIntent(resultIntent);

        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static NotificationChannel setChannel() {
        int importance = NotificationManager.IMPORTANCE_LOW;

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
        channel.setDescription(CHANNEL_DESCRIPTION);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        return channel;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void createWordNotification(Context context, String title, String text) {
        NotificationCompat.Builder builder = getNotificationBuilder(context, title, text);

        builder.setContentIntent(getPendingIntent(context, WordListActivity.class));
        Log.info(SchedulerUtil.class, "Posting Word Notification ...");

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(setChannel());
        }
        manager.notify(NOTIFICATION_ID, builder.build());
    }


}
