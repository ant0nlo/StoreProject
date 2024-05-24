
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;


public class DatabaseManagerTest {

    @Test
    public void testCreateTables() {
        DatabaseManager dbManager = new DatabaseManager();
        dbManager.createTables();

        try (Connection conn = dbManager.connect()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "Goods", null);
            assertTrue(rs.next()); // проверете дали таблицата "Goods" е създадена
            rs = metaData.getTables(null, null, "Cashiers", null);
            assertTrue(rs.next()); // проверете дали таблицата "Cashiers" е създадена
            rs = metaData.getTables(null, null, "Receipts", null);
            assertTrue(rs.next()); // проверете дали таблицата "Receipts" е създадена
            rs = metaData.getTables(null, null, "ShoppingCartItems", null);
            assertTrue(rs.next()); // проверете дали таблицата "ShoppingCartItems" е създадена
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Exception thrown during table creation: " + e.getMessage());
        }
    }

    @Test
    public void testAddGoods() {
        DatabaseManager dbManager = new DatabaseManager();
        dbManager.createTables();

        Goods goods = new Goods(1, "Apple Juice", 1.56, Category.EATABLE,
                                LocalDate.now().plusDays(5), 10, 5, 10000);
        dbManager.addGoods(goods);

        try (Connection conn = dbManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Goods WHERE id = 1")) {
            assertTrue(rs.next()); // проверете дали данните за стоката с id 1 са добавени успешно
            assertEquals("Apple Juice", rs.getString("name"));
            assertEquals(1.56, rs.getDouble("unitDeliveryPrice"), 0.01);
            assertEquals("EATABLE", rs.getString("category"));
            // продължете с останалите проверки за данните на стоката...
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Exception thrown during goods insertion: " + e.getMessage());
        }
    }

    @Test
    public void testAddCashier() {
        DatabaseManager dbManager = new DatabaseManager();
        dbManager.createTables();

        Cashier cashier = new Cashier(1, "John Doe", 1000);
        dbManager.addCashier(cashier);

        try (Connection conn = dbManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Cashiers WHERE id = 1")) {
            assertTrue(rs.next()); // проверете дали данните за касиера с id 1 са добавени успешно
            assertEquals("John Doe", rs.getString("name"));
            assertEquals(1000, rs.getDouble("monthlySalary"), 0.01);
            // продължете с останалите проверки за данните на касиера...
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Exception thrown during cashier insertion: " + e.getMessage());
        }
    }


}
