package syed.com.wordrepo.utility;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static syed.com.wordrepo.activity.WordListActivity.TAG;

/**
 * Created by syed on 1/19/18.
 */

public class AudioFileUtility {

    private static final String MEDIA_FORMAT = ".3gp";
    private static final String FILE_SEPARATOR = "/";

    public static void startRecording(MediaRecorder recorder, Context context) {
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(getFileName(context));
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }

        recorder.start();
    }

    public static void stopRecording(MediaRecorder recorder) {
        recorder.stop();
        recorder.release();
    }

    private static String getFileName(Context context) {
        StringBuilder sb = new StringBuilder(context.getExternalCacheDir().getAbsolutePath());
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        return sb.append(FILE_SEPARATOR).append(date).append(MEDIA_FORMAT).toString();
    }


    public static void startPlaying(MediaPlayer player, File file) {
        try {
            player.setDataSource(Uri.fromFile(file).toString());
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(" AudioFileUtility", "prepare() failed");
        }
    }

    public static void stopPlaying(MediaPlayer player) {
        player.release();
    }

    public static int getAudioDuration(MediaPlayer player, File file) {
        try {
            player.setDataSource(Uri.fromFile(file).toString());
            return player.getDuration();
        } catch (IOException e) {
            Log.e(" AudioFileUtility", "prepare() failed");
        }

        return -1;
    }

}
