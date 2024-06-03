import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        
        // Reading cashiers
        List<Cashier> cashiers = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(";"))
            .map(line -> {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                BigDecimal salary = new BigDecimal(data[2]);
                return new Cashier(id, name, salary);
            })
            .collect(Collectors.toList());

        // Reading goods
        List<Goods> goodsList = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(";"))
            .map(line -> {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                BigDecimal price = new BigDecimal(data[2]);
                Category category = Category.valueOf(data[3].toUpperCase());
                LocalDate expirationDate = LocalDate.parse(data[4]);
                Double discountPercentage = Double.parseDouble(data[5]);
                int quantity = Integer.parseInt(data[6]);
                return new Goods(id, name, price, category, expirationDate, discountPercentage, quantity);
            })
            .collect(Collectors.toList());

        // Creating store
        Store store = new Store();
        store.setMarkup(Category.EATABLE, 20);
        store.setMarkup(Category.NON_EDIBLE, 10);

        // Adding cashiers to store
        for (Cashier cashier : cashiers) {
            store.addCashier(cashier);
        }

        // Adding goods to store
        for (Goods goods : goodsList) {
            store.addGoods(goods);
        }


        // Reading shopping carts
        String line;
        int cashierIndex = 0;
        while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
            Map<Goods, Integer> items = new HashMap<>();
            BigDecimal totalPrice = new BigDecimal(bufferedReader.readLine());

            List<String> cartItems = Stream.of(line.replaceAll("\\s+$", "").split(";"))
                .collect(Collectors.toList());

            for (String item : cartItems) {
                String[] data = item.split(",");
                int goodsId = Integer.parseInt(data[0]);
                int quantity = Integer.parseInt(data[1]);
                Goods goods = goodsList.stream().filter(g -> g.getId() == goodsId).findFirst().orElse(null);
                if (goods != null) {
                    items.put(goods, quantity);
                }
            }

            ShoppingCart cart = new ShoppingCart(items, totalPrice);
            Checkout checkout = new Checkout(cashiers.get(cashierIndex));
            cashierIndex = (cashierIndex + 1) % cashiers.size(); // Rotate the cashier
            Receipt receipt = store.checkoutClient(checkout, cart);
        }

        // Viewing financial information
        System.out.println("Total delivery costs: $" + store.calculateTotalDeliveryCosts());
        System.out.println("Total cashier salaries: $" + store.calculateTotalCashierSalaries());
        System.out.println("Total revenue: $" + store.getTotalTurnover());
        System.out.println("Total profit: $" + store.calculateTotalProfit());
        System.out.println("Total number of receipts issued: " + store.getTotalReceiptsIssued());
    }
}


/*
public class Main {
    public static void main(String[] args) {
    	     
        // Creating store
        Store store = new Store();
        store.setMarkup(Category.EATABLE, 20); 
        store.setMarkup(Category.NON_EDIBLE, 10); 
        
        // Adding cashiers
        Cashier cashier1 = new Cashier(1, "John Doe", new BigDecimal("1000.00"));
        Cashier cashier2 = new Cashier(2, "Anna Smith", new BigDecimal("1200.00"));
        store.addCashier(cashier1);
        store.addCashier(cashier2);

        // Adding goods
        Goods goods1 = new Goods(1, "Apple",  new BigDecimal("1.56"), Category.EATABLE, LocalDate.now().plusDays(5), 5, 1000);
        Goods goods2 = new Goods(2, "Milk",  new BigDecimal("2.00"), Category.EATABLE, LocalDate.now().plusDays(5), 5, 1000);
        store.addGoods(goods1);
        store.addGoods(goods2);
        
        // First cart
        Map<Goods, Integer> items = new HashMap<>();
        ShoppingCart cart = new ShoppingCart(items,  new BigDecimal("200.55"));
        cart.addItem(goods1, 20);
        cart.addItem(goods2, 10);
        Checkout checkout = new Checkout(cashier2);
        Receipt receipt = store.checkoutClient(checkout, cart);   
               
        // Second cart
        Map<Goods, Integer> items1 = new HashMap<>();
        ShoppingCart cart1 = new ShoppingCart(items1,  new BigDecimal("150.25"));
        cart1.addItem(goods1, 10);
        cart1.addItem(goods2, 5);
        Checkout checkout1 = new Checkout(cashier1);
        Receipt receipt1 = store.checkoutClient(checkout1, cart1);

        
        // Third cart
        Map<Goods, Integer> items2 = new HashMap<>();
        ShoppingCart cart2 = new ShoppingCart(items2, new BigDecimal("300.75"));
        cart2.addItem(goods1, 15);
        cart2.addItem(goods2, 8);
        Checkout checkout2 = new Checkout(cashier2);
        Receipt receipt2 = store.checkoutClient(checkout2, cart2);

        // Fourth cart
        Map<Goods, Integer> items3 = new HashMap<>();
        ShoppingCart cart3 = new ShoppingCart(items3, new BigDecimal("500.50"));
        cart3.addItem(goods1, 30);
        cart3.addItem(goods2, 20);
        Checkout checkout3 = new Checkout(cashier1);
        Receipt receipt3 = store.checkoutClient(checkout3, cart3);

        // Viewing financial information
        System.out.println("Total delivery costs: $" + store.calculateTotalDeliveryCosts());
        System.out.println("Total cashier salaries: $" + store.calculateTotalCashierSalaries());
        System.out.println("Total revenue: $" + store.getTotalTurnover());
        System.out.println("Total profit: $" + store.calculateTotalProfit());
        System.out.println("Total number of receipts issued: " + store.getTotalReceiptsIssued());
    }
}
*/

