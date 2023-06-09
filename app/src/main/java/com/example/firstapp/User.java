package com.example.firstapp;

public class User {
    String name;
    double weight ;
    double height;
    int age;
    int gender; // female = 0, male =1

    public User(String name,double weight, double height,int age,int gender){
        this.name = name ;
        this.weight = weight;
        this.height = height;
        this.age=age;
        this.gender = gender;
    }

    public String geName(){
        return name;
    }
    public double getWeight(){
        return weight;
    }
    public double getHeight(){
        return height;
    }

    public int getAge(){
        return age;
    }
    public int getGender(){
       return gender;
    }
}

