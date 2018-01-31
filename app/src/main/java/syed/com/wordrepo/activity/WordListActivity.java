package syed.com.wordrepo.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import syed.com.wordrepo.R;
import syed.com.wordrepo.WordRepoApplication;
import syed.com.wordrepo.adapter.WordListAdapter;
import syed.com.wordrepo.entitiy.Word;
import syed.com.wordrepo.utility.Log;
import syed.com.wordrepo.viewmodel.WordViewModel;

public class WordListActivity extends AppCompatActivity {
    public static final int NEW_WORD_REQUEST_CODE = 0;

    private WordViewModel mWordViewModel;
    private WordListAdapter mAdapter;
    private MenuItem mActionEdit;
    private MenuItem mActionDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.word_recyclerview);
        mAdapter = new WordListAdapter(this, new ItemClickListener(), new ItemItemLongClickListener());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mWordViewModel =  ViewModelProviders.of(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {
                mAdapter.setWords(words);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(WordListActivity.this, NewWordActivity.class), NEW_WORD_REQUEST_CODE);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.info(getClass(), "grabing data complete");
                mWordViewModel.insert(((WordRepoApplication) getApplicationContext()).getWord());
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mAdapter.isSelctedMode()) {
            mAdapter.clearSelections();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        mAdapter.clearSelections();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_word_list, menu);

        mActionEdit = menu.findItem(R.id.action_edit);
        mActionDelete = menu.findItem(R.id.action_delete);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Working progress...", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_edit:
                //Todo Need to work with it
                if (mAdapter.getSelectedWords().size()>0) {
                    Word word = mAdapter.getSelectedWords().get(0);
                    Toast.makeText(getApplicationContext(), "Working progress... ", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.action_delete:
                List<Word> words = mAdapter.getSelectedWords();
                if (words.size() == 0 ) {
                    Toast.makeText(getApplicationContext(), "No word selected", Toast.LENGTH_LONG).show();
                } else {
                    mAdapter.clearSelections();
                    for (Word word: words) {
                        mWordViewModel.delete(word);
                    }
                }
                break;

            default: Log.info(getClass(), "action id is not recognized");
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_word_list:

                    return true;
                case R.id.navigation_audio:
                    startActivity(new Intent(WordListActivity.this, AudioListActivity.class));
                    return true;

            }
            return false;
        }
    };

    private class ItemClickListener implements WordListAdapter.WordOnItemClickListener {

        @Override
        public void onItemClickListener(View itemview, int position) {
            mActionEdit.setVisible(mAdapter.selectedWordsSize() == 1?true:false);
            mActionDelete.setVisible(mAdapter.selectedWordsSize() > 0?true:false);
            Log.info(getClass(), "wordlist item clicked");
        }
    }

    private class ItemItemLongClickListener implements WordListAdapter.WordOnItemLongClickListener {

        @Override
        public void onItemLongClickListener(View itemview, int position, boolean selectionMode) {
            Log.info(getClass(), "wordlist item long pressed");
        }
    }

    /*
    @Subscribe
    public void onEvent(Word word) {
        mWordViewModel.insert(word);
        Log.d(TAG, "Subscriber initiated :" );
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "syed : Registering..." );
        if(!EventBus.getDefault().isRegistered(Word.class)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        if(EventBus.getDefault().isRegistered(Word.class)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(EventBus.getDefault().isRegistered(Word.class)) {
            EventBus.getDefault().unregister(this);
        }
        super.onBackPressed();
    }*/
}
