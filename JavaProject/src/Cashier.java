import java.util.Objects;

public class Cashier {
	private String name;
    private int id;
    private double monthlySalary;

    public Cashier(int id, String name, double monthlySalary) {
    	this.id = id;
        this.name = name;
        this.monthlySalary = monthlySalary;
    }

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

    public double calculateAnnualSalary() {
        return monthlySalary * 12;
    }

    @Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Cashier other = (Cashier) obj;
		return id == other.id;
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
