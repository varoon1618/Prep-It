package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * This activity displays ideal calories and macros after they are calculated
 */

public class DisplayMacros extends AppCompatActivity {
    private Button homepage; // return to homepage
    private TextView dailyCalIntake; // ideal daily calories

    private Button generateMealPlan; // directs user to generate meal plan screen

    //ideal macros
    private TextView dailyCarbs;
    private TextView dailyProteins;
    private TextView dailyFats;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_macros);

        //linking variables to xml elements
        homepage = findViewById(R.id.macroToHome);
        dailyCalIntake = findViewById(R.id.dailyCalIntake);
        dailyCarbs = findViewById(R.id.dailyCarbs);
        dailyProteins = findViewById(R.id.dailyProteins);
        dailyFats = findViewById(R.id.dailyFats);
        generateMealPlan = findViewById(R.id.generateMp);
        generateMealPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMealPlanScreen();
            }
        });
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnHomePage(); // back to homepage
            }
        });

        // takes the calculated macros and displays them on screen
        displayMacros();
    }

    /**
     * Navigates user to generate meal plan after the ideal macros have been calculated
     */
    public void openMealPlanScreen(){
        Intent intent = new Intent(this, GenerateMealPlan.class);
        startActivity(intent);
    }
    public void returnHomePage(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    /**
     * Display macros in UI after they are calculated
     * These values are obtained from the instance of a Macro object created and
     * stored after the user inputs all the required values.
     */
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