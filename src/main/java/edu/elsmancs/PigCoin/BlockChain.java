package edu.elsmancs.PigCoin;

import java.util.ArrayList;
import java.util.List;


public class BlockChain {
    private final List<Transaction> BlockChain = new ArrayList<>();

    public BlockChain() {}
    
    public List<Transaction> getBlockChain() {
        return BlockChain;
    }
    
    public void addOrigin(Transaction trans) {
        BlockChain.add(trans);
    }
    
    public void summarize(){
        BlockChain.forEach((trans) -> {System.out.print(trans.toString());});
    }

    
    public void summarize(int position){
        if (position <= BlockChain.size() && position >= 0) {
        System.out.print(BlockChain.get(position).toString());
        }
    }
}
