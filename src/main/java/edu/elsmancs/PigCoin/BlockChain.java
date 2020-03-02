package edu.elsmancs.PigCoin;

import java.util.ArrayList;
import java.util.List;
import java.security.PublicKey;


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
    
    public double loadInputTransaction(PublicKey address) {
        double input = 0d;
        for (Transaction trans : BlockChain) {
            if (trans.getpKey_recipient().equals(address)) {
                input += trans.getPigcoins();
            }
        }
        return input;
    }

    public double loadOutputTransaction(PublicKey address) {
        double output = 0d;
        for (Transaction trans : BlockChain) {
            if (trans.getpKey_sender().equals(address)) {
                output += trans.getPigcoins();  
            }   
        }
        return output;
    }
    
    public double[] loadWallet(PublicKey address) {
        double input = loadInputTransaction(address);
        double output = loadOutputTransaction(address);
        double[] inputOutput = {input, output};
        return inputOutput;
    }
}
