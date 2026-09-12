package dao;

import db.DBConnection;
import model.CategorySummary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SuggestionDAO {

    // Get expenses grouped by category
    public List<CategorySummary> getExpenseByCategory(int userId) {

        List<CategorySummary> summaries =
                new ArrayList<>();

        String sql =
                "SELECT c.category_name, SUM(e.amount) AS total " +
                        "FROM expenses e " +
                        "JOIN categories c ON e.category_id = c.id " +
                        "WHERE e.user_id = ? " +
                        "GROUP BY c.category_name " +
                        "ORDER BY total DESC";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                String category =
                        resultSet.getString("category_name");

                double amount =
                        resultSet.getDouble("total");

                summaries.add(
                        new CategorySummary(
                                category,
                                amount
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return summaries;
    }


    // Get total budget
    public double getTotalBudget(int userId) {

        double totalBudget = 0;

        String sql =
                "SELECT COALESCE(SUM(amount), 0) AS total " +
                        "FROM budgets " +
                        "WHERE user_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                totalBudget =
                        resultSet.getDouble("total");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return totalBudget;
    }


    // Get total expenses
    public double getTotalExpense(int userId) {

        double totalExpense = 0;

        String sql =
                "SELECT COALESCE(SUM(amount), 0) AS total " +
                        "FROM expenses " +
                        "WHERE user_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                totalExpense =
                        resultSet.getDouble("total");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return totalExpense;
    }


    // Get total income
    public double getTotalIncome(int userId) {

        double totalIncome = 0;

        String sql =
                "SELECT COALESCE(SUM(amount), 0) AS total " +
                        "FROM income " +
                        "WHERE user_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                totalIncome =
                        resultSet.getDouble("total");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return totalIncome;
    }
}