package edu.elsmancs.PigCoin;

import org.junit.Test;
import static org.junit.Assert.*;

public class BlockChainTest {
    
    @Test
    public void createBlockChain() {
        BlockChain block = new BlockChain();
        Transaction trans = new Transaction();
        block.addOrigin(trans);
        assertEquals(block.getBlockChain().get(0), trans);
    }
}
