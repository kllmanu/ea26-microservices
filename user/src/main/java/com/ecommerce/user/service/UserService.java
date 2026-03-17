package com.ecommerce.user.service;

import com.ecommerce.user.dto.UserCreateRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.dto.UserUpdateRequestDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserResponseDto> getAllUsers();
    Optional<UserResponseDto> getUserById(UUID id);
    UserResponseDto createUser(UserCreateRequestDto userDto);
    Optional<UserResponseDto> updateUser(UUID id, UserUpdateRequestDto userDto);
    boolean deleteUser(UUID id);
    Optional<UserResponseDto> getUserByEmail(String email);
}
