package dao;

import db.DBConnection;
import model.CategorySummary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    public List<CategorySummary> getExpenseByCategory(int userId) {

        List<CategorySummary> summaries = new ArrayList<>();

        String sql =
                "SELECT c.category_name, SUM(e.amount) AS total " +
                        "FROM expenses e " +
                        "JOIN categories c ON e.category_id = c.id " +
                        "WHERE e.user_id = ? " +
                        "GROUP BY c.category_name " +
                        "ORDER BY total DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                CategorySummary summary =
                        new CategorySummary(
                                resultSet.getString("category_name"),
                                resultSet.getDouble("total")
                        );

                summaries.add(summary);
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return summaries;
    }


    public List<CategorySummary> getIncomeByCategory(int userId) {

        List<CategorySummary> summaries = new ArrayList<>();

        String sql =
                "SELECT c.category_name, SUM(i.amount) AS total " +
                        "FROM income i " +
                        "JOIN categories c ON i.category_id = c.id " +
                        "WHERE i.user_id = ? " +
                        "GROUP BY c.category_name " +
                        "ORDER BY total DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                CategorySummary summary =
                        new CategorySummary(
                                resultSet.getString("category_name"),
                                resultSet.getDouble("total")
                        );

                summaries.add(summary);
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return summaries;
    }
}