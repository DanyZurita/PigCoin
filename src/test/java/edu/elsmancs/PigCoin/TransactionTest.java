package edu.elsmancs.PigCoin;

import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionTest {
    
    @Test
    public void newEmptyTransaction() {
        Transaction trans = new Transaction();
        assertEquals(trans.getHash(), null);
    }
}
