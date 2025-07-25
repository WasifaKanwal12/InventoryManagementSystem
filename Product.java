package com.InventoryManagement;

//Product.java
import java.util.Objects;

public class Product {
 private String productId;
 private String name;
 private double price;
 private int quantity;
 private String category; // e.g., "Electronics", "Accessories"

 // Constructor
 public Product(String productId, String name, double price, int quantity, String category) {
     this.productId = productId;
     this.name = name;
     this.price = price;
     this.quantity = quantity;
     this.category = category;
 }

 // Getters 
 public String getProductId() {
     return productId;
 }

 public String getName() {
     return name;
 }

 public double getPrice() {
     return price;
 }

 public int getQuantity() {
     return quantity;
 }

 public String getCategory() {
     return category;
 }

 // Setters 
 public void setProductId(String productId) {
     this.productId = productId;
 }

 public void setName(String name) {
     this.name = name;
 }

 public void setPrice(double price) {
     this.price = price;
 }

 public void setQuantity(int quantity) {
     this.quantity = quantity;
 }

 public void setCategory(String category) {
     this.category = category;
 }

 // Method to update quantity (business logic encapsulated within the object)
 public void addQuantity(int amount) {
     if (amount > 0) {
         this.quantity += amount;
     }
 }

 public boolean removeQuantity(int amount) {
     if (amount > 0 && this.quantity >= amount) {
         this.quantity -= amount;
         return true;
     }
     return false; // Not enough stock
 }

 @Override
 public String toString() {
     return "ID: " + productId + ", Name: " + name + ", Price: $" + String.format("%.2f", price) + ", Qty: " + quantity + ", Category: " + category;
 }

 @Override
 public boolean equals(Object o) {
     if (this == o) return true;
     if (o == null || getClass() != o.getClass()) return false; 
     Product product = (Product) o;
     return Objects.equals(productId, product.productId); 
 }

 @Override
 public int hashCode() {
     return Objects.hash(productId);
 }
}