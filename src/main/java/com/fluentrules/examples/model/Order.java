package com.fluentrules.examples.model;

import java.math.BigDecimal;

/**
 * Simple order aggregate used in examples to demonstrate nested validation scenarios.
 *
 * @author Goshgar Mirzayev
 */
public class Order {
    private String id;
    private BigDecimal total;

    public Order() {
    }

    public Order(String id, BigDecimal total) {
        this.id = id;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
