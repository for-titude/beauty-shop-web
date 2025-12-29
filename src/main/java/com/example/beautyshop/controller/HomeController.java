// HomeController.java
package com.example.beautyshop.controller;

import com.example.beautyshop.mapper.AppointmentMapper;
import com.example.beautyshop.mapper.BeautyServiceMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final AppointmentMapper appointmentMapper;
    private final BeautyServiceMapper beautyServiceMapper;

    public HomeController(AppointmentMapper appointmentMapper, BeautyServiceMapper beautyServiceMapper) {
        this.appointmentMapper = appointmentMapper;
        this.beautyServiceMapper = beautyServiceMapper;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("appointments", appointmentMapper.findAllWithDetails());
        model.addAttribute("services", beautyServiceMapper.findAll());
        return "index";
    }
}