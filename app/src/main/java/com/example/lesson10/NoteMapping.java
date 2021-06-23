package com.example.lesson10;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

/**
 * homework com.example.notes
 *
 * @author Amina
 * 23.06.2021
 */
public class NoteMapping {
    public static class Fields {

        public final static String NAME = "name";
        public final static String DESCRIPTION = "description";
        public final static String AUTHOR = "author";
        public final static String DATE = "date";
    }

    public static Note toNoteData(String id, Map<String, Object> doc) {

        Timestamp date = (Timestamp) doc.get(Fields.DATE);
        Note answer = new Note((String) doc.get(Fields.NAME),
                (String) doc.get(Fields.DESCRIPTION),date.toDate());
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(Note noteData) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.NAME, noteData.getName());
        answer.put(Fields.DESCRIPTION, noteData.getDescription());
        answer.put(Fields.AUTHOR, noteData.getAuthor());
        answer.put(Fields.DATE, noteData.getDate());
        return answer;
    }
}
