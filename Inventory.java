package com.InventoryManagement;

//Inventory.java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set; // Added for getAllCategories
import java.util.stream.Collectors; // For stream operations like filtering

public class Inventory {
 // Encapsulation: Internal storage is private, not directly accessible from outside
 // Polymorphism: The Map stores Product objects, but can hold instances of its subclasses
 private Map<String, Product> products; // Stores products by productId for quick lookup
 private List<Transaction> transactions; // Stores all transactions

 public Inventory() {
     this.products = new HashMap<>(); // Initialize HashMap for products
     this.transactions = new ArrayList<>(); // Initialize ArrayList for transactions
 }

 // Abstraction: Public methods provide a high-level interface for inventory operations

 // Add a new product (Polymorphism: accepts any Product subclass)
 public boolean addProduct(Product product) {
     if (products.containsKey(product.getProductId())) {
         return false; // Product with this ID already exists
     }
     products.put(product.getProductId(), product); // Store the product (can be Product, Laptop, Smartphone)
     return true;
 }

 // Get a product by ID
 public Product getProduct(String productId) {
     return products.get(productId); // Efficient O(1) average time lookup using HashMap
 }

 // Update product details (base attributes)
 public boolean updateProduct(String productId, String newName, double newPrice, String newCategory) {
     Product product = products.get(productId);
     if (product != null) {
         product.setName(newName);
         product.setPrice(newPrice);
         product.setCategory(newCategory);
         return true;
     }
     return false; // Product not found
 }

 // Update specific attributes for Laptop (Polymorphism: uses instanceof and downcasting)
 public boolean updateLaptop(String productId, String processor, int ramGB, int storageGB) {
     Product product = products.get(productId);
     if (product instanceof Laptop) { // Check if it's actually a Laptop
         Laptop laptop = (Laptop) product; // Downcast to Laptop to access specific methods
         laptop.setProcessor(processor);
         laptop.setRamGB(ramGB);
         laptop.setStorageGB(storageGB);
         return true;
     }
     return false; // Not a Laptop or not found
 }

 // Update specific attributes for Smartphone (Polymorphism: uses instanceof and downcasting)
 public boolean updateSmartphone(String productId, double screenSizeInches, int cameraMP, String operatingSystem) {
     Product product = products.get(productId);
     if (product instanceof Smartphone) { // Check if it's actually a Smartphone
         Smartphone smartphone = (Smartphone) product; // Downcast to Smartphone to access specific methods
         smartphone.setScreenSizeInches(screenSizeInches);
         smartphone.setCameraMP(cameraMP);
         smartphone.setOperatingSystem(operatingSystem);
         return true;
     }
     return false; // Not a Smartphone or not found
 }

 // Delete a product
 public boolean deleteProduct(String productId) {
     if (products.containsKey(productId)) {
         products.remove(productId); // Remove from HashMap
         // Optionally, also remove related transactions or mark them as archived
         return true;
     }
     return false; // Product not found
 }

 // Add stock to a product
 public boolean addStock(String productId, int quantity) {
     Product product = products.get(productId);
     if (product != null) {
         product.addQuantity(quantity); // Calls encapsulated method on Product object
         transactions.add(new Transaction(productId, Transaction.Type.IN, quantity)); // Record transaction
         return true;
     }
     return false; // Product not found
 }

 // Remove stock from a product
 public boolean removeStock(String productId, int quantity) {
     Product product = products.get(productId);
     if (product != null) {
         if (product.removeQuantity(quantity)) { // Calls encapsulated method on Product object
             transactions.add(new Transaction(productId, Transaction.Type.OUT, quantity)); // Record transaction
             return true;
         }
     }
     return false; // Product not found or insufficient stock
 }

 // Get all products as a list (useful for display)
 // Polymorphism: Returns a list of Product, which can contain Laptop or Smartphone instances
 public List<Product> getAllProducts() {
     return new ArrayList<>(products.values()); // Returns a new ArrayList containing all products
 }

 // Get all transactions
 public List<Transaction> getAllTransactions() {
     return new ArrayList<>(transactions); // Returns a new ArrayList containing all transactions
 }

 // Search products by name (case-insensitive, partial match)
 public List<Product> searchProductsByName(String searchTerm) {
     String lowerCaseSearchTerm = searchTerm.toLowerCase();
     return products.values().stream() // Use Java Stream API for filtering
             .filter(p -> p.getName().toLowerCase().contains(lowerCaseSearchTerm))
             .collect(Collectors.toList()); // Collect results into a new List
 }

 // Filter products by category
 public List<Product> filterProductsByCategory(String category) {
     String lowerCaseCategory = category.toLowerCase();
     return products.values().stream()
             .filter(p -> p.getCategory().toLowerCase().equals(lowerCaseCategory))
             .collect(Collectors.toList());
 }

 // CORRECTED METHOD: Get all unique categories from products
 public Set<String> getAllCategories() {
     return products.values().stream()
             .map(Product::getCategory) // Get the category string for each product
             .collect(Collectors.toSet()); // Collect these strings into a Set to ensure uniqueness
 }
}
