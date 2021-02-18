package com.britishbroadcast.sqlmealplanner.model.data;

import com.britishbroadcast.sqlmealplanner.util.Day;

public class Meal {

    private int id;
    private String name;
    private Day day;
    private double price;

    public Meal(String name, Day day, double price) {
        this.name = name;
        this.day = day;
        this.price = price;
    }

    public Meal(int id, String name, Day day, double price) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", day=" + day +
                ", price=" + price +
                '}';
    }
}
