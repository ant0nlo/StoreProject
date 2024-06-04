import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Goods, Integer> items;
    private BigDecimal customerMoney;
    
    public ShoppingCart(Map<Goods, Integer> items, BigDecimal customerMoney) {
        this.items = items;
        this.customerMoney = customerMoney;
    }
    
    public Map<Goods, Integer> getItems() {
        return items;
    }

    public BigDecimal getCustomerMoney() {
        return customerMoney;
    }

    public void setCustomerMoney(BigDecimal customerMoney) {
        this.customerMoney = customerMoney;
    }
    
    public void addItem(Goods goods, int quantity) {
        if (items.containsKey(goods)) {
            int currentQuantity = items.get(goods);
            items.put(goods, currentQuantity + quantity);
        } else {
            items.put(goods, quantity);
        }
    }
    
    public void removeItem(Goods goods, int quantity) {
        if (items.containsKey(goods)) {
            int currentQuantity = items.get(goods);
            int newQuantity = Math.max(currentQuantity - quantity, 0);
            if (newQuantity > 0) {
                items.put(goods, newQuantity);
            } else {
                items.remove(goods);
            }
        }
    }
    
    public BigDecimal calculateTotalAmountToPay() {
        BigDecimal totalAmountToPay = BigDecimal.ZERO;

        for (Map.Entry<Goods, Integer> entry : items.entrySet()) {
            Goods goods = entry.getKey();
            int quantity = entry.getValue();

            if (!goods.isExpired()) {
                totalAmountToPay = totalAmountToPay.add(goods.calculateSellingPrice().multiply(BigDecimal.valueOf(quantity)));
            }
        }

        return totalAmountToPay.setScale(2, RoundingMode.HALF_UP);
    }
}
