package model;

// can create clothes objects and has methods to return values
public class Clothes {
    private String name;
    private String brand;
    private char gender;
    private String type;
    private double price;


    //EFFECTS: creates clothes object, sets name, brand name, gender, category, and price
    public Clothes(String name, String brand, char gender, String category, double price) {
        this.name = name;
        this.brand = brand;
        this.gender = gender;
        this.type = category;
        this.price = price;
    }

    //EFFECTS: returns name of the clothing
    public String getName() {
        return name;
    }

    //EFFECTS: return brand name of the clothing
    public String getBrand() {
        return brand;
    }

    //EFFECTS: return gender of the clothing
    public char getGender() {
        return gender;
    }

    //EFFECTS: return category of the clothing
    public String getType() {
        return type;
    }

    //EFFECTS: return price of the clothing
    public double getPrice() {
        return price;
    }


}
