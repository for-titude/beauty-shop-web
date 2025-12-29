package com.example.beautyshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class StatTypeController {

    private final DataSource dataSource;

    public StatTypeController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // ========== 统计类型选择页 ==========
    @GetMapping("/stats/type")
    public String statTypeSelector() {
        return "stats-type-selector";
    }

    // ========== 月份项目统计 ==========
    @GetMapping("/stats/monthly-service")
    public String monthlyServiceCount(
            @RequestParam(defaultValue = "2025") int year,
            @RequestParam(defaultValue = "12") int month,
            Model model) throws SQLException {

        List<Map<String, Object>> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             CallableStatement cs = conn.prepareCall("{CALL GetMonthlyServiceCount(?, ?)}")) {
            cs.setInt(1, year);
            cs.setInt(2, month);
            cs.execute();
            try (ResultSet rs = cs.getResultSet()) {
                while (rs.next()) {
                    result.add(Map.of(
                            "serviceName", rs.getString("service_name"),
                            "count", rs.getInt("service_count")
                    ));
                }
            }
        }

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("results", result);
        return "stats-monthly-service";
    }

    // ========== 年度客户统计 ==========
    @GetMapping("/stats/yearly-customer")
    public String yearlyCustomerCount(
            @RequestParam(defaultValue = "2025") int year,
            Model model) throws SQLException {

        List<Map<String, Object>> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             CallableStatement cs = conn.prepareCall("{CALL GetYearlyCustomerCount(?)}")) {
            cs.setInt(1, year);
            cs.execute();
            try (ResultSet rs = cs.getResultSet()) {
                while (rs.next()) {
                    result.add(Map.of(
                            "name", rs.getString("name"),
                            "phone", rs.getString("phone"),
                            "count", rs.getInt("visit_count")
                    ));
                }
            }
        }

        model.addAttribute("year", year);
        model.addAttribute("results", result);
        return "stats-yearly-customer";
    }

    // ========== 月份收入统计（复用原有）==========
    @GetMapping("/stats/monthly-income")
    public String monthlyIncome(
            @RequestParam(defaultValue = "2025") int year,
            @RequestParam(defaultValue = "12") int month,
            Model model) throws SQLException {

        BigDecimal income = BigDecimal.ZERO;
        try (Connection conn = dataSource.getConnection();
             CallableStatement cs = conn.prepareCall("{CALL GetMonthlyIncome(?, ?)}")) {
            cs.setInt(1, year);
            cs.setInt(2, month);
            cs.execute();
            try (ResultSet rs = cs.getResultSet()) {
                if (rs.next()) {
                    income = rs.getBigDecimal("total_income");
                }
            }
        }

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("income", income != null ? income : BigDecimal.ZERO);
        return "stats-monthly-income";
    }
}