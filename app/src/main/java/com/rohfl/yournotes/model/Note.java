package com.rohfl.yournotes.model;

public class Note {

    long noteId;
    String noteTitle;
    String noteDescription;
    String noteCardColor = "#FFFFFF";

    public Note() {
    }

    public Note(String noteTitle, String noteDescription, String noteCardColor) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteCardColor = noteCardColor;
    }

    public Note(long noteId, String noteTitle, String noteDescription,String noteCardColor) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteCardColor = noteCardColor;
    }

    public long getNoteId() {
        return noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public String getNoteCardColor() {
        return noteCardColor;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public void setNoteCardColor(String noteCardColor) {
        this.noteCardColor = noteCardColor;
    }
}
