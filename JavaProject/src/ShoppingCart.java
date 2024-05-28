import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Goods, Integer> items;
    private BigDecimal customerMoney;
    
    public ShoppingCart(Map<Goods, Integer> items, BigDecimal customerMoney) {
        this.items = new HashMap<>();
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
}

