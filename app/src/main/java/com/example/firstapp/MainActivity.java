package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Starting point or first screen of the app
 */

public class MainActivity extends AppCompatActivity {

    // api request

    public static ArrayList<Recipe> recipes = new ArrayList<>(); //arraylist to hold all recipes that meet all requirements
    public static ArrayList<User> users = new ArrayList<>(); //arraylist to store all users (should store only one user at a time)
    public static ArrayList<Macro> macros = new ArrayList<>();// araylist to store the macro value of the user(should store only one value at a time)
    private Button nutritionButton ; // button to navigate to screen where user is prompted to add information to calculate ideal macros
    private Button mealPlan;// button to navigate to screen where used is prompted for details to generate meal plan
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);

        //Assigning variables to xml UI
        nutritionButton = findViewById(R.id.nutritionButton);
        mealPlan = findViewById(R.id.mealPlanMain);

        //opens Meal plan screen when the generate meal plan button is clicked
        mealPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMealPlan();
            }
        });

        //opens the "input details" for nutritional analysis screen
        nutritionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNutritionAct();
            }
        });
        //getData();
    }

    public void openMealPlan(){
        if(users.size()!=1){
            Snackbar.make(findViewById(R.id.mainActivity),"Please calculate nutritional details before generating meal plan",Snackbar.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(this, GenerateMealPlan.class);
            startActivity(intent);
        }
    }

    public void openNutritionAct(){
        Intent intent = new Intent(this,NutritionActivity.class);
        startActivity(intent);
    }




}