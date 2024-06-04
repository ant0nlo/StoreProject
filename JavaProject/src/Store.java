import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Store {
    private Set<Cashier> cashiers;
    private Set<Receipt> receipts;
    private Map<Integer, Goods> deliveredGoods;
    private Map<Integer, Integer> soldGoods;
    private BigDecimal totalTurnover;

    public Store() {
        this.cashiers = new HashSet<>();
        this.receipts = new HashSet<>();
        this.deliveredGoods = new HashMap<>();
        this.soldGoods = new HashMap<>();
        this.totalTurnover = BigDecimal.ZERO;
    }
    
    public void addCashier(Cashier cashier) {
        cashiers.add(cashier);
    }
      
    public Cashier getCashierById(int id) {
        for (Cashier cashier : cashiers) {
          if (cashier.getId() == id) {
            return cashier;
          }
        }
        return null;
      }

    public void addGoods(Goods goods) {
        deliveredGoods.put(goods.getId(), goods);
    }

    public Goods getGoodsById(int id) {
        return deliveredGoods.get(id);
    }
    
	public BigDecimal getTotalTurnover() {
		return totalTurnover;
	}
	
    // Method to get total number of receipts issued
    public int getTotalReceiptsIssued() {
        return getReceipts().size();
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
             totalSalaries = totalSalaries.add(cashier.getMonthlySalary().multiply(BigDecimal.valueOf(12)));
         }
         return totalSalaries;
    }

    // Method to calculate total costs for goods delivery
    public BigDecimal calculateTotalDeliveryCosts() {
        BigDecimal totalDeliveryCosts = BigDecimal.ZERO;
        for (Goods goods : deliveredGoods.values()) {
            totalDeliveryCosts = totalDeliveryCosts.add(goods.getUnitDeliveryPrice().multiply(BigDecimal.valueOf(goods.getTotalDelivered())));
        }
        return totalDeliveryCosts;
    }

    // Method to calculate total profit
    public BigDecimal calculateTotalProfit() {
        return getTotalTurnover().subtract(calculateTotalCashierSalaries()).subtract(calculateTotalDeliveryCosts());
    }
    
    // Method to add sold goods
    private void addSoldGoods(Goods goods, int quantitySold) {
        soldGoods.merge(goods.getId(), quantitySold, Integer::sum);
    }
    
    public Receipt checkoutClient(Checkout checkout, ShoppingCart cart) {
        Receipt receipt = checkout.sellGoods(cart);
        totalTurnover = totalTurnover.add(receipt.getTotalAmountPaid());
        receipts.add(receipt);

        for (Map.Entry<Goods, Integer> entry : cart.getItems().entrySet()) {
            Goods goods = entry.getKey();
            int quantity = entry.getValue();
            addSoldGoods(goods, quantity);
        }
        
        return receipt;
    }

    public Set<Cashier> getCashier() {
		return cashiers;
	}
    
	public Set<Receipt> getReceipts() {
		return receipts;
	}
	
    public Map<Integer, Integer> getSoldGoods() {
        return soldGoods;
    }
    
    public Map<Integer, Goods> getDeliveredGoods() {
        return deliveredGoods;
    }
	
	public void setMarkup(Category category, double value) {
        category.setValue(value);
    }
	
    public void setExpirationDateInStore(Goods good, LocalDate expirationDate) {
    	good.setExpirationDate(expirationDate);
    }
    
    public void setDiscountPercentageInStore(Goods good, double discountPercentage) {
    	good.setDiscountPercentage(discountPercentage);
    }
    
}
