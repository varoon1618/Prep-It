package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    String api = "https://api.edamam.com/api/recipes/v2?type=public&app_id=6d289bce&app_key=e3c92bc15780f1669b17ef15df5fc7a1&diet=high-protein&diet=low-carb&cuisineType=Indian&mealType=Dinner&mealType=Lunch&calories=100-300&random=true";
    ArrayList<Recipe> recipes = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    private Button nutritionButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);
        nutritionButton = findViewById(R.id.nutritionButton);
        nutritionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNutritionAct();
            }
        });
        //getData();
    }

    public void openNutritionAct(){
        Intent intent = new Intent(this,NutritionActivity.class);
        startActivity(intent);
    }

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
                                String string_yield = recipe.getJSONObject("recipe").getString("yield");//gets yield in form of string
                                double yield = Math.round(Double.parseDouble(string_yield)); // converts yield to double
                                String string_calories = recipe.getJSONObject("recipe").getString("calories");//gets calories in form of string
                                double calories = Math.round(Double.parseDouble(string_calories)/yield);//calories as double
                                Log.i("yield",String.valueOf(yield));
                                Log.i("calories", String.valueOf(calories));
                                JSONArray ingredients = recipe.getJSONObject("recipe").getJSONArray("ingredientLines");
                                String string_carbs = recipe.getJSONObject("recipe").getJSONObject("totalNutrients").getJSONObject("CHOCDF.net").getString("quantity");
                                double carbs = Math.round(Double.parseDouble(string_carbs)/yield);
                                String string_proteins = recipe.getJSONObject("recipe").getJSONObject("totalNutrients").getJSONObject("PROCNT").getString("quantity");
                                double proteins = Math.round(Double.parseDouble(string_proteins)/yield);
                                String string_fats = recipe.getJSONObject("recipe").getJSONObject("totalNutrients").getJSONObject("FAT").getString("quantity");
                                Log.i("carbs",String.valueOf(carbs));
                                double fats = Math.round(Double.parseDouble(string_fats)/yield);
                                Log.i("carbs", String.valueOf(carbs));
                                Log.i("protein", String.valueOf(proteins));
                                Log.i("fats", String.valueOf(fats));
                                Recipe food_item = new Recipe(name,yield,calories,carbs,proteins,fats,ingredients,url);
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