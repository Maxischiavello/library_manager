package com.java.library.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String role; // No incluye el campo de contraseña por seguridad
}
