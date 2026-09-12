package service;

import dao.SuggestionDAO;
import model.CategorySummary;

import java.util.ArrayList;
import java.util.List;

public class SuggestionService {

    private SuggestionDAO suggestionDAO;

    public SuggestionService() {
        suggestionDAO = new SuggestionDAO();
    }

    public List<String> generateSuggestions(int userId) {

        List<String> suggestions =
                new ArrayList<>();

        if (userId <= 0) {
            return suggestions;
        }

        // Get financial data
        double totalIncome =
                suggestionDAO.getTotalIncome(userId);

        double totalExpense =
                suggestionDAO.getTotalExpense(userId);

        double totalBudget =
                suggestionDAO.getTotalBudget(userId);

        double balance =
                totalIncome - totalExpense;


        // Get category-wise expenses
        List<CategorySummary> expenses =
                suggestionDAO.getExpenseByCategory(userId);


        // -------------------------------------------------
        // 1. No expense data
        // -------------------------------------------------

        if (expenses.isEmpty()) {

            suggestions.add(
                    "💡 No expenses found. Start adding expenses to receive personalized suggestions."
            );

        } else {

            // Highest spending category
            CategorySummary highestExpense =
                    expenses.get(0);

            double highestAmount =
                    highestExpense.getAmount();

            String highestCategory =
                    highestExpense.getCategory();


            // -------------------------------------------------
            // 2. Highest spending category
            // -------------------------------------------------

            suggestions.add(
                    "⚠️ Your highest spending category is "
                            + highestCategory
                            + " with ₹"
                            + String.format("%.2f", highestAmount)
                            + "."
            );


            // -------------------------------------------------
            // 3. Highest category percentage
            // -------------------------------------------------

            if (totalExpense > 0) {

                double percentage =
                        (highestAmount / totalExpense) * 100;

                if (percentage >= 50) {

                    suggestions.add(
                            "🔴 "
                                    + highestCategory
                                    + " accounts for "
                                    + String.format("%.1f", percentage)
                                    + "% of your total expenses. "
                                    + "Try reducing spending in this category."
                    );

                } else if (percentage >= 30) {

                    suggestions.add(
                            "🟠 "
                                    + highestCategory
                                    + " accounts for "
                                    + String.format("%.1f", percentage)
                                    + "% of your total expenses. "
                                    + "Keep an eye on this category."
                    );

                } else {

                    suggestions.add(
                            "🟢 Your spending is reasonably distributed across categories."
                    );
                }
            }
        }


        // -------------------------------------------------
        // 4. Budget analysis
        // -------------------------------------------------

        if (totalBudget > 0) {

            double remainingBudget =
                    totalBudget - totalExpense;

            double budgetPercentage =
                    (totalExpense / totalBudget) * 100;


            if (totalExpense > totalBudget) {

                double exceededAmount =
                        totalExpense - totalBudget;

                suggestions.add(
                        "🚨 You have exceeded your total budget by ₹"
                                + String.format("%.2f", exceededAmount)
                                + ". Try reducing unnecessary expenses."
                );

            } else if (budgetPercentage >= 80) {

                suggestions.add(
                        "⚠️ You have used "
                                + String.format("%.1f", budgetPercentage)
                                + "% of your budget. Only ₹"
                                + String.format("%.2f", remainingBudget)
                                + " is remaining."
                );

            } else if (budgetPercentage >= 50) {

                suggestions.add(
                        "💡 You have used "
                                + String.format("%.1f", budgetPercentage)
                                + "% of your budget. Continue monitoring your spending."
                );

            } else {

                suggestions.add(
                        "✅ Good job! You are using only "
                                + String.format("%.1f", budgetPercentage)
                                + "% of your budget."
                );
            }

        } else {

            suggestions.add(
                    "🎯 You have not set a budget yet. Setting a monthly budget can help control your spending."
            );
        }


        // -------------------------------------------------
        // 5. Balance analysis
        // -------------------------------------------------

        if (totalIncome > 0) {

            double expensePercentage =
                    (totalExpense / totalIncome) * 100;


            if (balance < 0) {

                suggestions.add(
                        "🚨 Your expenses are higher than your income. "
                                + "Try reducing non-essential spending."
                );

            } else if (expensePercentage >= 80) {

                suggestions.add(
                        "⚠️ You have spent "
                                + String.format("%.1f", expensePercentage)
                                + "% of your income. "
                                + "Consider increasing your savings."
                );

            } else if (expensePercentage <= 50) {

                suggestions.add(
                        "💰 Excellent! You are spending less than half of your income. "
                                + "Consider saving or investing the remaining amount."
                );

            } else {

                suggestions.add(
                        "💰 Your remaining balance is ₹"
                                + String.format("%.2f", balance)
                                + ". Consider saving a portion of it."
                );
            }
        }


        // -------------------------------------------------
        // 6. General saving suggestion
        // -------------------------------------------------

        if (balance > 0) {

            double suggestedSaving =
                    balance * 0.10;

            suggestions.add(
                    "💰 Try saving at least ₹"
                            + String.format("%.2f", suggestedSaving)
                            + " from your current remaining balance."
            );
        }


        return suggestions;
    }
}