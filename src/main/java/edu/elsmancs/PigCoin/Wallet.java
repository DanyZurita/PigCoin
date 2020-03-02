package edu.elsmancs.PigCoin;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;


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
        for (Transaction trans : bchain.getBlockChain()) {
            if (trans.getpKey_recipient().equals(getAddress())) {
                inputTransaction.add(trans);
            }
        }
    }

    public void loadOutputTransactions(BlockChain bchain) {
        for (Transaction trans : bchain.getBlockChain()) {
            if (trans.getpKey_sender().equals(getAddress())) {
                outputTransaction.add(trans);
            }
        }
    }
    
    public void loadCoins(BlockChain bchain) {
        double[] inputOutput = bchain.loadWallet(getAddress());
        total_input = inputOutput[0];
        total_output = inputOutput[1];
        balance = total_input - total_output;
    }
    
    public void sendCoins(PublicKey pKey_recipient, double coins, String message, BlockChain bChain)  {
        double consumedCoins = collectCoins(coins);
        byte[] signedTransaction = signTransaction(message);
        bChain.processTransactions(getAddress(), pKey_recipient, consumedCoins, message, signedTransaction);
    }
    
    
    @Override
    public String toString() {
        return '\n' + "Wallet = " + getAddress().hashCode() + '\n' 
                + "Total input = " + total_input + '\n'
                + "Total output = " + total_output + '\n'
                + "Balance = " + balance + '\n';
    }
    
}
