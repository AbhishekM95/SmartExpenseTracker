package service;

import dao.MonthlySummaryDAO;
import model.MonthlySummary;

public class MonthlySummaryService {

    private MonthlySummaryDAO monthlySummaryDAO;

    public MonthlySummaryService() {
        monthlySummaryDAO = new MonthlySummaryDAO();
    }

    public MonthlySummary getMonthlySummary(
            int userId,
            int month,
            int year) {

        if (userId <= 0 || month < 1 || month > 12 || year <= 0) {
            return new MonthlySummary(
                    0,
                    0,
                    0,
                    0,
                    "None"
            );
        }

        return monthlySummaryDAO.getMonthlySummary(
                userId,
                month,
                year
        );
    }
}