package syed.com.wordrepo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import syed.com.wordrepo.R;
import syed.com.wordrepo.WordRepoApplication;
import syed.com.wordrepo.entitiy.Word;
import syed.com.wordrepo.utility.Log;

public class NewWordActivity extends AppCompatActivity {
    //Todo implement eventbus need to cleanup
    public static final String EXTRA_REPLY = "NEW_WORD";

    @BindView(R.id.edit_word)
    public EditText mEditWordView;

    @BindView(R.id.edit_meaning)
    public EditText mEditMeaningView;

    @BindView(R.id.edit_description)
    public EditText mEditDescriptionView;

    @BindView(R.id.edit_note)
    public EditText mEditNoteView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_save)
    public void saveWord() {
        Log.info(this.getClass(),"Clicked on the save button");
        if (TextUtils.isEmpty(mEditWordView.getText())) {
            Toast.makeText(getApplicationContext(),"Word is empty. Please add a word", Toast.LENGTH_SHORT).show();
        } else {
            Word word = new Word(mEditWordView.getText().toString()
                    , mEditMeaningView.getText().toString()
                    , mEditDescriptionView.getText().toString()
                    , mEditNoteView.getText().toString());

            //Todo need to work with it. Not good to expose repository in the whole application context
            WordRepoApplication.getWordRepository().insertWord(word);
            finish();
//          EventBus.getDefault().post(word);
        }
    }
}
