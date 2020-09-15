package data;


import model.Clothes;

import java.util.ArrayList;

// contains data on clothes as a list and has some methods to work with clothes
public class ClothesList {
    public static ArrayList<Clothes> clothesList = new ArrayList<>();

    //MODIFIES: this
    //EFFECTS: adds Clothes to clothesList
    public static void clothesList() {

        clothesList.add(new Clothes("SEAL", "NoN", 'M', "TSHIRT", 20.00));
        clothesList.add(new Clothes("BALL", "NoN", 'M', "TSHIRT", 25.00));
        clothesList.add(new Clothes("PLANE", "BB", 'M', "TSHIRT", 20.00));
        clothesList.add(new Clothes("ROSE", "BB", 'F', "TSHIRT", 22.50));
        clothesList.add(new Clothes("VIOLET", "FB", 'F', "TSHIRT", 30.00));

        clothesList.add(new Clothes("BEAR", "BB", 'M', "HOODIE", 55.0));
        clothesList.add(new Clothes("MARI", "BB", 'F', "HOODIE", 60.0));
        clothesList.add(new Clothes("CATCH", "NoN", 'M', "HOODIE", 48.90));
        clothesList.add(new Clothes("PINKIE", "FB", 'F', "HOODIE", 45.75));
        clothesList.add(new Clothes("COPY", "NoN", 'M', "HOODIE", 58.90));

        clothesList.add(new Clothes("CORDER", "BB", 'M', "PANTS", 60.0));
        clothesList.add(new Clothes("JACK", "NoN", 'M', "PANTS", 75.40));
        clothesList.add(new Clothes("MELANIE", "NoN", 'F', "PANTS", 80.50));
        clothesList.add(new Clothes("COLLIN", "BB", 'M', "PANTS", 65.90));
        clothesList.add(new Clothes("VALENTINE", "FB", 'F', "PANTS", 79.90));

    }

    public int listSize() {
        return clothesList.size();
    }

    // EFFECTS: returns a clothing if its the clothings name is inputted
    public Clothes returnClothes(String name) {
        for (Clothes c : clothesList) {
            if (name.equals(c.getName())) {
                return c;
            }
        }
        return null;
    }

}
