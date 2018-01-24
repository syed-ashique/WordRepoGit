package syed.com.wordrepo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import syed.com.wordrepo.R;
import syed.com.wordrepo.entitiy.Word;

/**
 * Created by syed on 1/11/18.
 */

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder>{
    private LayoutInflater mLayoutInflater;
    private List<Word> mWords;

    public WordListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
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
            String noteText = ""+mWords.get(position).getNote();

            holder.meaningText.setText(meaning);
            holder.descriptionText.setText(description);
            holder.noteText.setText(noteText);
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
        private TextView wordText;
        private TextView meaningText;
        private TextView descriptionText;
        private TextView noteText;

        public WordViewHolder(View itemView) {
            super(itemView);
            wordText = itemView.findViewById(R.id.word_text_view);
            meaningText = itemView.findViewById(R.id.meaning_text_view);
            descriptionText = itemView.findViewById(R.id.description_text_view);
            noteText = itemView.findViewById(R.id.note_text_view);
        }
    }
}
