package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

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

    private double idealCalories;
    private HashMap<String,String> hashMap = new HashMap<>();
    private HashMap<String,String> calorieSurplusMap = new HashMap<>();

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

        hashMap.put("Sedentary (Little to no exercise)","1.2"); //sedentary
        hashMap.put("Lightly Active (Exercise less than 3 days a week)","1.37");//light activity
        hashMap.put("Active (Exercise 3-5 days a week)","1.55");//active
        hashMap.put("Very Active (Exercise everyday)","1.725");//very active

        calorieSurplusMap.put("Lose weight(0.5kg per week)","-350");
        calorieSurplusMap.put("Maintain current weight","0");
        calorieSurplusMap.put("Gain Weight(0.5kg per week)","350");


        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnHomePage();

            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateIdealCalories();
            }
        });

    }

    public void returnHomePage(){
        int id = sedentary.getId();
        Log.i("sedentary", String.valueOf(id));
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void calculateIdealCalories(){
        if(radioGroup1.getCheckedRadioButtonId()==-1 || radioGroup2.getCheckedRadioButtonId()==-1){
            Snackbar.make(findViewById(R.id.coordLayout2),"Please enter all values",Snackbar.LENGTH_SHORT).show();
        }else {
            idealCalories = findCalories()*findActivityFactor()+findSurplus();
            double idealCarbs = Math.round((0.45*idealCalories)/4);
            double idealProtein = Math.round((0.35*idealCalories)/4);
            double idealFats = Math.round((0.20*idealCalories)/9);
            Macro macro = new Macro(idealCalories,idealCarbs,idealProtein,idealFats);
            MainActivity.macros.add(macro);
            Intent intent = new Intent(this, DisplayMacros.class);
            startActivity(intent);
        }
    }

    public double findCalories(){
        User user = MainActivity.users.get(0);
        double calories;
        if(user.getGender()==1){
            calories = 10*user.getWeight()+6.25*user.getHeight()-5* user.getAge()+5;
        }else{
            calories= 10*user.getWeight()+6.25*user.getHeight()-5* user.getAge()-131;
        }
        return calories;
    }

    public double findActivityFactor(){
        int id = radioGroup1.getCheckedRadioButtonId();
        RadioButton button = findViewById(id);
        String buttonText = button.getText().toString();
        String str_factor = hashMap.get(buttonText);
        double factor = Double.parseDouble(str_factor);
        return factor;
    }

    public int findSurplus(){
        int id = radioGroup2.getCheckedRadioButtonId();
        RadioButton button = findViewById(id);
        String buttonText = button.getText().toString();
        String str_surplus = calorieSurplusMap.get(buttonText);
        int surplus = Integer.parseInt(str_surplus);
        return surplus;
    }
}