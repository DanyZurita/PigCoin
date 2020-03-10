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
    
    void addOrigin(Transaction trans) {
        BlockChain.add(trans);
    }
    
    void summarize(){
        BlockChain.forEach((trans) -> {System.out.print(trans.toString());});
    }

    
    void summarize(int position){
        if (position <= BlockChain.size() && position >= 0) {
        System.out.print(BlockChain.get(position).toString());
        }
    }
    
    List<Transaction> loadInputTransaction(PublicKey address) {
        List<Transaction> inputTransactions = getBlockChain().stream()
                .filter((trans) -> (trans.getpKey_recipient().equals(address)))
                .collect(Collectors.toCollection(ArrayList<Transaction>::new));
        return inputTransactions;
    }

    List<Transaction> loadOutputTransaction(PublicKey address) {
        List<Transaction> outputTransactions = getBlockChain().stream()
                .filter((trans) -> (trans.getpKey_sender().equals(address)))
                .collect(Collectors.toCollection(ArrayList<Transaction>::new));
        return outputTransactions;
    }
    
    List[] loadWallet(PublicKey address) {
        List<Transaction> input = loadInputTransaction(address);
        List<Transaction> output = loadOutputTransaction(address);
        List[] inputOutput = {input, output};
        return inputOutput;
    }
    
    void processTransactions(PublicKey address, PublicKey pKey_recipient, Map<String, Double> consumedCoins, String message, byte[] signedTransaction) {
        isSignatureValid(address, message, signedTransaction);
        createTransaction(address, pKey_recipient, consumedCoins, message, signedTransaction);
    }
    
    private boolean isSignatureValid(PublicKey address, String message, byte[] signedTransaction) {
        return GenSig.verify(address, message, signedTransaction);
    }
    
    private void createTransaction(PublicKey address, PublicKey pKey_recipient, Map<String, Double> consumedCoins, String message, byte[] signedTransaction) {
        for (String hash : consumedCoins.keySet()){
            boolean CA = hash.startsWith("CA_");
            if (CA) {
                String actualHash = hash.substring(3, hash.length());
                Transaction trans = new Transaction(getNewHash(CA, hash), actualHash, address, pKey_recipient, consumedCoins.get(hash), message);
                getBlockChain().add(trans);
            }
            else {
                Transaction trans = new Transaction(getNewHash(CA, hash), hash, address, pKey_recipient, consumedCoins.get(hash), message);
                getBlockChain().add(trans);
            }
        }
    }
    
    private String getNewHash(boolean changeAddress, String hash){
        int maxNumBlockChain = 0;
        for (Transaction trans : getBlockChain()){
            
            int transferNum = Character.getNumericValue(trans.getHash().charAt(trans.getHash().length() -1));
            if(maxNumBlockChain < transferNum){
                maxNumBlockChain = transferNum; 
            }
        }
        if (changeAddress) {
            return hash;
        }
        else {
            maxNumBlockChain += 1;
            String stringMaxNum = String.valueOf(maxNumBlockChain);
            return "hash_".concat(stringMaxNum);
        }
    }
            
}
