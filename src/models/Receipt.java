package models;

public class Receipt {
    private String category;
    private double amount;
    private String description;

    // Costruttore vuoto necessario per Jackson
    public Receipt() {}

    public Receipt(String category, double amount, String description) {
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    // Getter e Setter
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}