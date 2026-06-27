import java.util.Scanner;
import java.util.HashMap;

public class FinancialForecasting {

    static Scanner scanner = new Scanner(System.in);
    static HashMap<String, Double> memo = new HashMap<>(); // For memoization
    static int callCount = 0; // Track how many times recursion runs

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║          FINANCIAL FORECASTING WITH RECURSION                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("See how your money grows year by year using recursion!");
        System.out.println();

        while (true) {
            System.out.println("----------------------------------------");
            System.out.println("Choose an option:");
            System.out.println("  1. Simple recursive forecast (slow, shows every step)");
            System.out.println("  2. Memoized recursive forecast (fast, remembers past results)");
            System.out.println("  3. Compare: naive vs optimized recursion");
            System.out.println("  4. See how recursion actually works (trace a small example)");
            System.out.println("  5. Run stress test (large year values)");
            System.out.println("  0. Exit");
            System.out.println();
            System.out.print("Your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": doSimpleForecast(); break;
                case "2": doMemoizedForecast(); break;
                case "3": compareMethods(); break;
                case "4": traceRecursion(); break;
                case "5": stressTest(); break;
                case "0":
                    System.out.println("\nHappy investing! Goodbye.");
                    return;
                default:
                    System.out.println("\nInvalid choice. Try again.");
            }
        }
    }

    // ═════════════════════════════════════════════════════════════
    // NAIVE RECURSION — calls itself every time, no memory
    // ═════════════════════════════════════════════════════════════
    static double futureValueNaive(double principal, double rate, int years) {
        callCount++;

        // BASE CASE: year 0, return what we started with
        if (years == 0) {
            return principal;
        }

        // RECURSIVE CASE: ask "what was last year worth?" then grow it
        double lastYear = futureValueNaive(principal, rate, years - 1);
        double result = lastYear * (1 + rate);

        return result;
    }

    // ═════════════════════════════════════════════════════════════
    // MEMOIZED RECURSION — remembers answers, skips重复 work
    // ═════════════════════════════════════════════════════════════
    static double futureValueMemoized(double principal, double rate, int years) {
        callCount++;

        // BASE CASE
        if (years == 0) {
            return principal;
        }

        // CHECK: did we already calculate this?
        String key = principal + "," + rate + "," + years;
        if (memo.containsKey(key)) {
            return memo.get(key); // Reuse! No extra work
        }

        // RECURSIVE CASE with storage
        double lastYear = futureValueMemoized(principal, rate, years - 1);
        double result = lastYear * (1 + rate);

        // REMEMBER this answer for next time
        memo.put(key, result);
        return result;
    }

    // ═════════════════════════════════════════════════════════════
    // INTERACTIVE FEATURES
    // ═════════════════════════════════════════════════════════════

    static void doSimpleForecast() {
        System.out.println();
        System.out.println("--- Simple Recursive Forecast ---");
        System.out.println("This recalculates everything from scratch each time.");
        System.out.println("Good for understanding, slow for big numbers.");
        System.out.println();

        double principal = readDouble("Initial investment ($): ");
        double rate = readDouble("Annual growth rate (as decimal, e.g. 0.07 for 7%): ");
        int years = readInt("Years to forecast: ");

        if (years > 50) {
            System.out.println("Warning: This might take a while with naive recursion...");
            System.out.print("Continue anyway? (y/n): ");
            if (!scanner.nextLine().trim().toLowerCase().startsWith("y")) return;
        }

        callCount = 0;
        long start = System.nanoTime();
        double result = futureValueNaive(principal, rate, years);
        long timeMs = (System.nanoTime() - start) / 1_000_000;

        System.out.println();
        System.out.println("Results:");
        System.out.printf("  Initial: $%,.2f%n", principal);
        System.out.printf("  After %d years: $%,.2f%n", years, result);
        System.out.printf("  Total growth: $%,.2f (%.1f%%)%n", 
            result - principal, ((result / principal) - 1) * 100);
        System.out.println();
        System.out.println("Performance:");
        System.out.println("  Recursive calls made: " + callCount);
        System.out.println("  Time taken: " + timeMs + " ms");
        System.out.println("  Time complexity: O(n) — one call per year");
        System.out.println();
    }

    static void doMemoizedForecast() {
        System.out.println();
        System.out.println("--- Memoized Recursive Forecast ---");
        System.out.println("This remembers answers so it never calculates the same thing twice.");
        System.out.println("Much faster for repeated queries or large values.");
        System.out.println();

        double principal = readDouble("Initial investment ($): ");
        double rate = readDouble("Annual growth rate (as decimal, e.g. 0.07 for 7%): ");
        int years = readInt("Years to forecast: ");

        memo.clear(); // Fresh start
        callCount = 0;

        long start = System.nanoTime();
        double result = futureValueMemoized(principal, rate, years);
        long timeMs = (System.nanoTime() - start) / 1_000_000;

        System.out.println();
        System.out.println("Results:");
        System.out.printf("  Initial: $%,.2f%n", principal);
        System.out.printf("  After %d years: $%,.2f%n", years, result);
        System.out.printf("  Total growth: $%,.2f%n", result - principal);
        System.out.println();
        System.out.println("Performance:");
        System.out.println("  Recursive calls made: " + callCount);
        System.out.println("  Values stored in memory: " + memo.size());
        System.out.println("  Time taken: " + timeMs + " ms");
        System.out.println("  Effective complexity: O(n) first time, O(1) for repeats");
        System.out.println();
    }

    static void compareMethods() {
        System.out.println();
        System.out.println("--- Head-to-Head Comparison ---");
        System.out.println("Same inputs, two different approaches.");
        System.out.println();

        double principal = readDouble("Initial investment ($): ");
        double rate = readDouble("Annual growth rate (decimal): ");
        int years = readInt("Years to forecast: ");

        System.out.println("\nRunning naive recursion...");
        callCount = 0;
        long t1 = System.nanoTime();
        double r1 = futureValueNaive(principal, rate, years);
        long naiveTime = (System.nanoTime() - t1) / 1_000_000;
        int naiveCalls = callCount;

        System.out.println("Running memoized recursion...");
        memo.clear();
        callCount = 0;
        long t2 = System.nanoTime();
        double r2 = futureValueMemoized(principal, rate, years);
        long memoTime = (System.nanoTime() - t2) / 1_000_000;
        int memoCalls = callCount;

        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     COMPARISON RESULTS                       ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.printf("║  Result:           $%,-52.2f ║%n", r1);
        System.out.println("╠════════════════════╦═══════════════════╦═════════════════════╣");
        System.out.println("║      Metric        ║   Naive Recursion ║  Memoized Recursion ║");
        System.out.println("╠════════════════════╬═══════════════════╬═════════════════════╣");
        System.out.printf("║  Time (ms)         ║  %-15d  ║  %-17d  ║%n", naiveTime, memoTime);
        System.out.printf("║  Recursive calls   ║  %-15d  ║  %-17d  ║%n", naiveCalls, memoCalls);
        System.out.printf("║  Memory used       ║  %-15s  ║  %-17d  ║%n", "Minimal", memo.size());
        System.out.println("╚════════════════════╩═══════════════════╩═════════════════════╝");

        if (naiveTime > 0 && memoTime > 0) {
            System.out.printf("%nMemoization was %.1fx faster in time!%n", (double) naiveTime / memoTime);
        }
        System.out.println();
    }

    static void traceRecursion() {
        System.out.println();
        System.out.println("--- How Recursion Actually Works ---");
        System.out.println("Let's trace a small example: $1000 at 10% for 3 years");
        System.out.println();
        System.out.println("The call stack builds UP, then resolves DOWN:");
        System.out.println();

        double principal = 1000;
        double rate = 0.10;
        int years = 3;

        System.out.println("futureValue(1000, 0.10, 3)");
        System.out.println("  → calls futureValue(1000, 0.10, 2)");
        System.out.println("      → calls futureValue(1000, 0.10, 1)");
        System.out.println("          → calls futureValue(1000, 0.10, 0)");
        System.out.println("              → BASE CASE! Returns 1000");
        System.out.println("          ← 1000 × 1.10 = 1100.00");
        System.out.println("      ← 1100.00 × 1.10 = 1210.00");
        System.out.println("  ← 1210.00 × 1.10 = 1331.00");
        System.out.println();
        System.out.println("Final answer: $1,331.00");

        callCount = 0;
        double result = futureValueNaive(principal, rate, years);
        System.out.println("Verified: $" + String.format("%,.2f", result));
        System.out.println("Recursive calls needed: " + callCount + " (equals years + 1)");
        System.out.println();
        System.out.println("Notice: for N years, we make N+1 calls.");
        System.out.println("That's why it's O(n) time complexity.");
        System.out.println();
    }

    static void stressTest() {
        System.out.println();
        System.out.println("--- Stress Test: Large Year Values ---");
        System.out.println("Testing with 10,000 years forecast...");
        System.out.println("Naive recursion will crash with StackOverflow!");
        System.out.println("Memoized version handles it fine.");
        System.out.println();

        double principal = 1000;
        double rate = 0.05;
        int years = 10000;

        // Try memoized first
        System.out.println("Running memoized version with " + years + " years...");
        memo.clear();
        callCount = 0;
        long start = System.nanoTime();
        double result = futureValueMemoized(principal, rate, years);
        long timeMs = (System.nanoTime() - start) / 1_000_000;

        System.out.println();
        System.out.printf("Result after %,d years: $%,.2f%n", years, result);
        System.out.println("Calls made: " + callCount);
        System.out.println("Time: " + timeMs + " ms");
        System.out.println();

        // Explain why naive fails
        System.out.println("Why naive recursion fails here:");
        System.out.println("  Java's default stack size is ~1MB");
        System.out.println("  Each recursive call uses some stack space");
        System.out.println("  10,000 calls × stack frame size = StackOverflowError!");
        System.out.println();
        System.out.println("Solutions:");
        System.out.println("  1. Memoization (what we just did)");
        System.out.println("  2. Iterative loop (no stack growth)");
        System.out.println("  3. Tail recursion optimization (Java doesn't do this well)");
        System.out.println();
    }

    // ═════════════════════════════════════════════════════════════
    // HELPER METHODS
    // ═════════════════════════════════════════════════════════════

    static double readDouble(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }

    static int readInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid whole number: ");
            }
        }
    }
}