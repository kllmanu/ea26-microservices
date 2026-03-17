package com.ecommerce.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request object for updating user details")
public class UserUpdateRequestDto {
    @Schema(description = "The updated name of the user", example = "John Smith")
    private String name;
    
    @Schema(description = "The updated email address of the user", example = "john.smith@example.com")
    private String email;
}
