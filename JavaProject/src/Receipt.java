import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(serialNumber);
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return serialNumber == receipt.serialNumber;
    }
	
}


