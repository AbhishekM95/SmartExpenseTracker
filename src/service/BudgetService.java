package service;

import dao.BudgetDAO;
import model.Budget;

public class BudgetService {

    private BudgetDAO budgetDAO;

    public BudgetService() {
        budgetDAO = new BudgetDAO();
    }

    public boolean addBudget(Budget budget) {

        if (budget.getUserId() <= 0) {
            return false;
        }

        if (budget.getCategoryId() <= 0) {
            return false;
        }

        if (budget.getAmount() <= 0) {
            return false;
        }

        if (budget.getStartDate() == null ||
                budget.getStartDate().isEmpty()) {
            return false;
        }

        if (budget.getEndDate() == null ||
                budget.getEndDate().isEmpty()) {
            return false;
        }

        return budgetDAO.addBudget(budget);
    }
}