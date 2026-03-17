package com.ecommerce.user.exception;

import com.ecommerce.user.exception.ResourceNotFoundException;

import java.util.UUID;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(UUID id) {
        super("User with ID " + id + " not found");
    }
}
