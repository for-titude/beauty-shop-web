package com.example.beautyshop.mapper;

import com.example.beautyshop.model.Customer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerMapper {
    List<Customer> findAll();
    void insert(Customer customer); // 新增
}