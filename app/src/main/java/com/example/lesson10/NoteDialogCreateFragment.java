package com.example.lesson10;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Calendar;

public class NoteDialogCreateFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Подключаем макет
        View view = inflater.inflate(R.layout.fragment_note_dialog_create, null);
        setCancelable(false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Note newNote = new Note("", "", Calendar.getInstance().getTime());
        int idView = R.id.dialog_create;
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(idView, NoteFragment.newInstance(newNote, 0)).commit();
    }
}
