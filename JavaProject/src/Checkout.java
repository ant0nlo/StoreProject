/*
import java.util.List;

public class Checkout  {
    private Cashier cashier;
    private Store store;

    public Checkout(Cashier cashier, Store store) {
        this.cashier = cashier;
        this.store = store;
    }

    public void sellGoods(List<Goods> goodsList, List<Integer> quantityToSell) {
        Receipt receipt = new Receipt(cashier, goodsList, quantityToSell);
        for (int i = 0; i < goodsList.size(); i++) {
            Goods item = goodsList.get(i);
            Goods goods = item.getGoods();
            int quantity = quantityToSell.get(i);
            if (goods.getQuantityAvailable() < quantity) {
                throw new IllegalArgumentException("Not enough quantity available for sale: " + goods.getName());
            }
            if (goods.isExpired()) {
                throw new IllegalArgumentException("The item " + goods.getName() + " has expired and cannot be sold.");
            }
            goods.decreaseQuantity(quantity);
        }
        store.setTotalTurnover(store.getTotalTurnover() + receipt.getTotalAmountPaid()); // Актуализиране на общия оборот на магазина
        store.getReceipts().add(receipt);
        receipt.saveReceiptToFile();
    }
    
    public void markGoods(List<Goods> goodsList, List<Integer> quantityToSell, double buyerMoney) {
        double totalAmountToPay = 0;
        for (int i = 0; i < goodsList.size(); i++) {
            Goods item = goodsList.get(i);
            int quantity = quantityToSell.get(i);
            totalAmountToPay += item.calculateSellingPrice() * quantity;
        }
        
        if (buyerMoney < totalAmountToPay) {
            throw new IllegalArgumentException("Not enough money to buy these goods.");
        } else {
            sellGoods(goodsList, quantityToSell);
        }
    }
    
}
*/
/*
import java.util.List;

public class Checkout  {
    private Cashier cashier;
    private Store store;

    public Checkout(Cashier cashier, Store store) {
        this.cashier = cashier;
        this.store = store;
    }

    public void sellGoods(ShoppingCart shoppingCart) {
            Receipt receipt = new Receipt(cashier, shoppingCart);
           
            for (int i = 0; i < shoppingCart.getGoodsList().size(); i++) {
                Goods item = shoppingCart.getGoodsList().get(i);
                Goods goods = item.getGoods();
                int quantity = shoppingCart.getQuantities().get(i);	

                
                if (goods.getQuantityAvailable() < quantity) {
                    throw new IllegalArgumentException("Not enough quantity available for sale: " + goods.getName());
                }
                if (goods.isExpired()) {
                    throw new IllegalArgumentException("The item " + goods.getName() + " has expired and cannot be sold.");
                }
                goods.decreaseQuantity(quantity);
            }
            store.setTotalTurnover(store.getTotalTurnover() + receipt.getTotalAmountPaid()); // Актуализиране на общия оборот на магазина
            store.getReceipts().add(receipt);
            receipt.saveReceiptToFile();
    }

    public void markGoods(ShoppingCart shoppingCart) {
        double totalAmountToPay = 0;
            for (int i = 0; i < shoppingCart.getGoodsList().size(); i++) {
                Goods item = shoppingCart.getGoodsList().get(i);
                int quantity = shoppingCart.getQuantities().get(i);
                totalAmountToPay += item.calculateSellingPrice() * quantity;
            }
        
            if (shoppingCart.getCustomerMoney() < totalAmountToPay) {
                throw new IllegalArgumentException("Not enough money to buy these goods.");
        }
        sellGoods(shoppingCart); 
    }


}
*/
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public class Checkout  {
    private Cashier cashier;
    private Store store;

    public Checkout(Cashier cashier, Store store) {
        this.cashier = cashier;
        this.store = store;
    }

    public void sellGoods(ShoppingCart shoppingCart) {
        Receipt receipt = new Receipt(cashier, shoppingCart);
        Map<Goods, Integer> items = shoppingCart.getItems();
        
        for (Map.Entry<Goods, Integer> entry : items.entrySet()) {
            Goods goods = entry.getKey();
            int quantity = entry.getValue();
            
            if (goods.getQuantityAvailable() < quantity) {
                throw new IllegalArgumentException("Not enough quantity available for sale: " + goods.getName());
            }
            if (goods.isExpired()) {
                throw new IllegalArgumentException("The item " + goods.getName() + " has expired and cannot be sold.");
            }
            goods.decreaseQuantity(quantity);
        }
        
        store.setTotalTurnover(store.getTotalTurnover() + receipt.getTotalAmountPaid());
        store.getReceipts().add(receipt);
        receipt.saveReceiptToFile();
    }

    public void markGoods(ShoppingCart shoppingCart) {
        double totalAmountToPay = 0;
        Map<Goods, Integer> items = shoppingCart.getItems();
        
        for (Map.Entry<Goods, Integer> entry : items.entrySet()) {
            Goods goods = entry.getKey();
            int quantity = entry.getValue();
            totalAmountToPay += goods.calculateSellingPrice() * quantity;
        }
        
        if (shoppingCart.getCustomerMoney() < totalAmountToPay) {
            throw new IllegalArgumentException("Not enough money to buy these goods.");
        }
        
        sellGoods(shoppingCart); 
    }
}
