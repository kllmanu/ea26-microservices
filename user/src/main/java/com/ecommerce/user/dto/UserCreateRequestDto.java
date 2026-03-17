package com.ecommerce.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request object for creating a new user")
public class UserCreateRequestDto {
    @Schema(description = "The name of the user", example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    
    @Schema(description = "The email address of the user", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    
    @Schema(description = "The password for the user account", example = "SuperSecret123!!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
