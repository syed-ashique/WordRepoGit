package syed.com.wordrepo.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import syed.com.wordrepo.R;
import syed.com.wordrepo.utility.AudioFileUtil;

public class NewRecordActivity extends AppCompatActivity {
    private static final int TIMER_RUNNING = 0;
    private static final int STOP_TIMER = 1;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    @BindView(R.id.timer_text)
    public TextView mCurrentTimeTV;

    @BindView(R.id.timer_rg)
    public RadioGroup timerRG;

    private MediaRecorder mRecorder = null;
    private int mCurrentTime;
    private boolean mStartRecording = false;
    private int mTimerSelected;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) {
            Toast.makeText(getApplicationContext(), "No permission to record.\n Goto \"Settings -> App -> wordrepo\"." +
                    "\nManually accept the mic permission first", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TIMER_RUNNING) {
                updateTimer();
                sendEmptyMessageDelayed(TIMER_RUNNING, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        ButterKnife.bind(this);

        mStartRecording = true;
    }

    //Todo need to handle the configuration change for mStartRecording variable
    @OnClick(R.id.record_button)
    public void onRecord(View v) {
        onRecord(mStartRecording);
        mStartRecording = !mStartRecording;
    }

    private void onRecord(boolean start) {
        if (start) {
            mRecorder = new MediaRecorder();
            AudioFileUtil.startRecording(mRecorder, this);
            startTimer();
        } else {
            AudioFileUtil.stopRecording(mRecorder);
            mRecorder = null;
            stopTimer();
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        stopTimer();
        super.onDestroy();
    }

    private void startTimer() {
        mCurrentTime = 0 ;
        if (mHandler != null) {
            mHandler.sendEmptyMessageDelayed(TIMER_RUNNING, 1000);
        }
    }

    private void stopTimer() {
        if (mHandler != null &&mHandler.hasMessages(TIMER_RUNNING)) {
            mHandler.removeMessages(TIMER_RUNNING);
        }
    }

    private void updateTimer() {
        mCurrentTime++;
        setmCurrentTime();
    }

    private void setmCurrentTime() {
        int hh = mCurrentTime / (3600);
        int mm = (mCurrentTime - (hh * 3600)) / 60;
        int ss = (mCurrentTime - (hh * 3600) - (mm * 60));
        mCurrentTimeTV.setText(hh + " : " + mm + " : " + ss);
    }
}
