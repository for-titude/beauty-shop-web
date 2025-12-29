package com.example.beautyshop.controller;

import com.example.beautyshop.mapper.*;
import com.example.beautyshop.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CustomerMapper customerMapper;
    private final BeautyServiceMapper beautyServiceMapper;

    public AdminController(CustomerMapper customerMapper, BeautyServiceMapper beautyServiceMapper) {
        this.customerMapper = customerMapper;
        this.beautyServiceMapper = beautyServiceMapper;
    }

    // ========== 客户管理 ==========

    @GetMapping("/customers")
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerMapper.findAll());
        return "admin/customers";
    }

    @GetMapping("/customers/{id}/edit")
    public String editCustomer(@PathVariable Integer id, Model model) {
        Customer customer = customerMapper.findAll().stream()
                .filter(c -> c.getCustomerId().equals(id))
                .findFirst().orElseThrow();
        model.addAttribute("customer", customer);
        return "admin/customer-edit";
    }

    @PostMapping("/customers/{id}")
    public String updateCustomer(@PathVariable Integer id, @ModelAttribute Customer customer) {
        customer.setCustomerId(id); // 确保 ID 正确
        customerMapper.update(customer);
        return "redirect:/admin/customers";
    }

    @PostMapping("/customers/{id}/delete")
    public String deleteCustomer(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            customerMapper.deleteById(id);
        } catch (Exception e) {
            // 可捕获外键约束异常，提示用户“存在历史记录，无法删除”
//            return "redirect:/admin/customers?error=has_records";
            redirectAttributes.addFlashAttribute("errorMsg", "该客户存在历史记录，无法删除。");
            return "redirect:/admin/customers";
        }
        return "redirect:/admin/customers";
    }

    // ========== 项目管理 ==========

    @GetMapping("/services")
    public String listServices(Model model) {
        model.addAttribute("services", beautyServiceMapper.findAll());
        return "admin/services";
    }

    @GetMapping("/services/{id}/edit")
    public String editService(@PathVariable Integer id, Model model) {
        BeautyService service = beautyServiceMapper.findAll().stream()
                .filter(s -> s.getServiceId().equals(id))
                .findFirst().orElseThrow();
        model.addAttribute("service", service);
        return "admin/service-edit";
    }

    @PostMapping("/services/{id}")
    public String updateService(@PathVariable Integer id, @ModelAttribute BeautyService service) {
        service.setServiceId(id);
        beautyServiceMapper.update(service);
        return "redirect:/admin/services";
    }

    @PostMapping("/services/{id}/delete")
    public String deleteService(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            beautyServiceMapper.deleteById(id);
        } catch (Exception e) {
//            return "redirect:/admin/services?error=has_records";
            redirectAttributes.addFlashAttribute("errorMsg", "该美容项目已被使用，存在历史记录，无法删除。");
            return "redirect:/admin/services";
        }
        return "redirect:/admin/services";
    }
}