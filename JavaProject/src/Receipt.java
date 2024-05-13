/*
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private static int serialNumberCounter = 1;
    private int serialNumber;
    private Cashier issuingCashier;
    private LocalDateTime issuanceDateTime;
    private List<Goods> items;
    private List<Integer> quantityToSell; // Променлива за съхранение на броя на всяка от стоките, които потребителя иска да купи
    private double totalAmountPaid;

    // Constructor
    public Receipt(Cashier issuingCashier, List<Goods> items, List<Integer> quantityToSell) {
        this.serialNumber = serialNumberCounter++;
        this.issuingCashier = issuingCashier;
        this.issuanceDateTime = LocalDateTime.now();
        this.items = new ArrayList<>(items);
        this.quantityToSell = new ArrayList<>(quantityToSell); // Инициализиране на променливата за съхранение на броя продукти за продажба
        calculateTotalAmountPaid();
    }

    // Getters
    public int getSerialNumber() {
        return serialNumber;
    }

    public Cashier getIssuingCashier() {
        return issuingCashier;
    }

    public LocalDateTime getIssuanceDateTime() {
        return issuanceDateTime;
    }

    public List<Goods> getItems() {
        return items;
    }

    public List<Integer> getQuantityToSell() {
        return quantityToSell;
    }

    public double getTotalAmountPaid() {
        return totalAmountPaid;
    }

    // Method to calculate the total amount paid
    private void calculateTotalAmountPaid() {
        totalAmountPaid = 0;
        for (int i = 0; i < items.size(); i++) {
            Goods item = items.get(i);
            int quantity = quantityToSell.get(i); // Вземаме броя на всяка стока, която потребителя иска да купи
            totalAmountPaid += item.calculateSellingPrice() * quantity; // Умножаваме цената на стоката по броя продукти за продажба
        }
    }

    // Method to generate receipt content
    public String generateReceiptContent() {
        StringBuilder receiptContent = new StringBuilder();
        receiptContent.append("Receipt Serial Number: ").append(serialNumber).append("\n");
        receiptContent.append("Issued by: ").append(issuingCashier.getName()).append("\n");
        receiptContent.append("Date and Time of Issuance: ").append(issuanceDateTime).append("\n\n");
        receiptContent.append("Items:\n");
        for (int i = 0; i < items.size(); i++) {
            Goods item = items.get(i);
            int quantity = quantityToSell.get(i); // Вземаме броя на всяка стока, която потребителя иска да купи
            receiptContent.append(item.getName()).append(" price: $").append(item.getUnitDeliveryPrice()).append(" quantity: ").append(quantity).append("\n");
        }
        receiptContent.append("\nTotal: $").append(totalAmountPaid).append("\n");
        return receiptContent.toString();
    }

    // Method to save receipt to a file
    public void saveReceiptToFile() {
        String filename = "Receipt_" + serialNumber + ".txt";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(generateReceiptContent());
        } catch (IOException e) {
            System.err.println("Error occurred while saving receipt to file: " + e.getMessage());
        }
    }
    
}
*/
/*
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private static int serialNumberCounter = 1;
    private int serialNumber;
    private Cashier issuingCashier;
    private LocalDateTime issuanceDateTime;
    private ShoppingCart shoppingCart;
    private double totalAmountPaid;

    // Constructor
    public Receipt(Cashier issuingCashier, ShoppingCart shoppingCart) {
        this.serialNumber = serialNumberCounter++;
        this.issuingCashier = issuingCashier;
        this.issuanceDateTime = LocalDateTime.now();
        this.shoppingCart = shoppingCart;
        calculateTotalAmountPaid();
    }

    // Getters
    public int getSerialNumber() {
        return serialNumber;
    }

    public Cashier getIssuingCashier() {
        return issuingCashier;
    }

    public LocalDateTime getIssuanceDateTime() {
        return issuanceDateTime;
    }

    public double getTotalAmountPaid() {
        return totalAmountPaid;
    }

    
    // Method to calculate the total amount paid
    private void calculateTotalAmountPaid() {
        totalAmountPaid = 0;
            for (int i = 0; i < shoppingCart.getGoodsList().size(); i++) {
                Goods item = shoppingCart.getGoodsList().get(i);
                int quantity = shoppingCart.getQuantities().get(i);
                totalAmountPaid += item.calculateSellingPrice() * quantity;
        }
    }

    // Method to generate receipt content
    public String generateReceiptContent() {
        StringBuilder receiptContent = new StringBuilder();
        receiptContent.append("Receipt Serial Number: ").append(serialNumber).append("\n");
        receiptContent.append("Issued by: ").append(issuingCashier.getName()).append("\n");
        receiptContent.append("Date and Time of Issuance: ").append(issuanceDateTime).append("\n\n");
        receiptContent.append("Items:\n");
        	List<Goods> goodsList = shoppingCart.getGoodsList();
            List<Integer> quantities = shoppingCart.getQuantities();
            for (int i = 0; i < goodsList.size(); i++) {
                Goods goods = goodsList.get(i);
                int quantity = quantities.get(i);
            receiptContent.append(goods.getName()).append(" price: $").append(goods.getUnitDeliveryPrice()).append(" quantity: ").append(quantity).append("\n");
        }
        receiptContent.append("\nTotal: $").append(totalAmountPaid).append("\n");
        return receiptContent.toString();

    } 
    
    // Method to save receipt to a file
    public void saveReceiptToFile() {
        String filename = "Receipt_" + serialNumber + ".txt";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(generateReceiptContent());
        } catch (IOException e) {
            System.err.println("Error occurred while saving receipt to file: " + e.getMessage());
        }
    }

}
*/

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public class Receipt {
    private static int serialNumberCounter = 1;
    private int serialNumber;
    private Cashier issuingCashier;
    private LocalDateTime issuanceDateTime;
    private ShoppingCart shoppingCart;
    private double totalAmountPaid;

    // Constructor
    public Receipt(Cashier issuingCashier, ShoppingCart shoppingCart) {
        this.serialNumber = serialNumberCounter++;
        this.issuingCashier = issuingCashier;
        this.issuanceDateTime = LocalDateTime.now();
        this.shoppingCart = shoppingCart;
        calculateTotalAmountPaid();
    }

    // Getters
    public int getSerialNumber() {
        return serialNumber;
    }

    public Cashier getIssuingCashier() {
        return issuingCashier;
    }

    public LocalDateTime getIssuanceDateTime() {
        return issuanceDateTime;
    }

    public double getTotalAmountPaid() {
        return totalAmountPaid;
    }
    
    // Setters
    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setIssuingCashier(Cashier issuingCashier) {
        this.issuingCashier = issuingCashier;
    }

    public void setIssuanceDateTime(LocalDateTime issuanceDateTime) {
        this.issuanceDateTime = issuanceDateTime;
    }

    public void setTotalAmountPaid(double totalAmountPaid) {
        this.totalAmountPaid = totalAmountPaid;
    }

    // Method to calculate the total amount paid
    private void calculateTotalAmountPaid() {
        totalAmountPaid = 0;
        Map<Goods, Integer> items = shoppingCart.getItems();
        
        for (Map.Entry<Goods, Integer> entry : items.entrySet()) {
            Goods goods = entry.getKey();
            int quantity = entry.getValue();
            totalAmountPaid += goods.calculateSellingPrice() * quantity;
        }
    }

    // Method to generate receipt content
    public String generateReceiptContent() {
        StringBuilder receiptContent = new StringBuilder();
        receiptContent.append("         RECEIPT").append("\n");
        receiptContent.append(String.valueOf("=").repeat(25) + "\n");
        receiptContent.append("Serial Number: ").append(serialNumber).append("\n");
        if (issuingCashier.getName().length() < 14) {
        	receiptContent.append("Issued by: ").append(issuingCashier.getName()).append("\n");
        } else {
        	receiptContent.append("Issued by: ").append("\n");
        	receiptContent.append(" " + issuingCashier.getName()).append("\n");
        }
        receiptContent.append(String.valueOf("-").repeat(25)).append("\n");
        receiptContent.append("Date: ").append(issuanceDateTime.toString().substring(0, 10)).append("\n");
        receiptContent.append("Time: ").append(issuanceDateTime.toString().substring(11, 19)).append("\n");
        receiptContent.append(String.valueOf("-").repeat(25)).append("\n");
        
        Map<Goods, Integer> items = shoppingCart.getItems();
        for (Map.Entry<Goods, Integer> entry : items.entrySet()) {
            Goods goods = entry.getKey();
            int quantity = entry.getValue();
            
            receiptContent.append("-" + quantity + " ")
            			  .append(goods.getName())
            			  .append(numberOfSpaces(goods.getName()))
            			  .append(" $")
            			  .append(goods.getUnitDeliveryPrice())
            			  .append("\n");
        }
        receiptContent.append(String.valueOf("=").repeat(25));
        
        receiptContent.append("\n TOTAL:")
        			  .append(numberOfSpaces(shoppingCart.getCustomerMoney()))
        			  .append(" $").append(totalAmountPaid)
        			  .append("\n");
        receiptContent.append("Cash:")
        			  .append(numberOfSpaces(shoppingCart.getCustomerMoney()))
        			  .append("   $").append(shoppingCart.getCustomerMoney())
        			  .append("\n");
        receiptContent.append("Change:")
        			  .append(numberOfSpaces(shoppingCart.getCustomerMoney()))
        			  .append(" $").append(shoppingCart.getCustomerMoney() - totalAmountPaid)
        			  .append("\n");
        
        return receiptContent.toString();
    }

    // Method for the spaces
    public String numberOfSpaces(Object getNameResult) {
    	String valueAsString = String.valueOf(getNameResult);
        int spacesNeeded = Math.max(0, 15 - valueAsString.length());
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < spacesNeeded; i++) {
            spaces.append(" ");
        }
        return spaces.toString();
    }
    
    // Method to save receipt to a file
    public void saveReceiptToFile() {
        String filename = "Receipt_" + serialNumber + ".txt";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(generateReceiptContent());
        } catch (IOException e) {
            System.err.println("Error occurred while saving receipt to file: " + e.getMessage());
        }
    }
}


