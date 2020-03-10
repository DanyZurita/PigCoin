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
    
    private void setInputTransaction(List<Transaction> inputTransaction) {
        this.inputTransaction = inputTransaction;
    }

    private void setOutputTransaction(List<Transaction> outputTransaction) {
        this.outputTransaction = outputTransaction;
    }
    
    
    public double getTotal_input() {
        return total_input;
    }

    private void setTotal_input(double total_input) {
        this.total_input = total_input;
    }

    public double getTotal_output() {
        return total_output;
    }

    private void setTotal_output(double total_output) {
        this.total_output = total_output;
    }

    public double getBalance() {
        return balance;
    }

    private void updateBalance() {
        this.balance = this.total_input - this.total_output;
    }
    
    double loadCoinsInputTransactions(BlockChain bchain) {
        double input = 0d;
        input = bchain.getBlockChain().stream()
                              .filter((trans) -> (trans.getpKey_recipient().equals(getAddress())))
                              .map((trans) -> trans.getPigcoins())
                              .reduce(input, (accumulator, _item) -> accumulator + _item);
        return input;
    }

    double loadCoinsOutputTransactions(BlockChain bchain) {
        double output = 0d;
        output = bchain.getBlockChain().stream()
                                       .filter((trans) -> (trans.getpKey_sender().equals(getAddress())))
                                       .map((trans) -> trans.getPigcoins())
                                       .reduce(output, (accumulator, _item) -> accumulator + _item);
        return output;
    }
    
    void loadInputTransactions(BlockChain bchain) {
        setInputTransaction(bchain.loadInputTransaction(getAddress()));
    }
    
    void loadOutputTransactions(BlockChain bchain) {
        setOutputTransaction(bchain.loadOutputTransaction(getAddress()));
    }
    
    public void loadCoins(BlockChain bchain) {
        List[] inputOutput = bchain.loadWallet(getAddress());
        setTotal_input(loadCoinsInputTransactions(bchain));
        setTotal_output(loadCoinsOutputTransactions(bchain));
        updateBalance();
    }
    
    public void sendCoins(PublicKey pKey_recipient, double coins, String message, BlockChain bChain)  {
        Map<String, Double> consumedCoins = collectCoins(coins);
        byte[] signedTransaction = signTransaction(message);
        bChain.processTransactions(getAddress(), pKey_recipient, consumedCoins, message, signedTransaction);
    }
    
    Map<String, Double> collectCoins(double coins) {
        Map<String, Double> consumedCoins = new HashMap<>();
        double coinsAmount = 0d; 
        for (Transaction trans : getInputTransactions()) {
            if (isTransactionValid(trans) && coins > coinsAmount) {
                coinsAmount += trans.getPigcoins();
                if (coinsAmount <= coins) {
                    consumedCoins.put(trans.getHash(), trans.getPigcoins());
                }
                else if (coinsAmount > coins) {
                    double CA_Amount = coinsAmount - coins;
                    consumedCoins.put(trans.getHash(), (trans.getPigcoins() - CA_Amount));
                    consumedCoins.put("CA_" + trans.getHash(), CA_Amount);
                }  
                else {
                    consumedCoins.put(trans.getHash(), trans.getPigcoins());
                }
            }
        }
        if (coinsAmount >= coins){
            return consumedCoins;
        }
        else {
            consumedCoins.clear();
            return consumedCoins;
        }
    }
    
    private boolean isTransactionValid(Transaction trans) {
        return getOutputTransactions().stream().noneMatch((output) -> (output.getPrev_hash() == null ? trans.getHash() == null : output.getPrev_hash().equals(trans.getHash())));
    }
    
    byte[] signTransaction(String message) {
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
