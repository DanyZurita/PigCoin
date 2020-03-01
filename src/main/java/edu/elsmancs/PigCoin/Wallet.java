package edu.elsmancs.PigCoin;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;


public class Wallet {
    private PublicKey address;
    private PrivateKey sKey;
    private double total_input = 0d;
    private double total_output = 0d;
    private double balance = 0d;
    private String inputTransaction;
    private String outputTransaction;
    
    public Wallet() {}
    
    public void generateKeyPair() {
        KeyPair pair = GenSig.generateKeyPair();
        this.sKey = pair.getPrivate();
        this.address = pair.getPublic();
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
    
    @Override
    public String toString() {
        return '\n' + "Wallet = " + getAddress().hashCode() + '\n' 
                + "Total input = " + total_input + '\n'
                + "Total output = " + total_output + '\n'
                + "Balance = " + balance + '\n';
    }
    
}
