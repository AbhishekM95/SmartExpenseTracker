package service;

import dao.TransactionDAO;
import model.Transaction;

import java.util.List;

public class TransactionService {

    private TransactionDAO transactionDAO;

    public TransactionService() {
        transactionDAO = new TransactionDAO();
    }


    // =========================================================
    // GET RECENT TRANSACTIONS
    // =========================================================

    public List<Transaction> getRecentTransactions(int userId) {

        if (userId <= 0) {
            return List.of();
        }

        return transactionDAO.getRecentTransactions(userId);
    }


    // =========================================================
    // GET ALL TRANSACTIONS
    // =========================================================

    public List<Transaction> getAllTransactions(int userId) {

        if (userId <= 0) {
            return List.of();
        }

        return transactionDAO.getAllTransactions(userId);
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

        if (userId <= 0) {
            return List.of();
        }

        return transactionDAO.filterTransactions(
                userId,
                type,
                category,
                search
        );
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

        if (id <= 0 ||
                userId <= 0 ||
                categoryId <= 0 ||
                amount <= 0 ||
                date == null ||
                date.isEmpty()) {

            return false;
        }

        return transactionDAO.updateExpense(
                id,
                userId,
                categoryId,
                amount,
                date,
                description
        );
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

        if (id <= 0 ||
                userId <= 0 ||
                categoryId <= 0 ||
                amount <= 0 ||
                date == null ||
                date.isEmpty()) {

            return false;
        }

        return transactionDAO.updateIncome(
                id,
                userId,
                categoryId,
                amount,
                date,
                description
        );
    }


    // =========================================================
    // DELETE EXPENSE
    // =========================================================

    public boolean deleteExpense(
            int id,
            int userId
    ) {

        if (id <= 0 || userId <= 0) {
            return false;
        }

        return transactionDAO.deleteExpense(
                id,
                userId
        );
    }


    // =========================================================
    // DELETE INCOME
    // =========================================================

    public boolean deleteIncome(
            int id,
            int userId
    ) {

        if (id <= 0 || userId <= 0) {
            return false;
        }

        return transactionDAO.deleteIncome(
                id,
                userId
        );
    }
}