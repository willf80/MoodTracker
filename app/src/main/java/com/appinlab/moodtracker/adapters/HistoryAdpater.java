package com.appinlab.moodtracker.adapters;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appinlab.moodtracker.R;
import com.appinlab.moodtracker.models.MoodStore;
import com.appinlab.moodtracker.utils.Constants;

import java.util.Calendar;
import java.util.List;

public class HistoryAdpater extends RecyclerView.Adapter<HistoryAdpater.HistoryViewHolder> {
    public interface OnDispatcher {
        void onClick(MoodStore moodStore);
    }

    private OnDispatcher mOnDispatcher;

    private List<MoodStore> mHistoryMoodStoreList;

    public HistoryAdpater(List<MoodStore> historyMoodStoreList, OnDispatcher onDispatcher) {
        mOnDispatcher = onDispatcher;
        mHistoryMoodStoreList = historyMoodStoreList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_layout_view, viewGroup, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {
        MoodStore moodStore = mHistoryMoodStoreList.get(i);

        historyViewHolder.mLinearLayout
                .setBackgroundColor(ContextCompat.getColor(historyViewHolder.itemView.getContext(),
                        moodStore.getColor()));

        historyViewHolder.mMoodImageView.setImageResource(moodStore.getImage());

        if(moodStore.getComment() == null || moodStore.getComment().isEmpty()) {
            historyViewHolder.mCommentImageView.setVisibility(View.GONE);
        }else {
            historyViewHolder.mCommentImageView.setVisibility(View.VISIBLE);
        }

        historyViewHolder.mLinearLayout.setOnClickListener(v -> {
            if(moodStore.getComment() == null || moodStore.getComment().isEmpty()){
                return;
            }

            mOnDispatcher.onClick(moodStore);
        });


        int dayDiff = Constants.getDateDiffBetweenTwoDate(moodStore.getDateAdd(), Calendar.getInstance().getTime());
        if(dayDiff >= 0 && dayDiff < Constants.daysString.length) {
            historyViewHolder.mDateTextView.setText(Constants.daysString[dayDiff]);
        }else {
            historyViewHolder.mDateTextView.setText(String.format("Depuis le %s",
                    Constants.dateToStringFormat(moodStore.getDateAdd(), "dd/MM/yyyy HH:mm")));
        }
    }

    @Override
    public int getItemCount() {
        return mHistoryMoodStoreList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView mDateTextView;
        ImageView mCommentImageView;
        ImageView mMoodImageView;
        LinearLayout mLinearLayout;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            mDateTextView = itemView.findViewById(R.id.date_textView);
            mCommentImageView = itemView.findViewById(R.id.comment_imageView);
            mMoodImageView = itemView.findViewById(R.id.mood_imageView);
            mLinearLayout = itemView.findViewById(R.id.history_item_layout);
        }
    }
}
