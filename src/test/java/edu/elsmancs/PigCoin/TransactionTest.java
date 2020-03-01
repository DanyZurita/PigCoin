package edu.elsmancs.PigCoin;

import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionTest {
    Wallet wallet_1 = new Wallet(); 
    Wallet wallet_2 = new Wallet();
    
    @Test
    public void newEmptyTransaction() {
        Transaction trans = new Transaction();
        assertEquals(trans.getHash(), null);
    }
    
    @Test
    public void new2WalletTransaction() {
        wallet_1.generateKeyPair();
        wallet_2.generateKeyPair();
        Transaction trans = new Transaction("hash_1", "0", wallet_1.getAddress(), wallet_2.getAddress(), 20, "a flying pig!");
        assertEquals(trans.getHash(), "hash1");
        assertEquals(trans.getPrev_hash(), "0");
        assertEquals(trans.getPigcoins(), 20, 0);
        assertEquals(trans.getMessage(), "a flying pig!");
        
    }
}
