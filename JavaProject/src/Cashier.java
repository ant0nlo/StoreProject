public class Cashier {
    private String name;
    private int id;
    private double monthlySalary;

    // Constructor
    public Cashier(int id, String name, double monthlySalary) {
    	this.id = id;
        this.name = name;
        this.monthlySalary = monthlySalary;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    // Method to calculate annual salary
    public double calculateAnnualSalary() {
        return monthlySalary * 12;
    }

    @Override
    public String toString() {
        return "Cashier{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", monthlySalary=" + monthlySalary +
                '}';
    }
}
