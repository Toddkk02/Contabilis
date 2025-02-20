package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import settings.CustomDateDeserializer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Receipt {
    private String category;
    private double amount;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date date;

    // Empty constructor for Jackson
    public Receipt() {}

    // Constructor with Date object
    public Receipt(String category, double amount, String description, Date date) {
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    // Constructor with date string
    public Receipt(String category, double amount, String description, String dateString) {
        this.category = category;
        this.amount = amount;
        this.description = description;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            this.date = formatter.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
            this.date = new Date(); // Use current date as fallback
        }
    }

    // Getters and Setters
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Mark this method to be ignored during JSON serialization/deserialization
    @JsonIgnore
    public String getFormattedDate() {
        if (this.date == null) {
            return "";
        }
        SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy");
        return displayFormat.format(this.date);
    }
}