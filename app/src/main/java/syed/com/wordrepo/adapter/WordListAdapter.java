package syed.com.wordrepo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import syed.com.wordrepo.R;
import syed.com.wordrepo.entitiy.Word;
import syed.com.wordrepo.utility.Log;

/**
 * Created by syed on 1/11/18.
 */

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder>{
    private LayoutInflater mLayoutInflater;
    private List<Word> mWords;
    private Set<Integer> mSelectedPositions;
    private WordOnItemClickListener mOnItemClickListener;
    private WordOnItemLongClickListener mOnItemLongClickListerer;
    private boolean mSelectionMode;
    private boolean mAllSelected;

    public WordListAdapter(Context context, WordOnItemClickListener itemClickListener, WordOnItemLongClickListener itemLongClickListener) {
        mOnItemClickListener = itemClickListener;
        mOnItemLongClickListerer = itemLongClickListener;
        mLayoutInflater = LayoutInflater.from(context);
        mSelectedPositions = new HashSet<>();
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.word_list_item, parent, false);
        return new WordViewHolder(itemView);
    }

    //Todo have to cleanup redundancy
    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (mWords != null) {
            holder.wordText.setText(mWords.get(position).getWord());

            String meaning = ""+mWords.get(position).getMeaning();
            String description = ""+mWords.get(position).getDescription();
            String note = ""+mWords.get(position).getNote();

            holder.checkbox.setVisibility(isSelctedMode() ? View.VISIBLE : View.GONE);
            ((CheckBox) holder.checkbox).setChecked((mSelectedPositions.contains(position))?true:false);

            holder.meaningText.setVisibility(meaning.isEmpty()?View.GONE:View.VISIBLE);
            holder.descriptionText.setVisibility(description.isEmpty()?View.GONE:View.VISIBLE);
            holder.noteText.setVisibility(note.isEmpty()?View.GONE:View.VISIBLE);

            holder.meaningText.setText(meaning);
            holder.descriptionText.setText(description);
            holder.noteText.setText(note);
        } else {
            holder.wordText.setText("No Word");
            holder.meaningText.setText("None");
            holder.descriptionText.setText("No Description");
            holder.noteText.setText("None");
        }
    }

    @Override
    public int getItemCount() {
        if (mWords != null) {
            return mWords.size();
        }
        return 0;
    }

    public void setWords(List<Word> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        private TextView checkbox;
        private TextView wordText;
        private TextView meaningText;
        private TextView descriptionText;
        private TextView noteText;

        public WordViewHolder(final View itemView) {
            super(itemView);
            wordText = itemView.findViewById(R.id.word_text_view);
            meaningText = itemView.findViewById(R.id.meaning_text_view);
            descriptionText = itemView.findViewById(R.id.description_text_view);
            noteText = itemView.findViewById(R.id.note_text_view);
            checkbox = itemView.findViewById(R.id.word_item_checkbox);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (!mSelectionMode) {
                        int position = getAdapterPosition();
                        performActionOnLongPressed(v, position);
                        performActionOnItemClicked(v, position);
                    }
                    return false;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSelectionMode) {
                        performActionOnItemClicked(v, getAdapterPosition());
                    }
                }
            });
        }
    }

    private void performActionOnLongPressed(View v, int position) {
        Log.info(getClass(), "performing onLongItem click operation on: " + position);
        mSelectionMode = true;
        mOnItemLongClickListerer.onItemLongClickListener(v, position, mSelectionMode);
        notifyDataSetChanged();
    }

    private void performActionOnItemClicked(View v, int position) {
        Log.info(getClass(), "performing onItem click operation on: " + position);

        if (mSelectedPositions.contains(position)) {
            mSelectedPositions.remove(position);
        } else {
            mSelectedPositions.add(position);
        }

        mOnItemClickListener.onItemClickListener(v, position);
        notifyItemChanged(position);
    }

    private void performSelectAllButtonAction(boolean selectAll) {
        mAllSelected = selectAll;
        if (selectAll) {
            Log.info(getClass(), "all list item selected");
        } else {
            Log.info(getClass(), "all list item deselected");
        }
    }

    public void clearSelections() {
        mSelectedPositions = new HashSet<>();
        mSelectionMode = false;

        notifyDataSetChanged();
    }

    public List<Word> getSelectedWords() {
        List<Word> list = new ArrayList<>();
        for (int key: mSelectedPositions) {
            list.add(mWords.get(key));
        }
        return list;
    }

    public int selectedWordsSize() {
        return mSelectedPositions.size();
    }

    public boolean isSelctedMode() {
        return mSelectionMode;
    }

    public interface WordOnItemLongClickListener {
        void onItemLongClickListener(View itemview, int position, boolean selectionMode);
    }

    public interface WordOnItemClickListener {
        void onItemClickListener(View itemview, int position);
    }
}
