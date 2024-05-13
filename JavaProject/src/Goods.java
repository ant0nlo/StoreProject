import java.time.LocalDate;

public class Goods {
    private int id;
    private String name;
    private double unitDeliveryPrice;
    private Category category;
    private LocalDate expirationDate;
    private double markupPercentage;
    private double discountPercentage;
    private int totalAvailable;
    private int quantityAvailable;

    public Goods(int id, String name, double unitDeliveryPrice, Category category, LocalDate expirationDate,
                 double markupPercentage, double discountPercentage, int totalAvailable) {
        this.id = id;
        this.name = name;
        this.unitDeliveryPrice = unitDeliveryPrice;
        this.category = category;
        this.expirationDate = expirationDate;
        this.markupPercentage = markupPercentage;
        this.discountPercentage = discountPercentage;
        this.totalAvailable = totalAvailable;
        this.quantityAvailable = totalAvailable;
    }
    
    public enum Category {
        EATABLE,
        NON_EDIBLE
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getUnitDeliveryPrice() {
        return unitDeliveryPrice;
    }
    public void setUnitDeliveryPrice(double unitDeliveryPrice) {
        this.unitDeliveryPrice = unitDeliveryPrice;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public LocalDate getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
    public double getMarkupPercentage() {
        return markupPercentage;
    }
    public void setMarkupPercentage(double markupPercentage) {
        this.markupPercentage = markupPercentage;
    }
    public double getDiscountPercentage() {
        return discountPercentage;
    }
    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
    public int getQuantityAvailable() {
        return quantityAvailable;
    }
    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }  
    public int getTotalAvailable() {
        return totalAvailable;
    }
    public void setTotalAvailable(int totalAvailable) {
        this.totalAvailable = totalAvailable;
    }  
    
    public Goods getGoods() {
        return this;
    }
    
    // Method to check if the goods are expired
    public boolean isExpired() {
        return expirationDate != null && LocalDate.now().isAfter(expirationDate);
    }
    
    // Method to decrease quantity when selling goods
    public void decreaseQuantity(int quantitySold) {
        if (quantitySold > quantityAvailable) {
            throw new IllegalArgumentException("Not enough quantity available for sale.");
        }
        quantityAvailable -= quantitySold;
    }
    
    // Method to calculate selling price
    public double calculateSellingPrice() {
        double markupPercentage;
    	if (category == Category.EATABLE) {
            markupPercentage = Store.getMarkupEatable();
        } else if (category == Category.NON_EDIBLE) {
            markupPercentage = Store.getMarkupNonEdible();
        } else {
            throw new IllegalArgumentException("Invalid category");
        }

        double sellingPrice = unitDeliveryPrice * (1 + markupPercentage / 100);
        // Check expiration date proximity and apply discount if needed
        if (expirationDate != null && LocalDate.now().isAfter(expirationDate.minusDays(3))) {
            sellingPrice *= (1 - discountPercentage / 100);
        }
        return sellingPrice;
    }
    
    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unitDeliveryPrice=" + unitDeliveryPrice +
                ", category='" + category + '\'' +
                ", expirationDate=" + expirationDate +
                ", markupPercentage=" + markupPercentage +
                ", discountPercentage=" + discountPercentage +
                ", quantityAvailable=" + quantityAvailable +
                '}';
    }
}
