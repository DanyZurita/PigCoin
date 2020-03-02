package edu.elsmancs.PigCoin;

import java.util.ArrayList;
import java.util.List;
import java.security.PublicKey;
import java.util.Map;


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
        input = BlockChain.stream().filter((trans) -> (trans.getpKey_recipient().equals(address))).map((trans) -> trans.getPigcoins()).reduce(input, (accumulator, _item) -> accumulator + _item);
        return input;
    }

    public double loadOutputTransaction(PublicKey address) {
        double output = 0d;
        output = BlockChain.stream().filter((trans) -> (trans.getpKey_sender().equals(address))).map((trans) -> trans.getPigcoins()).reduce(output, (accumulator, _item) -> accumulator + _item);
        return output;
    }
    
    public double[] loadWallet(PublicKey address) {
        double input = loadInputTransaction(address);
        double output = loadOutputTransaction(address);
        double[] inputOutput = {input, output};
        return inputOutput;
    }
    
    public void processTransactions(PublicKey address, PublicKey pKey_recipient, Map<String, Double> consumedCoins, String message, byte[] signedTransaction) {
        isSignatureValid(address, message, signedTransaction);
        isConsumedCoinsValid(consumedCoins);
        createTransaction(address, pKey_recipient, consumedCoins, message, signedTransaction);
    }
    
    public boolean isSignatureValid(PublicKey address, String message, byte[] signedTransaction) {
        return GenSig.verify(address, message, signedTransaction);
    }
    
    public void isConsumedCoinsValid(Map<String, Double> consumedCoins) {
        
    }
    
    public void createTransaction(PublicKey address, PublicKey pKey_recipient, Map<String, Double> consumedCoins, String message, byte[] signedTransaction) {
        Transaction trans = new Transaction();
        trans.setpKey_sender(address);
        trans.setpKey_recipient(pKey_recipient);
        trans.setMessage(message);
        BlockChain.add(trans);
    }
            
}
