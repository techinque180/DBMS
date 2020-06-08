package com.example.myproject;

public class BalanceData {
    private String balance_money;
    private int store_flag = 0;

    public String getBalance_money() {
        return balance_money;
    }

    public int getStore_flag() {
        return store_flag;
    }

    public void setBalance_money(String balance_money) {
        this.balance_money = balance_money;
    }

    public void setStore_flag(int store_flag) {
        this.store_flag = store_flag;
    }
}
