package edu.elsmancs.PigCoin;

import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionTest {
    Wallet wallet1 = new Wallet(); 
    Wallet wallet2 = new Wallet();
    
    @Test
    public void newEmptyTransaction() {
        Transaction trans = new Transaction();
        assertEquals(trans.getHash(), null);
    }
    
    @Test
    public void new2WalletTransaction() {
        wallet1.generateKeyPair();
        wallet2.generateKeyPair();
        Transaction trans = new Transaction("hash_1", "0", wallet1.getAddress(), wallet2.getAddress(), 20, "a flying pig!");
        assertEquals(trans.getHash(), "hash1");
        assertEquals(trans.getPrev_hash(), "0");
        assertEquals(trans.getpKey_sender().hashCode(), wallet1.getAddress().hashCode());
        assertEquals(trans.getpKey_recipient().hashCode(), wallet2.getAddress().hashCode());
        assertEquals(trans.getPigcoins(), 20, 0);
        assertEquals(trans.getMessage(), "a flying pig!");
        
    }
}
