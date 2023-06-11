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
    private Button homepage; // button goes back to homepage
    private Button calculate; // calculates ideal macros + displays the results screen
    private RadioButton sedentary; // button to identify if the user has chosen "sedentary" option in activity level question
    private RadioButton lightAct;// button to identify if the user has chosen "Light activity" option
    private RadioButton active;// button to identify if the user has chosen "active" option
    private RadioButton veryActive;// button to identify if used has chosen " very active" option
    private RadioButton loseWeight;// button to identify if the user has chosen to lose weight
    private RadioButton maintainWeight;// button to identify if the user has chosen to maintain weight
    private RadioButton gainWeight;// button to identify if the user has chosen to gain weight
    private RadioGroup radioGroup1;// stores all the options to "Level of activity"
    private RadioGroup radioGroup2;// stores all the options to "What is your goal"

    private double idealCalories; // stores ideal daily calorific intake for user
    private HashMap<String,String> hashMap = new HashMap<>(); // hashmap that maps the text of each radio button to the multiplicity factor
    private HashMap<String,String> calorieSurplusMap = new HashMap<>();// hashmap that maps the text of each radio button to the amount of surplus calories

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrion2);

        // intialising variables to their UI elements
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

        //populating hashmap
        //here the text of the "sedentary" button maps to the mutliplicity factor required to calculate
        //when a person is sedentary and so on
        hashMap.put("Sedentary (Little to no exercise)","1.2"); //sedentary
        hashMap.put("Lightly Active (Exercise less than 3 days a week)","1.37");//light activity
        hashMap.put("Active (Exercise 3-5 days a week)","1.55");//active
        hashMap.put("Very Active (Exercise everyday)","1.725");//very active

        // populating hasmap
        // lose weight maps to -350 an a deficit of 350 calories is required to lose weight
        // maintain weight maps to 0 are no deficit or surplus is needed
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

    /**
     * Goes back to homepage
     * Removes a user to ensure there is always one user in the arraylist
     */
    public void returnHomePage(){
        MainActivity.users.remove(0);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    /**
     * Calculates the ideal daily calories and ideal carbs,proteins and fats in g
     */
    public void calculateIdealCalories(){
        // if either of the questions are unanswered the user is prompted to input all values
        if(radioGroup1.getCheckedRadioButtonId()==-1 || radioGroup2.getCheckedRadioButtonId()==-1){
            Snackbar.make(findViewById(R.id.coordLayout2),"Please enter all values",Snackbar.LENGTH_SHORT).show();
        }
        // if all values are inputted the ideal macros are caluclated
        else
        {
            idealCalories = Math.round(findCalories()*findActivityFactor()+findSurplus()); // formula for calculating ideal macros

            //calculates ideal macros
            double idealCarbs = Math.round((0.40*idealCalories)/4);
            double idealProtein = Math.round((0.30*idealCalories)/4);
            double idealFats = Math.round((0.30*idealCalories)/9);

            //creates a new macro object with the ideal macros
            Macro macro = new Macro(idealCalories,idealCarbs,idealProtein,idealFats);
            MainActivity.macros.add(macro);

            //opens the next screen
            Intent intent = new Intent(this, DisplayMacros.class);
            startActivity(intent);
        }
    }

    /**
     * Used to find the base part of ideal calories
     * Basically ideal calories = (some base calories) * (multiplicity factor depending on activity level) + (surplus or defecit)
     * This method calculates and returns those base calories
     * @return base calories
     */
    public double findCalories(){
        User user = MainActivity.users.get(0);
        double calories;
        if(user.getGender()==1){ // user class is designed in such a way that all males are assigned "1" as their gender
            calories = 10*user.getWeight()+6.25*user.getHeight()-5* user.getAge()+5;  // formula for males
        }else{
            calories= 10*user.getWeight()+6.25*user.getHeight()-5* user.getAge()-131; // formula for females
        }
        Log.i("weight ", String.valueOf(user.getWeight()));
        Log.i("height ", String.valueOf(user.getHeight()));
        Log.i("age",String.valueOf(user.getAge()));
        return calories;
    }

    /**
     * Basically ideal calories = (some base calories) * (multiplicity factor depending on activity level) + (surplus or defecit)
     * This method searches the hashset and returns the multiplicity factor corresponding to activity level
     * For example "sedentary" has a multiplicity factor of 1.2
     * @return activity factor
     */
    public double findActivityFactor(){
        // code to find out which radio button is checked

        int id = radioGroup1.getCheckedRadioButtonId();
        RadioButton button = findViewById(id);
        String buttonText = button.getText().toString(); // buttonText stores the text of the selected radiobutton
        String str_factor = hashMap.get(buttonText); // searches for the value in hashset using text of selected button as key
        double factor = Double.parseDouble(str_factor);
        return factor;
    }

    /**
     * Basically ideal calories = (some base calories) * (multiplicity factor depending on activity level) + (surplus or defecit)
     * This method returns a deficit(-350) if user has chosen lose weight
     * returns 0 if user has chosen maintain
     * returns surplus (350) if the user has chosen gain weight
     * @return
     */
    public int findSurplus(){
        int id = radioGroup2.getCheckedRadioButtonId();
        RadioButton button = findViewById(id);
        String buttonText = button.getText().toString();
        String str_surplus = calorieSurplusMap.get(buttonText);
        int surplus = Integer.parseInt(str_surplus);
        return surplus;
    }
}