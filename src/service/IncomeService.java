package service;

import dao.IncomeDAO;
import model.Income;

public class IncomeService {

    private IncomeDAO incomeDAO;

    public IncomeService() {
        incomeDAO = new IncomeDAO();
    }

    public boolean addIncome(Income income) {

        if (income.getUserId() <= 0) {
            return false;
        }

        if (income.getCategoryId() <= 0) {
            return false;
        }

        if (income.getAmount() <= 0) {
            return false;
        }

        if (income.getIncomeDate() == null ||
                income.getIncomeDate().isEmpty()) {
            return false;
        }

        return incomeDAO.addIncome(income);
    }
}