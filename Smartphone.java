package com.InventoryManagement;

//Smartphone.java
public class Smartphone extends Product {
 private double screenSizeInches;
 private int cameraMP;
 private String operatingSystem; // e.g., "Android", "iOS"

 public Smartphone(String productId, String name, double price, int quantity, String category, double screenSizeInches, int cameraMP, String operatingSystem) {
     super(productId, name, price, quantity, category); // Call parent (Product) constructor
     this.screenSizeInches = screenSizeInches;
     this.cameraMP = cameraMP;
     this.operatingSystem = operatingSystem;
 }

 // Specific getters/setters for Smartphone
 public double getScreenSizeInches() { return screenSizeInches; }
 public void setScreenSizeInches(double screenSizeInches) { this.screenSizeInches = screenSizeInches; }

 public int getCameraMP() { return cameraMP; }
 public void setCameraMP(int cameraMP) { this.cameraMP = cameraMP; }

 public String getOperatingSystem() { return operatingSystem; }
 public void setOperatingSystem(String operatingSystem) { this.operatingSystem = operatingSystem; }

 @Override
 public String toString() {
     return super.toString() + ", Screen: " + screenSizeInches + "\", Camera: " + cameraMP + "MP, OS: " + operatingSystem;
 }
}
