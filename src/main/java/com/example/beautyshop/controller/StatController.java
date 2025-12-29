// StatController.java
package com.example.beautyshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

@Controller
public class StatController {

    private final DataSource dataSource;

    public StatController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/stats")
    public String stats(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Model model) throws SQLException {

        // 如果未传参，使用当前年月
        LocalDate now = LocalDate.now();
        int y = (year != null) ? year : now.getYear();
        int m = (month != null) ? month : now.getMonthValue();

        BigDecimal income = BigDecimal.ZERO;
        try (Connection conn = dataSource.getConnection();
             CallableStatement cs = conn.prepareCall("{CALL GetMonthlyIncome(?, ?)}")) {
            cs.setInt(1, y);
            cs.setInt(2, m);
            cs.execute();
            try (ResultSet rs = cs.getResultSet()) {
                if (rs.next()) {
                    income = rs.getBigDecimal("total_income");
                }
            }
        }

        model.addAttribute("year", y);
        model.addAttribute("month", m);
        model.addAttribute("income", income != null ? income : BigDecimal.ZERO);
        return "stats";
    }
}