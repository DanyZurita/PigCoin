package edu.elsmancs.PigCoin;

import java.util.ArrayList;
import java.util.List;
import java.security.PublicKey;
import java.util.Map;
import java.util.stream.Collectors;


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
    
    public List<Transaction> loadInputTransaction(PublicKey address) {
        List<Transaction> inputTransactions = getBlockChain().stream()
                .filter((trans) -> (trans.getpKey_recipient().equals(address)))
                .collect(Collectors.toCollection(ArrayList<Transaction>::new));
        return inputTransactions;
    }

    public List<Transaction> loadOutputTransaction(PublicKey address) {
        List<Transaction> outputTransactions = getBlockChain().stream()
                .filter((trans) -> (trans.getpKey_sender().equals(address)))
                .collect(Collectors.toCollection(ArrayList<Transaction>::new));
        return outputTransactions;
    }
    
    public List[] loadWallet(PublicKey address) {
        List<Transaction> input = loadInputTransaction(address);
        List<Transaction> output = loadOutputTransaction(address);
        List[] inputOutput = {input, output};
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
    
    public void createTransaction(PublicKey address, PublicKey pKey_recipient, Map<String, Double> consumedCoins, String message, byte[] signedTransaction) {
        Transaction trans = new Transaction();
        trans.setpKey_sender(address);
        trans.setpKey_recipient(pKey_recipient);
        trans.setMessage(message);
        BlockChain.add(trans);
    }
            
}
