package com.example.parkingapp;

public class Spot {
    String key;
    Boolean value;

    public Spot(String key, Boolean value){
        this.key = key;
        this.value = value;
    }

    public String getKey(){
        return this.key;
    }

    public Boolean getValue(){
        return this.value;
    }
}
