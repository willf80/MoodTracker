package com.appinlab.moodtracker;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


public class CommentFragment extends DialogFragment {

    public interface OnDispatcher {
        void onDoneClicked(String comment);
    }

    private OnDispatcher mOnDispatcher;

    public CommentFragment() {
        // Required empty public constructor
    }

    public static CommentFragment newInstance() {
        return new CommentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setOnDispatcher(OnDispatcher onDispatcher) {
        mOnDispatcher = onDispatcher;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_comment, null);

        final EditText commentEditText = view.findViewById(R.id.comment_edit_text);

        builder.setView(view)
                .setNegativeButton("Annuler", null)
                .setPositiveButton("Valider", (dialog, which) -> {
                    if(mOnDispatcher != null) {
                        mOnDispatcher.onDoneClicked(commentEditText.getText().toString());
                    }
                });

        return builder.create();
    }
}
