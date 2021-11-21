package com.rohfl.yournotes.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rohfl.yournotes.R;
import com.rohfl.yournotes.adapter.NoteAdapter;
import com.rohfl.yournotes.db.Database;
import com.rohfl.yournotes.model.Note;
import com.rohfl.yournotes.mvp.Mvp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotesActivity extends AppCompatActivity implements Mvp {

    FloatingActionButton fab;
    String color;
    boolean isCancel;

    private Database mDatabase;

    RecyclerView notesRv;

    EditText searchNoteEt;

    NoteAdapter mNoteAdapter;
    List<Note> mNoteList;

    long noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        updateStatusBarColorMain("#FFFFFF");

        searchNoteEt = findViewById(R.id.search_note_et);
        fab = findViewById(R.id.fab);
        notesRv = findViewById(R.id.notes_rv);

        mDatabase = new Database(this);

        mNoteList = new ArrayList<>();
        mNoteAdapter = new NoteAdapter(this, mNoteList, this);
        notesRv.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesRv.setAdapter(mNoteAdapter);

        searchNoteEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0) {
                    filterNotes(s.toString());
                } else {
                    displayAllNotes();
                }
            }
        });

        fab.setOnClickListener(view -> {
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.base_frame, new SigninFragment()).addToBackStack(null).commit();
            showNoteDialog(true, -1);
        });
    }


    private void showNoteDialog(boolean newNote, long id) {
        Dialog noteDialog = new Dialog(this);
        noteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        noteDialog.getWindow().getAttributes().windowAnimations = R.style.SlideUpDownAnim;
        noteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_note, null, false);

        noteDialog.setContentView(view);
        noteDialog.getWindow().setGravity(Gravity.CENTER);
        noteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        noteDialog.setCancelable(true);
        noteDialog.setCanceledOnTouchOutside(false);
        noteDialog.show();
        Window window = noteDialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        noteId = -1;

        LinearLayout note_bg = noteDialog.findViewById(R.id.note_bg);

        // init all the views
        ImageView back = noteDialog.findViewById(R.id.back_iv);
        TextView delete = noteDialog.findViewById(R.id.delete_tv);
        EditText noteTitle = noteDialog.findViewById(R.id.title_et);
        EditText noteDescription = noteDialog.findViewById(R.id.description_et);
        TextView cancelTv = noteDialog.findViewById(R.id.cancel_tv);
        TextView saveTv = noteDialog.findViewById(R.id.save_tv);

        isCancel = false;

        back.setOnClickListener(v -> {
            noteDialog.dismiss();
        });

        cancelTv.setOnClickListener(v -> {
            isCancel = true;
            noteDialog.dismiss();
        });

        // default color when a notes is added is white
        if (newNote) {
            color = "#FFFFFF";
            note_bg.setBackgroundColor(Color.parseColor(color));
            saveTv.setText("Add");
            delete.setVisibility(View.GONE);
        }
        else {
            noteId = id;
            Note note = mDatabase.getNote(noteId);
            noteTitle.setText(note.getNoteTitle());
            noteDescription.setText(note.getNoteDescription());
            color = note.getNoteCardColor();
            note_bg.setBackgroundColor(Color.parseColor(color));
        }

        saveTv.setOnClickListener(v -> {
//            String title = noteTitle.getText().toString().trim();
//            String description = noteDescription.getText().toString().trim();
//            if (title.length() == 0 && description.length() == 0) {
//                Toast.makeText(getContext(), "Please enter title or description.", Toast.LENGTH_SHORT).show();
//            } else {
            noteDialog.dismiss();
//            }
        });

        delete.setOnClickListener(v -> {
            isCancel = true;
            mDatabase.deleteNote(noteId);
            noteDialog.dismiss();
        });

        // views for the color of the note
        View white = noteDialog.findViewById(R.id.white_color);

        white.setOnClickListener(v -> {
            color = "#FFFFFF";
            note_bg.setBackgroundColor(Color.parseColor(color));
        });

//        View blue = noteDialog.findViewById(R.id.blue_color);
//        blue.setOnClickListener(v -> {
//            color = "#212121";
//            note_bg.setBackgroundColor(Color.parseColor(color));
//        });

        View amber = noteDialog.findViewById(R.id.amber_color);
        amber.setOnClickListener(v -> {
            color = "#FFC107";
            note_bg.setBackgroundColor(Color.parseColor(color));
        });

        View teal = noteDialog.findViewById(R.id.teal_color);
        teal.setOnClickListener(v -> {
            color = "#009688";
            note_bg.setBackgroundColor(Color.parseColor(color));
        });

        View green = noteDialog.findViewById(R.id.green_color);
        green.setOnClickListener(v -> {
            color = "#8BC34A";
            note_bg.setBackgroundColor(Color.parseColor(color));
        });

        View cream = noteDialog.findViewById(R.id.cream_color);
        cream.setOnClickListener(v -> {
            color = "#DCEDC8";
            note_bg.setBackgroundColor(Color.parseColor(color));
        });

        noteDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!isCancel) {
                    String title = noteTitle.getText().toString().trim();
                    String description = noteDescription.getText().toString().trim();
                    if (newNote) {
                        if (title.length() > 0 || description.length() > 0) {
                            Note mNote = new Note(title, description, color);
                            mDatabase.addNote(mNote);
                        }
                    } else {
                        Note mNote = new Note(noteId, title, description, color);
                        mDatabase.updateNote(mNote);
                    }
                }
                searchNoteEt.setText(null);
                searchNoteEt.clearFocus();
                displayAllNotes();
            }
        });

    }

    private void filterNotes(String s) {
        List<Note> mFilterNotes = new ArrayList<>();
        for(Note n: mNoteList) {
            if(n.getNoteTitle().toLowerCase(Locale.ROOT).contains(s.toLowerCase(Locale.ROOT)) ||
                    n.getNoteDescription().toLowerCase(Locale.ROOT).contains(s.toLowerCase(Locale.ROOT))) {
                mFilterNotes.add(n);
            }
        }
        mNoteAdapter.updateList(mFilterNotes);
    }

    @Override
    public void onResume() {
        super.onResume();
        searchNoteEt.clearFocus();
        displayAllNotes();
    }

    private void displayAllNotes() {
        mNoteList = mDatabase.getAllNotes();
        mNoteAdapter.updateList(mNoteList);
    }

    @Override
    public void onItemClicked(long id) {
        showNoteDialog(false, id);
    }

    @SuppressLint("ObsoleteSdkInt")
    public void updateStatusBarColorMain(String color) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor(color));
                window.setNavigationBarColor(Color.parseColor(color));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int systemUiVisibility = this.getWindow().getDecorView().getSystemUiVisibility();
                int flagLightStatusBar = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                systemUiVisibility |= flagLightStatusBar;
                this.getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
