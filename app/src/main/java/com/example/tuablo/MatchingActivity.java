package com.example.tuablo;

import static java.util.Collections.shuffle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class MatchingActivity extends AppCompatActivity {

    private static final int PREF_CURR_PAGE = 0;
    private String[][] englishWords = {
            {"Apple", "Banana", "Orange", "Grapes"},
            {"Cat", "Dog", "House", "Car"},
            {"Book", "Tree", "Chair", "Water"},
            {"Sun", "Moon", "Star", "Sky"},
            {"Bird", "Fish", "Flower", "Earth"},
            {"Mother", "Father", "Sister", "Brother"},
            {"Red", "Blue", "Green", "Yellow"},
            {"One", "Two", "Three", "Four"},
            {"Eat", "Drink", "Sleep", "Run"},
            {"Happy", "Sad", "Angry", "Excited"}
    };
    private String[][] spanishWords = {
            {"Manzana", "Plátano", "Naranja", "Uvas"} ,
            {"Gato", "Perro", "Casa", "Coche"},
            {"Libro", "Árbol", "Silla", "Agua"},
            {"Sol", "Luna", "Estrella", "Cielo"},
            {"Pájaro", "Pez", "Flor", "Tierra"},
            {"Madre", "Padre", "Hermana", "Hermano"},
            {"Rojo", "Azul", "Verde", "Amarillo"},
            {"Uno", "Dos", "Tres", "Cuatro"},
            {"Comer", "Beber", "Dormir", "Correr"},
            {"Feliz", "Triste", "Enojado", "Emocionado"}
    };

    private HashMap<String, String> wordMap = new HashMap();

    private ListView leftSideListView;

    private ListView rightSideListView;
    private Button homeButton;

    private Button resetButton;
    private Button nextButton;

    private String currEngWord = "";

    private String currSpanWord = "";

    private boolean leftSelected = false;

    private boolean rightSelected = false;

    private View leftItem;
    private View rightItem;

    private int page = 0;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_matching);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences(String.valueOf(PREF_CURR_PAGE), MODE_PRIVATE);

        page = sharedPreferences.getInt(String.valueOf(PREF_CURR_PAGE), 0);

        setupViews();
        fillMap();
        populateViews();

        leftSideListView.setOnItemClickListener((parent, view, pos, id) -> {
            currEngWord = englishWords[page][pos];
            leftSelected = true;
            leftItem = view;
            view.setBackgroundColor(Color.GRAY);
            if (leftSelected && rightSelected) {
                boolean sameWord = checkSelected();

                if (sameWord) {
                    leftItem.setBackgroundColor(Color.rgb(52, 203, 81));
                    rightItem.setBackgroundColor(Color.rgb(52, 203, 81));

                }
                else {
                    leftItem.setBackgroundColor(Color.TRANSPARENT);
                    rightItem.setBackgroundColor(Color.TRANSPARENT);
                }
                leftSelected = false;
                rightSelected = false;
            }
        });

        rightSideListView.setOnItemClickListener((parent, view, pos, id) -> {
            currSpanWord = spanishWords[page][pos];
            rightSelected = true;
            rightItem = view;
            view.setBackgroundColor(Color.GRAY);

            if (leftSelected && rightSelected) {
                boolean sameWord = checkSelected();

                if (sameWord) {
                    leftItem.setBackgroundColor(Color.rgb(52, 203, 81));
                    rightItem.setBackgroundColor(Color.rgb(52, 203, 81));

                }
                else {
                    leftItem.setBackgroundColor(Color.TRANSPARENT);
                    rightItem.setBackgroundColor(Color.TRANSPARENT);
                }
                leftSelected = false;
                rightSelected = false;
            }
        });



        homeButton.setOnClickListener(v -> startActivity(new Intent(MatchingActivity.this, MainActivity.class)));

        resetButton.setOnClickListener(v -> recreate());

        nextButton.setOnClickListener(v -> {
            if (page < englishWords.length)
                ++page;
            else page = 0;

            trackProgress(page);
            wordMap = new HashMap<>();
            fillMap();
            populateViews();
        });
    }

    private void setupViews() {
        leftSideListView = findViewById(R.id.leftSideListView);
        rightSideListView = findViewById(R.id.rightSideListView);
        homeButton = findViewById(R.id.hoemButton);
        resetButton = findViewById(R.id.resetButton);
        nextButton = findViewById(R.id.nextSetButton);
    }

    private void populateViews() {
        Collections.shuffle(Arrays.asList(englishWords[page]));
        ArrayAdapter<String> leftAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, englishWords[page]);
        leftSideListView.setAdapter(leftAdapter);

        Collections.shuffle(Arrays.asList(spanishWords[page]));
        ArrayAdapter<String> rightAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, spanishWords[page]);
        rightSideListView.setAdapter(rightAdapter);
    }

    private void fillMap() {
        for (int i = 0; i < englishWords[page].length; i++) {
            wordMap.put(englishWords[page][i], spanishWords[page][i]);
        }
    }

    private boolean checkSelected() {
        if (currSpanWord.equals(wordMap.get(currEngWord)))
            return true;
        else return false;
    }

    private void trackProgress(int page) { //tracks progress of page completions
        SharedPreferences.Editor editPrefs = sharedPreferences.edit();
        editPrefs.putInt(String.valueOf(PREF_CURR_PAGE), page);
        editPrefs.apply();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        sharedPreferences.edit().clear().apply();
    }
}