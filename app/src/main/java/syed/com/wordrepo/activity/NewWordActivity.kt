package syed.com.wordrepo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_new_word.*
import syed.com.wordrepo.R
import syed.com.wordrepo.WordRepoApplication
import syed.com.wordrepo.entitiy.Word
import syed.com.wordrepo.utility.Log

/**
 * Created by syed on 2/2/18.
 */

class NewWordActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)

        button_save.setOnClickListener { view -> saveWord(view) }
    }


    fun saveWord(view:View) {
        Log.info(NewWordActivity::class.java, "Clicked on the save button")
        if (TextUtils.isEmpty(edit_word.text)) {
            Toast.makeText(applicationContext, "Word is empty. Please add a word", Toast.LENGTH_SHORT).show()
        } else {
            val word = Word(null, edit_word.text.toString(), edit_meaning.text.toString(), edit_description.text.toString(), edit_note.text.toString())

            (applicationContext as WordRepoApplication).word = word
            setResult(RESULT_OK)
            finish()
            //          EventBus.getDefault().post(word);
        }
    }
}
