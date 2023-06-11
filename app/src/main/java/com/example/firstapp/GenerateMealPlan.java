package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashMap;

public class GenerateMealPlan extends AppCompatActivity {

    private Spinner bfastCuisine;
    private Spinner lunchCuisine;
    private Spinner dinnerCuisine;
    private Spinner preferences;
    private Button showMeals;
    private Button homepage;
    private EditText allergen;
    private HashMap<String,String> cuisineMap = new HashMap<>();
    private HashMap<String,String> preferenceMap = new HashMap<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_meal_plan);

        //hashmaps
        populateCuisines();
        populatePreferences();

        //connecting UI to xml
        bfastCuisine = findViewById(R.id.breakfastCuisines);
        lunchCuisine = findViewById(R.id.lunchCuisine);
        dinnerCuisine = findViewById(R.id.dinnerCuisine);
        preferences = findViewById(R.id.otherPref);
        allergen = findViewById(R.id.allergen);
        homepage = findViewById(R.id.button3);
        showMeals = findViewById(R.id.button2);


        //method to set dropdown menu in UI
        setCuisineAdapter(bfastCuisine);
        setCuisineAdapter(lunchCuisine);
        setCuisineAdapter(dinnerCuisine);
        setPreferenceAdapter(preferences);

        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePage();
            }
        });

        showMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    /**
     * Goes back to homepage
     */
    public void homePage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Uses an array of all possible cuisines from resources and sets a drop down box in UI
     * for the spinner passed in parameter
     * @param spinner the drop down box is set for this spinner
     */
    public void setCuisineAdapter(Spinner spinner){
        String[] cuisines = getResources().getStringArray(R.array.cuisines);
        ArrayAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,cuisines);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     Uses an array of all possible preferences from resources and sets a drop down box in UI
     * for the spinner passed in parameter
     * @param spinner the drop down box is set for this spinner
     */
    public void setPreferenceAdapter(Spinner spinner){
        String[] cuisines = getResources().getStringArray(R.array.preferences);
        ArrayAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,cuisines);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void generateAPI(String mealType) {
        String api = "https://api.edamam.com/api/recipes/v2?type=public&app_id=6d289bce&app_key=e3c92bc15780f1669b17ef15df5fc7a1";
        String health = preferenceMap.get(preferences.getSelectedItem().toString());
        api = api+health;
        if(mealType.equals("breakfast")){
            String cuisineType = cuisineMap.get(bfastCuisine.getSelectedItem().toString());
            api = api+cuisineType;
        }else if(mealType.equals("lunch")){
            String cuisineType = cuisineMap.get(lunchCuisine.getSelectedItem().toString());
            api = api+cuisineType;
        }else{
            String cuisineType = cuisineMap.get(dinnerCuisine.getSelectedItem().toString());
            api = api+cuisineType;
        }

    }

    public void populateCuisines(){
        cuisineMap.put("Any Cuisine","");
        cuisineMap.put("American","&cuisineType=American");
        cuisineMap.put("Asian","&cuisineType=Asian");
        cuisineMap.put("British","&cuisineType=British");
        cuisineMap.put("Chinese","&cuisineType=Chinese");
        cuisineMap.put("Central European","&cuisineType=Central%20Europe");
        cuisineMap.put("French","&cuisineType=French");
        cuisineMap.put("Indian","&cuisineType=Indian");
        cuisineMap.put("Italian","&cuisineType=Italian");
        cuisineMap.put("Japanese","&cuisineType=Japanese");
        cuisineMap.put("Mexican","&cuisineType=Mexican");
        cuisineMap.put("South American","&cuisineType=South%20American");
    }

    public void populatePreferences(){
        preferenceMap.put("No preferences","");
        preferenceMap.put("Dairy free","&health=dairy-free");
        preferenceMap.put("Egg free","&health=egg-free");
        preferenceMap.put("Fish free","&health=fish-free");
        preferenceMap.put("Vegetarian","&health=vegetarian");
        preferenceMap.put("Vegan","&health=vegan");
        preferenceMap.put("Kosher","&health=kosher");
        preferenceMap.put("Pork free","&health=pork-free");
        preferenceMap.put("Keto","&health=keto");
    }

}