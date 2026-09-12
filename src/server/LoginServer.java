package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import model.User;
import model.Expense;
import model.Income;
import model.Budget;
import model.Transaction;
import model.CategorySummary;
import model.MonthlySummary;

import service.UserService;
import service.ExpenseService;
import service.IncomeService;
import service.BudgetService;
import service.DashboardService;
import service.TransactionService;
import service.ReportService;
import service.SuggestionService;
import service.MonthlySummaryService;
import model.CategoryBudget;
import service.CategoryBudgetService;

import java.util.List;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class LoginServer {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8081),
                0
        );

        // =====================================================
        // SERVER ENDPOINTS
        // =====================================================

        server.createContext(
                "/login",
                LoginServer::handleLogin
        );

        server.createContext(
                "/register",
                LoginServer::handleRegister
        );

        server.createContext(
                "/expense",
                LoginServer::handleExpense
        );

        server.createContext(
                "/income",
                LoginServer::handleIncome
        );

        server.createContext(
                "/budget",
                LoginServer::handleBudget
        );

        server.createContext(
                "/dashboard",
                LoginServer::handleDashboard
        );

        server.createContext(
                "/transactions",
                LoginServer::handleTransactions
        );

        server.createContext(
                "/all-transactions",
                LoginServer::handleAllTransactions
        );

        server.createContext(
                "/reports",
                LoginServer::handleReports
        );

        server.createContext(
                "/suggestions",
                LoginServer::handleSuggestions
        );

        server.createContext(
                "/filter-transactions",
                LoginServer::handleFilterTransactions
        );

        server.createContext(
                "/update-expense",
                LoginServer::handleUpdateExpense
        );

        server.createContext(
                "/update-income",
                LoginServer::handleUpdateIncome
        );

        server.createContext(
                "/delete-expense",
                LoginServer::handleDeleteExpense
        );

        server.createContext(
                "/delete-income",
                LoginServer::handleDeleteIncome
        );

        // NEW MONTHLY SUMMARY ENDPOINT
        server.createContext(
                "/monthly-summary",
                LoginServer::handleMonthlySummary
        );
        server.createContext(
                "/category-budgets",
                LoginServer::handleCategoryBudgets
        );


        server.setExecutor(null);

        server.start();

        System.out.println("Login server started!");
        System.out.println(
                "Open: http://localhost:8081/login"
        );
    }


    // =========================================================
    // LOGIN
    // =========================================================

    private static void handleLogin(
            HttpExchange exchange)
            throws IOException {

        InputStream inputStream =
                exchange.getRequestBody();

        String requestBody =
                new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );

        System.out.println(
                "Received data: " + requestBody
        );

        String[] parts =
                requestBody.split("&");

        String email =
                URLDecoder.decode(
                        parts[0].split("=")[1],
                        StandardCharsets.UTF_8
                );

        String password =
                URLDecoder.decode(
                        parts[1].split("=")[1],
                        StandardCharsets.UTF_8
                );

        UserService userService =
                new UserService();

        User user =
                userService.loginUser(
                        email,
                        password
                );

        String response;

        if (user != null) {

            response =
                    "Login successful!"
                            + "|"
                            + user.getId();

        } else {

            response =
                    "Invalid email or password!";
        }

        System.out.println(
                "Sending response: " + response
        );

        sendResponse(
                exchange,
                response
        );
    }


    // =========================================================
    // REGISTER
    // =========================================================

    private static void handleRegister(
            HttpExchange exchange)
            throws IOException {

        InputStream inputStream =
                exchange.getRequestBody();

        String requestBody =
                new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );

        System.out.println(
                "Received registration data: "
                        + requestBody
        );

        String[] parts =
                requestBody.split("&");

        String name =
                URLDecoder.decode(
                        parts[0].split("=")[1],
                        StandardCharsets.UTF_8
                );

        String email =
                URLDecoder.decode(
                        parts[1].split("=")[1],
                        StandardCharsets.UTF_8
                );

        String password =
                URLDecoder.decode(
                        parts[2].split("=")[1],
                        StandardCharsets.UTF_8
                );

        User user =
                new User(
                        name,
                        email,
                        password
                );

        UserService userService =
                new UserService();

        boolean result =
                userService.registerUser(user);

        String response;

        if (result) {

            response =
                    "Registration successful!";

        } else {

            response =
                    "Registration failed!";
        }

        sendResponse(
                exchange,
                response
        );
    }


    // =========================================================
    // EXPENSE
    // =========================================================

    private static void handleExpense(
            HttpExchange exchange)
            throws IOException {

        InputStream inputStream =
                exchange.getRequestBody();

        String requestBody =
                new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );

        System.out.println(
                "Received expense data: "
                        + requestBody
        );

        String[] parts =
                requestBody.split("&");

        int userId =
                Integer.parseInt(
                        URLDecoder.decode(
                                parts[0].split("=")[1],
                                StandardCharsets.UTF_8
                        )
                );

        int categoryId =
                Integer.parseInt(
                        URLDecoder.decode(
                                parts[1].split("=")[1],
                                StandardCharsets.UTF_8
                        )
                );

        double amount =
                Double.parseDouble(
                        URLDecoder.decode(
                                parts[2].split("=")[1],
                                StandardCharsets.UTF_8
                        )
                );

        String expenseDate =
                URLDecoder.decode(
                        parts[3].split("=")[1],
                        StandardCharsets.UTF_8
                );

        String description =
                URLDecoder.decode(
                        parts[4].split("=")[1],
                        StandardCharsets.UTF_8
                );

        Expense expense =
                new Expense(
                        userId,
                        categoryId,
                        amount,
                        expenseDate,
                        description
                );

        ExpenseService expenseService =
                new ExpenseService();

        boolean result =
                expenseService.addExpense(expense);

        String response;

        if (result) {

            response =
                    "Expense added successfully!";

        } else {

            response =
                    "Failed to add expense!";
        }

        sendResponse(
                exchange,
                response
        );
    }


    // =========================================================
    // INCOME
    // =========================================================

    private static void handleIncome(
            HttpExchange exchange)
            throws IOException {

        InputStream inputStream =
                exchange.getRequestBody();

        String requestBody =
                new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );

        System.out.println(
                "Received income data: "
                        + requestBody
        );

        String[] parts =
                requestBody.split("&");

        int userId =
                Integer.parseInt(
                        URLDecoder.decode(
                                parts[0].split("=")[1],
                                StandardCharsets.UTF_8
                        )
                );

        int categoryId =
                Integer.parseInt(
                        URLDecoder.decode(
                                parts[1].split("=")[1],
                                StandardCharsets.UTF_8
                        )
                );

        double amount =
                Double.parseDouble(
                        URLDecoder.decode(
                                parts[2].split("=")[1],
                                StandardCharsets.UTF_8
                        )
                );

        String incomeDate =
                URLDecoder.decode(
                        parts[3].split("=")[1],
                        StandardCharsets.UTF_8
                );

        String description =
                URLDecoder.decode(
                        parts[4].split("=")[1],
                        StandardCharsets.UTF_8
                );

        Income income =
                new Income(
                        userId,
                        categoryId,
                        amount,
                        incomeDate,
                        description
                );

        IncomeService incomeService =
                new IncomeService();

        boolean result =
                incomeService.addIncome(income);

        String response;

        if (result) {

            response =
                    "Income added successfully!";

        } else {

            response =
                    "Failed to add income!";
        }

        sendResponse(
                exchange,
                response
        );
    }


    // =========================================================
    // BUDGET
    // =========================================================

    private static void handleBudget(
            HttpExchange exchange)
            throws IOException {

        InputStream inputStream =
                exchange.getRequestBody();

        String requestBody =
                new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );

        System.out.println(
                "Received budget data: "
                        + requestBody
        );

        String[] parts =
                requestBody.split("&");

        int userId =
                Integer.parseInt(
                        URLDecoder.decode(
                                parts[0].split("=")[1],
                                StandardCharsets.UTF_8
                        )
                );

        int categoryId =
                Integer.parseInt(
                        URLDecoder.decode(
                                parts[1].split("=")[1],
                                StandardCharsets.UTF_8
                        )
                );

        double amount =
                Double.parseDouble(
                        URLDecoder.decode(
                                parts[2].split("=")[1],
                                StandardCharsets.UTF_8
                        )
                );

        String startDate =
                URLDecoder.decode(
                        parts[3].split("=")[1],
                        StandardCharsets.UTF_8
                );

        String endDate =
                URLDecoder.decode(
                        parts[4].split("=")[1],
                        StandardCharsets.UTF_8
                );

        Budget budget =
                new Budget(
                        userId,
                        categoryId,
                        amount,
                        startDate,
                        endDate
                );

        BudgetService budgetService =
                new BudgetService();

        boolean result =
                budgetService.addBudget(budget);

        String response;

        if (result) {

            response =
                    "Budget added successfully!";

        } else {

            response =
                    "Failed to add budget!";
        }

        sendResponse(
                exchange,
                response
        );
    }


    // =========================================================
    // DASHBOARD
    // =========================================================

    private static void handleDashboard(
            HttpExchange exchange)
            throws IOException {

        InputStream inputStream =
                exchange.getRequestBody();

        String requestBody =
                new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );

        System.out.println(
                "Received dashboard request: "
                        + requestBody
        );

        String[] parts =
                requestBody.split("=");

        int userId =
                Integer.parseInt(
                        URLDecoder.decode(
                                parts[1],
                                StandardCharsets.UTF_8
                        )
                );

        DashboardService dashboardService =
                new DashboardService();

        double totalIncome =
                dashboardService.getTotalIncome(
                        userId
                );

        double totalExpense =
                dashboardService.getTotalExpense(
                        userId
                );

        double totalBudget =
                dashboardService.getTotalBudget(
                        userId
                );

        double balance =
                dashboardService.getBalance(
                        userId
                );

        String response =
                "income=" + totalIncome
                        + "&expense=" + totalExpense
                        + "&budget=" + totalBudget
                        + "&balance=" + balance;

        sendResponse(
                exchange,
                response
        );
    }


    // =========================================================
    // RECENT TRANSACTIONS
    // =========================================================

    private static void handleTransactions(
            HttpExchange exchange)
            throws IOException {

        InputStream inputStream =
                exchange.getRequestBody();

        String requestBody =
                new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );

        System.out.println(
                "Received transaction request: "
                        + requestBody
        );

        String[] parts =
                requestBody.split("=");

        int userId =
                Integer.parseInt(
                        URLDecoder.decode(
                                parts[1],
                                StandardCharsets.UTF_8
                        )
                );

        TransactionService transactionService =
                new TransactionService();

        List<Transaction> transactions =
                transactionService
                        .getRecentTransactions(userId);

        StringBuilder response =
                new StringBuilder();

        for (Transaction transaction :
                transactions) {

            response.append(
                    transaction.getId()
            ).append("|");

            response.append(
                    transaction.getType()
            ).append("|");

            response.append(
                    transaction.getCategory()
            ).append("|");

            response.append(
                    transaction.getAmount()
            ).append("|");

            response.append(
                    transaction.getDate()
            ).append("|");

            String description =
                    transaction.getDescription();

            if (description != null) {
                response.append(description);
            }

            response.append("\n");
        }

        sendResponse(
                exchange,
                response.toString()
        );
    }


    // =========================================================
    // ALL TRANSACTIONS
    // =========================================================

    private static void handleAllTransactions(
            HttpExchange exchange)
            throws IOException {

        InputStream inputStream =
                exchange.getRequestBody();

        String requestBody =
                new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );

        System.out.println(
                "Received all transactions request: "
                        + requestBody
        );

        String[] parts =
                requestBody.split("=");

        int userId =
                Integer.parseInt(
                        URLDecoder.decode(
                                parts[1],
                                StandardCharsets.UTF_8
                        )
                );

        TransactionService transactionService =
                new TransactionService();

        List<Transaction> transactions =
                transactionService
                        .getAllTransactions(userId);

        StringBuilder response =
                new StringBuilder();

        for (Transaction transaction :
                transactions) {

            response.append(
                    transaction.getId()
            ).append("|");

            response.append(
                    transaction.getType()
            ).append("|");

            response.append(
                    transaction.getCategory()
            ).append("|");

            response.append(
                    transaction.getAmount()
            ).append("|");

            response.append(
                    transaction.getDate()
            ).append("|");

            String description =
                    transaction.getDescription();

            if (description != null) {
                response.append(description);
            }

            response.append("\n");
        }

        sendResponse(
                exchange,
                response.toString()
        );
    }


    // =========================================================
    // REPORTS
    // =========================================================

    private static void handleReports(
            HttpExchange exchange)
            throws IOException {

        InputStream inputStream =
                exchange.getRequestBody();

        String requestBody =
                new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );

        System.out.println(
                "Received report request: "
                        + requestBody
        );

        String[] parts =
                requestBody.split("=");

        int userId =
                Integer.parseInt(
                        URLDecoder.decode(
                                parts[1],
                                StandardCharsets.UTF_8
                        )
                );

        ReportService reportService =
                new ReportService();

        List<CategorySummary> expenseSummaries =
                reportService.getExpenseByCategory(
                        userId
                );

        List<CategorySummary> incomeSummaries =
                reportService.getIncomeByCategory(
                        userId
                );

        StringBuilder response =
                new StringBuilder();

        for (CategorySummary summary :
                expenseSummaries) {

            response.append("EXPENSE")
                    .append("|")
                    .append(summary.getCategory())
                    .append("|")
                    .append(summary.getAmount())
                    .append("\n");
        }

        for (CategorySummary summary :
                incomeSummaries) {

            response.append("INCOME")
                    .append("|")
                    .append(summary.getCategory())
                    .append("|")
                    .append(summary.getAmount())
                    .append("\n");
        }

        sendResponse(
                exchange,
                response.toString()
        );
    }


    // =========================================================
    // SPENDING SUGGESTIONS
    // =========================================================

    private static void handleSuggestions(
            HttpExchange exchange)
            throws IOException {

        InputStream inputStream =
                exchange.getRequestBody();

        String requestBody =
                new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );

        System.out.println(
                "Received suggestion request: "
                        + requestBody
        );

        String[] parts =
                requestBody.split("=");

        int userId =
                Integer.parseInt(
                        URLDecoder.decode(
                                parts[1],
                                StandardCharsets.UTF_8
                        )
                );

        SuggestionService suggestionService =
                new SuggestionService();

        List<String> suggestions =
                suggestionService.generateSuggestions(
                        userId
                );

        StringBuilder response =
                new StringBuilder();

        for (String suggestion :
                suggestions) {

            response.append(
                    suggestion
            ).append("\n");
        }

        sendResponse(
                exchange,
                response.toString()
        );
    }


    // =========================================================
    // FILTER TRANSACTIONS
    // =========================================================

    private static void handleFilterTransactions(
            HttpExchange exchange)
            throws IOException {

        InputStream inputStream =
                exchange.getRequestBody();

        String requestBody =
                new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );

        System.out.println(
                "Received filter transaction request: "
                        + requestBody
        );

        String[] parts =
                requestBody.split("&");

        int userId = 0;

        String type = "ALL";

        String category = "ALL";

        String search = "";

        for (String part : parts) {

            String[] keyValue =
                    part.split("=", 2);

            if (keyValue.length < 2) {
                continue;
            }

            String key =
                    URLDecoder.decode(
                            keyValue[0],
                            StandardCharsets.UTF_8
                    );

            String value =
                    URLDecoder.decode(
                            keyValue[1],
                            StandardCharsets.UTF_8
                    );

            if (key.equals("userId")) {

                userId =
                        Integer.parseInt(value);

            } else if (key.equals("type")) {

                type = value;

            } else if (key.equals("category")) {

                category = value;

            } else if (key.equals("search")) {

                search = value;
            }
        }

        TransactionService transactionService =
                new TransactionService();

        List<Transaction> transactions =
                transactionService.filterTransactions(
                        userId,
                        type,
                        category,
                        search
                );

        StringBuilder response =
                new StringBuilder();

        for (Transaction transaction :
                transactions) {

            response.append(
                    transaction.getId()
            ).append("|");

            response.append(
                    transaction.getType()
            ).append("|");

            response.append(
                    transaction.getCategory()
            ).append("|");

            response.append(
                    transaction.getAmount()
            ).append("|");

            response.append(
                    transaction.getDate()
            ).append("|");

            String description =
                    transaction.getDescription();

            if (description != null) {
                response.append(description);
            }

            response.append("\n");
        }

        sendResponse(
                exchange,
                response.toString()
        );
    }


    // =========================================================
    // UPDATE EXPENSE
    // =========================================================

    private static void handleUpdateExpense(
            HttpExchange exchange)
            throws IOException {

        InputStream inputStream =
                exchange.getRequestBody();

        String requestBody =
                new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );

        System.out.println(
                "Received update expense request: "
                        + requestBody
        );

        String[] parts =
                requestBody.split("&");

        int id = 0;
        int userId = 0;
        int categoryId = 0;
        double amount = 0;
        String date = "";
        String description = "";

        for (String part : parts) {

            String[] keyValue =
                    part.split("=", 2);

            if (keyValue.length < 2) {
                continue;
            }

            String key =
                    URLDecoder.decode(
                            keyValue[0],
                            StandardCharsets.UTF_8
                    );

            String value =
                    URLDecoder.decode(
                            keyValue[1],
                            StandardCharsets.UTF_8
                    );

            if (key.equals("id")) {

                id = Integer.parseInt(value);

            } else if (key.equals("userId")) {

                userId = Integer.parseInt(value);

            } else if (key.equals("categoryId")) {

                categoryId = Integer.parseInt(value);

            } else if (key.equals("amount")) {

                amount = Double.parseDouble(value);

            } else if (key.equals("date")) {

                date = value;

            } else if (key.equals("description")) {

                description = value;
            }
        }

        TransactionService transactionService =
                new TransactionService();

        boolean result =
                transactionService.updateExpense(
                        id,
                        userId,
                        categoryId,
                        amount,
                        date,
                        description
                );

        String response;

        if (result) {
            response =
                    "Expense updated successfully!";
        } else {
            response =
                    "Failed to update expense!";
        }

        sendResponse(
                exchange,
                response
        );
    }


    // =========================================================
    // UPDATE INCOME
    // =========================================================

    private static void handleUpdateIncome(
            HttpExchange exchange)
            throws IOException {

        InputStream inputStream =
                exchange.getRequestBody();

        String requestBody =
                new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );

        System.out.println(
                "Received update income request: "
                        + requestBody
        );

        String[] parts =
                requestBody.split("&");

        int id = 0;
        int userId = 0;
        int categoryId = 0;
        double amount = 0;
        String date = "";
        String description = "";

        for (String part : parts) {

            String[] keyValue =
                    part.split("=", 2);

            if (keyValue.length < 2) {
                continue;
            }

            String key =
                    URLDecoder.decode(
                            keyValue[0],
                            StandardCharsets.UTF_8
                    );

            String value =
                    URLDecoder.decode(
                            keyValue[1],
                            StandardCharsets.UTF_8
                    );

            if (key.equals("id")) {

                id = Integer.parseInt(value);

            } else if (key.equals("userId")) {

                userId = Integer.parseInt(value);

            } else if (key.equals("categoryId")) {

                categoryId = Integer.parseInt(value);

            } else if (key.equals("amount")) {

                amount = Double.parseDouble(value);

            } else if (key.equals("date")) {

                date = value;

            } else if (key.equals("description")) {

                description = value;
            }
        }

        TransactionService transactionService =
                new TransactionService();

        boolean result =
                transactionService.updateIncome(
                        id,
                        userId,
                        categoryId,
                        amount,
                        date,
                        description
                );

        String response;

        if (result) {
            response =
                    "Income updated successfully!";
        } else {
            response =
                    "Failed to update income!";
        }

        sendResponse(
                exchange,
                response
        );
    }


    // =========================================================
    // DELETE EXPENSE
    // =========================================================

    private static void handleDeleteExpense(
            HttpExchange exchange)
            throws IOException {

        InputStream inputStream =
                exchange.getRequestBody();

        String requestBody =
                new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );

        System.out.println(
                "Received delete expense request: "
                        + requestBody
        );

        String[] parts =
                requestBody.split("&");

        int id = 0;
        int userId = 0;

        for (String part : parts) {

            String[] keyValue =
                    part.split("=", 2);

            if (keyValue.length < 2) {
                continue;
            }

            String key =
                    URLDecoder.decode(
                            keyValue[0],
                            StandardCharsets.UTF_8
                    );

            String value =
                    URLDecoder.decode(
                            keyValue[1],
                            StandardCharsets.UTF_8
                    );

            if (key.equals("id")) {

                id =
                        Integer.parseInt(value);

            } else if (key.equals("userId")) {

                userId =
                        Integer.parseInt(value);
            }
        }

        TransactionService transactionService =
                new TransactionService();

        boolean result =
                transactionService.deleteExpense(
                        id,
                        userId
                );

        String response;

        if (result) {

            response =
                    "Expense deleted successfully!";

        } else {

            response =
                    "Failed to delete expense!";
        }

        sendResponse(
                exchange,
                response
        );
    }


    // =========================================================
    // DELETE INCOME
    // =========================================================

    private static void handleDeleteIncome(
            HttpExchange exchange)
            throws IOException {

        InputStream inputStream =
                exchange.getRequestBody();

        String requestBody =
                new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );

        System.out.println(
                "Received delete income request: "
                        + requestBody
        );

        String[] parts =
                requestBody.split("&");

        int id = 0;
        int userId = 0;

        for (String part : parts) {

            String[] keyValue =
                    part.split("=", 2);

            if (keyValue.length < 2) {
                continue;
            }

            String key =
                    URLDecoder.decode(
                            keyValue[0],
                            StandardCharsets.UTF_8
                    );

            String value =
                    URLDecoder.decode(
                            keyValue[1],
                            StandardCharsets.UTF_8
                    );

            if (key.equals("id")) {

                id =
                        Integer.parseInt(value);

            } else if (key.equals("userId")) {

                userId =
                        Integer.parseInt(value);
            }
        }

        TransactionService transactionService =
                new TransactionService();

        boolean result =
                transactionService.deleteIncome(
                        id,
                        userId
                );

        String response;

        if (result) {

            response =
                    "Income deleted successfully!";

        } else {

            response =
                    "Failed to delete income!";
        }

        sendResponse(
                exchange,
                response
        );
    }


    // =========================================================
    // MONTHLY SUMMARY
    // =========================================================

    private static void handleMonthlySummary(
            HttpExchange exchange)
            throws IOException {

        Map<String, String> params =
                getQueryParams(
                        exchange.getRequestURI()
                                .getQuery()
                );

        int userId =
                Integer.parseInt(
                        params.getOrDefault(
                                "userId",
                                "0"
                        )
                );

        int month =
                Integer.parseInt(
                        params.getOrDefault(
                                "month",
                                "0"
                        )
                );

        int year =
                Integer.parseInt(
                        params.getOrDefault(
                                "year",
                                "0"
                        )
                );

        System.out.println(
                "Monthly summary request: "
                        + "userId=" + userId
                        + ", month=" + month
                        + ", year=" + year
        );

        MonthlySummaryService service =
                new MonthlySummaryService();

        MonthlySummary summary =
                service.getMonthlySummary(
                        userId,
                        month,
                        year
                );

        String response =
                summary.getTotalIncome()
                        + "|"
                        + summary.getTotalExpense()
                        + "|"
                        + summary.getSavings()
                        + "|"
                        + summary.getTransactionCount()
                        + "|"
                        + summary.getHighestCategory();

        sendResponse(
                exchange,
                response
        );
    }


    // =========================================================
    // GET QUERY PARAMETERS
    // =========================================================

    private static Map<String, String> getQueryParams(
            String query) {

        Map<String, String> params =
                new HashMap<>();

        if (query == null ||
                query.isEmpty()) {

            return params;
        }

        String[] pairs =
                query.split("&");

        for (String pair : pairs) {

            String[] keyValue =
                    pair.split("=", 2);

            if (keyValue.length == 2) {

                String key =
                        URLDecoder.decode(
                                keyValue[0],
                                StandardCharsets.UTF_8
                        );

                String value =
                        URLDecoder.decode(
                                keyValue[1],
                                StandardCharsets.UTF_8
                        );

                params.put(
                        key,
                        value
                );
            }
        }

        return params;
    }


    // =========================================================
    // SEND RESPONSE
    // =========================================================

    private static void sendResponse(
            HttpExchange exchange,
            String response)
            throws IOException {

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Origin",
                "*"
        );

        exchange.getResponseHeaders().set(
                "Content-Type",
                "text/plain; charset=UTF-8"
        );

        byte[] responseBytes =
                response.getBytes(
                        StandardCharsets.UTF_8
                );

        exchange.sendResponseHeaders(
                200,
                responseBytes.length
        );

        OutputStream outputStream =
                exchange.getResponseBody();

        outputStream.write(
                responseBytes
        );

        outputStream.close();
    }
    private static void handleCategoryBudgets(
            HttpExchange exchange) throws IOException {

        Map<String, String> params =
                getQueryParams(exchange.getRequestURI().getQuery());

        int userId = Integer.parseInt(
                params.getOrDefault("userId", "0")
        );

        CategoryBudgetService service =
                new CategoryBudgetService();

        List<CategoryBudget> budgets =
                service.getCategoryBudgets(userId);

        StringBuilder response =
                new StringBuilder();

        for (CategoryBudget budget : budgets) {

            response.append(
                    budget.getCategory()
            ).append("|");

            response.append(
                    budget.getBudget()
            ).append("|");

            response.append(
                    budget.getSpent()
            ).append("|");

            response.append(
                    budget.getRemaining()
            ).append("\n");
        }

        sendResponse(
                exchange,
                response.toString()
        );
    }
}