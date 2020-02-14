package com.example.parkingapp;

public class Spot {
    String key;
    Boolean value;
    String by;

    public Spot(String key, Boolean value){
        this.key = key;
        this.value = value;
        this.by = "";
    }

    public Spot(String key, Boolean value, String by){
        this.key = key;
        this.value = value;
        this.by = by;
    }


    public String getKey(){
        return this.key;
    }

    public Boolean getValue(){
        return this.value;
    }

    public String getBy() {return this.by;}
}
