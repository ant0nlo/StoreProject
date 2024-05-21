import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<Cashier> cashiers;
    private List<Goods> goodsList;
    private List<Receipt> receipts;
    private double totalTurnover;
    private static double markupEatable;
    private static double markupNonEdible;
    
    // Constructor
    public Store() {
        this.cashiers = new ArrayList<>();
        this.goodsList = new ArrayList<>();
        this.setReceipts(new ArrayList<>());
        this.totalTurnover = 0;
    }

    // Method to add a cashier to the store
    public void addCashier(Cashier cashier) {
        cashiers.add(cashier);
    }

    // Method to add goods to the store
    public void addGoods(Goods goods) {
        goodsList.add(goods);
    }

    // Method to check goods availability
    public boolean checkAvailability(Goods goods, int quantity) {
        return goods.getQuantityAvailable() >= quantity;
    }

    // Method to calculate total costs for cashiers' salaries
    public double calculateTotalCashierSalaries() {
        double totalSalaries = 0;
        for (Cashier cashier : cashiers) {
            totalSalaries += cashier.getMonthlySalary() * 12; // Assuming annual salary
        }
        return totalSalaries;
    }

    // Method to calculate total costs for goods delivery
    public double calculateTotalDeliveryCosts() {
        double totalDeliveryCosts = 0;
        for (Goods goods : goodsList) {
            totalDeliveryCosts += goods.getUnitDeliveryPrice() * goods.getTotalAvailable();
        }
        return totalDeliveryCosts;
    }

    // Method to calculate total revenue from goods sold
    public double calculateTotalRevenue() {
        return totalTurnover;
    }

    // Method to calculate total profit
    public double calculateTotalProfit() {
        return calculateTotalRevenue() - calculateTotalCashierSalaries() - calculateTotalDeliveryCosts();
    }

    // Method to get total number of receipts issued
    public int getTotalReceiptsIssued() {
        return getReceipts().size();
    }

	public static double getMarkupEatable() {
		return markupEatable;
	}

	public static double getMarkupNonEdible() {
		return markupNonEdible;
	}

	public List<Receipt> getReceipts() {
		return receipts;
	}
	
	public List<Receipt> getReceipts2() {
		return receipts;
	}

	public void setReceipts(List<Receipt> receipts) {
		this.receipts = receipts;
	}
	
	public double getTotalTurnover() {
		return totalTurnover;
	}

	public void setTotalTurnover(double totalTurnover) {
		this.totalTurnover = totalTurnover;
	}
	
	 // Static setter method for markupEatable
    public static void setMarkupEatable(double markupEatable) {
        Store.markupEatable = markupEatable;
    }

    // Static setter method for markupNonEdible
    public static void setMarkupNonEdible(double markupNonEdible) {
        Store.markupNonEdible = markupNonEdible;
    }

}
