package service;

import dao.ReportDAO;
import model.CategorySummary;

import java.util.List;

public class ReportService {

    private ReportDAO reportDAO;

    public ReportService() {
        reportDAO = new ReportDAO();
    }

    public List<CategorySummary> getExpenseByCategory(int userId) {

        if (userId <= 0) {
            return List.of();
        }

        return reportDAO.getExpenseByCategory(userId);
    }

    public List<CategorySummary> getIncomeByCategory(int userId) {

        if (userId <= 0) {
            return List.of();
        }

        return reportDAO.getIncomeByCategory(userId);
    }
}