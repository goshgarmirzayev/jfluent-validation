package com.fluentrules.examples.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private Integer age;
    private Address address;
    private final List<Order> orders = new ArrayList<>();

    public User() {
    }

    public User(String email, Integer age, Address address) {
        this.email = email;
        this.age = age;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
