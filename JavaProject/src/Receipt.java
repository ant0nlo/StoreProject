import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class Receipt {
    private static int serialNumberCounter = 1;
    private int serialNumber;
    private Cashier issuingCashier;
    private LocalDateTime issuanceDateTime;
    private ShoppingCart shoppingCart;
    private BigDecimal totalAmountPaid;

    public Receipt(Cashier issuingCashier, ShoppingCart shoppingCart) {
        this.serialNumber = serialNumberCounter++;
        this.issuingCashier = issuingCashier;
        this.issuanceDateTime = LocalDateTime.now();
        this.shoppingCart = shoppingCart;
        this.totalAmountPaid = shoppingCart.calculateTotalAmountToPay();
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public Cashier getIssuingCashier() {
        return issuingCashier;
    }

    public LocalDateTime getIssuanceDateTime() {
        return issuanceDateTime;
    }

    public BigDecimal getTotalAmountPaid() {
        return totalAmountPaid;
    }
    
    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setIssuingCashier(Cashier issuingCashier) {
        this.issuingCashier = issuingCashier;
    }

    public void setIssuanceDateTime(LocalDateTime issuanceDateTime) {
        this.issuanceDateTime = issuanceDateTime;
    }

    public void setTotalAmountPaid(BigDecimal totalAmountPaid) {
        this.totalAmountPaid = totalAmountPaid;
    }

    // Method to generate receipt content
    public String generateReceiptContent() {
    	int numOfChars = 25;
        StringBuilder receiptContent = new StringBuilder();
        receiptContent.append("         RECEIPT").append("\n");
        receiptContent.append(String.valueOf("=").repeat(numOfChars) + "\n");
        receiptContent.append("Serial Number: ").append(serialNumber).append("\n");
        if (issuingCashier.getName().length() < 14) {
        	receiptContent.append("Issued by: ").append(issuingCashier.getName()).append("\n");
        } else {
        	receiptContent.append("Issued by: ").append("\n");
        	receiptContent.append(" " + issuingCashier.getName()).append("\n");
        }
        receiptContent.append(String.valueOf("-").repeat(numOfChars)).append("\n");
        receiptContent.append("Date: ").append(issuanceDateTime.toString().substring(0, 10)).append("\n");
        receiptContent.append("Time: ").append(issuanceDateTime.toString().substring(11, 19)).append("\n");
        receiptContent.append(String.valueOf("-").repeat(numOfChars)).append("\n");
        
        Map<Goods, Integer> items = shoppingCart.getItems();
        
        for (Map.Entry<Goods, Integer> entry : items.entrySet()) {
            Goods goods = entry.getKey();
            int quantity = entry.getValue();
            
            String[] nameParts = splitName(goods.getName());
            receiptContent.append("-").append(quantity).append(" ");
            for (String part : nameParts) {
            	receiptContent.append(part).append(numberOfSpaces(part));
                if (part.equals(nameParts[nameParts.length - 1])) {
                    receiptContent.append(" $").append(goods.getUnitDeliveryPrice());
                }
                receiptContent.append("\n");
            }
            
        }
        receiptContent.append(String.valueOf("=").repeat(numOfChars));
        
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
        			  .append(" $").append(shoppingCart.getCustomerMoney().subtract(totalAmountPaid) )
        			  .append("\n");
        
        return receiptContent.toString();
    }

    private String[] splitName(String name) {
        if (name.length() <= 15) {
            return new String[] {name};
        }

        int middleIndex = name.length() / 2;
        int splitIndex = middleIndex;
        // Търсим интервал най-близо до средата, но не след средата
        while (splitIndex > 0 && name.charAt(splitIndex) != ' ') {
            splitIndex--;
        }
        // Ако няма интервал, който да разделя текста преди средата, използваме средата
        if (splitIndex == 0) {
            splitIndex = middleIndex;
        }
        String firstPart = name.substring(0, splitIndex).trim();
        String secondPart = name.substring(splitIndex).trim();
        return new String[] {firstPart, secondPart};
    }

    // Method for the spaces
    public String numberOfSpaces(Object getNameResult) {
    	String valueAsString = String.valueOf(getNameResult);
        int spacesNeeded = 15 - valueAsString.length();
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < spacesNeeded; i++) {
            spaces.append(" ");
        }
        return spaces.toString();
    }
    
    // Method to save receipt to a file
    public void saveReceiptToFile() {
        String filename = "Receipts/Receipt_" + serialNumber + ".txt";
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