import java.math.BigDecimal;
import java.util.Map;

public class Checkout {
    private Cashier cashier;

    public Checkout(Cashier cashier) {
        this.cashier = cashier;
    }

    public Receipt sellGoods(ShoppingCart shoppingCart) {
    	Map<Goods, Integer> items = shoppingCart.getItems();
    	
        BigDecimal totalAmountToPay = shoppingCart.calculateTotalAmountToPay();

        if (shoppingCart.getCustomerMoney().compareTo(totalAmountToPay) < 0) {
            throw new IllegalArgumentException("Not enough money to buy these goods.");
        }

        for (Map.Entry<Goods, Integer> entry : items.entrySet()) {
            Goods goods = entry.getKey();
            int quantity = entry.getValue();

            if (goods.getQuantityAvailable() < quantity) {
                throw new IllegalArgumentException("Not enough quantity available for sale: " + goods.getName());
            }
            
            if (!goods.isExpired()) {
                goods.decreaseQuantity(quantity);
            }
        }

        Receipt receipt = new Receipt(cashier, shoppingCart);
        receipt.saveReceiptToFile();
        return receipt;
    }
}
