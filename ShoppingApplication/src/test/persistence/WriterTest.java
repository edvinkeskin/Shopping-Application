package persistence;

import model.Buyer;
import model.Clothes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {
    private static final String TEST_FILE = "./data/testBuyer.txt";
    private Writer testWriter;
    private Buyer buyer1;
    private ArrayList<Clothes> cart;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        cart = new ArrayList<>();
        Clothes plane = new Clothes("PLANE", "BB", 'M', "TSHIRT", 20.00);
        Clothes violet = new Clothes("VIOLET", "FB", 'F', "TSHIRT", 30.00);
        Clothes jack = new Clothes("JACK", "NoN", 'M', "PANTS", 75.40);
        cart.add(plane);
        cart.add(violet);
        cart.add(jack);

        testWriter = new Writer(new File(TEST_FILE));
        buyer1 = new Buyer("brian", "varuhi12", cart ,212.30);
    }

    @Test
    void testWriteAccounts() {
        // save chequing and savings accounts to file
        testWriter.write(buyer1);
        testWriter.close();

        // re-going over the buyer account just crreated to verify
        try {
            List<Buyer> buyers = Reader.readBuyers(new File(TEST_FILE));
            Buyer buyer = buyers.get(0);
            assertEquals(212.30, buyer1.getSubtotal());
            assertEquals("varuhi12", buyer1.getPassword());
            assertEquals(3, buyer1.getCart().size());
            assertEquals("brian", buyer1.getUsername());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
