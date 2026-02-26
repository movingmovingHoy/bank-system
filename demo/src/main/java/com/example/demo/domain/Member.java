package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String account;
    private long balance;


    protected Member() {}

    public Member(String name) {

        this.name = name;
        this.account = UUID.randomUUID().toString();
        this.balance = 100000;
    }

    public String getAccount() {
        return account;
    }

    public long getBalance() {
        return balance;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void withdraw(int amount) {
        if(balance<amount)
        {
            throw new IllegalStateException("금액이 부족합니다");
        }
        balance-=amount;
    }

    public void deposit(int amount) {
        balance+=amount;
    }
}
