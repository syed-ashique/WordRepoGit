package syed.com.wordrepo.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_word_list.*
import kotlinx.android.synthetic.main.content_word_list.*
import syed.com.wordrepo.R
import syed.com.wordrepo.WordRepoApplication
import syed.com.wordrepo.adapter.WordListAdapter
import syed.com.wordrepo.utility.Log
import syed.com.wordrepo.viewmodel.WordViewModel


class WordListActivity : AppCompatActivity() {

    private lateinit var mWordViewModel: WordViewModel
    private lateinit var mAdapter: WordListAdapter
    private lateinit var mActionEdit: MenuItem
    private lateinit var mActionDelete: MenuItem

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.navigation_word_list -> true
            R.id.navigation_audio -> {
                startActivity(Intent(applicationContext, AudioListActivity::class.java))
                true
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_list)
        setSupportActionBar(toolbar)

        mAdapter = WordListAdapter(applicationContext, ItemClickListener(), ItemItemLongClickListener())
        word_recyclerview.adapter = mAdapter
        word_recyclerview.layoutManager = LinearLayoutManager(this)


        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
        mWordViewModel.mWords.observe(this, Observer { words -> mAdapter.setWords((ArrayList(words))) })

        fab.setOnClickListener {
            startActivityForResult(Intent(applicationContext, NewWordActivity::class.java), NEW_WORD_REQUEST_CODE)
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_WORD_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.info(javaClass, "grabing data complete")
                mWordViewModel.insert((applicationContext as WordRepoApplication).word)
            }
        }
    }

    override fun onBackPressed() {
        if (mAdapter.isSelctedMode) {
            mAdapter.clearSelections()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        mAdapter.clearSelections()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_word_list, menu)

        mActionEdit = menu.findItem(R.id.action_edit)
        mActionDelete = menu.findItem(R.id.action_delete)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        when (id) {
            R.id.action_settings -> Toast.makeText(applicationContext, "Working progress...", Toast.LENGTH_LONG).show()
            R.id.action_edit ->
                //Todo Need to work with it
                if (mAdapter.selectedWords.size > 0) {
                    val word = mAdapter.selectedWords[0]
                    Toast.makeText(applicationContext, "Working progress... ", Toast.LENGTH_LONG).show()
                }
            R.id.action_delete -> {
                val words = mAdapter.selectedWords
                if (words.size == 0) {
                    Toast.makeText(applicationContext, "No word selected", Toast.LENGTH_LONG).show()
                } else {
                    mAdapter.clearSelections()
                    for (word in words) {
                        if (word != null) {
                            mWordViewModel.delete(word)
                        }
                    }
                }
            }

            else -> Log.info(javaClass, "action id is not recognized")
        }

        return if (id == R.id.action_settings) true else super.onOptionsItemSelected(item)
    }

    private inner class ItemClickListener : WordListAdapter.WordOnItemClickListener {

        override fun onItemClickListener(itemview: View, position: Int) {
            mActionEdit.setVisible(if (mAdapter.selectedWordsSize() == 1) true else false)
            mActionDelete.setVisible(if (mAdapter.selectedWordsSize() > 0) true else false)
            Log.info(javaClass, "wordlist item clicked")
        }
    }

    private inner class ItemItemLongClickListener : WordListAdapter.WordOnItemLongClickListener {

        override fun onItemLongClickListener(itemview: View, position: Int, selectionMode: Boolean) {
            Log.info(javaClass, "wordlist item long pressed")
        }
    }

    companion object {
        val NEW_WORD_REQUEST_CODE = 0
    }
}
