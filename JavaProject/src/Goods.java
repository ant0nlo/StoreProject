import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public class Goods {
    private int id;
    private String name;
    private BigDecimal unitDeliveryPrice;
    private Category category;
    private LocalDate expirationDate;
    private double discountPercentage;
    private double markupPercentage;
    private int totalDelivered;
    private int quantityAvailable;

    public Goods(int id, String name, BigDecimal unitDeliveryPrice, Category category, LocalDate expirationDate,
    		double discountPercentage, int totalDelivered) {
        this.id = id;
        this.name = name;
        this.unitDeliveryPrice = unitDeliveryPrice;
        this.category = category;
        this.expirationDate = expirationDate;
        this.discountPercentage = discountPercentage;
        this.totalDelivered = totalDelivered;
        this.quantityAvailable = totalDelivered;
        this.markupPercentage = category.getValue();
    }
    
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
    public BigDecimal getUnitDeliveryPrice() {
        return unitDeliveryPrice;
    }
    public void setUnitDeliveryPrice(BigDecimal unitDeliveryPrice) {
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
    public int getTotalDelivered() {
        return totalDelivered;
    }
    public void setTotalDelivered(int totalAvailable) {
        this.totalDelivered = totalAvailable;
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
        quantityAvailable-= quantitySold;
    }

    // Method to calculate selling price
    public BigDecimal calculateSellingPrice() {
    	if (unitDeliveryPrice == null) {
            throw new IllegalStateException("Unit delivery price is not set.");
        }
    	
        BigDecimal markupPercentage = BigDecimal.valueOf(getCategory().getValue());
        BigDecimal sellingPrice = unitDeliveryPrice.multiply(
                BigDecimal.ONE.add(markupPercentage.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP))
        );

        if (expirationDate != null && LocalDate.now().isAfter(expirationDate.minusDays(3))) {
            BigDecimal discount = BigDecimal.valueOf(discountPercentage).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            sellingPrice = sellingPrice.multiply(BigDecimal.ONE.subtract(discount));
        }

        return sellingPrice.setScale(2, RoundingMode.HALF_UP);
    }
     
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return id == goods.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unitDeliveryPrice=" + unitDeliveryPrice +
                ", category='" + category.getName() + '\'' +
                ", expirationDate=" + expirationDate +
                ", markupPercentage=" + markupPercentage +
                ", discountPercentage=" + discountPercentage +
                ", quantityAvailable=" + quantityAvailable +
                '}';
    }
}
