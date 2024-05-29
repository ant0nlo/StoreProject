

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

class CheckoutTest {

    private Cashier cashier;
    private Store store;
    private Checkout checkout;
    private ShoppingCart shoppingCart;
    private Goods goods1;
    private Goods goods2;

    @BeforeEach
    public void setUp() {
        cashier = new Cashier(1, "John Doe", 1000);
        store = new Store();
        Store.setMarkup(Category.EATABLE, 20); 

        goods1 = new Goods(1, "Apple",  new BigDecimal("1.00"), Category.EATABLE, LocalDate.now().plusDays(5), 5, 100);
        goods2 = new Goods(2, "Milk",  new BigDecimal("2.00"), Category.EATABLE, LocalDate.now().plusDays(5), 5, 100);
        store.addGoods(goods1);
        store.addGoods(goods2);
        
        Map<Goods, Integer> items = new HashMap<>();
        shoppingCart = new ShoppingCart(items, new BigDecimal("100.00"));
        shoppingCart.addItem(goods1, 2);
        shoppingCart.addItem(goods2, 3);
        checkout = new Checkout(cashier);
    }

    @Test
    public void testSellGoodsSuccess() {
    	Receipt receipt = store.checkoutClient(checkout, shoppingCart);

        assertEquals(98, goods1.getQuantityAvailable());
        assertEquals(97, goods2.getQuantityAvailable());
        assertEquals(new BigDecimal("9.60"), receipt.getTotalAmountPaid());
        assertEquals(1, store.getReceipts().size());

    }
    
    @Test
    public void testSellGoodsNotEnoughQuantity() {
        goods1.setQuantityAvailable(1); // Not enough quantity

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	store.checkoutClient(checkout, shoppingCart);
        });
        assertEquals("Not enough quantity available for sale: Apple", thrown.getMessage());
    }

    @Test
    public void testSellGoodsItemExpired() {
        store.setExpirationDateInStore(goods1, LocalDate.now().minusDays(1)); // Item expired

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	store.checkoutClient(checkout, shoppingCart);
        });
        assertEquals("The item Apple has expired and cannot be sold.", thrown.getMessage());
    }

    

    @Test
    public void testSellGoodsNotEnoughMoney() {
        shoppingCart.setCustomerMoney(new BigDecimal("5.00")); // Not enough money

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            checkout.markGoods(shoppingCart);
            
        });
        assertEquals("Not enough money to buy these goods.", thrown.getMessage());
    }
}
