package com.example.firstapp;

public class Macro {
    double idealCalories;
    double idealCarbs;
    double idealProtein;
    double idealFats;

    public Macro(double cal,double carbs,double protein,double fats){
        this.idealCalories = cal;
        this.idealCarbs = carbs;
        this.idealProtein = protein;
        this.idealFats = fats;
    }
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
