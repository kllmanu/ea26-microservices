package com.ecommerce.user.mapper;

import com.ecommerce.user.domain.User;
import com.ecommerce.user.dto.UserUpdateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class UserUpdateRequestMapper {

    public User toDomain(UserUpdateRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }
}
