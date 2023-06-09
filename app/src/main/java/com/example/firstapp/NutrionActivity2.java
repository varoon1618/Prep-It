package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.snackbar.Snackbar;

public class NutrionActivity2 extends AppCompatActivity {
    private Button homepage;
    private Button calculate;
    private RadioButton sedentary;
    private RadioButton lightAct;
    private RadioButton active;
    private RadioButton veryActive;
    private RadioButton loseWeight;
    private RadioButton maintainWeight;
    private RadioButton gainWeight;

    private RadioGroup radioGroup1;

    private RadioGroup radioGroup2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrion2);
        homepage = findViewById(R.id.homepage);
        calculate = findViewById(R.id.calculateMacros);
        sedentary = findViewById(R.id.sedentary);
        lightAct = findViewById(R.id.lightAct);
        active = findViewById(R.id.active);
        veryActive = findViewById(R.id.veryAct);
        loseWeight = findViewById(R.id.loseWeight);
        maintainWeight = findViewById(R.id.maintain);
        gainWeight = findViewById(R.id.gainWeight);
        radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);

        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnHomePage();
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateMacros();
            }
        });

    }

    public void returnHomePage(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void calculateMacros(){
        if(radioGroup1.getCheckedRadioButtonId()==-1 || radioGroup2.getCheckedRadioButtonId()==-1){
            Snackbar.make(findViewById(R.id.coordLayout2),"Please enter all values",Snackbar.LENGTH_SHORT).show();
        }
    }
}