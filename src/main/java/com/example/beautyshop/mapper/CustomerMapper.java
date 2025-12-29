package com.example.beautyshop.mapper;

import com.example.beautyshop.model.Customer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerMapper {
    List<Customer> findAll();
    void insert(Customer customer);
    void update(Customer customer); // 新增
    void deleteById(Integer customerId); // 新增
}