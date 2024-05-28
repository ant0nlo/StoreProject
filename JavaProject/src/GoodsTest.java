
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GoodsTest {

    private Goods goods;


    @BeforeEach
    public void setUp() {
        Store.setMarkup(Category.EATABLE, 20); 

        goods = new Goods(1, "Apple",  new BigDecimal("1.00"), Category.EATABLE, LocalDate.now().plusDays(5), 5, 100);

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

        BigDecimal expectedSellingPrice = BigDecimal.valueOf(1.0).multiply(BigDecimal.ONE.add(BigDecimal.valueOf(20).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)));
        assertEquals(0, expectedSellingPrice.compareTo(goods.calculateSellingPrice()), 
                     "Selling price calculation should be correct");
    }

}
