package syed.com.wordrepo.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;

import syed.com.wordrepo.R;
import syed.com.wordrepo.adapter.AudioListAdapter;
import syed.com.wordrepo.utility.AudioFileUtil;

public class AudioListActivity extends AppCompatActivity {

    private AudioListAdapter mAdapter;
    private MediaPlayer mPlayer;
    private Handler mHandler;
    private int mRunningAudioPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        final RecyclerView recyclerView = findViewById(R.id.audio_recyclerview);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new AudioListAdapter(this, new AudioListAdapter.IAudioItemListClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                AudioListAdapter.AudioViewHolder viewHolder
                        = (AudioListAdapter.AudioViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
                if (mPlayer == null) {
                    onPlay(viewHolder, true, position);
                } else if (mRunningAudioPosition == position) {
                    onPlay(viewHolder, false, position);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "An audio is running. Please turn of that audio first", Toast.LENGTH_LONG).show();
                }
            }
        });
        recyclerView.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AudioListActivity.this, NewRecordActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //Todo Need to handle the configuration change
    private void onPlay(AudioListAdapter.AudioViewHolder viewHolder, boolean play, int position) {
        if (play) {
            File file = mAdapter.getFileByPosition(position);

            mPlayer = new MediaPlayer();
            AudioFileUtil.startPlaying(mPlayer, file);
            mRunningAudioPosition = position;

            viewHolder.playImageView.setVisibility(View.GONE);
            viewHolder.stopImageView.setVisibility(View.VISIBLE);
            viewHolder.audioProgress.setVisibility(View.VISIBLE);

            setAudioProgress(mPlayer.getDuration(), viewHolder.audioProgress);
        } else {
            AudioFileUtil.stopPlaying(mPlayer);
            resetRunningAudioPosition();
            mPlayer = null;

            viewHolder.playImageView.setVisibility(View.VISIBLE);
            viewHolder.stopImageView.setVisibility(View.GONE);
            viewHolder.audioProgress.setVisibility(View.GONE);
        }
    }

    private void setAudioProgress(final int duration, final ProgressBar progressBar) {
        Toast.makeText(getApplicationContext(), "duration of audio: " + duration, Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            public void run() {
                int progressStatus = 0;
                while (duration > progressStatus) {
                    progressStatus += 200;
                    // Update the progress bar and display the
                    //current value in the text view
                    final int progress = (100*progressStatus)/duration;
                    mHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progress);
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    private void onPlay(boolean start, File file) {
        if (start) {
            AudioFileUtil.startPlaying(mPlayer, file);
        } else {
            AudioFileUtil.stopPlaying(mPlayer);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            mAdapter.setFiles(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer = null;
    }

    private void resetRunningAudioPosition() {
        mRunningAudioPosition = -1;
    }

    //Todo finish the backstack or check backstack before switching. add some flag
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_word_list:
                    startActivity(new Intent(AudioListActivity.this, WordListActivity.class));
                    return true;
                case R.id.navigation_audio:

                    return true;

            }
            return false;
        }
    };
}
