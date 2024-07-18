package com.java.library.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificationDTO {
    private Long id;
    private Long userId;
    private String message;
    private LocalDate sendDate;
}
