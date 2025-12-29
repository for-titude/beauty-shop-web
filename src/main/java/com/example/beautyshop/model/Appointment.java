// Appointment.java
package com.example.beautyshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    private Integer recordId;
    private Integer customerId;
    private Integer serviceId;
    private LocalDate serviceDate;
    private BigDecimal amount;
    // 可选：关联对象
    private String customerName;
    private String serviceName;
    // getters & setters
}