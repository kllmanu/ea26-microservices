package com.ecommerce.user.mapper;

import com.ecommerce.user.domain.User;
import com.ecommerce.user.domain.UserId;
import com.ecommerce.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return User.builder()
                .id(entity.getId() != null ? UserId.of(entity.getId()) : null)
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setId(domain.getId() != null ? domain.getId().getValue() : null);
        entity.setName(domain.getName());
        entity.setEmail(domain.getEmail());
        entity.setPassword(domain.getPassword());
        return entity;
    }

    public void updateEntityFromDomain(UserEntity entity, User domain) {
        if (domain == null) {
            return;
        }

        if (domain.getName() != null) {
            entity.setName(domain.getName());
        }
        if (domain.getEmail() != null) {
            entity.setEmail(domain.getEmail());
        }
        if (domain.getPassword() != null) {
            entity.setPassword(domain.getPassword());
        }
    }
}
