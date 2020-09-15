package model;

import exceptions.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static data.ClothesList.clothesList;
import static org.junit.jupiter.api.Assertions.*;

class BuyerTest {
    Buyer buyer;
    Buyer buyer2;
    Clothes clothes;
    Clothes clothes2;
    ArrayList<Clothes> clothesArrayList;
    ArrayList<Clothes> clothesArrayList2;

    @BeforeEach
    public void runBefore() {
        clothesArrayList = new ArrayList<>();
        clothesArrayList2 = new ArrayList<>();
        buyer = new Buyer("edwin", "windows16", clothesArrayList, 0.0);
        buyer2 = new Buyer("account1", "testing1", clothesArrayList2, 0.0);
        clothes2 = new Clothes("BALL", "NoN", 'M', "TSHIRT", 90.00);
        clothes = new Clothes("PLANE", "BB", 'M', "TSHIRT", 100.00);
    }

    @Test
    public void getUsernameTest() {
        assertEquals(buyer.getUsername(), "edwin");
        assertEquals(buyer2.getUsername(), "account1");
    }

    @Test
    public void cartCreationTest() {
        buyer = new Buyer("xxx","else212222",null,0.0);
        assertEquals(buyer.getCart().size(),0);
        assertNotNull(buyer.getCart());
    }

    @Test
    public void getPasswordTest() {
        assertEquals(buyer.getPassword(), "windows16");
        assertEquals(buyer2.getPassword(), "testing1");
    }


    @Test
    public void discountTest() {
        buyer.setSubtotal(990);
        assertEquals(990, buyer.getSubtotal());
        buyer.addToCart(clothes);
        assertEquals(981, buyer.getSubtotal());
    }

    @Test
    public void getSubtotalTest() {
        assertEquals(buyer2.getSubtotal(), 0);
    }

    @Test
    public void getCartTest() {
        assertEquals(buyer.getCart().size(), 0);
        buyer.addToCart(clothes);
        assertEquals(buyer.getCart().size(), 1);
    }

    @Test
    void removeItemFromCartTest() {
        buyer.addToCart(clothes);
        buyer.addToCart(clothes2);
        assertEquals(buyer.getCart().size(),2);
        try {
            buyer.removeItemFromCart(1);
        } catch (InvalidInputException e) {
            fail("shouldnt thrown");
        }
        try {
            buyer.removeItemFromCart(2);
            fail("should've thrown");
        } catch (InvalidInputException e) {
            //is okay
        }
        try {
            buyer.removeItemFromCart(-1);
            fail("should've thrown");
        } catch (InvalidInputException e) {
            //is okay
        }
        assertEquals(buyer.getCart().size(),1);
        assertEquals(buyer.getSubtotal(),100.0);
    }

    @Test
    void addToCartTest(){
        assertEquals(buyer.getCart().size(),0);
        buyer.addToCart(clothes);
        buyer.getCart().add(clothes);
        assertEquals(buyer.getCart().size(),2);
    }

    @Test
    void setSubtotalTest(){
        assertEquals(buyer.getSubtotal(),0);
        buyer.setSubtotal(234.56);
        assertEquals(buyer.getSubtotal(),234.56);
    }

}