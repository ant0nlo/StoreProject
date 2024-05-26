import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManagerTest {

    private DatabaseManager dbManager;

    @BeforeEach
    public void setUp() {
        dbManager = new DatabaseManager();
        dbManager.createTables();
    }

    @Test
    public void testCreateTables() {
        try (Connection conn = dbManager.connect()) {
            DatabaseMetaData metaData = conn.getMetaData();

            ResultSet rs = metaData.getTables(null, null, "Goods", null);
            assertTrue(rs.next(), "Table 'Goods' should exist");

            rs = metaData.getTables(null, null, "Cashiers", null);
            assertTrue(rs.next(), "Table 'Cashiers' should exist");

            rs = metaData.getTables(null, null, "Receipts", null);
            assertTrue(rs.next(), "Table 'Receipts' should exist");

            rs = metaData.getTables(null, null, "ShoppingCartItems", null);
            assertTrue(rs.next(), "Table 'ShoppingCartItems' should exist");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Exception thrown during table creation: " + e.getMessage());
        }
    }

    @Test
    public void testAddGoods() {
        Goods goods = new Goods(1, "Apple Juice", 1.56, Category.EATABLE, LocalDate.now().plusDays(5), 5, 10000);
        dbManager.addGoods(goods);

        try (Connection conn = dbManager.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Goods WHERE id = ?")) {

            pstmt.setInt(1, 1);
            ResultSet rs = pstmt.executeQuery();

            assertTrue(rs.next(), "Goods with id 1 should exist");
            assertEquals("Apple Juice", rs.getString("name"), "Name should match");
            assertEquals(1.56, rs.getDouble("unitDeliveryPrice"), 0.01, "Unit delivery price should match");
            assertEquals("EATABLE", rs.getString("category"), "Category should match");
            assertEquals(LocalDate.now().plusDays(5).toString(), rs.getString("expirationDate"), "Expiration date should match");
            assertEquals(10000, rs.getInt("totalAvailable"), "Total available should match");
            assertEquals(10000, rs.getInt("quantityAvailable"), "Quantity available should match");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Exception thrown during goods insertion: " + e.getMessage());
        }
    }

    @Test
    public void testAddCashier() {
        Cashier cashier = new Cashier(1, "John Doe", 1000);
        dbManager.addCashier(cashier);

        try (Connection conn = dbManager.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Cashiers WHERE id = ?")) {

            pstmt.setInt(1, 1);
            ResultSet rs = pstmt.executeQuery();

            assertTrue(rs.next(), "Cashier with id 1 should exist");
            assertEquals("John Doe", rs.getString("name"), "Name should match");
            assertEquals(1000, rs.getDouble("monthlySalary"), 0.01, "Monthly salary should match");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Exception thrown during cashier insertion: " + e.getMessage());
        }
    }

    @Test
    public void testAddReceipt() {
        Cashier cashier = new Cashier(1, "Jane Smith", 1500);
        dbManager.addCashier(cashier);

        Goods goods = new Goods(1, "Apple", 1.0, Category.EATABLE, LocalDate.now().plusDays(5), 5, 10000);
        dbManager.addGoods(goods);

        Store store = new Store();
        store.addCashier(cashier);
        store.addGoods(goods);

        Store.setMarkup(Category.EATABLE, 20); 
        Map<Goods, Integer> items = new HashMap<>();
        ShoppingCart shoppingCart = new ShoppingCart(items, 200.00);
        shoppingCart.addItem(goods, 5);
        Checkout checkout = new Checkout(cashier, store);
        Receipt receipt = checkout.markGoods(shoppingCart);

        dbManager.addReceipt(receipt);

        try (Connection conn = dbManager.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Receipts WHERE serialNumber = ?")) {

            pstmt.setInt(1, receipt.getSerialNumber());
            ResultSet rs = pstmt.executeQuery();

            assertTrue(rs.next(), "Receipt with serialNumber 1 should exist");
            assertEquals(1, rs.getInt("cashierId"), "Cashier ID should match");
            assertEquals(6.0, rs.getDouble("totalAmountPaid"), 0.01, "Total amount paid should match");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Exception thrown during receipt insertion: " + e.getMessage());
        }
    }

    @Test
    public void testAddItemToShoppingCart() {
        Cashier cashier = new Cashier(1, "Jane Smith", 1500);
        dbManager.addCashier(cashier);

        Goods goods = new Goods(1, "Apple", 1.0, Category.EATABLE, LocalDate.now().plusDays(5), 5, 10000);
        dbManager.addGoods(goods);

        Store store = new Store();
        store.addCashier(cashier);
        store.addGoods(goods);

        Map<Goods, Integer> items = new HashMap<>();
        ShoppingCart shoppingCart = new ShoppingCart(items, 200.00);
        shoppingCart.addItem(goods, 5);

        Checkout checkout = new Checkout(cashier, store);
        Receipt receipt = checkout.markGoods(shoppingCart);

        dbManager.addReceipt(receipt);
        dbManager.addItemToShoppingCart(shoppingCart, receipt);

        try (Connection conn = dbManager.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ShoppingCartItems WHERE receiptSerialNumber = ? AND goodsId = ?")) {

            pstmt.setInt(1, receipt.getSerialNumber());
            pstmt.setInt(2, goods.getId());
            ResultSet rs = pstmt.executeQuery();

            assertTrue(rs.next(), "Item with receiptSerialNumber 1 and goodsId 1 should exist in shopping cart");
            assertEquals(5, rs.getInt("quantity"), "Quantity should match");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Exception thrown during shopping cart item insertion: " + e.getMessage());
        }
    }
}
