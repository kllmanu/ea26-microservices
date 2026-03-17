package com.ecommerce.user.service;

import com.ecommerce.user.domain.User;
import com.ecommerce.user.dto.UserCreateRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.dto.UserUpdateRequestDto;
import com.ecommerce.user.entity.UserEntity;
import com.ecommerce.user.exception.EmailAlreadyExistsException;
import com.ecommerce.user.exception.UserNotFoundException;
import com.ecommerce.user.exception.WeakPasswordException;
import com.ecommerce.user.mapper.*;
import com.ecommerce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserResponseMapper userResponseMapper;

    @Autowired
    private UserCreateRequestMapper userCreateRequestMapper;

    @Autowired
    private UserUpdateRequestMapper userUpdateRequestMapper;

    @Autowired
    private UserEntityMapper userEntityMapper;

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userEntityMapper::toDomain)
                .map(userResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserResponseDto> getUserById(UUID id) {
        return userRepository.findById(id)
                .map(userEntityMapper::toDomain)
                .map(userResponseMapper::toDto);
    }

    public UserResponseDto createUser(UserCreateRequestDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("User with email " + userDto.getEmail() + " already exists");
        }

        validatePasswordStrength(userDto.getPassword());

        User domain = userCreateRequestMapper.toDomain(userDto);
        UserEntity entity = userEntityMapper.toEntity(domain);
        UserEntity savedEntity = userRepository.save(entity);
        
        return userResponseMapper.toDto(userEntityMapper.toDomain(savedEntity));
    }

    public Optional<UserResponseDto> updateUser(UUID id, UserUpdateRequestDto userDto) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        User updateDomain = userUpdateRequestMapper.toDomain(userDto);
        userEntityMapper.updateEntityFromDomain(entity, updateDomain);
        UserEntity savedEntity = userRepository.save(entity);
        
        return Optional.of(userResponseMapper.toDto(userEntityMapper.toDomain(savedEntity)));
    }

    public boolean deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return true;
    }

    private void validatePasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            throw new WeakPasswordException("Password must be at least 8 characters long");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new WeakPasswordException("Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*\\d.*")) {
            throw new WeakPasswordException("Password must contain at least one digit");
        }
    }

    public Optional<UserResponseDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::toDomain)
                .map(userResponseMapper::toDto);
    }
}
