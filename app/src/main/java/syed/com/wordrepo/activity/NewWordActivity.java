package syed.com.wordrepo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import syed.com.wordrepo.R;
import syed.com.wordrepo.entitiy.Word;
import syed.com.wordrepo.utility.WordRepoApplication;

public class NewWordActivity extends AppCompatActivity {
    //Todo implement eventbus
    public static final String EXTRA_REPLY = "NEW_WORD";

    private EditText mEditWordView;
    private EditText mEditMeaningView;
    private EditText mEditDescriptionView;
    private EditText mEditNoteView;
    EventBus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        mEditWordView = findViewById(R.id.edit_word);
        mEditMeaningView = findViewById(R.id.edit_meaning);
        mEditDescriptionView = findViewById(R.id.edit_description);
        mEditNoteView = findViewById(R.id.edit_note);
//        final WordRepository wordRepository = new WordRepository(getApplication());


        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                Log.d("syed :","Clicked on the save button");
                if (TextUtils.isEmpty(mEditWordView.getText())) {
                    Toast.makeText(getApplicationContext(),"text is empty", Toast.LENGTH_SHORT).show();
                } else {
                    Word word = new Word(mEditWordView.getText().toString()
                            , mEditMeaningView.getText().toString()
                            , mEditDescriptionView.getText().toString()
                            , mEditNoteView.getText().toString());

                    WordRepoApplication.getWordRepository().insertWord(word);
//                    EventBus.getDefault().post(word);
                }
                finish();
            }

        });
    }
}
