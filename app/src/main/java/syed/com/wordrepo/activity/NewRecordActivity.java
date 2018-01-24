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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import syed.com.wordrepo.R;
import syed.com.wordrepo.utility.AudioFileUtil;

public class NewRecordActivity extends AppCompatActivity {

    private static final String TAG = "NewRecordActivity: syed";
    private static final int TIMER_RUNNING = 0;
    private static final int STOP_TIMER = 1;

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private Button mRecordButton = null;
    private MediaRecorder mRecorder = null;
    private TextView mCurrentTimeTV = null;
    private int mCurrentTime;
    private boolean mStartRecording = false;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) {
            Toast.makeText(getApplicationContext(), "No permission to record", Toast.LENGTH_SHORT).show();
            finish();
        }

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

    final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIMER_RUNNING:
                    updateTimer();
                    sendEmptyMessageDelayed(TIMER_RUNNING, 1000);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    private void updateTimer() {
        mCurrentTime++;
        int hh = mCurrentTime/(3600);
        int mm = (mCurrentTime-hh*3600)/60;
        int ss = (mCurrentTime - (hh*3600) - (mm*60));
        mCurrentTimeTV.setText(hh+" : "+mm+" : "+ss);
    }


    @Override
    protected void onDestroy() {
        stopTimer();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);

        mRecordButton = (Button) findViewById(R.id.record_button);

        mStartRecording = true;
        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecord(mStartRecording);
                mStartRecording = !mStartRecording;
            }
        });

        mCurrentTimeTV= (TextView) findViewById(R.id.timer_text);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

    }

    //    class RecordButton extends AppCompatButton {
//        boolean mStartRecording = true;
//
//        View.OnClickListener clicker = new View.OnClickListener() {
//            public void onClick(View v) {
//                onRecord(mStartRecording);
//                if (mStartRecording) {
//                    setText("Stop recording");
//                } else {
//                    setText("Start recording");
//                }
//                mStartRecording = !mStartRecording;
//            }
//        };
//
//        public RecordButton(Context ctx) {
//            super(ctx);
//            setText("Start recording");
//            setOnClickListener(clicker);
//        }
//    }
}
