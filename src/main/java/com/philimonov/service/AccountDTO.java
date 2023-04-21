package com.philimonov.service;

public class AccountDTO {
    private int id;
    private String name;
    private long amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
