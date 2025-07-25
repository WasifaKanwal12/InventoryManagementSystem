package com.InventoryManagement;

//Transaction.java
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID; // Used to generate unique IDs

public class Transaction {
 public enum Type { // Enum for transaction type (IN/OUT)
     IN, OUT
 }

 private String transactionId;
 private String productId; // ID of the product involved
 private Type type;        // Type of transaction (IN or OUT)
 private int quantity;     // Quantity of product moved
 private LocalDateTime transactionDate; // Timestamp of the transaction

 public Transaction(String productId, Type type, int quantity) {
     this.transactionId = UUID.randomUUID().toString(); // Generate unique ID for each transaction
     this.productId = productId;
     this.type = type;
     this.quantity = quantity;
     this.transactionDate = LocalDateTime.now(); // Record current time
 }

 // Getters
 public String getTransactionId() {
     return transactionId;
 }

 public String getProductId() {
     return productId;
 }

 public Type getType() {
     return type;
 }

 public int getQuantity() {
     return quantity;
 }

 public LocalDateTime getTransactionDate() {
     return transactionDate;
 }

 @Override
 public String toString() {
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
     return String.format("Trans ID: %s, Product ID: %s, Type: %s, Qty: %d, Date: %s",
             transactionId.substring(0, 8) + "...", productId, type, quantity, transactionDate.format(formatter));
 }
}
