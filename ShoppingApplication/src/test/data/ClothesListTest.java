package data;
import static data.ClothesList.clothesList;

import model.Clothes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ClothesListTest {
    Clothes clothes;
    Clothes clothes2;
    ClothesList cList;
    @BeforeEach
    void runBefore(){
        clothesList();
        cList = new ClothesList();
        clothes = new Clothes("herman","FB",'M',"jacket",35.30);
        clothes2 = new Clothes("ariana", "BB", 'F',"shirt",39.40);

    }
    @Test
    void clothesListTest(){
        assertEquals(clothesList.size(),15);
        clothesList.add(clothes);
        clothesList.add(clothes2);
        assertEquals(cList.listSize(),17);
    }

    @Test
    void returnClothesTest(){
        assertEquals(null,cList.returnClothes("hector"));
    }

}
