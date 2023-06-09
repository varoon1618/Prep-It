package com.example.firstapp;

public class User {
    String name;
    double weight ;
    double height;
    int gender; // female = 0, male =1

    public User(String name,double weight, double height,int gender){
        this.name = name ;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
    }

    public String geName(){
        return name;
    }
}
