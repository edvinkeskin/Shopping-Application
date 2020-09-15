package persistence;

import data.ClothesList;
import model.Buyer;
import model.Clothes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// reader can read data from file
public class Reader {
    public static final String DELIMITER = ",";

    private static List<Buyer> buyers;
    private static ClothesList clothesList;
    private static ArrayList<Clothes> cart;

    // EFFECTS: returns a list of buyers parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static List<Buyer> readBuyers(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of buyers with their carts parsed from list of strings
    // where each string contains data for one account, its subtotal and cart items
    private static List<Buyer> parseContent(List<String> fileContent) {
        buyers = new ArrayList<>();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            buyers.add(parseAccount(lineComponents));
        }

        return buyers;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components have arbitrary size  where element 0 represents the
    // username of the customer account to be constructed, element 1 represents
    // the password, elements 2 represents cart subtotal and element 3 and above
    // returns the cart items from the previous log in of same account
    // EFFECTS: returns an customer account constructed from components
    private static Buyer parseAccount(List<String> components) {
        clothesList = new ClothesList();
        String userName = components.get(0);
        String password = components.get(1);
        double subtotal = Double.parseDouble(components.get(2));
        cart = new ArrayList<>();
        for (int i = 3; i < components.size(); i++) {
            cart.add(clothesList.returnClothes(components.get(i)));
        }
        return new Buyer(userName, password, cart, subtotal);
    }

}
