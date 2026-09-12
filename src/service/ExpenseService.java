package service;

import dao.ExpenseDAO;
import model.Expense;

public class ExpenseService {

    private ExpenseDAO expenseDAO;

    public ExpenseService() {
        expenseDAO = new ExpenseDAO();
    }

    public boolean addExpense(Expense expense) {

        if (expense.getUserId() <= 0) {
            return false;
        }

        if (expense.getCategoryId() <= 0) {
            return false;
        }

        if (expense.getAmount() <= 0) {
            return false;
        }

        if (expense.getExpenseDate() == null ||
                expense.getExpenseDate().isEmpty()) {
            return false;
        }

        return expenseDAO.addExpense(expense);
    }
}