import java.math.BigDecimal;
import java.util.Map;

public class Checkout  {
    private Cashier cashier;

    public Checkout(Cashier cashier) {
        this.cashier = cashier;
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
        
        receipt.saveReceiptToFile();
        return receipt;

    }

    public Receipt markGoods(ShoppingCart shoppingCart) {
        BigDecimal totalAmountToPay = BigDecimal.ZERO;
        Map<Goods, Integer> items = shoppingCart.getItems();

        for (Map.Entry<Goods, Integer> entry : items.entrySet()) {
            Goods goods = entry.getKey();
            int quantity = entry.getValue();
            totalAmountToPay = totalAmountToPay.add(goods.calculateSellingPrice().multiply(BigDecimal.valueOf(quantity)));
        }

        if (shoppingCart.getCustomerMoney().compareTo(totalAmountToPay) < 0) {
            throw new IllegalArgumentException("Not enough money to buy these goods.");
        }

        return sellGoods(shoppingCart);
    }

}
