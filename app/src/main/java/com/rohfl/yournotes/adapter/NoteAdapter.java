package com.rohfl.yournotes.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rohfl.yournotes.R;
import com.rohfl.yournotes.model.Note;
import com.rohfl.yournotes.mvp.Mvp;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private Context mContext;
    private List<Note> mNoteList;
    private Mvp mMvp;

    public NoteAdapter(Context mContext, List<Note> mNoteList, Mvp mMvp) {
        this.mContext = mContext;
        this.mNoteList = mNoteList;
        this.mMvp = mMvp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_adapter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.noteBg.setBackgroundColor(Color.parseColor(mNoteList.get(position).getNoteCardColor()));
        holder.noteTitle.setText(mNoteList.get(position).getNoteTitle());
        holder.noteDescription.setText(mNoteList.get(position).getNoteDescription());
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    public void updateList(List<Note> mNoteList) {
        this.mNoteList = mNoteList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout noteBg;
        TextView noteTitle, noteDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteBg = itemView.findViewById(R.id.note_bg);
            noteTitle = itemView.findViewById(R.id.note_title_tv);
            noteDescription = itemView.findViewById(R.id.note_description_tv);

            itemView.setOnClickListener(v -> {
                mMvp.onItemClicked(mNoteList.get(getAdapterPosition()).getNoteId());
            });

        }
    }
}
