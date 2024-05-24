import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
    	
    	DatabaseManager dbManager = new DatabaseManager();
        dbManager.createTables();
        
        // Creating store
        Store store = new Store();
        Store.setMarkup(Category.EATABLE, 20); 
        Store.setMarkup(Category.NON_EDIBLE, 10); 

        // Adding cashiers
        Cashier cashier1 = new Cashier(1,"John Doe", 1000);
        Cashier cashier2 = new Cashier(2,"Anna Smith", 1200);
        store.addCashier(cashier1);
        store.addCashier(cashier2);
        
        dbManager.addCashier(cashier1);
        dbManager.addCashier(cashier2);

        // Adding goods
        Goods goods1 = new Goods(1, "Apple", 1.56, Category.EATABLE, LocalDate.now().plusDays(5), 5, 10000);
        Goods goods2 = new Goods(2, "Milk", 2.0, Category.EATABLE, LocalDate.now().plusDays(7), 5, 10000);
        store.addGoods(goods1);
        store.addGoods(goods2);
        
        dbManager.addGoods(goods1);
        dbManager.addGoods(goods2);
        
        System.out.println("Total delivery costs: $" + store.calculateTotalDeliveryCosts());
        
        Map<Goods, Integer> items = new HashMap<>();
        ShoppingCart cart = new ShoppingCart(items, 200.55);
        Checkout checkout = new Checkout(cashier2, store);
        cart.addItem(goods1, 20);
        cart.addItem(goods2, 10);     
        checkout.markGoods(cart);
        
        Map<Goods, Integer> items1 = new HashMap<>();
        ShoppingCart cart1 = new ShoppingCart(items1, 150.25);
        cart1.addItem(goods1, 10);
        cart1.addItem(goods2, 5);     
        Checkout checkout1 = new Checkout(cashier1, store);
        checkout1.markGoods(cart1);
        
        Map<Goods, Integer> items2 = new HashMap<>();
        ShoppingCart cart2 = new ShoppingCart(items2, 300.75);
        cart2.addItem(goods1, 15);
        cart2.addItem(goods2, 8);     
        Checkout checkout2 = new Checkout(cashier2, store);
        checkout2.markGoods(cart2);
        
        Map<Goods, Integer> items3 = new HashMap<>();
        ShoppingCart cart3 = new ShoppingCart(items3, 500.50);
        cart3.addItem(goods1, 30);
        cart3.addItem(goods2, 20);     
        Checkout checkout3 = new Checkout(cashier1, store);
        checkout3.markGoods(cart3);
        
        // Viewing financial information
        System.out.println("Total cashier salaries: $" + store.calculateTotalCashierSalaries());
        System.out.println("Total revenue: $" + store.calculateTotalRevenue());
        System.out.println("Total profit: $" + store.calculateTotalProfit());
        System.out.println("Total number of receipts issued: " + store.getTotalReceiptsIssued());
    }
}