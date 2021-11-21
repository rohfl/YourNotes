package com.rohfl.yournotes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rohfl.yournotes.model.Note;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DB";
    private static final String TABLE_NAME = "UserTable";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_NOTE_COLOR = "color";

//    Context ctx;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDb = "CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_TITLE + " TEXT," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_NOTE_COLOR + " TEXT"
                + " )";
        db.execSQL(createDb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_TITLE, note.getNoteTitle());
        cv.put(KEY_DESCRIPTION, note.getNoteDescription());
        cv.put(KEY_NOTE_COLOR, note.getNoteCardColor());

        long ID = db.insert(TABLE_NAME, null, cv);
        return ID;
    }

    public Note getNote(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[]{KEY_ID, KEY_TITLE, KEY_DESCRIPTION, KEY_NOTE_COLOR};
        Cursor cursor = db.query(TABLE_NAME, query, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return new Note(
                Long.parseLong(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));
    }

    public List<Note> getAllNotes() {
        List<Note> allNotes = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
//        Toast.makeText(ctx, query, Toast.LENGTH_SHORT).show();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setNoteId(Long.parseLong(cursor.getString(0)));
                note.setNoteTitle(cursor.getString(1));
                note.setNoteDescription(cursor.getString(2));
                note.setNoteCardColor(cursor.getString(3));
                allNotes.add(note);
            } while (cursor.moveToNext());
        }
        return allNotes;

    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_TITLE, note.getNoteTitle());
        c.put(KEY_DESCRIPTION, note.getNoteDescription());
        c.put(KEY_NOTE_COLOR, note.getNoteCardColor());
        return db.update(TABLE_NAME, c, KEY_ID + "=?", new String[]{String.valueOf(note.getNoteId())});
    }


    public void deleteNote(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }


}
