package com.ecommerce.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response object containing user details")
public class UserResponseDto {
    @Schema(description = "The unique identifier of the user", example = "123e4567-e89b-12d3-a456-426614174000")
    private String id;
    
    @Schema(description = "The name of the user", example = "John Doe")
    private String name;
    
    @Schema(description = "The email address of the user", example = "john.doe@example.com")
    private String email;
}
