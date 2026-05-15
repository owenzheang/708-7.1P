package com.example.a70871p;

public class Item {

    int id;
    String type;
    String name;
    String phone;
    String description;
    String location;
    String category;
    String image;
    String dateTime;
    double latitude;
    double longitude;

    public Item(int id, String type, String name, String phone, String description,
                String location, String category, String image, String dateTime, double latitude, double longitude) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.location = location;
        this.category = category;
        this.image = image;
        this.dateTime = dateTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
