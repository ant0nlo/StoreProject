import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Store {
    private Set<Cashier> cashiers;
    private Set<Receipt> receipts;
    private Map<Integer, Goods> goodsMap;
    private BigDecimal totalTurnover;

    public Store() {
        this.cashiers = new HashSet<>();
        this.goodsMap = new HashMap<>();
        this.receipts = new HashSet<>();
        this.totalTurnover = BigDecimal.ZERO;
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
    public BigDecimal calculateTotalCashierSalaries() {
    	 BigDecimal totalSalaries = BigDecimal.ZERO;
         for (Cashier cashier : cashiers) {
             totalSalaries = totalSalaries.add(BigDecimal.valueOf(cashier.getMonthlySalary()).multiply(BigDecimal.valueOf(12)));
         }
         return totalSalaries;
    }

    // Method to calculate total costs for goods delivery
    public BigDecimal calculateTotalDeliveryCosts() {
        BigDecimal totalDeliveryCosts = BigDecimal.ZERO;
        for (Goods goods : goodsMap.values()) {
            totalDeliveryCosts = totalDeliveryCosts.add(goods.getUnitDeliveryPrice().multiply(BigDecimal.valueOf(goods.getTotalDelivered())));
        }
        return totalDeliveryCosts;
    }

    // Method to calculate total profit
    public BigDecimal calculateTotalProfit() {
        return getTotalTurnover().subtract(calculateTotalCashierSalaries()).subtract(calculateTotalDeliveryCosts());
    }

    // Method to get total number of receipts issued
    public int getTotalReceiptsIssued() {
        return getReceipts().size();
    }
    
    public Receipt checkoutClient(Checkout checkout, ShoppingCart cart) {
        Receipt receipt = checkout.markGoods(cart);
        totalTurnover = totalTurnover.add(receipt.getTotalAmountPaid());
        receipts.add(receipt);
        return receipt;
    }

	public Set<Receipt> getReceipts() {
		return receipts;
	}

	public void setReceipts(Set<Receipt> receipts) {
		this.receipts = receipts;
	}
	
	public BigDecimal getTotalTurnover() {
		return totalTurnover;
	}

	public void setTotalTurnover(BigDecimal totalTurnover) {
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
