package edu.elsmancs.PigCoin;

import java.util.ArrayList;
import java.util.List;


public class BlockChain {
    private final List<Transaction> BlockChain = new ArrayList<>();
    
    public BlockChain() {}
    
    public void addOrigin(Transaction trans) {
        BlockChain.add(trans);
    }
}
