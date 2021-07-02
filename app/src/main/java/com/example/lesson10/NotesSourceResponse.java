package com.example.lesson10;

/**
 * homework com.example.notes
 *
 * @author Amina
 * 23.06.2021
 */
/*
данный интерфейс можно заменить на Runnable, но мне потребовалась такая конструкция чтоб понять как работают обозреватели
 */
public interface NotesSourceResponse {
    // Метод initialized() будет вызываться, когда данные проинициализируются и будут готовы.
    void initialized(NoteSource noteSource);
}
