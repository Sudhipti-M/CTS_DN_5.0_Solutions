class Logger {
    

    private static Logger instance;
    

    private Logger() {
        System.out.println("Logger initialized!");
    }
    

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    
    public void info(String message) {
        System.out.println("[INFO] " + message);
    }
    
    public void warn(String message) {
        System.out.println("[WARN] " + message);
    }
    
    public void error(String message) {
        System.out.println("[ERROR] " + message);
    }
}


public class SingletonPattern {
    
    public static void main(String[] args) {
        System.out.println("=== Singleton Pattern Test ===\n");
        

        System.out.println("Test 1: Multiple getInstance() calls");
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        Logger logger3 = Logger.getInstance();
        

        System.out.println("\n--- Reference Comparison ---");
        System.out.println("logger1 == logger2: " + (logger1 == logger2));
        System.out.println("logger1 == logger3: " + (logger1 == logger3));
        System.out.println("logger1 hashCode: " + logger1.hashCode());
        System.out.println("logger2 hashCode: " + logger2.hashCode());
        System.out.println("logger3 hashCode: " + logger3.hashCode());
        

        System.out.println("\n--- Logging Test ---");
        logger1.info("Application started");
        logger2.info("User logged in");
        logger3.warn("Low memory detected");
        logger1.error("Database connection failed");
        

        System.out.println("\n--- Multi-Service Simulation ---");
        simulateOrderService();
        simulatePaymentService();
        

        System.out.println("\n--- Constructor Access Test ---");
        System.out.println("Try uncommenting: // Logger bad = new Logger();");
        System.out.println("It will cause a COMPILE ERROR!");
    }
    
    static void simulateOrderService() {
        Logger log = Logger.getInstance();
        log.info("[OrderService] Processing order #12345");
    }
    
    static void simulatePaymentService() {
        Logger log = Logger.getInstance();
        log.info("[PaymentService] Charging $99.99");
    }
}