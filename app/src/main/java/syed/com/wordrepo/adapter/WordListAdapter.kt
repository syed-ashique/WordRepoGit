package syed.com.wordrepo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.word_list_item.view.*
import syed.com.wordrepo.R
import syed.com.wordrepo.entitiy.Word
import syed.com.wordrepo.utility.Log
import java.util.*

/**
 * Created by syed on 1/11/18.
 */

class WordListAdapter(context: Context, val mOnItemClickListener: WordOnItemClickListener, val mOnItemLongClickListerer: WordOnItemLongClickListener)
    : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    private val mLayoutInflater: LayoutInflater
    private var mSelectedPositions: HashSet<Int>
    private var mWords: ArrayList<Word>? = null

    private var mAllSelected: Boolean = false

    var isSelctedMode: Boolean = false
        private set

    val selectedWords: List<Word?>
        get() {
            val list = ArrayList<Word?>()
            for (key in mSelectedPositions) {
                list.add(mWords?.get(key))
            }
            return list
        }

    init {
        mLayoutInflater = LayoutInflater.from(context)
        mSelectedPositions = HashSet()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = mLayoutInflater.inflate(R.layout.word_list_item, parent, false)
        return WordViewHolder(itemView)
    }

    //Todo have to cleanup redundancy
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word: Word? = mWords?.get(position);
        if (word != null) {
            holder.bindView(word, position)
        }
    }

    override fun getItemCount(): Int {
        return if (mWords != null) {
            mWords!!.size
        } else 0
    }

    fun setWords(words: ArrayList<Word>?) {
        mWords = words
        notifyDataSetChanged()
    }

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnLongClickListener { v ->
                if (!isSelctedMode) {
                    val position = adapterPosition
                    performActionOnLongPressed(v, position)
                    performActionOnItemClicked(v, position)
                }
                false
            }

            itemView.setOnClickListener {
                if (isSelctedMode) {
                    performActionOnItemClicked(it, adapterPosition)
                }
            }
        }

        fun bindView(word: Word, position: Int) {
            itemView.word_text_view.text = word.word

            itemView.meaning_text_view.visibility = if (word.meaning.isEmpty()) View.GONE else View.VISIBLE
            itemView.description_text_view.visibility = if (word.description.isEmpty()) View.GONE else View.VISIBLE
            itemView.note_text_view.visibility = if (word.note.isEmpty()) View.GONE else View.VISIBLE
            itemView.word_item_checkbox.visibility = if (isSelctedMode) View.VISIBLE else View.GONE

            itemView.meaning_text_view.text = word.meaning
            itemView.description_text_view.text = word.description
            itemView.note_text_view.text = word.note
            itemView.word_item_checkbox.isChecked = if (mSelectedPositions.contains(position)) true else false
        }
    }

    private fun performActionOnLongPressed(v: View, position: Int) {
        Log.info(javaClass, "performing onLongItem click operation on: " + position)
        isSelctedMode = true
        mOnItemLongClickListerer.onItemLongClickListener(v, position, isSelctedMode)
        notifyDataSetChanged()
    }

    private fun performActionOnItemClicked(v: View, position: Int) {
        Log.info(javaClass, "performing onItem click operation on: " + position)

        if (mSelectedPositions.contains(position)) {
            mSelectedPositions.remove(position)
        } else {
            mSelectedPositions.add(position)
        }

        mOnItemClickListener.onItemClickListener(v, position)
        notifyItemChanged(position)
    }

    private fun performSelectAllButtonAction(selectAll: Boolean) {
        mAllSelected = selectAll
        if (selectAll) {
            Log.info(javaClass, "all list item selected")
        } else {
            Log.info(javaClass, "all list item deselected")
        }
    }

    fun clearSelections() {
        mSelectedPositions = HashSet()
        isSelctedMode = false

        notifyDataSetChanged()
    }

    fun selectedWordsSize(): Int {
        return mSelectedPositions.size
    }

    interface WordOnItemLongClickListener {
        fun onItemLongClickListener(itemview: View, position: Int, selectionMode: Boolean)
    }

    interface WordOnItemClickListener {
        fun onItemClickListener(itemview: View, position: Int)
    }
}
