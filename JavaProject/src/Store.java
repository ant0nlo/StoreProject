import java.time.LocalDate;
import java.util.*;

public class Store {
    private Set<Cashier> cashiers;
    private Set<Receipt> receipts;
    private Map<Integer, Goods> goodsMap;
    private double totalTurnover;

    public Store() {
        this.cashiers = new HashSet<>();
        this.goodsMap = new HashMap<>();
        this.receipts = new HashSet<>();
        this.totalTurnover = 0;
    }
    
    public void addCashier(Cashier cashier) {
        cashiers.add(cashier);
    }

    public void addGoods(Goods goods) {
        goodsMap.put(goods.getId(), goods);
    }

    public Goods getGoodsById(int id) {
        return goodsMap.get(id);
    }

    // Method to check goods availability
    public boolean checkAvailability(int goodsId, int quantity) {
        Goods goods = getGoodsById(goodsId);
        if (goods == null) {
            throw new IllegalArgumentException("Goods with id " + goodsId + " not found.");
        }
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
        for (Goods goods : goodsMap.values()) {
            totalDeliveryCosts += goods.getUnitDeliveryPrice() * goods.getTotalAvailable();
        }
        return totalDeliveryCosts;
    }

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

	public Set<Receipt> getReceipts() {
		return receipts;
	}

	public void setReceipts(Set<Receipt> receipts) {
		this.receipts = receipts;
	}
	
	public double getTotalTurnover() {
		return totalTurnover;
	}

	public void setTotalTurnover(double totalTurnover) {
		this.totalTurnover = totalTurnover;
	}
	
	public static void setMarkup(Category category, double value) {
        category.setValue(value);
    }
	
	public static double getMarkup(Category category) {
        return category.getValue();
    }
	
    public void setExpirationDateInStore(Goods good, LocalDate expirationDate) {
    	good.setExpirationDate(expirationDate);
    }
    
    public void setDiscountPercentageInStore(Goods good, double discountPercentage) {
    	good.setDiscountPercentage(discountPercentage);
    }
}
