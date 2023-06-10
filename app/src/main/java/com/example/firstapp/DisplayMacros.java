package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayMacros extends AppCompatActivity {

    private Button homepage;
    private TextView dailyCalIntake;
    private TextView dailyCarbs;
    private TextView dailyProteins;
    private TextView dailyFats;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_macros);

        homepage = findViewById(R.id.macroToHome);
        dailyCalIntake = findViewById(R.id.dailyCalIntake);
        dailyCarbs = findViewById(R.id.dailyCarbs);
        dailyProteins = findViewById(R.id.dailyProteins);
        dailyFats = findViewById(R.id.dailyFats);

        displayMacros();

        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnHomePage();
            }
        });
    }
    public void returnHomePage(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void displayMacros(){
        Macro macro = MainActivity.macros.get(0);
        String idealCal = String.valueOf(macro.getIdealCalories());
        String idealCarbs = String.valueOf(macro.getIdealCarbs());
        String idealProteins = String.valueOf(macro.getIdealProtein());
        String idealFats = String.valueOf(macro.getIdealFats());
        dailyCalIntake.setText(idealCal+" cal");
        dailyCarbs.setText(idealCarbs+"g");
        dailyProteins.setText(idealProteins+"g");
        dailyFats.setText(idealFats+"g");
    }
}