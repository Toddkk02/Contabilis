package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Target {
    private String date;
    private double financialGoal;

    @JsonCreator
    public Target(
            @JsonProperty("date") String date,
            @JsonProperty("financialGoal") double financialGoal) {
        this.date = date;
        this.financialGoal = financialGoal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getFinancialGoal() {
        return financialGoal;
    }

    public void setFinancialGoal(double financialGoal) {
        this.financialGoal = financialGoal;
    }
}