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

    public Item(int id, String type, String name, String phone, String description,
                String location, String category, String image, String dateTime) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.location = location;
        this.category = category;
        this.image = image;
        this.dateTime = dateTime;
    }
}
