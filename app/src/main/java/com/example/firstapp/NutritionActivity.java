package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.material.snackbar.Snackbar;

public class NutritionActivity extends AppCompatActivity {

    private Button home_button;
    private EditText nameText;
    private EditText weightText;
    private EditText heightText;
    private EditText ageText;
    private RadioButton maleOption;
    private RadioButton femaleOption;
    private Button continueButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);
        home_button = findViewById(R.id.home_button);
        nameText = findViewById(R.id.name);
        weightText = findViewById(R.id.weight);
        heightText = findViewById(R.id.height);
        ageText = findViewById(R.id.age);
        maleOption = findViewById(R.id.male);
        femaleOption = findViewById(R.id.female);
        continueButton = findViewById(R.id.continueButton);

        //Return back to homepage
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backtoHomePage();
            }
        });

        //Continue the process of entering details
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMoreDetails();
            }
        });
    }

    public void backtoHomePage(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void addMoreDetails(){
        if(nameText.getText().toString().equals("")||heightText.getText().toString().equals("")||weightText.getText().toString().equals("")||(maleOption.isChecked()==false && femaleOption.isChecked()==false)){
            Snackbar.make(findViewById(R.id.coordLayout2),"Please enter all values",Snackbar.LENGTH_SHORT).show();
        }else{
            MainActivity.users.add(createUser());
            Intent intent = new Intent(this,NutrionActivity2.class);
            startActivity(intent);
            Log.i("User",MainActivity.users.get(0).geName());
        }

    }
    public User createUser(){
        int gender;
        if(maleOption.isChecked()){
            gender = 1;
        }else{
            gender = 2;
        }
        String name = nameText.getText().toString();
        double weight = Double.parseDouble(weightText.getText().toString());
        double height = Double.parseDouble(heightText.getText().toString());
        int age = Integer.parseInt(ageText.getText().toString());
        User user = new User(name,weight,height,age,gender);
        return user;
    }
}