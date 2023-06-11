package com.example.firstapp;

/**
 * Macro class is used to hold all the ideal values for a user
 */
public class Macro {
    double idealCalories; // daily calorific intake
    double idealCarbs; // daily carbs in g
    double idealProtein; // daily protein in g
    double idealFats; // daily fats in g

    public Macro(double cal,double carbs,double protein,double fats){
        this.idealCalories = cal;
        this.idealCarbs = carbs;
        this.idealProtein = protein;
        this.idealFats = fats;
    }

    // getters

    public double getIdealCalories() {
        return idealCalories;
    }

    public double getIdealCarbs() {
        return idealCarbs;
    }

    public double getIdealProtein() {
        return idealProtein;
    }

    public double getIdealFats() {
        return idealFats;
    }
}
