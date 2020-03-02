package edu.elsmancs.PigCoin;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Wallet {
    private PublicKey address;
    private PrivateKey sKey;
    private double total_input = 0d;
    private double total_output = 0d;
    private double balance = 0d;
    private final List<Transaction> inputTransaction = new ArrayList<>();
    private final List<Transaction> outputTransaction = new ArrayList<>();

    public Wallet() {}
    
    public void generateKeyPair() {
        KeyPair pair = GenSig.generateKeyPair();
        setSK(pair.getPrivate());
        setAddress(pair.getPublic());
    }
    
    public void setSK(PrivateKey SK) {
        this.sKey = SK;
    }
    
    public void setAddress(PublicKey PK) {
        this.address = PK;
    }
    
    public PublicKey getAddress() {
        return this.address;
    }
    
    public PrivateKey getSKey() {
        return this.sKey;
    }
    
    public List<Transaction> getInputTransactions() {
        return inputTransaction;
    }

    public List<Transaction> getOutputTransactions() {
        return outputTransaction;
    }
    
    public void loadInputTransactions(BlockChain bchain) {
        bchain.getBlockChain().stream().filter((trans) -> (trans.getpKey_recipient().equals(getAddress()))).forEachOrdered((trans) -> {
            inputTransaction.add(trans);
        });
    }

    public void loadOutputTransactions(BlockChain bchain) {
        bchain.getBlockChain().stream().filter((trans) -> (trans.getpKey_sender().equals(getAddress()))).forEachOrdered((trans) -> {
            outputTransaction.add(trans);
        });
    }
    
    public void loadCoins(BlockChain bchain) {
        double[] inputOutput = bchain.loadWallet(getAddress());
        total_input = inputOutput[0];
        total_output = inputOutput[1];
        balance = total_input - total_output;
    }
    
    public void sendCoins(PublicKey pKey_recipient, double coins, String message, BlockChain bChain)  {
        Map<String, Double> consumedCoins = collectCoins(coins);
        byte[] signedTransaction = signTransaction(message);
        bChain.processTransactions(getAddress(), pKey_recipient, consumedCoins, message, signedTransaction);
    }
    
    public Map<String, Double> collectCoins(double coins) {
        Map<String, Double> consumedCoins = new HashMap<>();
        
        return consumedCoins;
    }
    
    public byte[] signTransaction(String message) {
        return GenSig.sign(getSKey(), message);
    }     
            
    @Override
    public String toString() {
        return '\n' + "Wallet = " + getAddress().hashCode() + '\n' 
                + "Total input = " + total_input + '\n'
                + "Total output = " + total_output + '\n'
                + "Balance = " + balance + '\n';
    }
    
}
