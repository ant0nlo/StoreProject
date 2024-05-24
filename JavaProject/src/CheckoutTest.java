import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

        goods1 = new Goods(1, "Apple", 1.0,Category.EATABLE, LocalDate.now().plusDays(5), 10, 5, 100);
        goods2 = new Goods(2, "Milk", 2.0, Category.EATABLE, LocalDate.now().plusDays(7), 10, 5, 100);

        Map<Goods, Integer> items = new HashMap<>();
        shoppingCart = new ShoppingCart(items, 100.0);
        shoppingCart.addItem(goods1, 2);
        shoppingCart.addItem(goods2, 3);
        checkout = new Checkout(cashier, store);
    }

    @Test
    public void testSellGoodsSuccess() {
        checkout.sellGoods(shoppingCart);

        assertEquals(98, goods1.getQuantityAvailable());
        assertEquals(97, goods2.getQuantityAvailable());
        assertEquals(1, store.getReceipts().size());
    }


    @Test
    public void testMarkGoodsSuccess() {
        checkout.markGoods(shoppingCart);

        assertEquals(98, goods1.getQuantityAvailable());
        assertEquals(97, goods2.getQuantityAvailable());
        assertEquals(1, store.getReceipts().size());
    }
    
    @Test
    public void testSellGoodsNotEnoughQuantity() {
        goods1.setQuantityAvailable(1); // Not enough quantity

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            checkout.sellGoods(shoppingCart);
        });
        assertEquals("Not enough quantity available for sale: Apple", thrown.getMessage());
    }

    @Test
    public void testSellGoodsItemExpired() {
        goods1.setExpirationDate(LocalDate.now().minusDays(1)); // Item expired

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            checkout.sellGoods(shoppingCart);
        });
        assertEquals("The item Apple has expired and cannot be sold.", thrown.getMessage());
    }

    

    @Test
    public void testMarkGoodsNotEnoughMoney() {
        shoppingCart.setCustomerMoney(5.0); // Not enough money

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            checkout.markGoods(shoppingCart);
        });
        assertEquals("Not enough money to buy these goods.", thrown.getMessage());
    }
}
