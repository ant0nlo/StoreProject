import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
	private static final String URL = "jdbc:sqlite:mydatabase.db";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }
    
    public void createTables() {

            String createGoodsTable = """
                CREATE TABLE IF NOT EXISTS Goods (
                    id INTEGER PRIMARY KEY,
                    name TEXT NOT NULL,
                    unitDeliveryPrice REAL NOT NULL,
                    category TEXT NOT NULL,
                    expirationDate TEXT,
                    markupPercentage REAL NOT NULL,
                    discountPercentage REAL NOT NULL,
                    totalAvailable INTEGER NOT NULL,
                    quantityAvailable INTEGER NOT NULL
                );
            """;

            String createCashiersTable = """
                    CREATE TABLE IF NOT EXISTS Cashiers (
                        id INTEGER PRIMARY KEY,
                        name TEXT NOT NULL,
                        monthlySalary REAL NOT NULL
                    );
                """;
            
            String createReceiptsTable = """
                CREATE TABLE IF NOT EXISTS Receipts (
                    serialNumber INTEGER PRIMARY KEY,
                    cashierId INTEGER,
                    issuanceDateTime TEXT NOT NULL,
                    totalAmountPaid REAL NOT NULL,
                    FOREIGN KEY(cashierId) REFERENCES Cashiers(id)
                );
            """;

            String createShoppingCartItemsTable = """
                CREATE TABLE IF NOT EXISTS ShoppingCartItems (
                    receiptSerialNumber INTEGER,
                    goodsId INTEGER,
                    quantity INTEGER NOT NULL,
                    FOREIGN KEY(receiptSerialNumber) REFERENCES Receipts(serialNumber),
                    FOREIGN KEY(goodsId) REFERENCES Goods(id)
                );
            """;
            try (Connection conn = connect();
                    Statement stmt = conn.createStatement()) {
                   stmt.execute(createGoodsTable);
                   stmt.execute(createCashiersTable);
                   stmt.execute(createReceiptsTable);
                   stmt.execute(createShoppingCartItemsTable);
               } catch (SQLException e) {
                   System.out.println(e.getMessage());
               }
    }
    
    public void addGoods(Goods goods) {
        String sql = "INSERT INTO Goods(name, unitDeliveryPrice, category, expirationDate, markupPercentage, discountPercentage, totalAvailable, quantityAvailable) VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, goods.getName());
            pstmt.setDouble(2, goods.getUnitDeliveryPrice());
            pstmt.setString(3, goods.getCategory().name());
            pstmt.setString(4, goods.getExpirationDate().toString());
            pstmt.setDouble(5, goods.getMarkupPercentage());
            pstmt.setDouble(6, goods.getDiscountPercentage());
            pstmt.setInt(7, goods.getTotalAvailable());
            pstmt.setInt(8, goods.getQuantityAvailable());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addCashier(Cashier cashier) {
        String sql = "INSERT INTO Cashiers(name, monthlySalary) VALUES(?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cashier.getName());
            pstmt.setDouble(2, cashier.getMonthlySalary());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addItemToShoppingCart(int receiptSerialNumber, int goodsId, int quantity) {
        String sql = "INSERT INTO ShoppingCartItems(receiptSerialNumber, goodsId, quantity) VALUES(?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, receiptSerialNumber);
            pstmt.setInt(2, goodsId);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeItemFromShoppingCart(int receiptSerialNumber, int goodsId) {
        String sql = "DELETE FROM ShoppingCartItems WHERE receiptSerialNumber = ? AND goodsId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, receiptSerialNumber);
            pstmt.setInt(2, goodsId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateItemQuantityInShoppingCart(int receiptSerialNumber, int goodsId, int newQuantity) {
        String sql = "UPDATE ShoppingCartItems SET quantity = ? WHERE receiptSerialNumber = ? AND goodsId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newQuantity);
            pstmt.setInt(2, receiptSerialNumber);
            pstmt.setInt(3, goodsId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
