package model;

import java.io.Serializable;

public class Report implements Serializable {

    private double totalIncome;
    private double totalExpense;
    private double balance;
    private double averageTransaction;
    private double highestExpense;
    private double lowestExpense;

    public Report() {
    }

    public Report(double totalIncome,
                  double totalExpense,
                  double balance,
                  double averageTransaction,
                  double highestExpense,
                  double lowestExpense) {

        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = balance;
        this.averageTransaction = averageTransaction;
        this.highestExpense = highestExpense;
        this.lowestExpense = lowestExpense;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAverageTransaction() {
        return averageTransaction;
    }

    public void setAverageTransaction(double averageTransaction) {
        this.averageTransaction = averageTransaction;
    }

    public double getHighestExpense() {
        return highestExpense;
    }

    public void setHighestExpense(double highestExpense) {
        this.highestExpense = highestExpense;
    }

    public double getLowestExpense() {
        return lowestExpense;
    }

    public void setLowestExpense(double lowestExpense) {
        this.lowestExpense = lowestExpense;
    }
}