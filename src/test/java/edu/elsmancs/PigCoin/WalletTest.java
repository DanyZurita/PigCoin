package edu.elsmancs.PigCoin;

import org.junit.Test;
import static org.junit.Assert.*;

public class WalletTest {
    @Test
    public void addWallet() {
        Wallet wall = new Wallet();
        assertNotEquals(wall.hashCode(), null);
    }
}
