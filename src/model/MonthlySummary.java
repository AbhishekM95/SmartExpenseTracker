package model;

public class MonthlySummary {

    private double totalIncome;
    private double totalExpense;
    private double savings;
    private int transactionCount;
    private String highestCategory;

    public MonthlySummary(
            double totalIncome,
            double totalExpense,
            double savings,
            int transactionCount,
            String highestCategory) {

        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.savings = savings;
        this.transactionCount = transactionCount;
        this.highestCategory = highestCategory;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getSavings() {
        return savings;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public String getHighestCategory() {
        return highestCategory;
    }
}