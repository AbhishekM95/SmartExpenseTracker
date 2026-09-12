package dao;

import db.DBConnection;
import model.Budget;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BudgetDAO {

    public boolean addBudget(Budget budget) {

        String sql = "INSERT INTO budgets " +
                "(user_id, category_id, amount, start_date, end_date) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, budget.getUserId());
            statement.setInt(2, budget.getCategoryId());
            statement.setDouble(3, budget.getAmount());
            statement.setString(4, budget.getStartDate());
            statement.setString(5, budget.getEndDate());

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}