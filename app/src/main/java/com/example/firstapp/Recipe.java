package com.example.firstapp;

import org.json.JSONArray;

public class Recipe {
    private String name;
    private double yield;
    private JSONArray ingredients;
    private double calories;
    private double carbs;
    private double proteins;
    private double fats;
    private String url;

    public Recipe(String name,double yield,double calories,double carbs,double proteins,double fats,JSONArray ingredients,String url){
        this.name = name;
        this.yield = yield;
        this.calories = calories;
        this.carbs = carbs;
        this.proteins = proteins;
        this.fats = fats;
        this.ingredients = ingredients;
        this.url = url;
    }

    // Getters

    private String getName(){
        return name;
    }

    private double getYield(){
        return yield;
    }

    private double getCalories(){
        return calories;
    }

    private double getCarbs(){
        return carbs;
    }

    private double getProteins(){
        return proteins;
    }

    private double getFats(){
        return fats;
    }

    private void getIngredients(){
        System.out.println("get ingredients implement this later");
    }

    private String getUrl(){
        return url;
    }
}



