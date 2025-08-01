package com.norhan.project.mappers;

import com.norhan.project.dtos.OrderDto;
import com.norhan.project.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}