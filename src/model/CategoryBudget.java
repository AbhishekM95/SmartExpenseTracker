package model;

public class CategoryBudget {

    private String category;
    private double budget;
    private double spent;
    private double remaining;

    public CategoryBudget(
            String category,
            double budget,
            double spent) {

        this.category = category;
        this.budget = budget;
        this.spent = spent;
        this.remaining = budget - spent;
    }

    public String getCategory() {
        return category;
    }

    public double getBudget() {
        return budget;
    }

    public double getSpent() {
        return spent;
    }

    public double getRemaining() {
        return remaining;
    }
}