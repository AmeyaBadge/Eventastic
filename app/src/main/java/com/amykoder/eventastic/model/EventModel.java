package com.amykoder.eventastic.model;

public class EventModel {
    public String title, address, date, time, image;
//    public int image;
    public EventModel(String title, String image, String address, String date, String time){
        this.title = title;
        this.image = image;
        this.address = address;
        this.date = date;
        this.time = time;
    }
}
