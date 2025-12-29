// BeautyServiceMapper.java
package com.example.beautyshop.mapper;

import com.example.beautyshop.model.BeautyService;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface BeautyServiceMapper {
    List<BeautyService> findAll();
    void insert(BeautyService service);
}