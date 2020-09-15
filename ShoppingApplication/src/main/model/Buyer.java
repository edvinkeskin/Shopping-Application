package model;

import exceptions.InvalidInputException;
import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.ArrayList;

// can create buyer objects and contains methods to work with buyer data
public class Buyer implements Saveable {

    private String username;
    private String password;
    private ArrayList<Clothes> cart;
    private double subtotal;


    //REQUIRES: username has a non-zero length
    //EFFECTS: creates buyer object and sets its username and password
    public Buyer(String username, String password, ArrayList<Clothes> cart, Double subtotal) {
        this.username = username;
        this.password = password;
        if (cart == null) {
            this.cart = new ArrayList<>();
        } else {
            this.cart = cart;
        }

        this.subtotal = subtotal;
    }


    //EFFECTS: adds clothing to cart and clothing price to subtotal
    public void addToCart(Clothes c) {
        subtotal += c.getPrice();
        cart.add(c);
    }

    //MODIFIES: this
    //EFFECTS: removes item from cart and deducts price from subtotal
    //         throws exception if invalid selection made
    public void removeItemFromCart(int itemGettingRemoved) throws InvalidInputException {
        if (itemGettingRemoved >= 0 && itemGettingRemoved < getCart().size()) {
            subtotal -= getCart().get(itemGettingRemoved).getPrice();
            getCart().remove(itemGettingRemoved);
        } else {
            throw new InvalidInputException();
        }
    }

    //EFFECTS: returns username
    public String getUsername() {
        return username;
    }

    //EFFECTS: returns username
    public String getPassword() {
        return password;
    }

    //MODIFIES: this
    //EFFECTS: discount on subtotal if its over 1000$
    public void discount() {
        if (subtotal >= 1000) {
            double discount = subtotal / 10;
            subtotal -= discount;
        }
    }

    //EFFECTS: returns subtotal
    public double getSubtotal() {
        discount();
        return subtotal;
    }

    public void setSubtotal(double newSubtotal) {
        subtotal = newSubtotal;
    }


    //EFFECTS: returns cart
    public ArrayList<Clothes> getCart() {
        return cart;
    }

    //EFFECTS saves account username, password, subtotal and cart items
    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(getUsername());
        printWriter.print(Reader.DELIMITER);
        printWriter.print(getPassword());
        printWriter.print(Reader.DELIMITER);
        printWriter.print(getSubtotal());
        for (Clothes c : cart) {
            printWriter.print(Reader.DELIMITER);
            printWriter.print(c.getName());
        }
        printWriter.println();

    }
}