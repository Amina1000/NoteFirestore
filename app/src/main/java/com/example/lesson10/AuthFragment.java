package com.example.lesson10;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // Используется, чтобы определить результат activity регистрации через
    // Google
    private static final int RC_SIGN_IN = 40404;
    private static final String TAG = "GoogleAuth";

    // TODO: Rename and change types of parameters
    // Клиент для регистрации пользователя через Google
    private GoogleSignInClient googleSignInClient;
    // Кнопка регистрации через Google
    private com.google.android.gms.common.SignInButton buttonSignIn;
    private TextView emailView;
    private MaterialButton continueRun;
    private GoogleSignInAccount account;


    public static AuthFragment newInstance() {
        return new AuthFragment();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth, container, false);
        initGoogleSign();
        initView(view);
        enableSign();
        return view;
    }

    // Инициализация запроса на аутентификацию
    private void initGoogleSign() {
        // Конфигурация запроса на регистрацию пользователя, чтобы получить
        // идентификатор пользователя, его почту и основной профайл
        // (регулируется параметром)
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Получаем клиента для регистрации и данные по клиенту
        googleSignInClient = GoogleSignIn.getClient(getContext(),gso);
    }

    // Инициализация пользовательских элементов
    private void initView(View view) {
        // Кнопка регистрации пользователя
        buttonSignIn = view.findViewById(R.id.sign_in_button);
        buttonSignIn.setOnClickListener(v -> signIn()
        );

        emailView = view.findViewById(R.id.email);

        // Кнопка «Продолжить», будем показывать главный фрагмент
        continueRun = view.findViewById(R.id.continue_);
        continueRun.setOnClickListener(v -> ((Controller) requireActivity()).openMainScreen());
    }

    @Override
    public void onStart() {
        super.onStart();
        // Проверим, входил ли пользователь в это приложение через Google
         account = GoogleSignIn.getLastSignedInAccount(getContext());
        if (account != null) {
            // Пользователь уже входил, сделаем кнопку недоступной
            disableSign();
            // Обновим почтовый адрес этого пользователя и выведем его на экран
            updateUI();
        }
    }

    // Инициируем регистрацию пользователя
    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // Здесь получим ответ от системы, что пользователь вошёл
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // Когда сюда возвращается Task, результаты аутентификации уже
            // готовы
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    public interface Controller {
        void openMainScreen();
    }

    // Получаем данные пользователя
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);

            // Регистрация прошла успешно
            disableSign();
            updateUI();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure
            // reason. Please refer to the GoogleSignInStatusCodes class
            // reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    // Обновляем данные о пользователе на экране
    private void updateUI() {
        emailView.setText(account.getEmail());
        User.INSTANCE.setNameUser(account.getGivenName());
    }

    // Разрешить аутентификацию и запретить остальные действия
    private void enableSign(){
        buttonSignIn.setEnabled(true);
        continueRun.setEnabled(false);
    }

    // Запретить аутентификацию (уже прошла) и разрешить остальные действия
    private void disableSign(){
        buttonSignIn.setEnabled(false);
        continueRun.setEnabled(true);
    }
}