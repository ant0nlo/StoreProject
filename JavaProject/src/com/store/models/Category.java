package com.store.models;

public enum Category {
	EATABLE(0.0),
    NON_EDIBLE(0.0);

    private double value;

    Category(double value) {
        this.value = value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
    public Category getName() {
        return this;
    }
}

