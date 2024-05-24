
public enum Category {
    EATABLE,
    NON_EDIBLE;

    private double value = 0.0;


    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
    
    public Category  getName() {
        return this;
    }
}

