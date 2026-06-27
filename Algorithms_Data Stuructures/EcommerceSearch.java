import java.util.Arrays;
import java.util.Scanner;

class Product {
    int id;
    String name;
    String category;
    double price;

    Product(int id, String name, String category, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("  #%d | %-18s | %-12s | $%.2f", id, name, category, price);
    }
}

public class EcommerceSearch {

    static Product[] catalog;
    static Product[] sortedCatalog;
    static Scanner scanner = new Scanner(System.in);
    static int comparisons = 0;

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   WELCOME TO SHOPSMART SEARCH DEMO");
        System.out.println("========================================");
        System.out.println();

        buildCatalog();
        
        System.out.println("We've loaded " + catalog.length + " products into our store.");
        System.out.println("Some are arranged randomly (like a messy warehouse),");
        System.out.println("others are neatly sorted by ID (like organized shelves).");
        System.out.println();

        showMenu();
    }

    static void buildCatalog() {
        catalog = new Product[] {
            new Product(842, "Wireless Earbuds", "Electronics", 59.99),
            new Product(156, "Running Shoes", "Footwear", 89.50),
            new Product(923, "Yoga Mat", "Fitness", 29.99),
            new Product(44,  "Coffee Maker", "Kitchen", 120.00),
            new Product(671, "Denim Jacket", "Clothing", 65.00),
            new Product(305, "Smart Watch", "Electronics", 199.99),
            new Product(518, "Backpack", "Accessories", 45.00),
            new Product(289, "Blender", "Kitchen", 55.00),
            new Product(734, "Sneakers", "Footwear", 75.00),
            new Product(112, "T-Shirt Pack", "Clothing", 25.00),
            new Product(956, "Gaming Mouse", "Electronics", 39.99),
            new Product(63,  "Water Bottle", "Fitness", 15.00)
        };

        sortedCatalog = catalog.clone();
        Arrays.sort(sortedCatalog, (a, b) -> Integer.compare(a.id, b.id));
    }

    static void showMenu() {
        while (true) {
            System.out.println("----------------------------------------");
            System.out.println("What would you like to do?");
            System.out.println("  1. Browse all products (unsorted)");
            System.out.println("  2. Browse all products (sorted by ID)");
            System.out.println("  3. Search by ID - Linear Search");
            System.out.println("  4. Search by ID - Binary Search");
            System.out.println("  5. Compare both search methods");
            System.out.println("  6. Run speed test (large dataset)");
            System.out.println("  0. Exit");
            System.out.println();
            System.out.print("Your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": showProducts(catalog, "unsorted warehouse"); break;
                case "2": showProducts(sortedCatalog, "organized shelves"); break;
                case "3": doLinearSearch(); break;
                case "4": doBinarySearch(); break;
                case "5": compareBoth(); break;
                case "6": runSpeedTest(); break;
                case "0": 
                    System.out.println("\nThanks for visiting ShopSmart! Goodbye.");
                    return;
                default:
                    System.out.println("\nHmm, didn't catch that. Try again?");
            }
        }
    }

    static void showProducts(Product[] products, String description) {
        System.out.println();
        System.out.println("--- Products (" + description + ") ---");
        for (Product p : products) {
            System.out.println(p);
        }
        System.out.println();
    }

    static void doLinearSearch() {
        System.out.println();
        System.out.println("--- Linear Search ---");
        System.out.println("How it works: I check each product one by one,");
        System.out.println("starting from the beginning, until I find it.");
        System.out.println("Best for: small or unsorted collections.");
        System.out.println();

        System.out.print("Enter product ID to search: ");
        int target = readInt();

        comparisons = 0;
        Product found = null;
        int foundAt = -1;

        System.out.println();
        System.out.println("Searching through the warehouse...");
        
        for (int i = 0; i < catalog.length; i++) {
            comparisons++;
            System.out.println("  Checking position " + (i+1) + ": Product #" + catalog[i].id);
            
            if (catalog[i].id == target) {
                found = catalog[i];
                foundAt = i;
                System.out.println("  >> MATCH FOUND! <<");
                break;
            }
        }

        System.out.println();
        if (found != null) {
            System.out.println("✓ Found it after " + comparisons + " checks:");
            System.out.println(found);
        } else {
            System.out.println("✗ Not found. Checked all " + comparisons + " products.");
        }
        System.out.println("Time complexity: O(n) — checked " + comparisons + " out of " + catalog.length);
        System.out.println();
    }

    static void doBinarySearch() {
        System.out.println();
        System.out.println("--- Binary Search ---");
        System.out.println("How it works: I jump to the middle, then decide");
        System.out.println("which half to search next. I keep splitting in half.");
        System.out.println("Best for: large sorted collections.");
        System.out.println();

        System.out.print("Enter product ID to search: ");
        int target = readInt();

        comparisons = 0;
        int left = 0;
        int right = sortedCatalog.length - 1;
        Product found = null;

        System.out.println();
        System.out.println("Searching through organized shelves...");

        while (left <= right) {
            comparisons++;
            int mid = left + (right - left) / 2;
            
            System.out.println("  Step " + comparisons + ": Checking middle position " + (mid+1) + 
                             " (Product #" + sortedCatalog[mid].id + ")");
            System.out.println("  Range: positions " + (left+1) + " to " + (right+1));

            if (sortedCatalog[mid].id == target) {
                found = sortedCatalog[mid];
                System.out.println("  >> MATCH FOUND! <<");
                break;
            }

            if (sortedCatalog[mid].id < target) {
                System.out.println("  " + target + " is larger, so I'll search the RIGHT half");
                left = mid + 1;
            } else {
                System.out.println("  " + target + " is smaller, so I'll search the LEFT half");
                right = mid - 1;
            }
            System.out.println();
        }

        System.out.println();
        if (found != null) {
            System.out.println("✓ Found it after just " + comparisons + " checks:");
            System.out.println(found);
        } else {
            System.out.println("✗ Not found after " + comparisons + " checks.");
        }
        System.out.println("Time complexity: O(log n) — only needed " + comparisons + " checks");
        System.out.println();
    }

    static void compareBoth() {
        System.out.println();
        System.out.println("--- Head-to-Head Comparison ---");
        System.out.println("I'll search for the same product using both methods.");
        System.out.println();

        System.out.print("Enter product ID: ");
        int target = readInt();

        System.out.println("\n=== LINEAR SEARCH ===");
        long t1 = System.nanoTime();
        Product r1 = linearSearchSilent(catalog, target);
        long linearTime = System.nanoTime() - t1;

        System.out.println("Result: " + (r1 != null ? r1.name : "Not found"));
        System.out.println("Checks: " + comparisons);
        System.out.println("Time: " + (linearTime / 1000) + " microseconds");

        System.out.println("\n=== BINARY SEARCH ===");
        long t2 = System.nanoTime();
        Product r2 = binarySearchSilent(sortedCatalog, target);
        long binaryTime = System.nanoTime() - t2;

        System.out.println("Result: " + (r2 != null ? r2.name : "Not found"));
        System.out.println("Checks: " + comparisons);
        System.out.println("Time: " + (binaryTime / 1000) + " microseconds");

        System.out.println("\n--- Winner ---");
        if (linearTime < binaryTime) {
            System.out.println("Linear was faster this time (small dataset advantage)");
        } else if (binaryTime < linearTime) {
            double speedup = (double) linearTime / binaryTime;
            System.out.printf("Binary was %.1fx faster!%n", speedup);
        } else {
            System.out.println("Too close to call!");
        }
        System.out.println();
    }

    static void runSpeedTest() {
        System.out.println();
        System.out.println("--- Large Dataset Speed Test ---");
        System.out.println("Creating 1 million products and searching...");
        System.out.println("(This might take a few seconds)");
        System.out.println();

        int size = 1_000_000;
        Product[] bigArray = new Product[size];
        for (int i = 0; i < size; i++) {
            bigArray[i] = new Product(i, "Product-" + i, "Category", i * 0.01);
        }

        int target = size - 1; // Worst case: last element

        System.out.println("Searching for product #" + target + " (worst case scenario)...");


        long start = System.nanoTime();
        Product linResult = linearSearchSilent(bigArray, target);
        long linearMs = (System.nanoTime() - start) / 1_000_000;


        start = System.nanoTime();
        Product binResult = binarySearchSilent(bigArray, target);
        long binaryMs = (System.nanoTime() - start) / 1_000_000;

        System.out.println();
        System.out.println("Results with " + size + " products:");
        System.out.println("  Linear Search:  " + linearMs + " ms (" + comparisons + " comparisons)");
        System.out.println("  Binary Search:  " + binaryMs + " ms (" + comparisons + " comparisons)");
        
        if (binaryMs > 0) {
            System.out.printf("  Binary was %.0fx faster!%n", (double) linearMs / binaryMs);
        }
        System.out.println();
        System.out.println("This is why Amazon doesn't use linear search ;)");
        System.out.println();
    }


    static Product linearSearchSilent(Product[] arr, int target) {
        comparisons = 0;
        for (Product p : arr) {
            comparisons++;
            if (p.id == target) return p;
        }
        return null;
    }

    static Product binarySearchSilent(Product[] arr, int target) {
        comparisons = 0;
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            comparisons++;
            int mid = left + (right - left) / 2;
            if (arr[mid].id == target) return arr[mid];
            if (arr[mid].id < target) left = mid + 1;
            else right = mid - 1;
        }
        return null;
    }

    static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("That's not a number. Try again: ");
            }
        }
    }
}