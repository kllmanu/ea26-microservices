package com.ecommerce.user.mapper;

import com.ecommerce.user.domain.User;
import com.ecommerce.user.dto.UserCreateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class UserCreateRequestMapper {

    public User toDomain(UserCreateRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }
}
