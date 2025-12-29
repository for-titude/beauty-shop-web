package com.example.beautyshop.controller;

import com.example.beautyshop.mapper.*;
import com.example.beautyshop.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Controller
public class DataEntryController {

    private final CustomerMapper customerMapper;
    private final BeautyServiceMapper beautyServiceMapper;
    private final AppointmentMapper appointmentMapper;

    public DataEntryController(CustomerMapper customerMapper,
                               BeautyServiceMapper beautyServiceMapper,
                               AppointmentMapper appointmentMapper) {
        this.customerMapper = customerMapper;
        this.beautyServiceMapper = beautyServiceMapper;
        this.appointmentMapper = appointmentMapper;
    }

    // ========== 新增客户 ==========
    @GetMapping("/customers/new")
    public String newCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer-form";
    }

    @PostMapping("/customers")
    public String createCustomer(@ModelAttribute Customer customer) {
        customerMapper.insert(customer);
        return "redirect:/";
    }

    // ========== 新增美容项目 ==========
    @GetMapping("/services/new")
    public String newServiceForm(Model model) {
        model.addAttribute("service", new BeautyService());
        return "service-form";
    }

    @PostMapping("/services")
    public String createService(@ModelAttribute BeautyService service) {
        beautyServiceMapper.insert(service);
        return "redirect:/";
    }

    // ========== 新增美容记录 ==========
    @GetMapping("/appointments/new")
    public String newAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("customers", customerMapper.findAll());
        model.addAttribute("services", beautyServiceMapper.findAll());
        return "appointment-form";
    }

    @PostMapping("/appointments")
    public String createAppointment(@ModelAttribute Appointment appointment) {
        // 自动填充 amount 为对应项目的价格（可选：也可前端传）
        var service = beautyServiceMapper.findAll().stream()
                .filter(s -> s.getServiceId().equals(appointment.getServiceId()))
                .findFirst().orElseThrow();
        appointment.setAmount(service.getPrice());
        appointment.setServiceDate(LocalDate.now()); // 或从前端传入

        appointmentMapper.insert(appointment);
        return "redirect:/";
    }
}