
package model;

public class Transaction {

    private int id;
    private String type;
    private String category;
    private double amount;
    private String date;
    private String description;


    // Constructor

    public Transaction(
            int id,
            String type,
            String category,
            double amount,
            String date,
            String description) {

        this.id = id;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }


    // Get ID

    public int getId() {

        return id;

    }


    // Get type

    public String getType() {

        return type;

    }


    // Get category

    public String getCategory() {

        return category;

    }


    // Get amount

    public double getAmount() {

        return amount;

    }


    // Get date

    public String getDate() {

        return date;

    }


    // Get description

    public String getDescription() {

        return description;

    }

}

