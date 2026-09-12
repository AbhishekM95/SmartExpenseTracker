package service;

import dao.DashboardDAO;

public class DashboardService {

    private DashboardDAO dashboardDAO;

    public DashboardService() {
        dashboardDAO = new DashboardDAO();
    }

    public double getTotalIncome(int userId) {

        if (userId <= 0) {
            return 0;
        }

        return dashboardDAO.getTotalIncome(userId);
    }

    public double getTotalExpense(int userId) {

        if (userId <= 0) {
            return 0;
        }

        return dashboardDAO.getTotalExpense(userId);
    }

    public double getTotalBudget(int userId) {

        if (userId <= 0) {
            return 0;
        }

        return dashboardDAO.getTotalBudget(userId);
    }

    public double getBalance(int userId) {

        double income = getTotalIncome(userId);
        double expense = getTotalExpense(userId);

        return income - expense;
    }
}