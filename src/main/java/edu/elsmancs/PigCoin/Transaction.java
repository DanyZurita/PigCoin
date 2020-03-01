package edu.elsmancs.PigCoin;

import java.security.PrivateKey;
import java.security.PublicKey;


public class Transaction {
    private String hash;
    private String prev_hash;
    private PublicKey pKey_Sender;
    private PublicKey pKey_recipient;
    private double pigcoins = 0d;
    private String message = null;
    
    public Transaction () {}
    
    public Transaction (String hash, String prev_hash, PublicKey senderPK, PublicKey recipientPK, double pigcoins, String message) {
        this.hash = hash;
        this.prev_hash = prev_hash;
        this.pKey_Sender = senderPK;
        this.pKey_recipient = recipientPK;
        this.pigcoins = pigcoins;
        this.message = message;
    }
}
