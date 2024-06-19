
## Store Management System

### Overview

The Store Management System is a Java-based application designed to manage a store's operations, including cashier management, goods management, and checkout processes. The system utilizes a MySQL database to store and manage data related to cashiers, goods, receipts, and shopping cart items.

### Features

- **Cashier Management**: Add, update, and delete cashier records.
- **Goods Management**: Add, update, and delete goods records.
- **Checkout Process**: Manage the checkout process, generate receipts, and update inventory.
- **Database Integration**: Store and manage data in a MySQL database.
- **Financial Reporting**: Calculate total delivery costs, cashier salaries, revenue, profit, and number of receipts issued.

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- MySQL database server
- MySQL Connector/J (JDBC driver for MySQL)
- Maven (for project build and dependency management)

### Getting Started

#### 1. Clone the Repository

```sh
git clone https://github.com/ant0nlo/StoreProject.git
cd StoreProject
```

#### 2. Set Up the Database

Ensure you have a MySQL database running. Create a database named `mydb` and update the `DatabaseManager` class with your database credentials.

```sql
CREATE DATABASE mydb;
```

Update `DatabaseManager.java` with your MySQL username and password:

```java
String URL = "jdbc:mysql://localhost:3306/mydb";
String username = "your_mysql_username";
String password = "your_mysql_password";
```

#### 3. Build the Project

Use Maven to build the project:

```sh
mvn clean install
```

#### 4. Run the Application

You can run the application with the following command. Ensure you have an input file (`input.txt`) with the required format as described earlier.

```sh
java -cp target/StoreProject-1.0-SNAPSHOT.jar com.store.main.Main < input.txt
```

### Input Format

The input should be provided in the following format:

1. **Cashiers**: `id,name,salary` separated by `;`.
2. **Goods**: `id,name,price,category,expirationDate,discountPercentage,quantity` separated by `;`.
3. **Shopping Carts**: `id,quantity` separated by `;`, followed by the total price of the cart.

Example:

```plaintext
1,John Doe,1000;2,Anna Smith,1200
1,Apple,1.56,EATABLE,2024-12-31,5,1000;2,Milk,2.00,EATABLE,2024-12-31,5,1000
1,20;2,10
200.55
1,10;2,5
150.25
```

### Usage

1. **Cashier Management**: Add and manage cashier details including ID, name, and salary.
2. **Goods Management**: Add and manage goods details including ID, name, price, category, expiration date, discount percentage, and quantity.
3. **Checkout Process**: Manage the checkout process, ensure goods availability, generate receipts, and update inventory.
4. **Database Management**: Use the `DatabaseManager` class to handle database operations such as creating tables, adding records, and deleting records.

### Example Output

The application will output financial information after processing the input:

```plaintext
Total delivery costs: $1560.00
Total cashier salaries: $26400.00
Total revenue: $500.00
Total profit: $200.00
Total number of receipts issued: 2
```

### Testing

Unit tests are provided in the `com.store.tests` package. You can run the tests using Maven:

```sh
mvn test
```

### Contributions

Contributions are welcome! Please fork the repository and submit a pull request with your changes.

### License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

