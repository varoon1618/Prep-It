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
    String api = "https://api.edamam.com/api/recipes/v2?type=public&app_id=6d289bce&app_key=e3c92bc15780f1669b17ef15df5fc7a1&diet=high-protein&diet=low-carb&cuisineType=Indian&mealType=Dinner&mealType=Lunch&calories=100-300&random=true";
    ArrayList<Recipe> recipes = new ArrayList<>(); //arraylist to hold all recipes that meet all requirements
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

    /**
     * Method to parse the json api call and retrieve the details about all recipes that
     * meet the required conditions (like cuisine type , calories , protein etc)
     */
    public void getData(){
        RequestQueue queue = Volley.newRequestQueue(this);

        //Calling api request
        StringRequest stringRequest = new StringRequest(Request.Method.GET,api,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("api","worked "+response.toString());
                        try {
                            JSONObject object = new JSONObject(response.toString());
                            JSONArray hits = object.getJSONArray("hits"); //json array of all recipes
                            for(int i=0;i<hits.length();i++){
                                JSONObject recipe = new JSONObject(hits.get(i).toString()); // first recipe from the above array
                                String name = recipe.getJSONObject("recipe").getString("label"); //gets name of recipe
                                Log.i("label",name);
                                String url = recipe.getJSONObject("recipe").getString("url");//gets url for recipe
                                String string_yield = recipe.getJSONObject("recipe").getString("yield");//gets yield in form of string (yield here is the number of servings the recipe makes)
                                double yield = Math.round(Double.parseDouble(string_yield)); // converts yield to double
                                String string_calories = recipe.getJSONObject("recipe").getString("calories");//gets calories in form of string
                                double calories = Math.round(Double.parseDouble(string_calories)/yield);//calories as double
                                Log.i("yield",String.valueOf(yield));
                                Log.i("calories", String.valueOf(calories));
                                JSONArray ingredients = recipe.getJSONObject("recipe").getJSONArray("ingredientLines");// gets json array of all ingredients
                                String string_carbs = recipe.getJSONObject("recipe").getJSONObject("totalNutrients").getJSONObject("CHOCDF.net").getString("quantity");// gets the carbs in that recipe as a string
                                double carbs = Math.round(Double.parseDouble(string_carbs)/yield); // divides carbs by yield to get total carbs in one serving
                                String string_proteins = recipe.getJSONObject("recipe").getJSONObject("totalNutrients").getJSONObject("PROCNT").getString("quantity"); // gets proteins in that recipe as str
                                double proteins = Math.round(Double.parseDouble(string_proteins)/yield); // again divides protein by yeild as above
                                String string_fats = recipe.getJSONObject("recipe").getJSONObject("totalNutrients").getJSONObject("FAT").getString("quantity");// same thing with fats (See above)
                                Log.i("carbs",String.valueOf(carbs));
                                double fats = Math.round(Double.parseDouble(string_fats)/yield);
                                Log.i("carbs", String.valueOf(carbs));
                                Log.i("protein", String.valueOf(proteins));
                                Log.i("fats", String.valueOf(fats));
                                Recipe food_item = new Recipe(name,yield,calories,carbs,proteins,fats,ingredients,url); // creates a new recipe object and adds them to an arraylist of recipes
                                recipes.add(food_item);
                                Log.i("arraylist", String.valueOf(recipes.size()));
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api","error: "+error.getLocalizedMessage());
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}