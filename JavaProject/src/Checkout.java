
import java.util.Map;

public class Checkout  {
    private Cashier cashier;
    private Store store;

    public Checkout(Cashier cashier, Store store) {
        this.cashier = cashier;
        this.store = store;
    }

    public Receipt sellGoods(ShoppingCart shoppingCart) {
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
        return receipt;

    }

    public Receipt markGoods(ShoppingCart shoppingCart) {
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
        
        return sellGoods(shoppingCart); 
    }
}
