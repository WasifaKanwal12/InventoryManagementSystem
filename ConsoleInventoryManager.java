// ConsoleInventoryManager.java
package com.InventoryManagement;

import java.util.List;
import java.util.Scanner;
import java.util.Set; // Used for filtering by categories

/**
 * Console-based application for managing inventory.
 * This class replaces the GUI frontend, providing text-based interaction
 * with the backend Inventory management logic.
 */
public class ConsoleInventoryManager {

    private Inventory inventory; // Instance of the backend Inventory manager
    private Scanner scanner;     // For reading user input from the console

    /**
     * Constructor for ConsoleInventoryManager.
     * Initializes the Inventory backend and the Scanner for input.
     * Also adds some sample data for quick testing.
     */
    public ConsoleInventoryManager() {
        inventory = new Inventory(); // Initialize your Inventory backend
        scanner = new Scanner(System.in);

        // --- Add some sample data for testing purposes ---
        // This helps you test functionality without manually adding everything each time.
        System.out.println("Initializing inventory with sample data...");
        inventory.addProduct(new Product("P001", "Generic Item A", 10.50, 100, "Electronics"));
        inventory.addProduct(new Laptop("L001", "Dell XPS 15", 1500.00, 50, "Laptops", "Intel i7", 16, 512));
        inventory.addProduct(new Smartphone("S001", "iPhone 15", 999.99, 75, "Smartphones", 6.1, 48, "iOS"));
        inventory.addProduct(new Product("P002", "Office Chair", 120.00, 30, "Furniture"));
        inventory.addProduct(new Product("P003", "USB Cable", 5.99, 200, "Accessories"));
        inventory.addProduct(new Smartphone("S002", "Samsung Galaxy", 799.00, 60, "Smartphones", 6.7, 64, "Android"));
        System.out.println("Sample data loaded.\n");
    }

    /**
     * Starts the console-based inventory management application.
     * This is the main loop that presents the menu and handles user choices.
     */
    public void start() {
        int choice;
        do {
            printMenu(); // Display the main menu
            System.out.print("Enter your choice: ");
            try {
                // Read user input for menu choice
                choice = parseIntInput(); // Use helper for robust integer input

                // Process the user's choice
                switch (choice) {
                    case 1:
                        addProduct();
                        break;
                    case 2:
                        updateProduct();
                        break;
                    case 3:
                        deleteProduct();
                        break;
                    case 4:
                        addStock();
                        break;
                    case 5:
                        removeStock();
                        break;
                    case 6:
                        viewAllProducts();
                        break;
                    case 7:
                        searchProduct();
                        break;
                    case 8:
                        filterProductsByCategory();
                        break;
                    case 9:
                        viewTransactions();
                        break;
                    case 0:
                        System.out.println("Exiting Inventory Management System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number from the menu.");
                }
            } catch (Exception e) {
                // Catch any unexpected errors during operation
                System.out.println("An unexpected error occurred: " + e.getMessage());
                choice = -1; // Set to -1 to ensure the loop continues
            }
            System.out.println("\n--------------------------------------------------\n"); // Separator for readability
        } while (choice != 0); // Continue loop until user chooses to exit (0)
        scanner.close(); // Close the scanner when done to release resources
    }

    /**
     * Prints the main menu options to the console.
     */
    private void printMenu() {
        System.out.println("===== Inventory Management System (Console) =====");
        System.out.println("1. Add New Product");
        System.out.println("2. Update Product Details");
        System.out.println("3. Delete Product");
        System.out.println("4. Add Stock");
        System.out.println("5. Remove Stock");
        System.out.println("6. View All Products");
        System.out.println("7. Search Product by Name");
        System.out.println("8. Filter Products by Category");
        System.out.println("9. View All Transactions");
        System.out.println("0. Exit");
    }

    /**
     * Prompts the user for product details and adds a new product to the inventory.
     * Handles different product types (Generic, Laptop, Smartphone) using polymorphism.
     */
    private void addProduct() {
        System.out.println("\n--- Add New Product ---");
        System.out.print("Enter Product ID: ");
        String productId = scanner.nextLine().trim();
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Price: ");
        double price = parseDoubleInput();
        if (price < 0) {
            System.out.println("Error: Price cannot be negative. Aborting add product.");
            return;
        }
        System.out.print("Enter Quantity: ");
        int quantity = parseIntInput();
        if (quantity < 0) {
            System.out.println("Error: Quantity cannot be negative. Aborting add product.");
            return;
        }
        System.out.print("Enter Category: ");
        String category = scanner.nextLine().trim();

        // Basic validation for essential fields
        if (productId.isEmpty() || name.isEmpty() || category.isEmpty()) {
            System.out.println("Error: Product ID, Name, and Category cannot be empty. Aborting add product.");
            return;
        }

        System.out.println("Select Product Type:");
        System.out.println("1. Generic Product");
        System.out.println("2. Laptop");
        System.out.println("3. Smartphone");
        System.out.print("Enter type choice (1-3): ");
        int typeChoice = parseIntInput();

        Product newProduct = null;
        try {
            // Use switch to create specific product types based on user choice
            switch (typeChoice) {
                case 1:
                    newProduct = new Product(productId, name, price, quantity, category);
                    break;
                case 2:
                    System.out.print("Enter Processor: ");
                    String processor = scanner.nextLine().trim();
                    System.out.print("Enter RAM (GB): ");
                    int ramGB = parseIntInput();
                    System.out.print("Enter Storage (GB): ");
                    int storageGB = parseIntInput();
                    if (processor.isEmpty() || ramGB <= 0 || storageGB <= 0) {
                        System.out.println("Error: Invalid Laptop specific details (Processor cannot be empty, RAM/Storage must be positive). Aborting add product.");
                        return;
                    }
                    newProduct = new Laptop(productId, name, price, quantity, category, processor, ramGB, storageGB);
                    break;
                case 3:
                    System.out.print("Enter Screen Size (inches): ");
                    double screenSizeInches = parseDoubleInput();
                    System.out.print("Enter Camera (MP): ");
                    int cameraMP = parseIntInput();
                    System.out.print("Enter Operating System: ");
                    String operatingSystem = scanner.nextLine().trim();
                    if (screenSizeInches <= 0 || cameraMP <= 0 || operatingSystem.isEmpty()) {
                        System.out.println("Error: Invalid Smartphone specific details (Screen Size/Camera MP must be positive, OS cannot be empty). Aborting add product.");
                        return;
                    }
                    newProduct = new Smartphone(productId, name, price, quantity, category, screenSizeInches, cameraMP, operatingSystem);
                    break;
                default:
                    System.out.println("Error: Invalid product type choice. Aborting add product.");
                    return;
            }

            // Attempt to add the product to the inventory
            if (inventory.addProduct(newProduct)) {
                System.out.println("Product added successfully!");
            } else {
                System.out.println("Error: Product with ID '" + productId + "' already exists. Please use a unique ID.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred during product creation: " + e.getMessage());
        }
    }

    /**
     * Prompts the user for a product ID and new details to update an existing product.
     * Handles updates for specific product types (Laptop, Smartphone) using polymorphism.
     */
    private void updateProduct() {
        System.out.println("\n--- Update Product Details ---");
        System.out.print("Enter Product ID to update: ");
        String productId = scanner.nextLine().trim();
        Product existingProduct = inventory.getProduct(productId);

        if (existingProduct == null) {
            System.out.println("Error: Product with ID '" + productId + "' not found.");
            return;
        }

        System.out.println("Current Details: " + existingProduct);

        System.out.print("Enter New Name (current: '" + existingProduct.getName() + "') [Press Enter to keep current]: ");
        String newName = scanner.nextLine().trim();
        if (newName.isEmpty()) newName = existingProduct.getName(); // Keep current if input is empty

        System.out.print("Enter New Price (current: " + String.format("%.2f", existingProduct.getPrice()) + ") [Press Enter to keep current]: ");
        String priceStr = scanner.nextLine().trim();
        double newPrice = existingProduct.getPrice(); // Default to current price
        if (!priceStr.isEmpty()) {
            try {
                double parsedPrice = Double.parseDouble(priceStr);
                if (parsedPrice < 0) {
                    System.out.println("Warning: Price cannot be negative. Keeping current price.");
                } else {
                    newPrice = parsedPrice;
                }
            } catch (NumberFormatException e) {
                System.out.println("Warning: Invalid price format. Keeping current price.");
            }
        }

        System.out.print("Enter New Category (current: '" + existingProduct.getCategory() + "') [Press Enter to keep current]: ");
        String newCategory = scanner.nextLine().trim();
        if (newCategory.isEmpty()) newCategory = existingProduct.getCategory(); // Keep current if input is empty

        // Update base product attributes first
        boolean baseUpdateSuccess = inventory.updateProduct(productId, newName, newPrice, newCategory);

        // Handle specific product type updates using instanceof and downcasting
        boolean specificUpdateSuccess = true; // Assume success unless a specific update fails
        if (existingProduct instanceof Laptop) {
            System.out.println("\n--- Laptop Specific Details Update ---");
            Laptop laptop = (Laptop) existingProduct; // Downcast to access Laptop-specific methods

            System.out.print("Enter New Processor (current: " + laptop.getProcessor() + ") [Press Enter to keep current]: ");
            String processor = scanner.nextLine().trim();
            if (processor.isEmpty()) processor = laptop.getProcessor();

            System.out.print("Enter New RAM (GB) (current: " + laptop.getRamGB() + ") [Press Enter to keep current]: ");
            String ramStr = scanner.nextLine().trim();
            int ramGB = laptop.getRamGB();
            if (!ramStr.isEmpty()) {
                try {
                    int parsedRam = Integer.parseInt(ramStr);
                    if (parsedRam <= 0) { System.out.println("Warning: RAM must be positive. Keeping current."); }
                    else { ramGB = parsedRam; }
                } catch (NumberFormatException e) { System.out.println("Warning: Invalid RAM format. Keeping current."); }
            }

            System.out.print("Enter New Storage (GB) (current: " + laptop.getStorageGB() + ") [Press Enter to keep current]: ");
            String storageStr = scanner.nextLine().trim();
            int storageGB = laptop.getStorageGB();
            if (!storageStr.isEmpty()) {
                try {
                    int parsedStorage = Integer.parseInt(storageStr);
                    if (parsedStorage <= 0) { System.out.println("Warning: Storage must be positive. Keeping current."); }
                    else { storageGB = parsedStorage; }
                } catch (NumberFormatException e) { System.out.println("Warning: Invalid Storage format. Keeping current."); }
            }
            specificUpdateSuccess = inventory.updateLaptop(productId, processor, ramGB, storageGB);
        } else if (existingProduct instanceof Smartphone) {
            System.out.println("\n--- Smartphone Specific Details Update ---");
            Smartphone smartphone = (Smartphone) existingProduct; // Downcast to access Smartphone-specific methods

            System.out.print("Enter New Screen Size (inches) (current: " + smartphone.getScreenSizeInches() + ") [Press Enter to keep current]: ");
            String screenStr = scanner.nextLine().trim();
            double screenSizeInches = smartphone.getScreenSizeInches();
            if (!screenStr.isEmpty()) {
                try {
                    double parsedScreenSize = Double.parseDouble(screenStr);
                    if (parsedScreenSize <= 0) { System.out.println("Warning: Screen size must be positive. Keeping current."); }
                    else { screenSizeInches = parsedScreenSize; }
                } catch (NumberFormatException e) { System.out.println("Warning: Invalid screen size format. Keeping current."); }
            }

            System.out.print("Enter New Camera (MP) (current: " + smartphone.getCameraMP() + ") [Press Enter to keep current]: ");
            String cameraStr = scanner.nextLine().trim();
            int cameraMP = smartphone.getCameraMP();
            if (!cameraStr.isEmpty()) {
                try {
                    int parsedCameraMP = Integer.parseInt(cameraStr);
                    if (parsedCameraMP <= 0) { System.out.println("Warning: Camera MP must be positive. Keeping current."); }
                    else { cameraMP = parsedCameraMP; }
                } catch (NumberFormatException e) { System.out.println("Warning: Invalid camera MP format. Keeping current."); }
            }

            System.out.print("Enter New Operating System (current: '" + smartphone.getOperatingSystem() + "') [Press Enter to keep current]: ");
            String operatingSystem = scanner.nextLine().trim();
            if (operatingSystem.isEmpty()) operatingSystem = smartphone.getOperatingSystem();
            specificUpdateSuccess = inventory.updateSmartphone(productId, screenSizeInches, cameraMP, operatingSystem);
        }

        if (baseUpdateSuccess && specificUpdateSuccess) {
            System.out.println("Product updated successfully!");
            System.out.println("Updated Details: " + inventory.getProduct(productId));
        } else {
            System.out.println("Error updating product. Check product ID or specific details provided.");
        }
    }

    /**
     * Prompts the user for a product ID and deletes the product from the inventory.
     */
    private void deleteProduct() {
        System.out.println("\n--- Delete Product ---");
        System.out.print("Enter Product ID to delete: ");
        String productId = scanner.nextLine().trim();

        if (inventory.deleteProduct(productId)) {
            System.out.println("Product '" + productId + "' deleted successfully!");
        } else {
            System.out.println("Error: Product with ID '" + productId + "' not found.");
        }
    }

    /**
     * Prompts the user for a product ID and quantity to add to its stock.
     */
    private void addStock() {
        System.out.println("\n--- Add Stock ---");
        System.out.print("Enter Product ID: ");
        String productId = scanner.nextLine().trim();
        System.out.print("Enter Quantity to Add: ");
        int quantity = parseIntInput();

        if (quantity <= 0) {
            System.out.println("Error: Quantity must be positive.");
            return;
        }

        if (inventory.addStock(productId, quantity)) {
            System.out.println(quantity + " units added to Product ID '" + productId + "'. New quantity: " + inventory.getProduct(productId).getQuantity());
        } else {
            System.out.println("Error: Product with ID '" + productId + "' not found.");
        }
    }

    /**
     * Prompts the user for a product ID and quantity to remove from its stock.
     */
    private void removeStock() {
        System.out.println("\n--- Remove Stock ---");
        System.out.print("Enter Product ID: ");
        String productId = scanner.nextLine().trim();
        System.out.print("Enter Quantity to Remove: ");
        int quantity = parseIntInput();

        if (quantity <= 0) {
            System.out.println("Error: Quantity must be positive.");
            return;
        }

        if (inventory.removeStock(productId, quantity)) {
            System.out.println(quantity + " units removed from Product ID '" + productId + "'. New quantity: " + inventory.getProduct(productId).getQuantity());
        } else {
            Product p = inventory.getProduct(productId);
            if (p == null) {
                System.out.println("Error: Product with ID '" + productId + "' not found.");
            } else {
                System.out.println("Error: Insufficient stock for Product ID '" + productId + "'. Current quantity: " + p.getQuantity());
            }
        }
    }

    /**
     * Displays all products currently in the inventory.
     */
    private void viewAllProducts() {
        System.out.println("\n--- All Products ---");
        List<Product> allProducts = inventory.getAllProducts();
        if (allProducts.isEmpty()) {
            System.out.println("No products in inventory.");
            return;
        }
        // Print header for clarity
        System.out.printf("%-10s %-25s %-10s %-10s %-15s %s\n", "ID", "Name", "Price", "Qty", "Category", "Specific Details");
        System.out.println("---------------------------------------------------------------------------------------------------");
        for (Product product : allProducts) {
            // Determine specific details string based on product type
            String specificDetails = "";
            if (product instanceof Laptop) {
                Laptop laptop = (Laptop) product;
                specificDetails = String.format("Processor: %s, RAM: %dGB, Storage: %dGB",
                                                laptop.getProcessor(), laptop.getRamGB(), laptop.getStorageGB());
            } else if (product instanceof Smartphone) {
                Smartphone smartphone = (Smartphone) product;
                specificDetails = String.format("Screen: %.1fin, Camera: %dMP, OS: %s",
                                                smartphone.getScreenSizeInches(), smartphone.getCameraMP(), smartphone.getOperatingSystem());
            }
            // Use String.format for aligned output
            System.out.printf("%-10s %-25s %-10.2f %-10d %-15s %s\n",
                    product.getProductId(),
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getCategory(),
                    specificDetails // Use the determined specificDetails string
            );
        }
    }

    /**
     * Prompts the user for a search term and displays products matching the name.
     */
    private void searchProduct() {
        System.out.println("\n--- Search Product by Name ---");
        System.out.print("Enter search term (product name or part of it): ");
        String searchTerm = scanner.nextLine().trim();

        List<Product> searchResults = inventory.searchProductsByName(searchTerm);
        if (searchResults.isEmpty()) {
            System.out.println("No products found matching '" + searchTerm + "'.");
            return;
        }
        System.out.println("Search Results for '" + searchTerm + "':");
        System.out.printf("%-10s %-25s %-10s %-10s %-15s %s\n", "ID", "Name", "Price", "Qty", "Category", "Specific Details");
        System.out.println("---------------------------------------------------------------------------------------------------");
        for (Product product : searchResults) {
            // Determine specific details string based on product type
            String specificDetails = "";
            if (product instanceof Laptop) {
                Laptop laptop = (Laptop) product;
                specificDetails = String.format("Processor: %s, RAM: %dGB, Storage: %dGB",
                                                laptop.getProcessor(), laptop.getRamGB(), laptop.getStorageGB());
            } else if (product instanceof Smartphone) {
                Smartphone smartphone = (Smartphone) product;
                specificDetails = String.format("Screen: %.1fin, Camera: %dMP, OS: %s",
                                                smartphone.getScreenSizeInches(), smartphone.getCameraMP(), smartphone.getOperatingSystem());
            }
            System.out.printf("%-10s %-25s %-10.2f %-10d %-15s %s\n",
                    product.getProductId(),
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getCategory(),
                    specificDetails // Use the determined specificDetails string
            );
        }
    }

    /**
     * Prompts the user to select a category and displays products belonging to that category.
     */
    private void filterProductsByCategory() {
        System.out.println("\n--- Filter Products by Category ---");
        Set<String> categories = inventory.getAllCategories();
        if (categories.isEmpty()) {
            System.out.println("No categories available yet in inventory.");
            return;
        }
        System.out.println("Available Categories: " + String.join(", ", categories));
        System.out.print("Enter Category to Filter by (or 'All Categories' to show all): ");
        String categoryFilter = scanner.nextLine().trim();

        List<Product> filteredProducts;
        if (categoryFilter.equalsIgnoreCase("All Categories")) {
            filteredProducts = inventory.getAllProducts();
        } else {
            filteredProducts = inventory.filterProductsByCategory(categoryFilter);
        }

        if (filteredProducts.isEmpty()) {
            System.out.println("No products found in category '" + categoryFilter + "'.");
            return;
        }
        System.out.println("Products in category '" + categoryFilter + "':");
        System.out.printf("%-10s %-25s %-10s %-10s %-15s %s\n", "ID", "Name", "Price", "Qty", "Category", "Specific Details");
        System.out.println("---------------------------------------------------------------------------------------------------");
        for (Product product : filteredProducts) {
            // Determine specific details string based on product type
            String specificDetails = "";
            if (product instanceof Laptop) {
                Laptop laptop = (Laptop) product;
                specificDetails = String.format("Processor: %s, RAM: %dGB, Storage: %dGB",
                                                laptop.getProcessor(), laptop.getRamGB(), laptop.getStorageGB());
            } else if (product instanceof Smartphone) {
                Smartphone smartphone = (Smartphone) product;
                specificDetails = String.format("Screen: %.1fin, Camera: %dMP, OS: %s",
                                                smartphone.getScreenSizeInches(), smartphone.getCameraMP(), smartphone.getOperatingSystem());
            }
            System.out.printf("%-10s %-25s %-10.2f %-10d %-15s %s\n",
                    product.getProductId(),
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getCategory(),
                    specificDetails // Use the determined specificDetails string
            );
        }
    }

    /**
     * Displays all recorded stock transactions.
     */
    private void viewTransactions() {
        System.out.println("\n--- All Transactions ---");
        List<Transaction> transactions = inventory.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded yet.");
            return;
        }
        // Print header for clarity
        System.out.printf("%-10s %-15s %-10s %-10s %-20s\n", "Trans ID", "Product ID", "Type", "Quantity", "Date");
        System.out.println("--------------------------------------------------------------------");
        for (Transaction t : transactions) {
            // Using t.toString() here as it already provides the formatted date string
            System.out.printf("%-10s %-15s %-10s %-10d %-20s\n",
                    t.getTransactionId().substring(0, 8) + "...", // Shorten UUID for display
                    t.getProductId(),
                    t.getType(),
                    t.getQuantity(),
                    t.getTransactionDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) // Corrected method call
            );
        }
    }

    /**
     * Helper method to safely parse integer input from the console.
     * Keeps prompting until a valid integer is entered.
     * @return Parsed integer.
     */
    private int parseIntInput() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a whole number: ");
            }
        }
    }

    /**
     * Helper method to safely parse double input from the console.
     * Keeps prompting until a valid double is entered.
     * @return Parsed double.
     */
    private double parseDoubleInput() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number (e.g., 12.34): ");
            }
        }
    }

    /**
     * Main method to run the console application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        ConsoleInventoryManager app = new ConsoleInventoryManager();
        app.start(); // Start the console application
    }
}
