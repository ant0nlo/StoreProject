import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Creating store
        Store store = new Store();
        Store.setMarkupEatable(20);
        Store.setMarkupNonEdible(10);

        // Adding cashiers
        Cashier cashier1 = new Cashier(1,"John Doe", 1000);
        Cashier cashier2 = new Cashier(2,"Anna Smith", 1200);
        store.addCashier(cashier1);
        store.addCashier(cashier2);

        // Adding goods
        Goods goods1 = new Goods(1, "Apple Juice", 1.56, Goods.Category.EATABLE, LocalDate.now().plusDays(5), 10, 5, 10000);
        Goods goods2 = new Goods(2, "Milk", 2.0, Goods.Category.EATABLE, LocalDate.now().plusDays(7), 10, 5, 10000);
        store.addGoods(goods1);
        store.addGoods(goods2);
        System.out.println("Total delivery costs: $" + store.calculateTotalDeliveryCosts());
        
        /*
        List<Goods> items = new ArrayList<>();
        List<Integer> quantityToSell = new ArrayList<>();
        items.add(goods1);
        items.add(goods2);    
        quantityToSell.add(9000); // Quantity to sell for goods1
        quantityToSell.add(8000); // Quantity to sell for goods2
        Checkout checkout = new Checkout(cashier1, store);
        double buyerMoney = 50000.0;
        checkout.markGoods(items, quantityToSell, buyerMoney);
       */
        /*
        List<Goods> items = new ArrayList<>();
        List<Integer> quantityToBuy = new ArrayList<>();
        ShoppingCart cart = new ShoppingCart(items, quantityToBuy, 200);
        Checkout checkout = new Checkout(cashier1, store);
        cart.addItem(goods1, 100);
        cart.addItem(goods2, 10);     
        checkout.markGoods(cart);
        */
        
        Map<Goods, Integer> items = new HashMap<>();
        ShoppingCart cart = new ShoppingCart(items, 200.55);
        Checkout checkout = new Checkout(cashier2, store);
        cart.addItem(goods1, 20);
        cart.addItem(goods2, 10);     
        checkout.markGoods(cart);
        
        // Viewing financial information
        System.out.println("Total cashier salaries: $" + store.calculateTotalCashierSalaries());
        System.out.println("Total revenue: $" + store.calculateTotalRevenue());
        System.out.println("Total profit: $" + store.calculateTotalProfit());
        System.out.println("Total number of receipts issued: " + store.getTotalReceiptsIssued());
    }
}