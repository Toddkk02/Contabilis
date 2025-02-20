package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import settings.CustomDateDeserializer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Receipt {
    private String category;
    private double amount;
    private String description;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date date;

    // Default constructor needed for Jackson
    public Receipt() {
    }

    public Receipt(String category, double amount, String description, String date) {
        this.category = category;
        this.amount = amount;
        this.description = description;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            this.date = formatter.parse(date);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
        }
    }

    // Getters and setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("date")
    public Date getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(Date date) {
        this.date = date;
    }

    // Utility method for formatting date - not serialized to JSON
    @JsonIgnore
    public String getFormattedDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(this.date);
    }
}