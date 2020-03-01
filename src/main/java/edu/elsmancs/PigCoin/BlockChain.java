package edu.elsmancs.PigCoin;

import java.util.ArrayList;
import java.util.List;


public class BlockChain {
    private final List<Transaction> BlockChain = new ArrayList<>();
    
    public BlockChain() {}
    
    public void addOrigin(Transaction trans) {
        BlockChain.add(trans);
    }
    
    public void summarize(){
        BlockChain.forEach((trans) -> {System.out.print(trans.toString());});
    }

    
    public void summarize(int position){
        System.out.print(BlockChain.get(position).toString());
    }
}
