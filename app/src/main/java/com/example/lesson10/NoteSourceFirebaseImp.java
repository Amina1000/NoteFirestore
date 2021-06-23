package com.example.lesson10;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * homework com.example.notes
 *
 * @author Amina
 * 23.06.2021
 */
public class NoteSourceFirebaseImp implements NoteSource {
    private static final String NOTES_COLLECTION = "note";
    private static final String TAG = "NotesSourceFirebaseImpl";

    // База данных Firestore
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    ;

    // Коллекция документов
    private CollectionReference collection = store.collection(NOTES_COLLECTION);

    // Загружаемый список карточек
    private List<Note> notesData = new ArrayList<>();

    public NoteSourceFirebaseImp() {
    }

    @Override
    public NoteSource init(final NotesSourceResponse notesSourceResponse) {
        // Получить всю коллекцию, отсортированную по полю «Заголовок»
        collection.orderBy("name", Query.Direction.ASCENDING).get()
                .addOnCompleteListener(task -> {
                    notesData = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {

                        Map<String, Object> doc = documentSnapshot.getData();
                        String id = documentSnapshot.getId();
                        Note noteData = NoteMapping.toNoteData(id, doc);

                        notesData.add(noteData);
                    }
                    notesSourceResponse.initialized(NoteSourceFirebaseImp.this);
                })
                .addOnFailureListener(e -> Log.d(TAG, "get failed with ", e));
        return this;
    }

    @Override
    public Note getNoteData(int position) {
        return notesData.get(position);
    }

    @Override
    public int size() {
        if (notesData == null) {
            return 0;
        }
        return notesData.size();
    }

    @Override
    public void updateNoteData(int position, Note noteData) {
        String id = noteData.getId();
        if (id != null) {
            // Изменить документ по идентификатору
            collection.document(id).set(noteData);
        }
    }

    @Override
    public void addNoteData(Note noteData) {
        // Добавить документ
        collection.add(noteData).addOnSuccessListener(documentReference ->
                noteData.setId(documentReference.getId()));
    }

    @Override
    public boolean isGroupItem(int position) {
        return false;
    }

    @Override
    public String getGroupTitle(int position) {
        return null;
    }

    @Override
    public void deleteNoteData(int position) {
        // Удалить документ с определённым идентификатором
        String id = notesData.get(position).getId();
        if (id != null) {
            collection.document(id).delete();
            notesData.remove(position);
        }
    }

    @Override
    public void clearNoteData() {
        for (Note note : notesData) {
            String id = note.getId();
            collection.document(id).delete()
                    .addOnSuccessListener(command -> notesData.clear());
        }

    }
}