package dao;

import db.DBConnection;
import model.MonthlySummary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MonthlySummaryDAO {

    public MonthlySummary getMonthlySummary(
            int userId,
            int month,
            int year) {

        double totalIncome = 0;
        double totalExpense = 0;
        int transactionCount = 0;
        String highestCategory = "None";

        try (Connection connection = DBConnection.getConnection()) {

            // Total Income
            String incomeQuery =
                    "SELECT COALESCE(SUM(amount), 0) " +
                            "FROM income " +
                            "WHERE user_id = ? " +
                            "AND MONTH(income_date) = ? " +
                            "AND YEAR(income_date) = ?";

            PreparedStatement incomeStatement =
                    connection.prepareStatement(incomeQuery);

            incomeStatement.setInt(1, userId);
            incomeStatement.setInt(2, month);
            incomeStatement.setInt(3, year);

            ResultSet incomeResult =
                    incomeStatement.executeQuery();

            if (incomeResult.next()) {
                totalIncome = incomeResult.getDouble(1);
            }


            // Total Expenses
            String expenseQuery =
                    "SELECT COALESCE(SUM(amount), 0) " +
                            "FROM expenses " +
                            "WHERE user_id = ? " +
                            "AND MONTH(expense_date) = ? " +
                            "AND YEAR(expense_date) = ?";

            PreparedStatement expenseStatement =
                    connection.prepareStatement(expenseQuery);

            expenseStatement.setInt(1, userId);
            expenseStatement.setInt(2, month);
            expenseStatement.setInt(3, year);

            ResultSet expenseResult =
                    expenseStatement.executeQuery();

            if (expenseResult.next()) {
                totalExpense = expenseResult.getDouble(1);
            }


            // Number of transactions
            String countQuery =
                    "SELECT " +
                            "(SELECT COUNT(*) FROM income " +
                            "WHERE user_id = ? " +
                            "AND MONTH(income_date) = ? " +
                            "AND YEAR(income_date) = ?) " +
                            "+ " +
                            "(SELECT COUNT(*) FROM expenses " +
                            "WHERE user_id = ? " +
                            "AND MONTH(expense_date) = ? " +
                            "AND YEAR(expense_date) = ?)";

            PreparedStatement countStatement =
                    connection.prepareStatement(countQuery);

            countStatement.setInt(1, userId);
            countStatement.setInt(2, month);
            countStatement.setInt(3, year);
            countStatement.setInt(4, userId);
            countStatement.setInt(5, month);
            countStatement.setInt(6, year);

            ResultSet countResult =
                    countStatement.executeQuery();

            if (countResult.next()) {
                transactionCount = countResult.getInt(1);
            }


            // Highest spending category
            String categoryQuery =
                    "SELECT c.category_name, SUM(e.amount) AS total " +
                            "FROM expenses e " +
                            "JOIN categories c ON e.category_id = c.id " +
                            "WHERE e.user_id = ? " +
                            "AND MONTH(e.expense_date) = ? " +
                            "AND YEAR(e.expense_date) = ? " +
                            "GROUP BY c.category_name " +
                            "ORDER BY total DESC " +
                            "LIMIT 1";

            PreparedStatement categoryStatement =
                    connection.prepareStatement(categoryQuery);

            categoryStatement.setInt(1, userId);
            categoryStatement.setInt(2, month);
            categoryStatement.setInt(3, year);

            ResultSet categoryResult =
                    categoryStatement.executeQuery();

            if (categoryResult.next()) {
                highestCategory =
                        categoryResult.getString("category_name");
            }


            double savings = totalIncome - totalExpense;

            return new MonthlySummary(
                    totalIncome,
                    totalExpense,
                    savings,
                    transactionCount,
                    highestCategory
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new MonthlySummary(
                0,
                0,
                0,
                0,
                "None"
        );
    }
}