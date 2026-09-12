package dao;

import db.DBConnection;
import model.Income;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class IncomeDAO {

    public boolean addIncome(Income income) {

        String sql = "INSERT INTO income " +
                "(user_id, category_id, amount, income_date, description) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, income.getUserId());
            statement.setInt(2, income.getCategoryId());
            statement.setDouble(3, income.getAmount());
            statement.setString(4, income.getIncomeDate());
            statement.setString(5, income.getDescription());

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
}