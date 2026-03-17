package com.ecommerce.user.mapper;

import com.ecommerce.user.domain.User;
import com.ecommerce.user.dto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {

    public UserResponseDto toDto(User domain) {
        if (domain == null) {
            return null;
        }

        UserResponseDto dto = new UserResponseDto();
        dto.setId(domain.getId() != null ? domain.getId().toString() : null);
        dto.setName(domain.getName());
        dto.setEmail(domain.getEmail());
        return dto;
    }
}
