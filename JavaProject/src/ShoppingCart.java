/*
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Goods> goodsList;
    private List<Integer> quantities;
    private double customerMoney;
    
    public ShoppingCart(List<Goods> goodsList, List<Integer> quantities, double customerMoney) {
        this.goodsList = new ArrayList<>();
        this.quantities = new ArrayList<>();
        this.customerMoney = customerMoney;
    }
    
    public void addItem(Goods goods, int quantity) {
        goodsList.add(goods);
        quantities.add(quantity);
    }
    
    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public double getCustomerMoney() {
        return customerMoney;
    }

    public void setCustomerMoney(double customerMoney) {
        this.customerMoney = customerMoney;
    }
}
*/
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

