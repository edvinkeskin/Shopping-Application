package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClothesTest {
    Clothes clothes;
    Clothes clothes2;

    @BeforeEach
    public void runBefore(){
        clothes = new Clothes("herman","FB",'M',"jacket",35.30);
        clothes2 = new Clothes("ariana", "BB", 'F',"shirt",39.40);
    }

    @Test
    public void getNameTest(){
        assertEquals(clothes.getName(),"herman");
        assertEquals(clothes2.getName(),"ariana");
    }
    @Test
    public void getBrandTest(){
        assertEquals(clothes.getBrand(),"FB");
        assertEquals(clothes2.getBrand(),"BB");
    }
    @Test
    public void getGenderTest(){
        assertEquals(clothes.getGender(),'M');
        assertEquals(clothes2.getGender(),'F');
    }
    @Test
    public void getTypeTest(){
        assertEquals(clothes.getType(),"jacket");
        assertEquals(clothes2.getType(),"shirt");
    }
    @Test
    public void getPriceTest(){
        assertEquals(clothes.getPrice(),35.30);
        assertEquals(clothes2.getPrice(),39.40);
    }
}
