package com.bjpowernode.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    //标识当前用户是否已经借了书
    private boolean isLend;

    private String name;

    //状态
    private String status;

    //余额
    private BigDecimal money;

    public User(int id, boolean isLend, String name, String status, BigDecimal money) {
        this.id = id;
        this.isLend = isLend;
        this.name = name;
        this.status = status;
        this.money = money;
    }

    public User() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLend() {
        return isLend;
    }

    public void setLend(boolean lend) {
        isLend = lend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                isLend == user.isLend &&
                Objects.equals(name, user.name) &&
                Objects.equals(status, user.status) &&
                Objects.equals(money, user.money);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, isLend, name, status, money);
    }
}
