package com.example.tuablo;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class FlashcardsActivity extends AppCompatActivity {
    private final String[][] flashcardPairs = {
            {"Hola", "Hello"},
            {"Adiós", "Goodbye"},
            {"Sí", "Yes"},
            {"No", "No"},
            {"Por favor", "Please"},
            {"Gracias", "Thank you"},
            {"De nada", "You're welcome"},
            {"¿Cómo estás?", "How are you?"},
            {"Bien", "Fine / Good"},
            {"Mal", "Bad"},
            {"Amigo/Amiga", "Friend"},
            {"Familia", "Family"},
            {"Comida", "Food"},
            {"Agua", "Water"},
            {"Casa", "House / Home"},
            {"Libro", "Book"},
            {"Escuela", "School"},
            {"Perro", "Dog"},
            {"Gato", "Cat"},
            {"Rojo", "Red"},
            {"Azul", "Blue"},
            {"Verde", "Green"},
            {"Blanco", "White"},
            {"Negro", "Black"},
            {"Uno", "One"},
            {"Dos", "Two"},
            {"Tres", "Three"},
            {"Cuatro", "Four"},
            {"Cinco", "Five"},
            {"Manzana", "Apple"},
            {"Plátano", "Banana"},
            {"Mesa", "Table"},
            {"Silla", "Chair"},
            {"Playa", "Beach"},
            {"Sol", "Sun"},
            {"Luna", "Moon"},
            {"Estrella", "Star"},
            {"Tiempo", "Weather"},
            {"Hoy", "Today"},
            {"Mañana", "Tomorrow"},
            {"Ahora", "Now"},
            {"Siempre", "Always"},
            {"Nunca", "Never"},
            {"Hombre", "Man"},
            {"Mujer", "Woman"},
            {"Niño/Niña", "Boy/Girl"},
            {"Padre", "Father"},
            {"Madre", "Mother"},
            {"Hermano/Hermana", "Brother/Sister"}
    };

    private int cardPos = 0;// init to first flash card

    private boolean cardFace = false; // false for Spanish, true for English

    private CardView cardView;
    private Button nextButton;
    private Button backButton;
    private Button homeButton;

    private TextView flashCardContent;

    public FlashcardsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flashcards);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupViews();

        flashCardContent.setText(flashcardPairs[0][0]); // initialization

        cardView.setOnClickListener(v -> {
            if (cardFace == true) {
                cardView.setCardBackgroundColor(Color.BLACK);
                flashCardContent.setTextColor(Color.WHITE);
                flashCardContent.setText(flashcardPairs[cardPos][1]);
                cardFace = false;
            }
            else {
                cardView.setCardBackgroundColor(Color.WHITE);
                flashCardContent.setTextColor(Color.BLACK);
                flashCardContent.setText(flashcardPairs[cardPos][0]);
                cardFace = true;
            }

        });

        nextButton.setOnClickListener(v -> {
            if (cardPos < flashcardPairs.length - 1)
                ++cardPos;
            else
                cardPos = 0;
            flashCardContent.setText(flashcardPairs[cardPos][0]);
        });

        backButton.setOnClickListener(v -> {
            if (cardPos > 0)
                --cardPos;
            else
                cardPos = flashcardPairs.length - 1;
            flashCardContent.setText(flashcardPairs[cardPos][0]);
        });

        homeButton.setOnClickListener(v -> startActivity(new Intent(FlashcardsActivity.this, MainActivity.class)));

    }

    private void setupViews() {
        cardView = findViewById(R.id.cardView);
        backButton = findViewById(R.id.backButton);
        nextButton = findViewById(R.id.nextButton);
        homeButton = findViewById(R.id.hoemButton);
        flashCardContent = findViewById(R.id.flashcardContent);
    }
}