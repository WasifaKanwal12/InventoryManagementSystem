package com.InventoryManagement;

//Laptop.java
public class Laptop extends Product {
 private String processor;
 private int ramGB;
 private int storageGB;

 public Laptop(String productId, String name, double price, int quantity, String category, String processor, int ramGB, int storageGB) {
     super(productId, name, price, quantity, category); 
     this.ramGB = ramGB;
     this.storageGB = storageGB;
 }

 // Specific getters/setters for Laptop
 public String getProcessor() { return processor; }
 public void setProcessor(String processor) { this.processor = processor; }

 public int getRamGB() { return ramGB; }
 public void setRamGB(int ramGB) { this.ramGB = ramGB; }

 public int getStorageGB() { return storageGB; }
 public void setStorageGB(int storageGB) { this.storageGB = storageGB; }

 @Override
 public String toString() {
     return super.toString() + ", Processor: " + processor + ", RAM: " + ramGB + "GB, Storage: " + storageGB + "GB";
 }
}