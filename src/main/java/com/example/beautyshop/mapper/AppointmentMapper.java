package com.example.beautyshop.mapper;

import com.example.beautyshop.model.Appointment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppointmentMapper {
    List<Appointment> findAllWithDetails();
    void insert(Appointment appointment);
}