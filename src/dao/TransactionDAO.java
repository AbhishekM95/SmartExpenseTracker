package dao;

import db.DBConnection;
import model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {


    // =========================================================
    // GET RECENT 10 TRANSACTIONS
    // =========================================================

    public List<Transaction> getRecentTransactions(int userId) {

        List<Transaction> transactions =
                new ArrayList<>();

        String sql =
                "SELECT id, type, category, amount, transaction_date, description " +
                        "FROM (" +

                        "SELECT e.id AS id, " +
                        "'EXPENSE' AS type, " +
                        "c.category_name AS category, " +
                        "e.amount, " +
                        "e.expense_date AS transaction_date, " +
                        "e.description " +
                        "FROM expenses e " +
                        "JOIN categories c ON e.category_id = c.id " +
                        "WHERE e.user_id = ? " +

                        "UNION ALL " +

                        "SELECT i.id AS id, " +
                        "'INCOME' AS type, " +
                        "c.category_name AS category, " +
                        "i.amount, " +
                        "i.income_date AS transaction_date, " +
                        "i.description " +
                        "FROM income i " +
                        "JOIN categories c ON i.category_id = c.id " +
                        "WHERE i.user_id = ? " +

                        ") AS transactions " +
                        "ORDER BY transaction_date DESC " +
                        "LIMIT 10";


        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);
            statement.setInt(2, userId);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                Transaction transaction =
                        new Transaction(

                                resultSet.getInt("id"),

                                resultSet.getString("type"),

                                resultSet.getString("category"),

                                resultSet.getDouble("amount"),

                                resultSet.getString("transaction_date"),

                                resultSet.getString("description")
                        );

                transactions.add(transaction);
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return transactions;
    }


    // =========================================================
    // GET ALL TRANSACTIONS
    // =========================================================

    public List<Transaction> getAllTransactions(int userId) {

        List<Transaction> transactions =
                new ArrayList<>();

        String sql =
                "SELECT id, type, category, amount, transaction_date, description " +
                        "FROM (" +

                        "SELECT e.id AS id, " +
                        "'EXPENSE' AS type, " +
                        "c.category_name AS category, " +
                        "e.amount, " +
                        "e.expense_date AS transaction_date, " +
                        "e.description " +
                        "FROM expenses e " +
                        "JOIN categories c ON e.category_id = c.id " +
                        "WHERE e.user_id = ? " +

                        "UNION ALL " +

                        "SELECT i.id AS id, " +
                        "'INCOME' AS type, " +
                        "c.category_name AS category, " +
                        "i.amount, " +
                        "i.income_date AS transaction_date, " +
                        "i.description " +
                        "FROM income i " +
                        "JOIN categories c ON i.category_id = c.id " +
                        "WHERE i.user_id = ? " +

                        ") AS transactions " +
                        "ORDER BY transaction_date DESC";


        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);
            statement.setInt(2, userId);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                Transaction transaction =
                        new Transaction(

                                resultSet.getInt("id"),

                                resultSet.getString("type"),

                                resultSet.getString("category"),

                                resultSet.getDouble("amount"),

                                resultSet.getString("transaction_date"),

                                resultSet.getString("description")
                        );

                transactions.add(transaction);
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return transactions;
    }


    // =========================================================
    // FILTER TRANSACTIONS
    // =========================================================

    public List<Transaction> filterTransactions(
            int userId,
            String type,
            String category,
            String search
    ) {

        List<Transaction> transactions =
                new ArrayList<>();

        String sql =
                "SELECT id, type, category, amount, transaction_date, description " +
                        "FROM (" +

                        "SELECT e.id AS id, " +
                        "'EXPENSE' AS type, " +
                        "c.category_name AS category, " +
                        "e.amount, " +
                        "e.expense_date AS transaction_date, " +
                        "e.description " +
                        "FROM expenses e " +
                        "JOIN categories c ON e.category_id = c.id " +
                        "WHERE e.user_id = ? " +

                        "UNION ALL " +

                        "SELECT i.id AS id, " +
                        "'INCOME' AS type, " +
                        "c.category_name AS category, " +
                        "i.amount, " +
                        "i.income_date AS transaction_date, " +
                        "i.description " +
                        "FROM income i " +
                        "JOIN categories c ON i.category_id = c.id " +
                        "WHERE i.user_id = ? " +

                        ") AS transactions " +

                        "WHERE (? = 'ALL' OR type = ?) " +

                        "AND (? = 'ALL' OR category = ?) " +

                        "AND (? = '' OR LOWER(description) LIKE ?) " +

                        "ORDER BY transaction_date DESC";


        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            String searchText =
                    "%" + search.toLowerCase() + "%";

            statement.setInt(1, userId);
            statement.setInt(2, userId);

            statement.setString(3, type);
            statement.setString(4, type);

            statement.setString(5, category);
            statement.setString(6, category);

            statement.setString(7, search);
            statement.setString(8, searchText);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                Transaction transaction =
                        new Transaction(

                                resultSet.getInt("id"),

                                resultSet.getString("type"),

                                resultSet.getString("category"),

                                resultSet.getDouble("amount"),

                                resultSet.getString("transaction_date"),

                                resultSet.getString("description")
                        );

                transactions.add(transaction);
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return transactions;
    }


    // =========================================================
    // UPDATE EXPENSE
    // =========================================================

    public boolean updateExpense(
            int id,
            int userId,
            int categoryId,
            double amount,
            String date,
            String description
    ) {

        String sql =
                "UPDATE expenses " +
                        "SET category_id = ?, " +
                        "amount = ?, " +
                        "expense_date = ?, " +
                        "description = ? " +
                        "WHERE id = ? AND user_id = ?";


        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, categoryId);
            statement.setDouble(2, amount);
            statement.setString(3, date);
            statement.setString(4, description);
            statement.setInt(5, id);
            statement.setInt(6, userId);

            int rowsUpdated =
                    statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // UPDATE INCOME
    // =========================================================

    public boolean updateIncome(
            int id,
            int userId,
            int categoryId,
            double amount,
            String date,
            String description
    ) {

        String sql =
                "UPDATE income " +
                        "SET category_id = ?, " +
                        "amount = ?, " +
                        "income_date = ?, " +
                        "description = ? " +
                        "WHERE id = ? AND user_id = ?";


        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, categoryId);
            statement.setDouble(2, amount);
            statement.setString(3, date);
            statement.setString(4, description);
            statement.setInt(5, id);
            statement.setInt(6, userId);

            int rowsUpdated =
                    statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // DELETE EXPENSE
    // =========================================================

    public boolean deleteExpense(
            int id,
            int userId
    ) {

        String sql =
                "DELETE FROM expenses " +
                        "WHERE id = ? AND user_id = ?";


        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);
            statement.setInt(2, userId);

            int rowsDeleted =
                    statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // DELETE INCOME
    // =========================================================

    public boolean deleteIncome(
            int id,
            int userId
    ) {

        String sql =
                "DELETE FROM income " +
                        "WHERE id = ? AND user_id = ?";


        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);
            statement.setInt(2, userId);

            int rowsDeleted =
                    statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

}