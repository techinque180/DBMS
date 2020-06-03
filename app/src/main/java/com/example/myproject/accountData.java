package com.example.myproject;

public class accountData {
    private String account_id;
    private String password;
    private String manager_id;
    private String balance_money;

    public String getAccount_id() {
        return account_id;
    }

    public String getPassword() {
        return password;
    }

    public String getManager_id() {
        return manager_id;
    }

    public String getBalance_money() {
        return balance_money;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setManager_id(String manager_id) {
        this.manager_id = manager_id;
    }

    public void setBalance_money(String balance_money) {
        this.balance_money = balance_money;
    }
}
