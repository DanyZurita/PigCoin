package edu.elsmancs.PigCoin;

import org.junit.Test;
import static org.junit.Assert.*;

public class WalletTest {
    @Test
    public void addWallet() {
        Wallet wall = new Wallet();
        assertNotEquals(wall.hashCode(), null);
    }
    
    @Test
    public void walletPK() {
        Wallet wall = new Wallet();
        wall.generateKeyPair();
        assertNotEquals(wall.getAddress().hashCode(), null);
    }
    
    @Test
    public void walletSK() {
        Wallet wall = new Wallet();
        wall.generateKeyPair();
        assertNotEquals(wall.getSKey().hashCode(), null);
    }
}
