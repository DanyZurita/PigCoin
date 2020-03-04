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
    private List<Transaction> inputTransaction = new ArrayList<>();
    private List<Transaction> outputTransaction = new ArrayList<>();

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
    
    public void setInputTransaction(List<Transaction> inputTransaction) {
        this.inputTransaction = inputTransaction;
    }

    public void setOutputTransaction(List<Transaction> outputTransaction) {
        this.outputTransaction = outputTransaction;
    }
    
    public double loadInputTransactions(BlockChain bchain) {
        double input = 0d;
        bchain.getBlockChain().stream().filter((trans) -> (trans.getpKey_recipient().equals(getAddress()))).map((trans) -> trans.getPigcoins()).reduce(input, (accumulator, _item) -> accumulator + _item);
        return input;
    }

    public double loadOutputTransactions(BlockChain bchain) {
        double output = 0d;
        bchain.getBlockChain().stream().filter((trans) -> (trans.getpKey_sender().equals(getAddress()))).map((trans) -> trans.getPigcoins()).reduce(output, (accumulator, _item) -> accumulator + _item);
        return output;
    }
    
    public void loadCoins(BlockChain bchain) {
        List[] inputOutput = bchain.loadWallet(getAddress());
        setInputTransaction(inputOutput[0]);
        setInputTransaction(inputOutput[1]);
        
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
