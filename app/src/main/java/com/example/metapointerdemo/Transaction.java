package com.example.metapointerdemo;

public class Transaction {
    private String senderPhone;
    private String receiverPhone;
    private int amount;
    private String timestamp;

    public Transaction() {
        // Default constructor required for calls to DataSnapshot.getValue(Transaction.class)
    }

    public Transaction(String senderPhone, String receiverPhone, int amount, String timestamp) {
        this.senderPhone = senderPhone;
        this.receiverPhone = receiverPhone;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public int getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
