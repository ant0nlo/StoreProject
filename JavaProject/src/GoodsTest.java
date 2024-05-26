
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GoodsTest {

    private Goods goods;


    @BeforeEach
    public void setUp() {
        Store.setMarkup(Category.EATABLE, 20); 

        goods = new Goods(1, "Apple", 1.0, Category.EATABLE,LocalDate.now().plusDays(5),5, 100);

    }

    @Test
    public void testIsExpired() {
        assertFalse(goods.isExpired(), "Goods should not be expired yet");
    }

    @Test
    public void testDecreaseQuantity() {        
        goods.decreaseQuantity(50);
        assertEquals(50, goods.getQuantityAvailable(), "Decrease quantity should update quantity available");
    }

    @Test
    public void testCalculateSellingPrice() {
        Store.setMarkup(Category.EATABLE, 20); 

    	double expectedSellingPrice = 1.0 * (1 + (20 / 100.0));
        assertEquals(expectedSellingPrice, goods.calculateSellingPrice(), 0.001,
                     "Selling price calculation should be correct");
    }

}
