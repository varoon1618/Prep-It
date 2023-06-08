package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String api = "https://api.edamam.com/api/recipes/v2/by-uri?type=public&uri=http%3A%2F%2Fwww.edamam.com%2Fontologies%2Fedamam.owl%23recipe_cf665dda5068a07d26ccfda829588474&app_id=6d289bce&app_key=%20e3c92bc15780f1669b17ef15df5fc7a1%09";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();


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
                            JSONObject recipe = new JSONObject(hits.get(0).toString()); // first recipe from the above array
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