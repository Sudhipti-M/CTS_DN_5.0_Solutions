interface Document {
    void open();
    void save();
    void close();
    String getType();
}


class WordDocument implements Document {
    @Override
    public void open() {
        System.out.println("Opening Word document (.docx)...");
    }
    
    @Override
    public void save() {
        System.out.println("Saving Word document with formatting...");
    }
    
    @Override
    public void close() {
        System.out.println("Closing Word document.");
    }
    
    @Override
    public String getType() {
        return "Word Document";
    }
}

class PdfDocument implements Document {
    @Override
    public void open() {
        System.out.println("Opening PDF document (.pdf)...");
    }
    
    @Override
    public void save() {
        System.out.println("Saving PDF document with compression...");
    }
    
    @Override
    public void close() {
        System.out.println("Closing PDF document.");
    }
    
    @Override
    public String getType() {
        return "PDF Document";
    }
}

class ExcelDocument implements Document {
    @Override
    public void open() {
        System.out.println("Opening Excel spreadsheet (.xlsx)...");
    }
    
    @Override
    public void save() {
        System.out.println("Saving Excel with formulas and charts...");
    }
    
    @Override
    public void close() {
        System.out.println("Closing Excel spreadsheet.");
    }
    
    @Override
    public String getType() {
        return "Excel Document";
    }
}

abstract class DocumentFactory {
    
    abstract Document createDocument();
    
    public Document createAndOpen() {
        Document doc = createDocument();
        System.out.println("Factory created: " + doc.getType());
        doc.open();
        return doc;
    }
}


class WordFactory extends DocumentFactory {
    @Override
    Document createDocument() {
        return new WordDocument();
    }
}

class PdfFactory extends DocumentFactory {
    @Override
    Document createDocument() {
        return new PdfDocument();
    }
}

class ExcelFactory extends DocumentFactory {
    @Override
    Document createDocument() {
        return new ExcelDocument();
    }
}

public class FactoryMethodPattern {
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   FACTORY METHOD PATTERN DEMO");
        System.out.println("========================================\n");
        
        System.out.println("--- Test 1: Creating Documents via Factories ---\n");
        
        DocumentFactory wordFactory = new WordFactory();
        DocumentFactory pdfFactory = new PdfFactory();
        DocumentFactory excelFactory = new ExcelFactory();
        
        Document wordDoc = wordFactory.createAndOpen();
        wordDoc.save();
        wordDoc.close();
        System.out.println();
        
        Document pdfDoc = pdfFactory.createAndOpen();
        pdfDoc.save();
        pdfDoc.close();
        System.out.println();
        
        Document excelDoc = excelFactory.createAndOpen();
        excelDoc.save();
        excelDoc.close();
        System.out.println();

        System.out.println("--- Test 2: Dynamic Factory Selection ---\n");
        
        String[] requestedTypes = {"word", "pdf", "excel", "unknown"};
        
        for (String type : requestedTypes) {
            System.out.println("Requesting: " + type);
            DocumentFactory factory = getFactory(type);
            if (factory != null) {
                Document doc = factory.createAndOpen();
                doc.close();
            } else {
                System.out.println(" Unknown document type: " + type);
            }
            System.out.println();
        }

        System.out.println("--- Test 3: Polymorphism Test ---\n");
        
        DocumentFactory[] factories = {
            new WordFactory(),
            new PdfFactory(),
            new ExcelFactory()
        };
        
        for (DocumentFactory factory : factories) {
            processDocument(factory);
        }
    }

    static DocumentFactory getFactory(String type) {
        switch (type.toLowerCase()) {
            case "word":  return new WordFactory();
            case "pdf":   return new PdfFactory();
            case "excel": return new ExcelFactory();
            default:      return null;
        }
    }

    static void processDocument(DocumentFactory factory) {
        Document doc = factory.createDocument();
        System.out.println("Processing " + doc.getType() + ":");
        doc.open();
        doc.save();
        doc.close();
        System.out.println();
    }
}