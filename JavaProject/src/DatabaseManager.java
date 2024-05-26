import java.sql.*;
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
                serialNumber INTEGER PRIMARY KEY AUTOINCREMENT,
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

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createGoodsTable);
            stmt.execute(createCashiersTable);
            stmt.execute(createReceiptsTable);
            stmt.execute(createShoppingCartItemsTable);
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }
    
    public void addGoods(Goods goods) {
        String sqlSelect = "SELECT id FROM Goods WHERE id = ?";
        String sqlInsert = "INSERT INTO Goods(id, name, unitDeliveryPrice, category, expirationDate, markupPercentage, discountPercentage, totalAvailable, quantityAvailable) VALUES(?,?,?,?,?,?,?,?,?)";
        String sqlUpdate = "UPDATE Goods SET name = ?, unitDeliveryPrice = ?, category = ?, expirationDate = ?, markupPercentage = ?, discountPercentage = ?, totalAvailable = ?, quantityAvailable = ? WHERE id = ?";

        try (Connection conn = connect(); 
             PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
             PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
             PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
            
            pstmtSelect.setInt(1, goods.getId());
            ResultSet rs = pstmtSelect.executeQuery();
            
            if (rs.next()) {
                // If the goods exist, update it
                pstmtUpdate.setString(1, goods.getName());
                pstmtUpdate.setDouble(2, goods.getUnitDeliveryPrice());
                pstmtUpdate.setString(3, goods.getCategory().toString());
                pstmtUpdate.setString(4, goods.getExpirationDate() != null ? goods.getExpirationDate().toString() : null);
                pstmtUpdate.setDouble(5, goods.getMarkupPercentage());
                pstmtUpdate.setDouble(6, goods.getDiscountPercentage());
                pstmtUpdate.setInt(7, goods.getTotalAvailable());
                pstmtUpdate.setInt(8, goods.getQuantityAvailable());
                pstmtUpdate.setInt(9, goods.getId());
                pstmtUpdate.executeUpdate();
            } else {
                // If the goods do not exist, insert it
                pstmtInsert.setInt(1, goods.getId());
                pstmtInsert.setString(2, goods.getName());
                pstmtInsert.setDouble(3, goods.getUnitDeliveryPrice());
                pstmtInsert.setString(4, goods.getCategory().toString());
                pstmtInsert.setString(5, goods.getExpirationDate() != null ? goods.getExpirationDate().toString() : null);
                pstmtInsert.setDouble(6, goods.getMarkupPercentage());
                pstmtInsert.setDouble(7, goods.getDiscountPercentage());
                pstmtInsert.setInt(8, goods.getTotalAvailable());
                pstmtInsert.setInt(9, goods.getQuantityAvailable());
                pstmtInsert.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error adding or updating goods: " + e.getMessage());
        }
    }


    public void addCashier(Cashier cashier) {
        String sqlSelect = "SELECT id FROM Cashiers WHERE id = ?";
        String sqlInsert = "INSERT INTO Cashiers(id, name, monthlySalary) VALUES(?,?,?)";
        String sqlUpdate = "UPDATE Cashiers SET name = ?, monthlySalary = ? WHERE id = ?";

        try (Connection conn = connect(); 
             PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
             PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
             PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
            
            pstmtSelect.setInt(1, cashier.getId());
            ResultSet rs = pstmtSelect.executeQuery();
            
            if (rs.next()) {
                pstmtUpdate.setString(1, cashier.getName());
                pstmtUpdate.setDouble(2, cashier.getMonthlySalary());
                pstmtUpdate.setInt(3, cashier.getId());
                pstmtUpdate.executeUpdate();
            } else {
                pstmtInsert.setInt(1, cashier.getId());
                pstmtInsert.setString(2, cashier.getName());
                pstmtInsert.setDouble(3, cashier.getMonthlySalary());
                pstmtInsert.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error adding or updating cashier: " + e.getMessage());
        }
    }

    public void addReceipt(Receipt receipt) {
        String sqlSelect = "SELECT serialNumber FROM Receipts WHERE serialNumber = ?";
        String sqlInsert = "INSERT INTO Receipts(serialNumber, cashierId, issuanceDateTime, totalAmountPaid) VALUES(?,?,?,?)";
        String sqlUpdate = "UPDATE Receipts SET cashierId = ?, issuanceDateTime = ?, totalAmountPaid = ? WHERE serialNumber = ?";

        try (Connection conn = connect(); 
             PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
             PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
             PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
            
            pstmtSelect.setInt(1, receipt.getSerialNumber());
            ResultSet rs = pstmtSelect.executeQuery();
            
            if (rs.next()) {
                pstmtUpdate.setInt(1, receipt.getIssuingCashier().getId());
                pstmtUpdate.setString(2, receipt.getIssuanceDateTime().toString());
                pstmtUpdate.setDouble(3, receipt.getTotalAmountPaid());
                pstmtUpdate.setInt(4, receipt.getSerialNumber());
                pstmtUpdate.executeUpdate();
            } else {
                pstmtInsert.setInt(1, receipt.getSerialNumber());
                pstmtInsert.setInt(2, receipt.getIssuingCashier().getId());
                pstmtInsert.setString(3, receipt.getIssuanceDateTime().toString());
                pstmtInsert.setDouble(4, receipt.getTotalAmountPaid());
                pstmtInsert.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error adding or updating receipt: " + e.getMessage());
        }
    }


    public void addItemToShoppingCart(ShoppingCart shoppingCart, Receipt receipt) {
        String sqlSelect = "SELECT * FROM ShoppingCartItems WHERE receiptSerialNumber = ? AND goodsId = ?";
        String sqlInsert = "INSERT INTO ShoppingCartItems(receiptSerialNumber, goodsId, quantity) VALUES(?,?,?)";
        String sqlUpdate = "UPDATE ShoppingCartItems SET quantity = ? WHERE receiptSerialNumber = ? AND goodsId = ?";

        try (Connection conn = connect(); 
             PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
             PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
             PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
            
            conn.setAutoCommit(false);  // Start transaction

            for (Map.Entry<Goods, Integer> entry : shoppingCart.getItems().entrySet()) {
                pstmtSelect.setInt(1, receipt.getSerialNumber());
                pstmtSelect.setInt(2, entry.getKey().getId());
                ResultSet rs = pstmtSelect.executeQuery();
                
                if (rs.next()) {
                    pstmtUpdate.setInt(1, entry.getValue());
                    pstmtUpdate.setInt(2, receipt.getSerialNumber());
                    pstmtUpdate.setInt(3, entry.getKey().getId());
                    pstmtUpdate.addBatch();
                } else {
                    pstmtInsert.setInt(1, receipt.getSerialNumber());
                    pstmtInsert.setInt(2, entry.getKey().getId());
                    pstmtInsert.setInt(3, entry.getValue());
                    pstmtInsert.addBatch();
                }
            }

            pstmtInsert.executeBatch();
            pstmtUpdate.executeBatch();
            conn.commit();  // Commit transaction
        } catch (SQLException e) {
            System.out.println("Error adding or updating items to shopping cart: " + e.getMessage());
        }
    }
    
    public void deleteAllGoods() {
        String sql = "DELETE FROM Goods";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
            System.out.println("All goods have been deleted.");
        } catch (SQLException e) {
            System.out.println("Error deleting goods: " + e.getMessage());
        }
    }

    public void deleteAllCashiers() {
        String sql = "DELETE FROM Cashiers";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
            System.out.println("All cashiers have been deleted.");
        } catch (SQLException e) {
            System.out.println("Error deleting cashiers: " + e.getMessage());
        }
    }

    public void deleteAllReceipts() {
        String sql = "DELETE FROM Receipts";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
            System.out.println("All receipts have been deleted.");
        } catch (SQLException e) {
            System.out.println("Error deleting receipts: " + e.getMessage());
        }
    }

    public void deleteAllShoppingCartItems() {
        String sql = "DELETE FROM ShoppingCartItems";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
            System.out.println("All shopping cart items have been deleted.");
        } catch (SQLException e) {
            System.out.println("Error deleting shopping cart items: " + e.getMessage());
        }
    }


}
