package dao;

import db.DBConnection;
import model.Expense;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ExpenseDAO {

    public boolean addExpense(Expense expense) {

        String sql = "INSERT INTO expenses " +
                "(user_id, category_id, amount, expense_date, description) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, expense.getUserId());
            statement.setInt(2, expense.getCategoryId());
            statement.setDouble(3, expense.getAmount());
            statement.setString(4, expense.getExpenseDate());
            statement.setString(5, expense.getDescription());

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
}