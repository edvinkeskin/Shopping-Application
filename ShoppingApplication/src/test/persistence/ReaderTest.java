package persistence;

import model.Buyer;
import model.Clothes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {

    @BeforeEach
    void runBefore(){
        Reader reader = new Reader();
    }
    @Test
    void testParseBuyerFile1() {
        try {
            List<Buyer> buyers = Reader.readBuyers(new File("./data/testBuyerFile1.txt"));
            Buyer buyer1 = buyers.get(0);
            assertEquals(60.0, buyer1.getSubtotal());
            assertEquals("keskin16", buyer1.getPassword());
            assertEquals(2, buyer1.getCart().size());
            assertEquals("ed", buyer1.getUsername());

            Buyer buyer2 = buyers.get(1);
            assertEquals(123.54, buyer2.getSubtotal());
            assertEquals("christmas", buyer2.getPassword());
            assertEquals(2, buyer2.getCart().size());
            assertEquals("happy", buyer2.getUsername());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testParseAccountsFile2() {
        try {
            List<Buyer> buyers = Reader.readBuyers(new File("./data/testBuyerFile2.txt"));
            Buyer buyer1 = buyers.get(0);
            assertEquals(23.12, buyer1.getSubtotal());
            assertEquals("nayra123", buyer1.getPassword());
            assertEquals(3, buyer1.getCart().size());
            assertEquals("nayra", buyer1.getUsername());

            Buyer buyer2 = buyers.get(1);
            assertEquals(30.65, buyer2.getSubtotal());
            assertEquals("keskin30", buyer2.getPassword());
            assertEquals(2, buyer2.getCart().size());
            assertEquals("herman", buyer2.getUsername());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testIOException() {
        try {
            Reader.readBuyers(new File("./path/does/not/exist/testAccount.txt"));
        } catch (IOException e) {
            // expected
        }
    }
}
