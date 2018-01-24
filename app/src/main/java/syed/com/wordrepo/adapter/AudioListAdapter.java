package syed.com.wordrepo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

import syed.com.wordrepo.R;

/**
 * Created by syed on 1/11/18.
 */

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.AudioViewHolder> {
    private LayoutInflater mLayoutInflater;
    private IAudioItemListClickListener mListener;
    private Context mContext;
    private File[] mFiles;
    private int mSelectedPosition;
    private int mLastSelectedPosition;
    private boolean mCurrentlyPlaying;

    public AudioListAdapter(Context context, IAudioItemListClickListener listner) {
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listner;
        setFiles(context);
        mContext = context;
        mSelectedPosition = -1;
    }

    @Override
    public AudioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mLayoutInflater.inflate(R.layout.audio_list_item, parent, false);
        final AudioViewHolder viewHolder = new AudioViewHolder(itemView);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v, viewHolder.getAdapterPosition());

//                mLastSelectedPosition = mSelectedPosition;
//                mSelectedPosition = viewHolder.getAdapterPosition();
//                notifyItemChanged(mSelectedPosition);
//
//                if (mSelectedPosition != mLastSelectedPosition) {
//                    notifyItemChanged(mLastSelectedPosition);
//                }

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AudioViewHolder holder, int position) {
        if (mFiles != null) {
            String fileName = mFiles[mFiles.length - 1 - position].getName();
            holder.audioTextView.setText(fileName.substring(0, fileName.length() - 4));


//            Toast.makeText(mContext, "position: " + position + ", mLastSelectedPosition: "
//                    + mLastSelectedPosition + ", mSelectedPosition: " + mSelectedPosition, Toast.LENGTH_SHORT).show();
//            if ((mSelectedPosition == mLastSelectedPosition || mLastSelectedPosition == position) && mCurrentlyPlaying) {
//                mCurrentlyPlaying = false;
//                holder.audioProgress.setVisibility(View.GONE);
//                holder.playImageView.setVisibility(View.VISIBLE);
//                holder.stopImageView.setVisibility(View.GONE);
//            } else if (mSelectedPosition == position) {
//                mCurrentlyPlaying = true;
//                holder.audioProgress.setVisibility(View.VISIBLE);
//                holder.playImageView.setVisibility(View.GONE);
//                holder.stopImageView.setVisibility(View.VISIBLE);
//            }

        } else {
            holder.audioTextView.setText("updating...");
        }
    }

    @Override
    public int getItemCount() {
        if (mFiles == null)
            return 0;
        return mFiles.length;
    }

    public void setFiles(Context context) {
        File yourDir = new File(context.getExternalCacheDir().getAbsolutePath());
        // Do your stuff
        mFiles = yourDir.listFiles();
        notifyDataSetChanged();
    }

    public File getFileByPosition(int position) {
        if (mFiles != null && mFiles.length > position) {
            return mFiles[mFiles.length - 1 - position];
        }
        return null;
    }

    public void onMediaPlaying(int position) {

    }

    public void onMediaStoping(int position) {

    }

    public class AudioViewHolder extends RecyclerView.ViewHolder {
        public TextView audioTextView;
        public ImageView playImageView;
        public ImageView stopImageView;
        public ProgressBar audioProgress;

        public AudioViewHolder(View itemView) {
            super(itemView);
            audioTextView = itemView.findViewById(R.id.audio_text_view);
            playImageView = itemView.findViewById(R.id.play_audio_button);
            stopImageView = itemView.findViewById(R.id.stop_audio_button);
            audioProgress = itemView.findViewById(R.id.audio_progress_bar);
        }
    }

    public interface IAudioItemListClickListener {
        void onItemClick(View v, int position);
    }

}
