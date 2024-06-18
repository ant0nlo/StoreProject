package com.store.services;

import java.math.BigDecimal;

import com.store.database.DatabaseManager;
import com.store.models.Cashier;
import com.store.models.Checkout;
import com.store.models.Goods;
import com.store.models.Receipt;
import com.store.models.ShoppingCart;
import com.store.models.Store;

public class StoreService {
    private DatabaseManager dbManager;
    private Store store;

    public StoreService() {
        dbManager = new DatabaseManager();
        store = new Store();
        dbManager.createTables();
    }

    // Cashier Services
    public void addCashier(Cashier cashier) {
        store.addCashier(cashier);
        dbManager.addCashier(cashier);
    }

    public Cashier getCashierById(int id) {
        for (Cashier cashier : store.getCashiers()) {
            if (cashier.getId() == id) {
                return cashier;
            }
        }
        return null;
    }

    // Goods Services
    public void addGoods(Goods goods) {
        store.addGoods(goods);
        dbManager.addGoods(goods);
    }

    public Goods getGoodsById(int id) {
        return store.getGoodsById(id);
    }

    // Checkout Services
    public Receipt checkoutClient(Checkout checkout, ShoppingCart cart) {
        Receipt receipt = store.checkoutClient(checkout, cart);
        dbManager.addReceipt(receipt);
        dbManager.addItemToShoppingCart(cart, receipt);
        return receipt;
    }

    // Store financial calculations
    public BigDecimal calculateTotalCashierSalaries() {
        return store.calculateTotalCashierSalaries();
    }

    public BigDecimal calculateTotalDeliveryCosts() {
        return store.calculateTotalDeliveryCosts();
    }

    public BigDecimal calculateTotalProfit() {
        return store.calculateTotalProfit();
    }

    public int getTotalReceiptsIssued() {
        return store.getTotalReceiptsIssued();
    }

    public BigDecimal getTotalTurnover() {
        return store.getTotalTurnover();
    }
}
