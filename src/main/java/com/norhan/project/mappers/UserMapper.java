package com.norhan.project.mappers;

import com.norhan.project.dtos.RegisterUserRequest;
import com.norhan.project.dtos.UpdateUserRequest;
import com.norhan.project.dtos.UserDto;
import com.norhan.project.entities.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void updateUser(UpdateUserRequest request, @MappingTarget User user);
}