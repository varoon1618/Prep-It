package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public String generateAPI(String mealType) {
        String carbs="";
        String fats="";
        String protein="";
        String api = "https://api.edamam.com/api/recipes/v2?type=public&app_id=6d289bce&app_key=e3c92bc15780f1669b17ef15df5fc7a1";
        String health = preferenceMap.get(preferences.getSelectedItem().toString());
        api = api+health;
        String cuisineType = cuisineMap.get(bfastCuisine.getSelectedItem().toString());
        api = api+cuisineType;
        if(mealType.equals("breakfast")){
            api = api+"&mealType=Breakfast";
            int idealCalories = Math.toIntExact(Math.round(0.3 * MainActivity.macros.get(0).getIdealCalories()));
            String calories = "&calories="+String.valueOf(idealCalories-30)+"-"+String.valueOf(idealCalories+30);
            api = api+calories;
            int idealCarbs = Math.toIntExact(Math.round(0.3 * MainActivity.macros.get(0).getIdealCarbs()));
            carbs = "&nutrients%5BCHOCDF.net%5D="+String.valueOf(idealCarbs-30)+"-"+String.valueOf(idealCarbs+30);
            int idealFats = Math.toIntExact(Math.round(0.3 * MainActivity.macros.get(0).getIdealFats()));
            fats = "&nutrients%5BFAT%5D="+String.valueOf(idealFats-30)+"-"+String.valueOf(idealFats+30);
            int idealProt = Math.toIntExact(Math.round(0.3 * MainActivity.macros.get(0).getIdealProtein()));
            protein = "&nutrients%5BPROCNT%5D="+String.valueOf(idealProt-30)+"-"+String.valueOf(idealProt+30);
        }else if(mealType.equals("lunch")){
            api = api+"&mealType=Dinner&mealType=Lunch";
            int idealCalories = Math.toIntExact(Math.round(0.4 * MainActivity.macros.get(0).getIdealCalories()));
            String calories = "&calories="+String.valueOf(idealCalories-10)+"-"+String.valueOf(idealCalories+10);
            api = api+calories;
            int idealCarbs = Math.toIntExact(Math.round(0.4 * MainActivity.macros.get(0).getIdealCarbs()));
            carbs = "&nutrients%5BCHOCDF.net%5D="+String.valueOf(idealCarbs-30)+"-"+String.valueOf(idealCarbs+30);
            int idealFats = Math.toIntExact(Math.round(0.4 * MainActivity.macros.get(0).getIdealFats()));
            fats = "&nutrients%5BFAT%5D="+String.valueOf(idealFats-30)+"-"+String.valueOf(idealFats+30);
            int idealProt = Math.toIntExact(Math.round(0.4 * MainActivity.macros.get(0).getIdealProtein()));
            protein = "&nutrients%5BPROCNT%5D="+String.valueOf(idealProt-30)+"-"+String.valueOf(idealProt+30);
        }else{
            api = api+"&mealType=Dinner&mealType=Lunch";
            int idealCalories = Math.toIntExact(Math.round(0.3 * MainActivity.macros.get(0).getIdealCalories()));
            String calories = "&calories="+String.valueOf(idealCalories-10)+"-"+String.valueOf(idealCalories+10);
            api = api+calories;
            int idealCarbs = Math.toIntExact(Math.round(0.3 * MainActivity.macros.get(0).getIdealCarbs()));
            carbs = "&nutrients%5BCHOCDF.net%5D="+String.valueOf(idealCarbs-30)+"-"+String.valueOf(idealCarbs+30);
            int idealFats = Math.toIntExact(Math.round(0.3 * MainActivity.macros.get(0).getIdealFats()));
            fats = "&nutrients%5BFAT%5D="+String.valueOf(idealFats-30)+"-"+String.valueOf(idealFats+30);
            int idealProt = Math.toIntExact(Math.round(0.3 * MainActivity.macros.get(0).getIdealProtein()));
            protein = "&nutrients%5BPROCNT%5D="+String.valueOf(idealProt-30)+"-"+String.valueOf(idealProt+30);
        }
        if(allergen.getText().toString().trim().length()>0){
            String excluded = "&excluded="+allergen.getText().toString();
            api = api+excluded;
        }
        api = api+"&random=true"+carbs+fats+protein;
        Log.i("api",api);
        return api;
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

    /**
     * Method to first call an api inputted and then parse the json api call and retrieve
     * the details about all recipes that meet the required conditions (
     * like cuisine type , calories , protein etc)
     * Method also creates a recipe object and adds it to an arrayList recipes
     */

    public void getData(String api){
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
                                MainActivity.recipes.add(food_item);
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