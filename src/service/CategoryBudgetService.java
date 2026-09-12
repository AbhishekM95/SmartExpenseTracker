package service;

import dao.CategoryBudgetDAO;
import model.CategoryBudget;

import java.util.List;

public class CategoryBudgetService {

    private CategoryBudgetDAO categoryBudgetDAO;

    public CategoryBudgetService() {
        categoryBudgetDAO = new CategoryBudgetDAO();
    }

    public List<CategoryBudget> getCategoryBudgets(int userId) {

        if (userId <= 0) {
            return List.of();
        }

        return categoryBudgetDAO.getCategoryBudgets(userId);
    }
}