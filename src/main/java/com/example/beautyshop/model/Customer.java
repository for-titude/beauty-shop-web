// Customer.java
package com.example.beautyshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Integer customerId;
    private String name;
    private String gender;
    private String phone;
    // getters & setters
}