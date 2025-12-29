// BeautyService.java
package com.example.beautyshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeautyService {
    private Integer serviceId;
    private String serviceName;
    private java.math.BigDecimal price;
    // getters & setters
}