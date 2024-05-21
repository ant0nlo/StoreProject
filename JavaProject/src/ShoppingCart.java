import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Goods, Integer> items;
    private double customerMoney;
    
    public ShoppingCart(Map<Goods, Integer> items, double customerMoney) {
        this.items = new HashMap<>();
        this.customerMoney = customerMoney;
    }
    
    public Map<Goods, Integer> getItems() {
        return items;
    }

    public double getCustomerMoney() {
        return customerMoney;
    }

    public void setCustomerMoney(double customerMoney) {
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

