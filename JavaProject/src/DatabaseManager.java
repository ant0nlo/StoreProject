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
            
            try (Connection conn = connect();
                    Statement stmt = conn.createStatement()) {
                   // Изпълняваме заявката за създаване на таблицата Cashiers
                   stmt.execute(createCashiersTable);
                   
                   // Проверяваме дали таблицата Cashiers е успешно създадена
                   ResultSet rs = conn.getMetaData().getTables(null, null, "Cashiers", null);
                   if (rs.next()) {
                       System.out.println("Table 'Cashiers' created successfully");
                   } else {
                       System.out.println("Table 'Cashiers' creation failed");
                   }
                   
                   // код за създаване на другите таблиците...
               } catch (SQLException e) {
                   System.out.println(e.getMessage());
               }
            
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

    

}
