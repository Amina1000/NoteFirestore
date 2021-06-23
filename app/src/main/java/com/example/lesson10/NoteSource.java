package com.example.lesson10;

/**
 * homework com.example.notes
 *
 * @author Amina
 * 12.06.2021
 */
public interface NoteSource {
    NoteSource init(NotesSourceResponse notesSourceResponse);
    Note getNoteData(int position);
    int size();
    boolean isGroupItem(int position);
    String getGroupTitle(int position);
    void deleteNoteData(int position);
    void updateNoteData(int position, Note noteData);
    void addNoteData(Note noteData);
    void clearNoteData();
}
