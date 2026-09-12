package dao;

import db.DBConnection;
import model.CategoryBudget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryBudgetDAO {

    public List<CategoryBudget> getCategoryBudgets(int userId) {

        List<CategoryBudget> list = new ArrayList<>();

        String query =
                "SELECT c.category_name, " +
                        "COALESCE(SUM(b.amount), 0) AS budget, " +
                        "COALESCE((SELECT SUM(e.amount) " +
                        "FROM expenses e " +
                        "WHERE e.user_id = ? " +
                        "AND e.category_id = c.id), 0) AS spent " +
                        "FROM categories c " +
                        "LEFT JOIN budgets b " +
                        "ON c.id = b.category_id " +
                        "AND b.user_id = ? " +
                        "WHERE c.type = 'EXPENSE' " +
                        "GROUP BY c.id, c.category_name";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            statement.setInt(2, userId);

            ResultSet result = statement.executeQuery();

            while (result.next()) {

                String category =
                        result.getString("category_name");

                double budget =
                        result.getDouble("budget");

                double spent =
                        result.getDouble("spent");

                list.add(
                        new CategoryBudget(
                                category,
                                budget,
                                spent
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}